package com.cn.excelimporttest.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ColumnWidth(20)
@EqualsAndHashCode
@HeadStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
public class CashierDetailPay {

    @ExcelProperty("序号")//忽略该字段在导出时的写入
    private Integer serialNumber;

    /**
     * 记账日期;格式：yyyyMMdd
     */

    @ExcelProperty("日期")
    private String businessDate;
    /**
     * 门店编码
     */

    @ExcelProperty("店铺组织编码")
    private String storeCode;

    /**
     * 门店名称
     */
    @ExcelProperty("店铺名称")
    private String storeName;
    /**
     * 中台订单ID
     */
    @ExcelProperty("账单号")
    private String orderId;
    /**
     * 支付方式编码;POS支付方式
     */
    @ExcelProperty("支付类型编码")
    private String payType;
    /**
     * 支付方式名称
     */
    @ExcelProperty("支付类型")
    private String payTypeName;

    /**
     * 支付金额(分)
     */
    @ExcelIgnore
    private Integer money;
    @ExcelProperty("哈哈")
    private String income;

}
