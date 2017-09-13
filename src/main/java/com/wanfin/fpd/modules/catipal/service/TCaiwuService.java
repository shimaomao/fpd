/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.catipal.service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.quartz.ftp.FileUtil;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.utils.sftp.DownLoadFileFromFtp;
import com.wanfin.fpd.modules.catipal.dao.TCaiwuDao;
import com.wanfin.fpd.modules.catipal.entity.TCaiwu;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 小贷财务报表Service
 * @author lxh
 * @version 2016-11-02
 */
@Service("tCaiwuService")
@Transactional(readOnly = true)
public class TCaiwuService extends CrudService<TCaiwuDao, TCaiwu> {

	@Autowired
	TCaiwuDao tCaiwuDao;
	
	private DownLoadFileFromFtp loadFileFromFtp = new DownLoadFileFromFtp();
	
	public TCaiwu get(String id) {
		return super.get(id);
	}
	
	public List<TCaiwu> findList(TCaiwu tCaiwu) {
		tCaiwu.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));	
		return super.findList(tCaiwu);
	}
	
	public List<TCaiwu> findListByScanFlag(TCaiwu tCaiwu) {
		tCaiwu.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));	
		return this.findListByScanFlag(tCaiwu);
	}
	
	public Page<TCaiwu> findPage(Page<TCaiwu> page, TCaiwu tCaiwu) {
		tCaiwu.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));		
		return super.findPage(page, tCaiwu);
	}
	
	@Transactional(readOnly = false)
	public void save(TCaiwu tCaiwu) {
		//super.save(tCaiwu);
		if (StringUtils.isBlank(tCaiwu.getId())){
			tCaiwu.preInsert();
			dao.insert(tCaiwu);
		}else{
			// 更新数据
			tCaiwu.preUpdate();
			dao.update(tCaiwu);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TCaiwu tCaiwu) {
		super.delete(tCaiwu);
	}
	
	@Transactional(readOnly = false)
	public StringBuffer updateGetListByscanFlagData(String fileName, Long informFilingType) {	
		TCaiwu query = new TCaiwu();
		query.setScanFlag("0");
		query.setInformFilingType(informFilingType);
		List<TCaiwu> proceedsList = tCaiwuDao.findListByscanFlag(query);
		List<TCaiwu> successList = new ArrayList<TCaiwu>();
		StringBuffer data = new StringBuffer();
		if (proceedsList != null && proceedsList.size() > 0) {			
			for (TCaiwu temp : proceedsList) {
				data.append(temp.toString());
				data.append("\r\n");
				successList.add(temp);
			}

			for (TCaiwu temp : successList) {
				temp.setScanFlag("1");
				temp.setPushStatus("1");
				temp.preUpdate();
				//this.save(temp);
				tCaiwuDao.updateByscanFlag(temp);
			}

		}		
		return data;
	}
	
	@Transactional(readOnly = false)
	public void updateListByscanFlagData(List list) {
		TCaiwu response = new TCaiwu();
		for (int i = 0; i < list.size(); i++) {				
			String tmp = (String) list.get(i);
			String []msg = tmp.split("\\|");	
			if (msg[0] != null && !msg[0].trim().equals("")) {
				if("A".equals(msg[0])){
					response.setId(msg[2].trim());
					response.setScanFlag("0");	
					response.setPushStatus("0");	
					response.preUpdate();
					tCaiwuDao.updateByscanFlag(response);
				} else {
					response.setId(msg[2].trim());
					response.setScanFlag("0");	
					response.setPushStatus("1");	
					response.preUpdate();
					tCaiwuDao.updateByscanFlag(response);
				}					
			}				
		}				
	}
	
	

	//判断文件类型
    public Workbook createWorkBook(InputStream is,String fileName) throws IOException{
        if(fileName.endsWith("xls")){
            return new HSSFWorkbook(is);
        }else if(fileName.endsWith("xlsx")){
            return new XSSFWorkbook(is);
        }else {return null;}
    }
    
    /** 
     * 把单元格内的类型转换至String类型 
     */  
	private Object ConvertCellStr(Cell cell, Workbook wb) {
		Object cellStr="";
		FormulaEvaluator eval = null;
		if (wb instanceof HSSFWorkbook)
			eval = new HSSFFormulaEvaluator((HSSFWorkbook) wb);
		else if (wb instanceof XSSFWorkbook)
			eval = new XSSFFormulaEvaluator((XSSFWorkbook) wb);
		CellValue cellValue = eval.evaluate(cell);
		if (cellValue == null) {
			return cellStr;
		}
		switch (cellValue.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			cellStr = cellValue.getBooleanValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			// 这里的日期类型会被转换为数字类型，需要判别后区分处理
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				cellStr = cell.getDateCellValue();
			} else {
				cellStr = FileUtil.formatMoney2(cellValue.getNumberValue());
				
			}
			//cellStr = cellValue.getStringValue();
			break;
		case Cell.CELL_TYPE_STRING:
			cellStr = cellValue.getStringValue();
			break;
		case Cell.CELL_TYPE_FORMULA:
			break;
		case Cell.CELL_TYPE_BLANK:
			break;
		case Cell.CELL_TYPE_ERROR:
			break;
		default:
			break;
		}
		return cellStr;
	}
	
	/**
	 * 导入excle
	 * @param file 文件
	 * @return 返回对应的 Collection<T>
	 */
	@Transactional(readOnly = false)
	public void insertExcel(InputStream file, String fileName, int tnum,
			int cnum, TCaiwu tObject) throws Exception {
		Class<TCaiwu> clazz = TCaiwu.class;
		String tempId = null;
		if (!StringUtils.isBlank(tObject.getId())) {
			tempId = new String(tObject.getId());
		}		
		String baoDate = new String(tObject.getBaoDate());
		String setFileName = new String(tObject.getFileName());
		String setFilePath = new String(tObject.getFilePath());
		String setFilingDate = new String(tObject.getFilingDate());
	
		/**
		 * excel的解析开始
		 */
		// 将传入的File构造为FileInputStream;			
		Workbook book = createWorkBook(file, fileName);
		if (book == null) {
			if (file != null) {
				file.close();
				file = null;
			}
			throw new Exception("导入文件格式不对");
		}
		
		//判断有几张活动的sheet表 
		int length = book.getNumberOfSheets();
		for (int k = 0; k < length; k++) {
			
			if (k == 0) {
				tObject.setInformFilingType(new Long("1"));
				tnum = 4;
				cnum  = 6;
			} else if (k == 1) {
				if (tempId == null) {
					tObject = new TCaiwu();
					tObject.setId(null);
				} else {
					TCaiwu queryCaiwu = new TCaiwu();
					queryCaiwu.setBaoDate(baoDate);
					queryCaiwu.setInformFilingType(new Long("2"));
					List<TCaiwu> tempCWList = this.findList(queryCaiwu);
					if (tempCWList != null && tempCWList.size() > 0) {
						tObject = tempCWList.get(0);
					}
				}
				tnum = 5;
				cnum  = 7;
				tObject.setBaoDate(baoDate);
				tObject.setFileName(setFileName);
				tObject.setFilePath(setFilePath);
				tObject.setFilingDate(setFilingDate);	
				tObject.setInformFilingType(new Long("2"));
			} else if (k == 2) {
				if (tempId == null) {
					tObject = new TCaiwu();
					tObject.setId(null);
				} else {
					TCaiwu queryCaiwu = new TCaiwu();
					queryCaiwu.setBaoDate(baoDate);
					queryCaiwu.setInformFilingType(new Long("3"));
					List<TCaiwu> tempCWList = this.findList(queryCaiwu);
					if (tempCWList != null && tempCWList.size() > 0) {
						tObject = tempCWList.get(0);
					}
				}
				tnum = 4;
				cnum  = 6;
				tObject.setBaoDate(baoDate);
				tObject.setFileName(setFileName);
				tObject.setFilePath(setFilePath);
				tObject.setFilingDate(setFilingDate);
				tObject.setInformFilingType(new Long("3"));
			}
			Sheet sheet = book.getSheetAt(k);
			// 得到第一面的所有行
			Iterator<Row> row = sheet.rowIterator();
			/**
			 * 标题解析
			 */

			//定位到标题也就是标题行
			for (int i = 1; i < tnum; i++) {
				row.next();
			}
			/*Row titleRow = row.next();
			// 得到第一行的所有列
			Iterator<Cell> cellTitle = titleRow.cellIterator();
		
			Cell cellCd = (Cell) cellTitle.next();
			String valueCd = cellCd.getStringCellValue();
			Method methodCd = clazz.getMethod("setCd", String.class);
			methodCd.invoke(tObject, valueCd.replaceAll("编制单位：", ""));
			// 循环标题所有的列
			while (cellTitle.hasNext()) {
				Cell cell = (Cell) cellTitle.next();
				Object value = ConvertCellStr(cell, book);
				if(value != null && !value.toString().equals("")) {
					Method method = clazz.getMethod("setCr", String.class);
					method.invoke(tObject, value.toString());
					break;
				}				
			}*/
			
			//定位到内容
			for (int j = 1; j < cnum - tnum; j++) {
				row.next();
			}
			/**
			 * 解析内容行
			 */
			try {
				boolean overFlag = false;
				while (row.hasNext()) {
					Row rown = row.next();
					// 行的所有列
					Iterator<Cell> cellBody = rown.cellIterator();		
				
					while (cellBody.hasNext()) {
						//两列同时赋值
						
						Cell cell = (Cell) cellBody.next();
						if (overFlag) {
							Object value = ConvertCellStr(cell, book).toString().trim();	
							String str = value.toString();
							String strCf = "";
							String strCj = "";
							String strCd = "";
							String strCz = "";
							
							int cfIndex = str.indexOf("：");	
							if (cfIndex > 0) {
								str = str.substring(cfIndex + 1);
								strCf = str.substring(0, str.indexOf("财务主管"));
								
								cfIndex = str.indexOf("：");
								str = str.substring(cfIndex + 1);
								strCj = str.substring(0, str.indexOf("复核"));
								
								cfIndex = str.indexOf("：");
								str = str.substring(cfIndex + 1);
								strCd = str.substring(0, str.indexOf("制表"));
								
								cfIndex = str.indexOf("：");
								str = str.substring(cfIndex + 1);
								strCz = str;
								
								//int cfIndex = .indexOf("");
								System.out.println("strCf:"+strCf);
								System.out.println("strCj:"+strCj);
								System.out.println("strCd:"+strCd);
								System.out.println("strCz:"+strCz);
								//单位负责人：                         财务主管：                          复核：                               制表：
								
								if (value != null && value.toString().contains("单位负责人")) {								
									Method method = clazz.getMethod("setCf", String.class);								
									method.invoke(tObject, strCf.trim());
								}  
								if (value != null && value.toString().contains("财务主管")) {
									Method method = clazz.getMethod("setCj", String.class);
									method.invoke(tObject, strCj.trim());
								} 
								if (value != null && value.toString().contains("复核")) {
									Method method = clazz.getMethod("setCd", String.class);
									method.invoke(tObject, strCd.trim());
								}  
								if (value != null && value.toString().contains("制表：")) {
									Method method = clazz.getMethod("setCz", String.class);
									method.invoke(tObject, strCz.trim());
								}
							}
							
							break;
						}
						cell = (Cell) cellBody.next();
						//获取序列
						Object value = ConvertCellStr(cell, book).toString().trim();						
						System.out.println("rowNum:"+value);
						if (StringUtils.isEmpty(value.toString())) {
							continue;
						}
						
						
						int numRow = (int)Double.parseDouble(value.toString().trim());
						//K == 0 是解析资产负债  1 是解析利润表  2 是解析现    金    流    量    表
						if ((numRow == 30 && k == 0) || (numRow == 46 && k == 1)  || (numRow == 26 && k == 2)) {
							overFlag = true;
							//System.out.println("rowNum break:"+value);
						}
						Method method = clazz.getMethod("setC"+numRow, String.class);
						Method method2 = clazz.getMethod("setC"+numRow+"t", String.class);
						//资产	行次 	年初数	期末数

						// 货币资金	1	587,926.50 	108,438.64 

						//
						cell = (Cell) cellBody.next();
						value = ConvertCellStr(cell, book);
						System.out.println("value:"+value);
						method.invoke(tObject, value.toString());						
						
						cell = (Cell) cellBody.next();
						value = ConvertCellStr(cell, book);
						method2.invoke(tObject, value.toString());
						
						//利润表跳过
						if (k == 1) {
							break;
						}
						
						if (cellBody.hasNext()) {
							//获取序列
							cell = (Cell) cellBody.next();
							cell = (Cell) cellBody.next();
							value = ConvertCellStr(cell, book).toString().trim();
							if (StringUtils.isEmpty(value.toString())) {
								continue;
							}
							numRow = (int)Double.parseDouble(value.toString().trim());
							method = clazz.getMethod("setC"+numRow, String.class);
							method2 = clazz.getMethod("setC"+numRow+"t", String.class);						
							
							cell = (Cell) cellBody.next();
							value = ConvertCellStr(cell, book);
							method.invoke(tObject, value.toString());
							cell = (Cell) cellBody.next();
							value = ConvertCellStr(cell, book);
							method2.invoke(tObject, value.toString());
							
							//if (!cellBody.hasNext()) {							 
								break;
							//}
						} else {
							break;
						}
					}
					
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				//e.printStackTrace();
			}
			
		
			if (StringUtils.isBlank(tObject.getId())) {
				tObject.preInsert();
				tCaiwuDao.insert(tObject);
			} else {
				tObject.preUpdate();
				tCaiwuDao.update(tObject);
			}
		}

		if (file != null) {
			file.close();
			file = null;
		}
	}
	
	
}