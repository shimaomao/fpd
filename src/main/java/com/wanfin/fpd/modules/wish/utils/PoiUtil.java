package com.wanfin.fpd.modules.wish.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import com.wanfin.fpd.modules.wish.order.entity.WishOverdue;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.wanfin.fpd.modules.wish.order.entity.WishOrder;

public class PoiUtil {


    private Sheet sheet;    //表格类实例
    private LinkedList[] result;    //保存每个单元格的数据 ，使用的是一种链表数组的结构

    //读取excel文件，创建表格实例
    public void loadExcel(String filePath) {
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(new File(filePath));
            Workbook workBook = WorkbookFactory.create(inStream);

            sheet = workBook.getSheetAt(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //获取单元格的值
    private String getCellValue(Cell cell) {
        String cellValue = "";
        DataFormatter formatter = new DataFormatter();
        DecimalFormat df = new DecimalFormat("######0.00");
        if (cell != null) {
            //判断单元格数据的类型，不同类型调用不同的方法
            switch (cell.getCellType()) {
                //数值类型
                case Cell.CELL_TYPE_NUMERIC:
                    //进一步判断 ，单元格格式是日期格式
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = formatter.formatCellValue(cell);
                        System.out.println("cellvalue:"+cellValue);
                        Date theDate = cell.getDateCellValue();
                        System.out.println("------------"+theDate);
                        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
                        cellValue = dff.format(theDate);

                        System.out.println("date cellValue" + cellValue);
                    } else {
                        //数值
                        double value = cell.getNumericCellValue();
                        int intValue = (int) value;
                        cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                //判断单元格是公式格式，需要做一种特殊处理来得到相应的值
                case Cell.CELL_TYPE_FORMULA: {

                    try {
                        cellValue = String.valueOf(df.format(cell.getNumericCellValue()));
                    } catch (IllegalStateException e) {
                        cellValue = String.valueOf(cell.getRichStringCellValue());
                    }

                }
                break;
                case Cell.CELL_TYPE_BLANK:
                    cellValue = "";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    cellValue = "";
                    break;
                default:
                    cellValue = cell.toString().trim();
                    break;
            }
        }
        return cellValue.trim();
    }


    //初始化表格中的每一行，并得到每一个单元格的值
    public LinkedList[] init() {
        int rowNum = sheet.getLastRowNum() + 1;
        result = new LinkedList[rowNum];
        for (int i = 0; i < rowNum; i++) {
            Row row = sheet.getRow(i);
            //每有新的一行，创建一个新的LinkedList对象
            result[i] = new LinkedList();
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                //获取单元格的值
                String str = getCellValue(cell);
                //将得到的值放入链表中
                result[i].add(str);
            }
        }
        return result;
    }

    //控制台打印保存的表格数据
    public void show() {
        for (int i = 0; i < result.length; i++) {
            //    	   System.out.println("size:"+result[i].size());
            for (int j = 0; j < result[i].size(); j++) {
                System.out.print(result[i].get(j) + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws ParseException {
//	       PoiUtil poi = new PoiUtil();
////	       poi.loadExcel("C:\\Users\\qiao\\Documents\\20170814_1000017218_init.xlsx");
//		   poi.loadExcel("D:\\20170830_1000042910_20170601_20170829.xlsx");
//		   //
//		   LinkedList[] result = poi.init();
//		   WishOrder wishOrder = null;
//	//       poi.show();
//		   SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
//		   for(int i=1;i<result.length;i++){
//				for(int j=0;j<result[i].size();j++){
//					System.out.print(result[i].get(j) + "\t");
//				}
//			}
        String date = "2017/09/14";
        String da = date.replaceAll("/", "-");
        System.out.println(da);
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
        Date d = dff.parse(da);

        System.out.println("date cellValue" + d);
        WishOrder wishOrder = new WishOrder();
        wishOrder.setOrderDate(d);
        System.out.println("end");
    }

    public static void readTxtFile(String filePath) {
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    System.out.println(lineTxt);
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }

//	public static void main(String args[]){
//		String filePath = "C:\\Users\\qiao\\Documents\\20170814_1000017218_init.xlsx";
//		//     "res/";
//		readTxtFile(filePath);
//	}

}  