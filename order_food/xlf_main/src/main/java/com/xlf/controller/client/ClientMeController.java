package com.xlf.controller.client;


import com.xlf.domain.User;
import com.xlf.exception.BizException;
import com.xlf.service.UserService;
import com.xlf.strategy.context.UploadStrategyContext;
import com.xlf.utils.ResponseResult;
import com.xlf.vo.ClientUpdateUserVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/client/me")
public class ClientMeController {


    @Resource
    UserService userService;

    @Resource
    UploadStrategyContext uploadStrategyContext;

    @PostMapping("/updateUserById")
    public ResponseResult updateUserById(@RequestBody ClientUpdateUserVO userVO){
        if(userVO.getId()==null)
            throw new BizException("更新失败");
        User user = new User();
        user.setId(userVO.getId());
//        HttpRequest httpRequest = HttpRequest.get(userVO.getAvatar());
        user.setAvatar(userVO.getAvatar());
        user.setNickName(user.getNickName());
        user.setPhoneNumber(userVO.getPhoneNumber());
        user.setUpdateTime(new Date());
        boolean b = userService.updateById(user);
        if(b)
             return ResponseResult.success("更新用户信息成功");
        else
            throw new BizException("更新失败");
    }

    /**
     * 能调用到这里表示可以登录
     * @return
     */
    @GetMapping("/isLogin")
    public ResponseResult isLogin(){
        return ResponseResult.success();
    }


}
