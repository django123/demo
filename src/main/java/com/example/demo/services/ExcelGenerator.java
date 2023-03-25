package com.example.demo.services;

import com.example.demo.entities.ImportModel;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;



public class ExcelGenerator {

    private List<ImportModel> importModels;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;



    public ExcelGenerator(List<ImportModel> importModels){
        this.importModels = importModels;
        workbook = new XSSFWorkbook();
    }

    private void createCell(Row row, int columnCount, Object value,CellStyle style){
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof  Integer){
            cell.setCellValue((Long)value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long)value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean)value);
        }
        else if (value instanceof String) {
            cell.setCellValue((String)value);
        }
        else if (value instanceof LocalDateTime) {
            cell.setCellValue((LocalDateTime)value);
        }
        else {
            cell.setCellValue((String) value);

        }
        cell.setCellStyle(style);

    }

    private void writeHeader(){
        sheet = workbook.createSheet("Import records");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 1, "USER DOWNLOAD", style);
        createCell(row, 2, "Date DOWNLOAD", style);
        createCell(row, 3, "Type Import", style);
    }

    private void write(){
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (ImportModel importModel : importModels) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, importModel.getUserDownload(), style);
            createCell(row, columnCount++, importModel.getDateDownload(), style);
            createCell(row, columnCount++, importModel.getTypeImport(), style);
        }
    }

    public void generate(HttpServletResponse response) throws IOException{
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
