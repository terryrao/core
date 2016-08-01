package com.orange.excel;

import com.raowei.exception.NestedRuntimeException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * poi的一些工具类
 *
 * @author terryrao
 */
public class PoiUtils {

    /**
     * 从Cell里面获即一个单元格样式，并保留该单元格原来的样式
     *
     * @param cell
     * @return
     */
    public static CellStyle getCellStyle(Cell cell) {
        Workbook workbook = cell.getRow().getSheet().getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.cloneStyleFrom(cell.getCellStyle()); // 保留原来的格式
        return cellStyle;
    }

    /**
     * 从Sheet 中获取指定行，如果没有就创建，有就直接返回已存在的
     *
     * @param sheet 从中获取行的表
     * @param index 行索引（从0开始）
     * @return Row
     */
    public static Row getRow(Sheet sheet, int index) {
        return sheet.getRow(index) == null ? sheet.createRow(index) : sheet.getRow(index);
    }

    /**
     * 从Row中获取指定单元格，如果没有就创建，有就直接返回已存在的
     *
     * @param row   从中获取单元格的行
     * @param index 列索引（从0开始）
     * @return Cell
     */
    public static Cell getCell(Row row, int index) {
        return row.getCell(index) == null ? row.createCell(index) : row.getCell(index);
    }


    /**
     * 获取Excel workbook
     */
    public static Workbook getWorkbook(InputStream is) {
        try {
            return new HSSFWorkbook(is);
        } catch (IOException e) {
            throw new NestedRuntimeException("创建Excel 文件失败");
        }
    }

    public static Workbook getWorkbook() {
        return new HSSFWorkbook();
    }





    enum Suffix{
        Excel_2003{
            @Override
            public String getSuffix() {
                return ".xls";
            }
        },
        Excel_2007 {
            @Override
            public String getSuffix() {
                return ".xlsx";
            }
        };


        public abstract String getSuffix();
    }
}
