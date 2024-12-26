package com.cn.excelimporttest.controller;

import com.cn.excelimporttest.dto.User;
import com.cn.excelimporttest.uilts.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
    @GetMapping("/testList")
    public void testList() {
        System.out.println("测试");
        List<User> list=new ArrayList<>();
        list.add(new User(1, "测试"));
        list.add(new User(2, "测试2"));
        list.add(new User(3, "测试3"));
        redisUtil.lSet("test:test:2", list);
    }
}
