package com.xlf.controller.client;

import com.xlf.service.LoginService;
import com.xlf.utils.ResponseResult;
import com.xlf.vo.WxLoginVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/client/login")
public class ClientLoginController {

    @Resource
    LoginService loginService;


//    @GetMapping("/{code}")
//    public ResponseResult getPhoneNumber(@PathVariable(value = "code") String code){
//
//        return null;
//    }

    @PostMapping("/wxLogin")
    public ResponseResult wxLogin(@RequestBody WxLoginVO wxLoginVO) {
        return loginService.WxLogin(wxLoginVO);
    }
}
