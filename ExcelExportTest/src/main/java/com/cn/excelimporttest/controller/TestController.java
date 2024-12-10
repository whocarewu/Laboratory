package com.cn.excelimporttest.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.cn.excelimporttest.Handler.ReportMergeHandler;
import com.cn.excelimporttest.dto.CashierDetailPay;
import com.cn.excelimporttest.dto.DemoData;
import com.cn.excelimporttest.dto.Test;
import com.cn.excelimporttest.dto.User;
import com.cn.excelimporttest.mapper.DemoDAO;
import com.cn.excelimporttest.service.DemoService;
import com.cn.excelimporttest.uilts.DemoDataListener;
import com.cn.excelimporttest.uilts.TestDemoDataListener;
import com.cn.excelimporttest.uilts.UserDemoListener;
import com.cn.excelimporttest.uilts.UserDemoListener2;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    @PostMapping(value = "/exportTest")
    public void cashierDetailExcel(HttpServletResponse response) throws IOException {
        List<CashierDetailPay> list = new ArrayList<>();
        // 假设每个订单有2-3种支付方式
        for (int i = 1; i <= 5; i++) {
            String orderId = "OID" + String.format("%05d", i); // 订单ID
            int paymentCount = (i % 3) + 2; // 每笔订单随机分配2-3种支付方式
            int income = 1000 * paymentCount;
            for (int j = 1; j <= paymentCount; j++) {
                CashierDetailPay data = new CashierDetailPay();
                data.setSerialNumber((i - 1) * 3 + j); // 序号
                data.setBusinessDate("2024120" + i); // yyyyMMdd 格式
                data.setStoreCode("SC" + String.format("%03d", i)); // 门店编码
                data.setStoreName("店铺" + i); // 门店名称
                data.setOrderId(orderId); // 相同订单ID
                data.setIncome(String.valueOf(income));
                data.setPayType("PT" + j); // 支付方式编码
                data.setPayTypeName("支付类型" + j); // 支付方式名称
                data.setMoney(1000 * j); // 支付金额（单位：分，忽略导出）
                list.add(data);
            }
        }
        System.out.println(list);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("收银明细报表.xlsx", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        ExcelWriter excelWriter = EasyExcelFactory.write(response.getOutputStream()).build();
        WriteSheet writeSheet1 = EasyExcelFactory.writerSheet(1, "收银明细表-支付").head(CashierDetailPay.class).registerWriteHandler(new ReportMergeHandler(0, new int[]{7})).build();
        excelWriter.write(list, writeSheet1);
        excelWriter.finish();
    }

}
