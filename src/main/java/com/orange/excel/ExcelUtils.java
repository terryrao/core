package com.orange.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * poi的一些工具类
 *
 * @author terryrao
 */
public class ExcelUtils<T> {

    /**
     * 要反射出来的对象的clazz类
     */
    private Class<T> aClass;

    private ExcelUtils(Class<T> tClass) {
        this.aClass = tClass;
    }

    public static <T> ExcelUtils getInstance(Class<T> aClass) {
        return new ExcelUtils(aClass);
    }

    /**
     * 导入excel 文件 转化成相应的List文件
     *
     * @param is 文件的输入流
     */
    public List<T> importExcel(InputStream is) throws IOException {
        Workbook workbook = getWorkBook(is);
        List<Sheet> sheets = getSheets(workbook);
        sheets.stream().flatMap(sheet -> getRows(sheet).stream()).peek(row -> {
            if (isHeaderRow(row)) {

            }
        });
        return null;
    }


    /**
     * 将每一行转成列
     */
    private T mapRowToEnitiy(Row row) {
        return null;
    }

    /**
     * 是不是标题行
     */
    private boolean isHeaderRow(Row row) {
        return row.getRowNum() == row.getSheet().getFirstRowNum();
    }


    /**
     * 从Cell里面获即一个单元格样式，并保留该单元格原来的样式
     */
    public CellStyle getCellStyle(Cell cell) {
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
    public Row getRow(Sheet sheet, int index) {
        return sheet.getRow(index) == null ? sheet.createRow(index) : sheet.getRow(index);
    }

    /**
     * 从Row中获取指定单元格，如果没有就创建，有就直接返回已存在的
     *
     * @param row   从中获取单元格的行
     * @param index 列索引（从0开始）
     * @return Cell
     */
    public Cell getCell(Row row, int index) {
        return row.getCell(index) == null ? row.createCell(index) : row.getCell(index);
    }


    /**
     * 获取所有的工作薄
     */
    public List<Sheet> getSheets(Workbook workbook) {
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
    public List<Row> getRows(Sheet sheet) {
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
    public List<Cell> getCells(Row row) {
        List<Cell> cells = new ArrayList<>();
        int start = row.getFirstCellNum();
        int end = row.getLastCellNum();
        for (int i = start; i <= end; i++) {
            cells.add(getCell(row, i));

        }
        return cells;
    }

    public Workbook getWorkBook(InputStream is) throws IOException {
        return new HSSFWorkbook(is);
    }


    /**
     * 属性对应表
     */
    private class AttributeMapColumn {
        private String name;
        private String column;
        private int order;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }


        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }


        /**
         * 将字母映射成数字序号
         * 将 A,B,C,D .... 转成 1,2,3,4....
         */
        public int getOrderByCol(String column) {
            char[] chars = column.toUpperCase().toCharArray();
            int count = -1;
            //大写的A的ascii码从65开始
            for (int i = 0; i < chars.length; i++) {
                count += (chars[i] -64)*Math.pow(26,i);
            }
            return 0;
        }
    }

}
