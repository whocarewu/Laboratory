package com.cn.excelimporttest.controller;

import com.cn.excelimporttest.uilts.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
@RequiredArgsConstructor
public class TestController {
    private final RedisUtil redisUtil;
    @GetMapping("/test")
    public void test() {
        System.out.println("测试");
        redisUtil.set("test:test:ha", "测试");
    }
}
