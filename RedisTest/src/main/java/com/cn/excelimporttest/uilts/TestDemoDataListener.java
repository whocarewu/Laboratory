package com.cn.excelimporttest.uilts;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import com.cn.excelimporttest.dto.Test;
import com.cn.excelimporttest.dto.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class TestDemoDataListener implements ReadListener<Test> {
    // 存放列数据
    @Getter
    private final List<Test> dataList = new ArrayList<>();
    @Override
    public void invoke(Test data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        dataList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
