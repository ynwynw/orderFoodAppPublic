package com.xlf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xlf.Enum.OrderStatusEnum;
import com.xlf.Enum.PayStatusEnum;
import com.xlf.domain.Order;
import com.xlf.domain.OrderDetails;
import com.xlf.dto.OrderScreenDTO;
import com.xlf.dto.StatusDTO;
import com.xlf.dto.UserDTO;
import com.xlf.exception.BizException;
import com.xlf.mapper.*;
import com.xlf.realTimeOrder.ChatEndpoint;
import com.xlf.service.FoodService;

import com.xlf.service.OrderDetailsService;
import com.xlf.service.OrderService;
import com.xlf.utils.BeanCopyUtils;
import com.xlf.utils.MessageUtils;
import com.xlf.utils.UserHolder;
import com.xlf.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
* @author 小新
* @description 针对表【order】的数据库操作Service实现
* @createDate 2022-10-03 09:27:10
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{


    @Resource
    OrderMapper orderMapper;

    @Resource
    FoodMapper foodMapper;

    @Resource
    FoodService foodService;

    @Resource
    OrderDetailsService orderFoodService;

    @Resource
    ChatEndpoint chatEndpoint;

    @Resource
    SeatMapper seatMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    TagMapper tagMapper;



    @Override
    public Page<OrderVO> getOrderPage(OrderScreenDTO orderScreenDTO) {
        Page<OrderVO> orderPage = new Page<>();
        orderPage.setSize(orderScreenDTO.getPageSize());
        orderPage.setCurrent(orderScreenDTO.getCurrentPage());
        QueryWrapper<OrderVO> wrapper = new QueryWrapper<>();
        wrapper.eq(orderScreenDTO.getOrderStatus()!=null&& !orderScreenDTO.getOrderStatus().equals(""),
                        "o1.status",orderScreenDTO.getOrderStatus())
                .eq(orderScreenDTO.getPayStatus()!=null&& !orderScreenDTO.getPayStatus().equals(""),
                        "o1.pay_status",orderScreenDTO.getPayStatus())
                .eq(orderScreenDTO.getPhoneNumber()!=null&& !orderScreenDTO.getPhoneNumber().equals(""),
                        "u1.phone_number",orderScreenDTO.getPhoneNumber());
        if(orderScreenDTO.getValue()!=null)
             wrapper.between(orderScreenDTO.getValue()!=null&&orderScreenDTO.getValue().size()>=2,
                "o1.created_at",orderScreenDTO.getValue().get(0),orderScreenDTO.getValue().get(1));

        Page<OrderVO> orderPage1 = orderMapper.getOrderPage(orderPage, wrapper);

        return orderPage1;

    }

    @Transactional
    @Override
    public Long addOrder(AddOrderVO addOrderVO) {

        UserDTO user = UserHolder.getUser();
        if(user==null){
            throw new BizException("未登录");
        }
        //商品总数
        Integer num = addOrderVO.getOrderFood().stream().map(OrderFoodVO2::getNumber).reduce(0, (a, b) -> a + b);

        //1.扣库存,计算总价钱
        AtomicReference<Double> totalPrice= new AtomicReference<>(0.0);
        addOrderVO.getOrderFood().forEach(item->{
            //加食物原价格
            totalPrice.updateAndGet(v -> v + getPriceByFoodId(item.getId()) * item.getNumber());

            //加食物标签价格
            if(item.getProps()!=null)
                 totalPrice.updateAndGet(v -> v + getPriceByTagIds(item.getProps())*item.getNumber());

            //扣库存
            if(!foodMapper.updateNum(item.getNumber(),item.getId())){
                throw new BizException("库存不足");
            }
        });

        if(!totalPrice.get().equals(addOrderVO.getTotal()))
            throw new BizException("金额出错");

        //2.生成订单
        Order order = Order.builder()
                .id(IdWorker.getId(Order.class))
                .orderNo(IdWorker.getIdStr())
                .total(totalPrice.get())
                .userId(user.getId())
                .seatId(addOrderVO.getSeatId())
                .status(OrderStatusEnum.Pending.getCode())
                .remarks(addOrderVO.getRemarks())
                .payStatus(PayStatusEnum.ToBePaid.getCode())
                .mobile(addOrderVO.getMobile())
                .num(num)
                .takeFoodCode("1234")
                .payTime(new Date())
                .payName("微信支付")
                .payCode("123456789")
                .build();
        //3.保存订单
       save(order);

       //4.处理订单详情表
        addOrderVO.getOrderFood().forEach(item->{
            OrderDetails orderDetails = OrderDetails.builder()
                    .id(IdWorker.getId(OrderDetails.class))
                    .foodId(item.getId())
                    .orderId(order.getId())
                    .num(item.getNumber())
                    .price(getPriceByFoodId(item.getId())+getPriceByTagIds(item.getProps()))
                    .propertyText(item.getProps_text())
                    .build();
            orderFoodService.save(orderDetails);
        });

        //5.使用websocket推送给后台
        push(order.getId());

        return order.getId();
    }

    private Double getPriceByTagIds(List<Long> props) {
        if(props==null)
            return 0.0;
        return tagMapper.getPriceByTagIds(props);
    }


    @Override
    public List<OrderVO> getRealOrderTimeByStatus(StatusDTO statusDTO) {
        //
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        //建立索引1.（status，pay_status) 2. (pay_status)
        orderQueryWrapper.eq(statusDTO.getOrderStatus()!=null&& !statusDTO.getOrderStatus().equals(""),
                        "status",statusDTO.getOrderStatus())
                        .eq(statusDTO.getPayStatus()!=null&& !statusDTO.getPayStatus().equals(""),
                                "pay_status",statusDTO.getPayStatus());

        List<OrderVO> orders = BeanCopyUtils.copyList(orderMapper.selectList(orderQueryWrapper), OrderVO.class);
        orders.forEach(item->{
            if(item.getSeatId()!=null)
            item.setSeat(seatMapper.selectById(item.getSeatId()));
            if(item.getUserId()!=null)
            item.setUser(userMapper.selectById(item.getUserId()));
            if(item.getHandler()!=null)
            item.setHandler(userMapper.selectById(item.getHandlerId()));

        });
        return orders;
    }

    @Override
    public Boolean updateOrderStatusById(StatusDTO statusDTO) {
        if(statusDTO.getPayStatus()==null||statusDTO.getOrderStatus()==null){
            throw new BizException("订单状态有误");
        }
        if(!PayStatusEnum.getStatus(statusDTO.getPayStatus())||!OrderStatusEnum.getStatus(statusDTO.getOrderStatus())){
            throw new BizException("订单状态有误");
        }
        return update().set("status",statusDTO.getOrderStatus())
                .set("pay_status",statusDTO.getPayStatus())
                .eq("id",statusDTO.getId())
                .update();
    }

    @Override
    public Page<ClientOrderPageVO> getClientOrderPage(QueryPageBean queryPageBean) {

        Page<ClientOrderPageVO> page = new Page<>();
        page.setCurrent(queryPageBean.getCurrentPage());
        page.setSize(queryPageBean.getPageSize());
        Page<ClientOrderPageVO> clientOrderPage = orderMapper.getClientOrderPage(page);
        clientOrderPage.getRecords().forEach(item->{
            item.setGoods(foodMapper.getClientOrderGoodsByOrderId(item.getId()));
        });
        return clientOrderPage;
    }

    @Override
    public ClientOrderDetailVO getClientOrderById(Long id) {
        ClientOrderDetailVO clientOrderDetailVO = BeanCopyUtils.copyObject(orderMapper.selectById(id), ClientOrderDetailVO.class);
        clientOrderDetailVO.setSeatNum(seatMapper.selectById(clientOrderDetailVO.getSeatId()).getNum());
        clientOrderDetailVO.setGoods(foodMapper.getClientOrderGoodsByOrderId(id));
        return clientOrderDetailVO;
    }

    @Override
    public StatusDTO getOrderStatusByOrderId(Long id) {
        return orderMapper.getOrderStatusFoodByOrderId(id);
    }

    /**
     * 计算价格
     * @param id
     * @return
     */
    public Double getPriceByFoodId(Long id) {
        return foodMapper.selectById(id).getPrice();

    }

    public void push(Long id){
      Order order = orderMapper.selectById(id);
      String message = MessageUtils.getMessage(2, UserHolder.getUser().getNickName(), order);
      chatEndpoint.broadcastAllUsers(message);
    }
}





