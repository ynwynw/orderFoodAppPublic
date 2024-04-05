package com.xlf.controller.admin;

import com.xlf.strategy.context.UploadStrategyContext;
import com.xlf.utils.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    UploadStrategyContext uploadStrategyContext;


    /**
     * 上传头像的方法
     * @param file
     * @return
     */
    @PostMapping("/userAvatar")
    public ResponseResult userAvatar(MultipartFile file) {
        String url = uploadStrategyContext.executeUploadStrategy(file,"userAvatar/");
        HashMap<String, String> map = new HashMap<>();
        map.put("url", url);
        return ResponseResult.success("上传成功", map);
    }
    /**
     * 上传食物图片的方法
     * @param file
     * @return
     */
    @PostMapping("/foodPricutre")
    public ResponseResult foodPricutre(MultipartFile file) {
        String url = uploadStrategyContext.executeUploadStrategy(file,"foodPricutre/");
        HashMap<String, String> map = new HashMap<>();
        map.put("url", url);
        return ResponseResult.success("上传成功", map);
    }

}
