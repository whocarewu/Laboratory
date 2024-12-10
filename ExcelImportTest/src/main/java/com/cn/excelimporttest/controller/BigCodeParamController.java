package com.cn.excelimporttest.controller;

import com.alibaba.excel.EasyExcel;
import com.cn.excelimporttest.dto.DemoData;
import com.cn.excelimporttest.dto.StoreCodeDto;
import com.cn.excelimporttest.dto.User;
import com.cn.excelimporttest.uilts.DemoDataListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bigTest")
@Slf4j
@RequiredArgsConstructor
public class BigCodeParamController {
    @PostMapping("/test")
    public void bigParam(@RequestBody StoreCodeDto storeCodeDto)  {
        System.out.println("测试开始");
        System.out.println(storeCodeDto.getStoreCodeList().size());

        System.out.println("测试结束");

    }
}
