package com.meeting.meetresv.utils;

import com.meeting.meetresv.pojo.MrUser;
import com.meeting.meetresv.pojo.MrUserExample;
import com.meeting.meetresv.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadUserUtil {

    private static Logger logger = LoggerFactory.getLogger(ReadUserUtil.class);
//    private static final Logger logger = Logger.getLogger(ReadUserUtil.class);

    static Map<String,String> zhToEn=new HashMap<String,String>();//中英文映射
    static Map<Integer,String> map=new HashMap<Integer,String>();//列，字段名

    static {
        zhToEn.put("姓名","Name");
        zhToEn.put("联系方式","Phone");
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

            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                //防止多余的空行
                if(row==null){
                    continue;
                }
                MrUser userModel=null;
                if(rowNum!=0){
                    userModel=new MrUser();
                }
                for (int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
                    //单元格内容
                    Cell cell=row.getCell(colNum);
                    //防止空指针异常
                    if(cell==null){
                        continue;
                    }
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String content=getCellStringVal(cell).trim();
                    //第一行表头添加映射
                    if(rowNum==0){
                        map.put(colNum,zhToEn.get(content));
                        continue;
                    }
                    if(map.get(colNum)!=null){
                        ReflectUtil.invokeSetM(userModel,"set"+map.get(colNum),String.class,content);
                    }
                }
                if(rowNum!=0){
                    //姓名为空，跳过循环
                    if(StringUtil.isEmpty(userModel.getName())){
                        continue;
                    }
                    if(StringUtil.isEmpty(userModel.getPhone())){
                        userModel.setPassword("123456");//默认密码
                    }else{
                        userModel.setPassword(userModel.getPhone());//密码初始化为手机号
                    }
                    //用户存在则跳过
                    MrUserExample example=new MrUserExample();
                    MrUserExample.Criteria criteria=example.createCriteria();
                    criteria.andNameEqualTo(userModel.getName());
                    criteria.andPasswordEqualTo(EncryptUtil.md5Password(userModel.getPassword()));
                    if(userService.selectByExample(example).size()>0){
                        continue;
                    }
                    userModel.setRole(false);//只支持导入普通用户
                    EncryptUtil.encrypt(userModel);
                    try{
                        userService.insert(userModel);
                    }catch (DuplicateKeyException e){
//                        e.printStackTrace();
                        userModel.setPassword("");//避免打印加密后的密码
                        logger.error("用户名重复，未导入："+userModel.toString());
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
