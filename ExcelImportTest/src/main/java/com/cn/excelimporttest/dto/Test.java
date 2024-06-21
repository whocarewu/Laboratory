package com.cn.excelimporttest.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Test {
    @ExcelProperty("id")
    private Integer id;
    @ExcelProperty("姓名")
    private String userName;
    @ExcelProperty("钱")
    private String pay;
}
