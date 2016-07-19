package com.ava.util.excel;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.web.multipart.MultipartFile;

/**
 * ExcelUtil工具类实现功能: 
 * 导出时传入list<T>,即可实现导出为一个excel,其中每个对象Ｔ为Excel中的一条记录. 
 * 导入时读取excel,得到的结果是一个list<T>.T是自己定义的对象. 
 * 需要导出的实体对象只需简单配置注解就能实现灵活导出,通过注解您可以方便实现下面功能: 
 * 1.实体属性配置了注解就能导出到excel中,每个属性都对应一列. 
 * 2.列名称可以通过注解配置. 
 * 3.导出到哪一列可以通过注解配置. 
 * 4.鼠标移动到该列时提示信息可以通过注解配置. 
 * 5.用注解设置只能下拉选择不能随意填写功能. 
 * 6.用注解设置是否只导出标题而不导出内容,这在导出内容作为模板以供用户填写时比较实用. 
 * @author yyl
 * @version Id: ExcelUtil.java, v 0.1 上午10:50:40 yyl Exp
 */
public class ExcelUtil<T> {
	
	protected final static Logger logger = Logger.getLogger(ExcelUtil.class);
	
	Class<T> clazz;  
	  
    public ExcelUtil(Class<T> clazz) {  
        this.clazz = clazz;  
    }  
    public List<T> importExcel(String sheetName, InputStream input) {  
        int maxCol = 0;  
        List<T> list = new ArrayList<T>();  
        try {  
            Workbook workbook = WorkbookFactory.create(input); 
            Sheet sheet = workbook.getSheet(sheetName);  
            if (!sheetName.trim().equals("")) {  
                sheet = workbook.getSheet(sheetName);// 如果指定sheet名,则取指定sheet中的内容.  
            }  
            if (sheet == null) {  
                sheet = workbook.getSheetAt(0); // 如果传入的sheet名不存在则默认指向第1个sheet.  
            }  
            int rows = sheet.getPhysicalNumberOfRows();  
  
            if (rows > 0) {// 有数据时才处理  
                List<Field> allFields = getMappedFiled(clazz, null);  
  
                Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();// 定义一个map用于存放列的序号和field.  
                for (Field field : allFields) {  
                    // 将有注解的field存放到map中.  
                    if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
                        ExcelVOAttribute attr = field  
                                .getAnnotation(ExcelVOAttribute.class);
                        int col = getExcelCol(attr.column());// 获得列号  
                        maxCol = Math.max(col, maxCol);  
                        // System.out.println(col + "====" + field.getName());  
                        field.setAccessible(true);// 设置类的私有字段属性可访问.  
                        fieldsMap.put(col, field);  
                    }  
                }  
                for (int i = 1; i < rows; i++) {// 从第2行开始取数据,默认第一行是表头.  
                    Row row = sheet.getRow(i);  
                    // int cellNum = row.getPhysicalNumberOfCells();  
                    // int cellNum = row.getLastCellNum();  
                    int cellNum = maxCol;  
                    T entity = null;  
                    for (int j = 0; j <= cellNum; j++) {  
                        Cell cell = row.getCell(j);  
                        if (cell == null) {  
                            continue;  
                        } 
                        Field field = fieldsMap.get(j);// 从map中得到对应列的field.  
                        if (field==null) {  
                            continue;  
                        } 
                        int cellType = cell.getCellType();  
                        String c = "";  
                        if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {  
                        	ExcelVOAttribute attr = field.getAnnotation(ExcelVOAttribute.class);
                        	if ( "Date".equals( attr.fieldType()) && StringUtils.isNotEmpty( attr.dateFormat()) ){
                        		Date tempDate = HSSFDateUtil.getJavaDate( new Double( cell.getNumericCellValue() ));
                        		//TODO c = DateUtils.format(tempDate, attr.dateFormat());
                        	}else{
                        		c = String.valueOf(cell.getNumericCellValue());
                        	}
                        } else if (cellType == HSSFCell.CELL_TYPE_BOOLEAN) {  
                            c = String.valueOf(cell.getBooleanCellValue());  
                        } else {  
                            c = cell.getStringCellValue();  
                        }  
                        if (c == null || c.equals("")) {  
                            continue;  
                        }  
                        entity = (entity == null ? clazz.newInstance() : entity);// 如果不存在实例则新建.  
                         
                        // 取得类型,并根据对象类型设置值.  
                        Class<?> fieldType = field.getType();  
                        if (String.class == fieldType) {  
                            //field.set(entity, String.valueOf(c));
                        	String[] item = c.split("[.]");  
                            if(1<item.length&&"0".equals(item[1])){  
                                c=item[0];  
                            }  
                            this.setFieldValue(entity, field, String.valueOf(c));
                        } else if ((Integer.TYPE == fieldType)  
                                || (Integer.class == fieldType)) {  
                            //field.set(entity, Integer.parseInt(c)); 
                        	//如果是integer  判断是否有.0，如果有去掉
                        	 String[] item = c.split("[.]");  
                             if(1<item.length&&"0".equals(item[1])){  
                                 c=item[0];  
                             }  
                            this.setFieldValue(entity, field, Integer.parseInt(c));
                        } else if ((Long.TYPE == fieldType)  
                                || (Long.class == fieldType)) {  
                            //field.set(entity, Long.valueOf(c));  
                            this.setFieldValue(entity, field, Long.valueOf(c));
                        } else if ((Float.TYPE == fieldType)  
                                || (Float.class == fieldType)) {  
                            //field.set(entity, Float.valueOf(c));  
                            this.setFieldValue(entity, field, Float.valueOf(c));
                        } else if ((Short.TYPE == fieldType)  
                                || (Short.class == fieldType)) {  
                            //field.set(entity, Short.valueOf(c));  
                            this.setFieldValue(entity, field, Short.valueOf(c));
                        } else if ((Double.TYPE == fieldType)  
                                || (Double.class == fieldType)) {  
                            //field.set(entity, Double.valueOf(c));  
                            this.setFieldValue(entity, field, Double.valueOf(c));
                        } else if (Character.TYPE == fieldType) {  
                            if ((c != null) && (c.length() > 0)) {  
                                //field.set(entity, Character.valueOf(c.charAt(0)));  
                                this.setFieldValue(entity, field, Character.valueOf(c.charAt(0)));
                            }  
                        }  
  
                    }  
                    if (entity != null) {  
                        list.add(entity);  
                    }  
                }  
            }  
  
        } catch (Exception e) {  
            logger.error("ERROR::", e);
        } 
        return list;  
    } 
    
    public void exportExcel(List<T> lists[], String sheetNames[], String fileName,  HttpServletResponse response) throws Exception{
    	response.setContentType("application/vnd.ms-excel; charset=utf-8");   
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1")+".xls");
    	this.exportExcel(lists, sheetNames, response.getOutputStream());
    }
    
    public void exportExcel(List<T> list, String sheetName, String fileName, HttpServletResponse response) throws Exception{  
    	response.setContentType("application/vnd.ms-excel; charset=utf-8");   
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1")+".xls");
        this.exportExcel(list, sheetName, response.getOutputStream());
    }
  
    /** 
     * 对list数据源将其里面的数据导入到excel表单 
     *  
     * @param sheetName 
     *            工作表的名称 
     * @param output 
     *            java输出流 
     */  
    public boolean exportExcel(List<T> lists[], String sheetNames[],  
            OutputStream output) {  
        if (lists.length != sheetNames.length) {  
            System.out.println("数组长度不一致");  
            return false;  
        }  
  
        HSSFWorkbook workbook = new HSSFWorkbook();// 产生工作薄对象  
  
        for (int ii = 0; ii < lists.length; ii++) {  
            List<T> list = lists[ii];  
            String sheetName = sheetNames[ii];  
  
            List<Field> fields = getMappedFiled(clazz, null);  
  
            HSSFSheet sheet = workbook.createSheet();// 产生工作表对象  
  
            workbook.setSheetName(ii, sheetName);  
  
            HSSFRow row;  
            HSSFCell cell;// 产生单元格  
            HSSFCellStyle style = workbook.createCellStyle();  
            style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
            style.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);  
            row = sheet.createRow(0);// 产生一行  
            // 写入各个字段的列头名称  
            for (int i = 0; i < fields.size(); i++) {  
                Field field = fields.get(i);  
                ExcelVOAttribute attr = field  
                        .getAnnotation(ExcelVOAttribute.class);  
                int col = getExcelCol(attr.column());// 获得列号  
                cell = row.createCell(col);// 创建列  
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置列中写入内容为String类型  
                cell.setCellValue(attr.name());// 写入列名  
  
                // 如果设置了提示信息则鼠标放上去提示.  
                if (!attr.prompt().trim().equals("")) {  
                    setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, col, col);// 这里默认设了2-101列提示.  
                }  
                // 如果设置了combo属性则本列只能选择不能输入  
                if (attr.combo().length > 0) {  
                    setHSSFValidation(sheet, attr.combo(), 1, 100, col, col);// 这里默认设了2-101列只能选择不能输入.  
                }  
                cell.setCellStyle(style);  
            }  
  
            int startNo = 0;  
            int endNo = list.size();  
            // 写入各条记录,每条记录对应excel表中的一行  
            for (int i = startNo; i < endNo; i++) {  
                row = sheet.createRow(i + 1 - startNo);  
                T vo = (T) list.get(i); // 得到导出对象.  
                for (int j = 0; j < fields.size(); j++) {  
                    Field field = fields.get(j);// 获得field.  
                    field.setAccessible(true);// 设置实体类私有属性可访问  
                    ExcelVOAttribute attr = field  
                            .getAnnotation(ExcelVOAttribute.class);  
                    Class<?> fieldType = field.getType();
                    try {  
                        // 根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.  
                        if (attr.isExport()) {  
                            cell = row.createCell(getExcelCol(attr.column()));// 创建cell  
                            if ((Integer.TYPE == fieldType)  || (Integer.class == fieldType)){
                                 cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                 cell.setCellValue(field.get(vo) == null ? null : (Integer)(field.get(vo)));
                            }else{
                            	cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
                            	Object value = this.getFieldValue(vo, field);
                            	cell.setCellValue(value == null ? ""  
                            			: String.valueOf(value));// 如果数据存在就填入,不存在填入空格.  
                            }
                        }  
                    } catch (Exception e) {  
                    	logger.error("ERROR::", e);
                    } 
                }  
            }  
        }  
  
        try {  
            output.flush();  
            workbook.write(output);  
            output.close();  
            return true;  
        } catch (Exception e) {  
        	logger.error("ERROR::", e);
            return false;  
        }  
  
    }  
    
    private Object getFieldValue(T vo, Field field) throws Exception{
    	String getMethodName = "get"+ StringUtils.substring(field.getName(), 0, 1).toUpperCase()+StringUtils.substring(field.getName(), 1, field.getName().length());
    	Method method = vo.getClass().getMethod(getMethodName, new Class<?>[]{});
        return method.invoke(vo, new Object[]{});
    }
    
    private void setFieldValue(T vo, Field field, Object value) throws Exception{
    	String getMethodName = "set"+ StringUtils.substring(field.getName(), 0, 1).toUpperCase()+StringUtils.substring(field.getName(), 1, field.getName().length());
    	Method method = vo.getClass().getMethod(getMethodName, new Class<?>[]{(Class<?>) field.getGenericType()});
        method.invoke(vo, new Object[]{value});
    }
  
    /** 
     * 对list数据源将其里面的数据导入到excel表单 
     *  
     * @param sheetName 
     *            工作表的名称 
     * @param sheetSize 
     *            每个sheet中数据的行数,此数值必须小于65536 
     * @param output 
     *            java输出流 
     */  
    public boolean exportExcel(List<T> list, String sheetName,  
            OutputStream output) {  
        List<T>[] lists = new ArrayList[1];  
        lists[0] = list;  
  
        String[] sheetNames = new String[1];  
        sheetNames[0] = sheetName;  
  
        return exportExcel(lists, sheetNames, output);  
    }  
  
    /** 
     * 将EXCEL中A,B,C,D,E列映射成0,1,2,3 
     *  
     * @param col 
     */  
    public static int getExcelCol(String col) {  
        col = col.toUpperCase();  
        // 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。  
        int count = -1;  
        char[] cs = col.toCharArray();  
        for (int i = 0; i < cs.length; i++) {  
            count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);  
        }  
        return count;  
    }  
  
    /** 
     * 设置单元格上提示 
     *  
     * @param sheet 
     *            要设置的sheet. 
     * @param promptTitle 
     *            标题 
     * @param promptContent 
     *            内容 
     * @param firstRow 
     *            开始行 
     * @param endRow 
     *            结束行 
     * @param firstCol 
     *            开始列 
     * @param endCol 
     *            结束列 
     * @return 设置好的sheet. 
     */  
    public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle,  
            String promptContent, int firstRow, int endRow, int firstCol,  
            int endCol) {  
        // 构造constraint对象  
        DVConstraint constraint = DVConstraint  
                .createCustomFormulaConstraint("DD1");  
        // 四个参数分别是：起始行、终止行、起始列、终止列  
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,  
                endRow, firstCol, endCol);  
        // 数据有效性对象  
        HSSFDataValidation data_validation_view = new HSSFDataValidation(  
                regions, constraint);  
        data_validation_view.createPromptBox(promptTitle, promptContent);  
        sheet.addValidationData(data_validation_view);  
        return sheet;  
    }  
  
    /** 
     * 设置某些列的值只能输入预制的数据,显示下拉框. 
     *  
     * @param sheet 
     *            要设置的sheet. 
     * @param textlist 
     *            下拉框显示的内容 
     * @param firstRow 
     *            开始行 
     * @param endRow 
     *            结束行 
     * @param firstCol 
     *            开始列 
     * @param endCol 
     *            结束列 
     * @return 设置好的sheet. 
     */  
    public static HSSFSheet setHSSFValidation(HSSFSheet sheet,  
            String[] textlist, int firstRow, int endRow, int firstCol,  
            int endCol) {  
        // 加载下拉列表内容  
        DVConstraint constraint = DVConstraint  
                .createExplicitListConstraint(textlist);  
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列  
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,  
                endRow, firstCol, endCol);  
        // 数据有效性对象  
        HSSFDataValidation data_validation_list = new HSSFDataValidation(  
                regions, constraint);  
        sheet.addValidationData(data_validation_list);  
        return sheet;  
    }  
  
    /** 
     * 得到实体类所有通过注解映射了数据表的字段 
     *  
     * @param map 
     * @return 
     */  
    private List<Field> getMappedFiled(Class clazz, List<Field> fields) {  
        if (fields == null) {  
            fields = new ArrayList<Field>();  
        }  
  
        Field[] allFields = clazz.getDeclaredFields();// 得到所有定义字段  
        // 得到所有field并存放到一个list中.  
        for (Field field : allFields) {  
            if (field.isAnnotationPresent(ExcelVOAttribute.class)) {  
                fields.add(field);  
            }  
        }  
        if (clazz.getSuperclass() != null  
                && !clazz.getSuperclass().equals(Object.class)) {  
            getMappedFiled(clazz.getSuperclass(), fields);  
        }  
  
        return fields;  
    }  
    /**
     * 
     * 获得上传文件的格式
     *
     * @author liutengfei
     * @version 
     * @param file
     * @return
     */
    public String CheckMultipartFile(MultipartFile file){
		// 判断上传的文件格式是否合法 start
		String fileType = "";
		String fileName = file.getOriginalFilename();
		fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		if (!fileType.toLowerCase().equals("xls") && !fileType.toLowerCase().equals("xlsx")) {
			return "文件格式不正确，应该选择excel文件";
		}
		// 判断上传的文件格式是否合法 end
		return fileType.toLowerCase();
    }
}
