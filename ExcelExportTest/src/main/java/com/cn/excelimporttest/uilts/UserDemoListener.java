package com.cn.excelimporttest.uilts;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import com.cn.excelimporttest.dto.User;
import com.cn.excelimporttest.mapper.DemoDAO;
import com.cn.excelimporttest.service.DemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserDemoListener implements ReadListener<User> {
    private List<User> userList=new ArrayList<>();
    @Override
    public void invoke(User data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        userList.add(data);
    }
    private DemoService demoService;
    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param demoDAO
     */
    public UserDemoListener(DemoService demoDAO) {
        this.demoService = demoDAO;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("所有数据解析完成！");
    }
    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", userList.size());
        demoService.saveUser(userList);
        log.info("存储数据库成功！");
    }
}
