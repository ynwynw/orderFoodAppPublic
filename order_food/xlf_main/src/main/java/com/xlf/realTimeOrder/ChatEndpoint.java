package com.xlf.realTimeOrder;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.xlf.service.UserService;
import com.xlf.strategy.context.UploadStrategyContext;

import com.xlf.utils.MessageUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.xlf.constant.WebsocketMessageConstant.*;

@ServerEndpoint(value = "/realOrderTime/{username}", configurator = GetHttpSessionConfigurator.class)
@Service
@Slf4j
public class ChatEndpoint {

    //用map管理endpoint对象 分布式使用redis来保存
    private static Map<String, ChatEndpoint> onlineUsers = new ConcurrentHashMap<>();

    //声明session对象，通过该对象 可以发送消息给指定用户
    private Session session;

    //声明HttpSession,该对象存了用户名,这里没有用到，使用了ThreadLocal解决分布式Session
   // private HttpSession httpSession;

    private String username;

//    @Resource
//    private UserService userServiceAuto;
//
//    private static UserService userService;


//    @Autowired
//    public void setUploadStrategyContext(UploadStrategyContext uploadStrategyContext) {
//        ChatEndpoint.uploadStrategyContext = uploadStrategyContext;
//    }
//
//    private static UploadStrategyContext uploadStrategyContext;
//
//    @PostConstruct
//    public void init() {
//        userService = this.userServiceAuto;
//    }

    /**
     * 连接建立时被调用
     */
    //我连接的时候 要把我的信息发给所有人 告诉别人我来了
    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session, EndpointConfig config) {
        this.session = session;
        log.info("聊天建立：{}", username);
        this.username = username;
        //将当前对象存到容器里
        onlineUsers.put(username, this);

        //将当前在线用户的用户名推送给所以客户端
        //1.获取消息
      //  String message = MessageUtils.getMessage(SYSTEM_BROADCAST, "online", this.username);
        //2.调用方法进行系统消息推送
        // 1：系统消息；2：文本消息；4：发在私聊的图片消息；5：发在群聊的图片消息
        //  类型          在线            名字
        //[{mesType:"1"},{fromName:"online"},{message:""}]
       // broadcastAllUsers(message);
    }
//群发
    public void broadcastAllUsers(String message) {
        try {
            // 将消息推送给所有客户端
            Set<String> username = onlineUsers.keySet();
            for (String name : username) {
                ChatEndpoint chatEndpoint = onlineUsers.get(name);
                chatEndpoint.session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("群聊播送出现异常");
            e.printStackTrace();
        }
    }

    private Set<String> getNames() {
        return onlineUsers.keySet();
    }

    /**
     * 收到客户端发来消息
     *
     * @param message 消息对象
     */
    @OnMessage
    public void onMessage(String message, Session session) {
//        log.info("服务端收到客户端发来的消息: {}", message);
        try {
            ObjectMapper mapper = new ObjectMapper();
            //json转java对象
            Message mess = mapper.readValue(message, Message.class);
            //1：系统消息；2：文本消息；4：发在私聊的图片消息；5：发在群聊的图片消息
            switch (mess.getMesType()) {
                case PRIVATE_IMAGE_MESSAGE:
                case PRIVATE_TEXT_MESSAGE: // 私聊消息
                    // 获取要发送的用户的用户名
                    String toName = mess.getToName();
                    String data = mess.getMessage();
                    // 发送给客户端的message
                    // 类型           谁发的           信息
                    //[{mesType:""},{fromName:""},{message:""}]
                    String messageToSend = MessageUtils.getMessage(mess.getMesType(), this.username, data);
                    //发给这个人信息：[{mesType:""},{fromName:""},{message:""}]
                    onlineUsers.get(toName).session.getBasicRemote().sendText(messageToSend);
                    break;
                case GROUP_IMAGE_MESSAGE:
//                case GROUP_TEXT_MESSAGE: // 群聊消息
//                    // 调用service查出该用户的昵称和头像
//                    User user = userService.selectByUsername(this.username);
//                    GroupChatVO groupChatVO = new GroupChatVO();
//                    BeanUtils.copyProperties(user, groupChatVO);
//                    groupChatVO.setContent(mess.getMessage());
//                    groupChatVO.setType(mess.getMesType());
//                    String groupMess = MessageUtils.getMessage(mess.getMesType(), this.username, groupChatVO);
//                    //调用方法进行群聊消息推送
//                    //发给群发的信息：[{mesType:""},{fromName:""},{message:{groupChatVO对象}}]
//                    broadcastAllUsers(groupMess);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 链接关闭时调用
     *
     * @param session session
     */
    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        log.info("有用户断开了, id为:{}", session.getId());
        //从容器追踪删除指定用户
        onlineUsers.remove(username);
        //1.获取消息
    //    String message = MessageUtils.getMessage(SYSTEM_BROADCAST, "offline", username);
        //2.调用方法进行系统消息推送
        //[{mesType:"1"},{fromName:"offline"},{message:""}]
     //   broadcastAllUsers(message);
    }

    /**
     * 发生错误
     *
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }



}
