package com.xlf;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.xlf.domain.*;
import com.xlf.dto.UserMenuDTO;
import com.xlf.generateDTO.ClientMenuVO;
import com.xlf.mapper.UserRoleMapper;
import com.xlf.service.*;
import com.xlf.utils.GenerateObject;
import com.xlf.utils.TreeUtils;
import com.xlf.vo.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminApplicationTests {


    @Resource
    UserService userService;

    @Resource
    UserRoleMapper userRoleMapper;

    @Autowired
    FoodService foodService;

    @Resource
    OrderService orderService;

    @Resource
    TypeService typeService;

    @Resource
    RedisTemplate redisTemplate;
    @Test
    void Test(){
//        UserRoleVO userRoleVO = new UserRoleVO();
//        userRoleVO.setId(1L);
//        ArrayList<Long> objects = new ArrayList<>();
//        objects.add(2L);
//        userRoleVO.setRoleIdList(objects);
//        userService.updateUserRole(userRoleVO);
//        UserRole userRole = new UserRole();
//        userRole.setRoleId(3L);
//        userRole.setUserId(1L);
//        userRoleMapper.insert(userRole);
        Order order = new Order();
    //    order.setId(2L);
        orderService.save(order);

        QueryPageBean queryPageBean = new QueryPageBean();
        queryPageBean.setCurrentPage(1);
        queryPageBean.setPageSize(5);
//
//        redisTemplate.opsForValue().set("a");
////        foodService.getFoodPage(queryPageBean);
//       queryPageBean.setTypeId(5);
//        System.out.println(foodService.getFoodPage(queryPageBean).getRecords());
//        System.out.println(foodService.getFoodById(1L));
//        List<FoodMinVO> foodMinList = foodService.getFoodMinList();
//        System.out.println(foodMinList);

        //     System.out.println(orderService.getOrderPage(queryPageBean).getRecords());



    }

    @Test
    public void test2(){
        //造订单
        int j=1;
        QueryPageBean queryPageBean = new QueryPageBean();
        queryPageBean.setCurrentPage(1);
        queryPageBean.setPageSize(5);
        List<OrderFoodVO2> orderFoodVO2s= new ArrayList<OrderFoodVO2>();
        OrderFoodVO2 orderFoodVO2 = new OrderFoodVO2();
        orderFoodVO2.setId(1L);
        orderFoodVO2.setNumber(2);
        ArrayList<Long> longs = new ArrayList<>();
        longs.add(2L);
        longs.add(3L);
        orderFoodVO2.setChildrenId(longs);
        orderFoodVO2s.add(orderFoodVO2);

    }

    /**
     * 获取前台列表
     */
    @Test
    public void test3(){
        System.out.println(typeService.getFoodsList());

    }

    @Resource
    MenuService menuService;

    @Resource
    UserRoleService userRoleService;

    @Resource
    RoleMenuService roleMenuService;
    /**
     * 获取树形结构
     */
    @Test
    public void TreeTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        Long id =1L;
        //通过用户id拿角色id
        List<Long> roleId = userRoleService.list(new QueryWrapper<UserRole>()
                        .eq("user_id", id)
                        .select("role_id"))
                .stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        //通过角色id拿菜单id
        List<Long> menuId = roleMenuService.list(new QueryWrapper<RoleMenu>()
                        .in("role_id", roleId)
                        .select("menu_id"))
                .stream().map(item -> item.getMenuId()).collect(Collectors.toList());

        //通过菜单id拿菜单
        List<UserMenuDTO> menuList = menuService.list(new QueryWrapper<Menu>()
                .in("id", menuId)
                .orderByAsc("order_num")
                .eq("status", 0))
                .stream().map(menu -> UserMenuDTO.builder()
                        .id(menu.getId())
                        .name(menu.getMenuName())
                        .routerName(menu.getRouterName())
                        .path(menu.getPath())
                        .component(menu.getComponent())
                        .icon(menu.getIcon())
                        .isHidden(menu.getVisible())
                        .parentId(menu.getParentId())
                        .type(menu.getMenuType())
                        .build()).collect(Collectors.toList());
        List<UserMenuDTO> userMenuDTOS = TreeUtils.buildTree(menuList, UserMenuDTO::getId, UserMenuDTO::getParentId, 0L, (userMenuDTO, userMenuDTOS1) -> userMenuDTO.setChildren(userMenuDTOS1));
        System.out.println(userMenuDTOS);

    }



    @Test
    public void FileTest(){
//        List<User> list = JSON.parseObject("[1,2,3]", List.class);
//        System.out.println(list);
        Tag tag = Tag.builder().id(100L).name("1111").parentId(0L).build();
        tagService.save(tag);
    }


    @Autowired
    TagService tagService;

    @Autowired
    FoodTagService foodTagService;




    @Test
    public void generateFood(){
        List<com.xlf.generateDTO.ClientMenuVO> list = GenerateObject.readJSONToObjectList("D:\\mytemp\\text.txt", ClientMenuVO.class);

        AtomicReference<Long> tagSort = new AtomicReference<>(10L);


        /**
         * 遍历拿到的所有分类
         */
        list.forEach(item->{
            Type type = typeService.getById(item.getId());

            //数据库没有就插入
            if(type==null){

                 type = Type.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .sort(item.getSort())
                        .status(0)
                        .icon(item.getIcon()).build();
//                boolean save = typeService.save(type);
                if(!typeService.save(type)){
                    System.out.println("分类保存失败"+item.getId());
                }
            }
            System.out.println(type.getName());
            //遍历标签下的所有食物
            if(item.getGoods_list()!=null){
                Type finalType = type;
                item.getGoods_list().forEach(food->{

                    Food food1 = foodService.getById(food.getId());

                    if(food1==null){
                        food1 = Food.builder().id(food.getId())
                                .name(food.getName())
                                .price(food.getPrice())
                                .pricutre(food.getImages())
                                .remarks(food.getContent())
                                .minBuyNum(food.getMinBuyNum())
                                .packagingFee(food.getPack_cost())
                                .sort(food.getSort())
                                .unit(food.getUnit())
                                .useProperty(food.getUse_property())
                                .typeId(finalType.getId())
                                .status(0)
                                .inventory(999)
                                .build();
//                        boolean save = foodService.save(food1);
                        if(!foodService.save(food1)){
                            System.out.println("食物保存失败"+food.getId());
                        }
                    }

                    //遍历食物的标签
                    if(food.getProperty()!=null) {
                        Food finalFood = food1;
                        food.getProperty().forEach(tag->{


                            Tag tag1 = tagService.getOne(new QueryWrapper<Tag>().eq("name", tag.getName()));

                            //如果数据库找不到
                            if(tag1==null){
                                tag1 = Tag.builder()
                                        .name(tag.getName())
                                        .parentId(0L)
                                        .isDefault(false)
                                        .price(0.0)
                                        .sort(tagSort.getAndSet(tagSort.get() + 1))
                                        .status(0)
                                        .build();

                                if(!tagService.save(tag1)){
                                    System.out.println("标签保存失败"+tag.getName());
                                }
                            }

                            FoodTag foodTag = new FoodTag();
                            foodTag.setFoodId(finalFood.getId());
                            foodTag.setTagId(tag1.getId());
                            foodTag.setIsDefault(0);
                            foodTag.setDesc(tag.getDesc());
                            foodTag.setIs_open_checkbox(tag.getIs_open_checkbox());
                            foodTagService.saveOrUpdateByMultiId(foodTag);


                            //遍历标签的孩子
                            if(tag.getValues()!=null) {
                                Tag finalTag = tag1;
                                tag.getValues().forEach(tagSon->{

                                    //如果存在且这个b是上面那个的孩子。 如果存在然后不是上面的孩子也表示不存在
                                    Tag tag2 = tagService.getOne(new QueryWrapper<Tag>().eq("name", tagSon.getValue()).eq("parent_id",finalTag.getId()));

                                    //如果数据库找不到
                                    if(tag2==null){
                                        tag2 = Tag.builder()
                                                .name(tagSon.getValue())
                                                .parentId(finalTag.getId())
                                                .isDefault(false)
                                                .price(0.0)
                                                .sort(tagSort.getAndSet(tagSort.get() + 1))
                                                .status(0)
                                                .build();
                                        if(!tagService.save(tag2)){
                                            System.out.println("孩子标签保存失败"+tagSon.getValue());
                                        }
                                    }

                                    FoodTag foodTag2 = new FoodTag();
                                    foodTag2.setFoodId(finalFood.getId());
                                    foodTag2.setTagId(tag2.getId());
                                    foodTag2.setIsDefault(tagSon.getIs_default());
                                    foodTagService.saveOrUpdateByMultiId(foodTag2);

                                });
                            }
                        });
                    }
                });
            }

        });
    }

}
