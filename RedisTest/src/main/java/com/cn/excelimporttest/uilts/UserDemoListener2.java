package com.cn.excelimporttest.uilts;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import com.cn.excelimporttest.dto.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class UserDemoListener2 implements ReadListener<User>  {
    // 存放列数据
    @Getter
    private final List<User> dataList = new ArrayList<>();


    @Override
    public void invoke(User data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        dataList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 上面把每一行的数据读取完之后，就会进入到这个方法，一般是处理dataList的数据
    }
}
