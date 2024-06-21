package com.cn.excelimporttest.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;
import com.cn.excelimporttest.dto.DemoData;
import com.cn.excelimporttest.dto.Test;
import com.cn.excelimporttest.dto.User;
import com.cn.excelimporttest.mapper.DemoDAO;
import com.cn.excelimporttest.service.DemoService;
import com.cn.excelimporttest.uilts.DemoDataListener;
import com.cn.excelimporttest.uilts.TestDemoDataListener;
import com.cn.excelimporttest.uilts.UserDemoListener;
import com.cn.excelimporttest.uilts.UserDemoListener2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final DemoService demoService;
    @GetMapping("/hello")
    public String test() {
        return "嗨喽";
    }

    //导入  1.0
    @PostMapping("/importExcel")
    public void importExcel(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return;
        }
        List<User> users = new ArrayList<>();
        // 第一种 使用 EasyExcel 自带的 PageReadListener，可以将数据读取后拿到读取的所有数据
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
        // 具体需要返回多少行可以在`PageReadListener`的构造函数设置

        try {
            EasyExcel.read(multipartFile.getInputStream(), User.class,
                            new PageReadListener<User>(dateList -> {
                                log.info(JSON.toJSONString(dateList));
                                users.addAll(dateList);
                            }))
                    .sheet()
                    .doRead();
            System.out.println(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //导入  2.0
    //导入  1.0
    @PostMapping("/importExcel2")
    public void importExcel2(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return;
        }
        List<User> users = new ArrayList<>();
        // 第一种 使用 EasyExcel 自带的 PageReadListener，可以将数据读取后拿到读取的所有数据
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
        // 具体需要返回多少行可以在`PageReadListener`的构造函数设置

        try {
            // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
            EasyExcel.read(multipartFile.getInputStream(), DemoData.class, new DemoDataListener()).sheet().doRead();
            System.out.println(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/importExcel3")
    public void importExcel3(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return;
        }
        List<User> users = new ArrayList<>();
        // 第一种 使用 EasyExcel 自带的 PageReadListener，可以将数据读取后拿到读取的所有数据
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
        // 具体需要返回多少行可以在`PageReadListener`的构造函数设置

        try {
            // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
            EasyExcel.read(multipartFile.getInputStream(), User.class, new UserDemoListener(demoService)).sheet().doRead();
            System.out.println(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取多个sheet
     * @param multipartFile
     * @throws IOException
     */
    @PostMapping("/importExcel4")
    public void importExcel4(MultipartFile multipartFile) throws IOException {
        ExcelReader excelReader;
        excelReader = EasyExcel.read(multipartFile.getInputStream()).build();
        if (multipartFile.isEmpty()) {
            return;
        }
        // 读取Sheet页数据，如果是多个sheet页面需要多个Listener，比如readSheet1、readSheet2
        UserDemoListener2 demoListener = new UserDemoListener2();
        ReadSheet readSheet = EasyExcel
                .readSheet(0)
                .head(User.class)
                .registerReadListener(demoListener)
                .headRowNumber(6)
                .build();
        TestDemoDataListener demoDataListener=new TestDemoDataListener();
        ReadSheet readSheet1 = EasyExcel
                .readSheet(1)
                .head(Test.class)
                .registerReadListener(demoDataListener)
                .build();
        excelReader.read(readSheet,readSheet1);
        // 获取数据
        List<User> dataList = demoListener.getDataList();
        List<Test> testData = demoDataListener.getDataList();
        System.out.println("=======获取到数据");
        System.out.println(dataList);
        System.out.println("=======获取到数据");
        System.out.println("=======获取到数据");
        System.out.println(testData);
        System.out.println("=======获取到数据");

    }
}
