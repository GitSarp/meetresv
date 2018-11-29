package com.meeting.meetresv.utils;

import com.meeting.meetresv.pojo.MrUser;
import com.meeting.meetresv.service.UserService;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadUserUtil {

    private static final Logger logger = Logger.getLogger(ReadUserUtil.class);

    static Map<String,String> zhToEn=new HashMap<String,String>();//中英文映射
    static Map<Integer,String> map=new HashMap<Integer,String>();//列，字段名

    static {
        zhToEn.put("姓名","Name");
        zhToEn.put("手机号码","Phone");
        zhToEn.put("部门","Department");
    }

    //读取excel
    private static Workbook getReadWorkBookType(String filePath)throws IOException{
        //xls-2003, xlsx-2007
        FileInputStream is = null;

        try {
            is = new FileInputStream(filePath);
            if (filePath.toLowerCase().endsWith("xlsx")) {
                return new XSSFWorkbook(is);
            } else if (filePath.toLowerCase().endsWith("xls")) {
                return new HSSFWorkbook(is);
            }
        } finally {
            is.close();
        }
        return null;
    }

    private static String getCellStringVal(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        switch (cellType) {
            case NUMERIC:
                return cell.getStringCellValue();
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            case ERROR:
                return String.valueOf(cell.getErrorCellValue());
            default:
                return "";
        }
    }

    public static void readExcel(String sourceFilePath,UserService userService)throws IOException{// throws BusinessException {
        Workbook workbook = null;
        try {
            workbook = getReadWorkBookType(sourceFilePath);
            if(workbook==null){
                throw new IOException("");
            }

            //获取第一个sheet
            Sheet sheet = workbook.getSheetAt(0);

            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                MrUser userModel=null;
                if(rowNum!=0){
                    userModel=new MrUser();
                }
                for (int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
                    //单元格内容
                    Cell cell=row.getCell(colNum);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String content=getCellStringVal(cell).trim();
                    //第一行表头添加映射
                    if(rowNum==0){
                        map.put(colNum,zhToEn.get(content));
                        continue;
                    }
                    ReflectUtil.invokeSetM(userModel,"set"+map.get(colNum),String.class,content);
                }
                if(rowNum!=0){
                    userModel.setPassword(userModel.getPhone());//密码初始化为手机号或固定值
                    EncryptUtil.encrypt(userModel);
                    try{
                        userService.insert(userModel);
                    }catch (DuplicateKeyException e){
//                        e.printStackTrace();
                        userModel.setPassword("");//避免打印加密后的密码
                        logger.error("导入错误："+userModel.toString());
                        continue;
                    }
                }
            }
        } finally {
            workbook.close();
            //IOUtils.closeQuietly(workbook);
        }
    }

}
