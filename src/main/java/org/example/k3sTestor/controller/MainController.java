package org.example.k3sTestor.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/main")
@Api("k3s 测试接口")
public class MainController {
    private static double id = java.lang.Math.random();
    private static boolean raiseError = false;
    private static int delaySeconds = 0;

    @GetMapping("/getId")
    @ApiOperation("获取当前服务实例id")
    public String getId() throws Exception {
        if (raiseError) {
            throw new Exception("error set!" + id);
        } else if (delaySeconds > 0) {
            Thread.currentThread().sleep(delaySeconds * 1000);
            return String.valueOf(id) + " delayed " + delaySeconds;
        }
        return String.valueOf(id);
    }

    @GetMapping("/setError")
    @ApiOperation("设置为出错模式，调用此方法后，所有的getId请求将返回500服务器错误")
    public String setError() {
        raiseError = true;
        return "done!" + id;
    }

    @GetMapping("/clearError")
    @ApiOperation("清除出错模式")
    public String clearError() {
        raiseError = false;
        return "done" + id;
    }

    @GetMapping("/setDelay")
    @ApiOperation("设置为延迟模式，调用后，getId方法将会延迟seconds秒响应")
    public String setDelay(int seconds) {
        delaySeconds = seconds;
        return "done" + id + " delay " + seconds;
    }

    @GetMapping("/clearDelay")
    @ApiOperation("清除延迟模式")
    public String clearDelay() {
        delaySeconds = 0;
        return "done" + id;
    }
}
