package com.orange.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * poi的一些工具类
 *
 * @author terryrao
 */
public class ExcelUtils {


    /**
     * 导入excel 文件 转化成相应的List文件
     *
     * @param is   文件的输入流
     * @param clss 要反射出来的对象的clazz类
     */
    public static <T> List<T> importExcel(InputStream is, Class<T> clss) throws IOException {
        Workbook workbook = getWorkBook(is);
        List<Sheet> sheets = getSheets(workbook);
        sheets.stream().flatMap(sheet -> getRows(sheet).stream()).peek(row -> {
            if (isHeaderRow(row)) {

            }
        })
        return null;
    }


    /**
     * 将每一行转成列
     */
    private static <T> T mapRowToEnitiy(Row row) {

    }

    /**
     * 是不是标题行
     */
    private static boolean isHeaderRow(Row row) {
        return row.getRowNum() == row.getSheet().getFirstRowNum();
    }


    /**
     * 从Cell里面获即一个单元格样式，并保留该单元格原来的样式
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
     * 获取所有的工作薄
     */
    public static List<Sheet> getSheets(Workbook workbook) {
        List<Sheet> list = new ArrayList<>();
        int length = workbook.getNumberOfSheets();
        for (int i = 0; i < length; i++) {
            list.add(workbook.getSheetAt(i));
        }
        return list;
    }

    /**
     * 获取所有的行
     */
    public static List<Row> getRows(Sheet sheet) {
        List<Row> rows = new ArrayList<>();
        int startRowIndex = sheet.getFirstRowNum();
        int endRowIndex = sheet.getLastRowNum();
        for (int i = startRowIndex; i <= endRowIndex; i++) {
            rows.add(getRow(sheet, i));
        }
        return rows;
    }


    /**
     * 获取所有的单元格
     */
    public static List<Cell> getCells(Row row) {
        List<Cell> cells = new ArrayList<>();
        int start = row.getFirstCellNum();
        int end = row.getLastCellNum();
        for (int i = start; i <= end; i++) {
            cells.add(getCell(row, i));

        }
        return cells;
    }

    public static Workbook getWorkBook(InputStream is) throws IOException {
        return new HSSFWorkbook(is);
    }


}
