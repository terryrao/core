package com.orange.excel.enums;

/**
 * @author terryrao on 2016/8/2.
 */
public enum ExcelSuffix{
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