package com.xlf.controller.admin;

import com.wf.captcha.SpecCaptcha;
import com.xlf.constant.MessageConstant;
import com.xlf.domain.User;
import com.xlf.service.LoginService;
import com.xlf.service.UserService;
import com.xlf.utils.RedisCache;
import com.xlf.utils.ResponseResult;
import com.xlf.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.xlf.constant.RedisConst.USER_CODE_KEY;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    LoginService loginService;

    @Autowired
    RedisCache redisCache;

    @Resource
    UserService userService;

    /**
     * 获取验证码
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/captcha")
    public ResponseResult captcha(HttpServletRequest request, HttpServletResponse response) {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为10分钟
        redisCache.setCacheObject(USER_CODE_KEY + key, verCode, 600, TimeUnit.DAYS);
        request.getSession().setAttribute("CAPTCHA", verCode);  //存入session
        HashMap<String, Object> code = new HashMap<>();
        code.put("verKey", key);
        code.put("verCode", specCaptcha.toBase64());
        // 将key和base64返回给前端
        return ResponseResult.success(MessageConstant.VERIFICATION_CODE_SUCCESS,code);
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserVO user,HttpServletRequest request){
        return loginService.login(user,request);
    }

    /**
     * 注销
     * @return
     */
    @GetMapping("/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }

    /**
     *注册
     * @param user
     * @return
     */
    @PostMapping("/add")
    public ResponseResult register(@RequestBody User user) {
        userService.add(user);
        return ResponseResult.success("注册成功");

    }

}
