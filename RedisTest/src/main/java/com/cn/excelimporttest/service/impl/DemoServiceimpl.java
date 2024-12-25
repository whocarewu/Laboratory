package com.cn.excelimporttest.service.impl;

import com.cn.excelimporttest.dto.User;
import com.cn.excelimporttest.service.DemoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoServiceimpl implements DemoService {
    @Override
    public void saveUser(List<User> userList) {
        System.out.println(123);
        System.out.println(userList);
    }
}
