package com.cn.excelimporttest.Handler;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

public class ReportMergeHandler implements RowWriteHandler {
    //  需要合并的列索引
    private int[] mergeColumnIndex;
    //  从那行开始合并
    private int mergeRowIndex;

    public ReportMergeHandler() {
    }

    public ReportMergeHandler(int mergeRowIndex, int[] mergeColumnIndex) {
        this.mergeRowIndex = mergeRowIndex;
        this.mergeColumnIndex = mergeColumnIndex;
    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            return;
        }
        int curRowIndex = row.getRowNum();
        if (curRowIndex > mergeRowIndex) {
            for (int columnIndex : mergeColumnIndex) {
                mergeWithPrevRow(writeSheetHolder, row, curRowIndex, columnIndex);
            }
        }
    }

    private void mergeWithPrevRow(WriteSheetHolder writeSheetHolder, Row row, int curRowIndex, int columnIndex) {
        Row preRow = writeSheetHolder.getCachedSheet().getRow(curRowIndex - 1);
        //  注意这里是自己的合并规则：以项目编号及前向合同编号为合并标识
        String currUniqueMerge = row.getCell(4).getStringCellValue();
        String preUniqueMerge = preRow.getCell(4).getStringCellValue();
        boolean dataBool = StringUtils.isNotEmpty(currUniqueMerge) && StringUtils.isNotEmpty(preUniqueMerge) && currUniqueMerge.equals(preUniqueMerge);
        if (dataBool) {
            Sheet sheet = writeSheetHolder.getSheet();
            List<CellRangeAddress> mergeRegions = sheet.getMergedRegions();
            boolean isMerged = false;
            for (int i = 0; i < mergeRegions.size() && !isMerged; i++) {
                CellRangeAddress cellRangeAddr = mergeRegions.get(i);
                // 若上一个单元格已经被合并，则先移出原有的合并单元，再重新添加合并单元
                if (cellRangeAddr.isInRange(curRowIndex - 1, columnIndex)) {
                    sheet.removeMergedRegion(i);
                    cellRangeAddr.setLastRow(curRowIndex);
                    sheet.addMergedRegion(cellRangeAddr);
                    isMerged = true;
                }
            }
            // 若上一个单元格未被合并，则新增合并单元
            if (!isMerged) {
                CellRangeAddress cellRangeAddress = new CellRangeAddress(curRowIndex - 1, curRowIndex, columnIndex, columnIndex);
                sheet.addMergedRegion(cellRangeAddress);
            }
        }
    }
}
