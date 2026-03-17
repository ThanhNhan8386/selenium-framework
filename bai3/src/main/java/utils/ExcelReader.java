package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {
    private Workbook workbook;
    private String filePath;
    
    public ExcelReader(String filePath) {
        this.filePath = filePath;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            throw new RuntimeException("Không thể đọc file Excel: " + filePath, e);
        }
    }
    
    /**
     * Lấy giá trị cell xử lý null và 4 kiểu dữ liệu: STRING, NUMERIC, BOOLEAN, FORMULA
     */
    public String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // Xử lý số nguyên và số thập phân
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        return String.valueOf((long) numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                // Đánh giá công thức và trả về kết quả
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                CellValue cellValue = evaluator.evaluate(cell);
                switch (cellValue.getCellType()) {
                    case STRING:
                        return cellValue.getStringValue();
                    case NUMERIC:
                        double numValue = cellValue.getNumberValue();
                        if (numValue == (long) numValue) {
                            return String.valueOf((long) numValue);
                        } else {
                            return String.valueOf(numValue);
                        }
                    case BOOLEAN:
                        return String.valueOf(cellValue.getBooleanValue());
                    default:
                        return "";
                }
            case BLANK:
            case _NONE:
            default:
                return "";
        }
    }
    
    /**
     * Đọc dữ liệu từ sheet và trả về danh sách Map
     */
    public List<Map<String, String>> getSheetData(String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();
        Sheet sheet = workbook.getSheet(sheetName);
        
        if (sheet == null) {
            throw new RuntimeException("Không tìm thấy sheet: " + sheetName);
        }
        
        // Lấy header từ dòng đầu tiên
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            return data;
        }
        
        List<String> headers = new ArrayList<>();
        for (Cell cell : headerRow) {
            headers.add(getCellValue(cell));
        }
        
        // Đọc dữ liệu từ dòng thứ 2 trở đi
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            
            Map<String, String> rowData = new HashMap<>();
            for (int j = 0; j < headers.size(); j++) {
                Cell cell = row.getCell(j);
                rowData.put(headers.get(j), getCellValue(cell));
            }
            
            // Chỉ thêm dòng có dữ liệu (không phải dòng trống)
            if (!isEmptyRow(rowData)) {
                data.add(rowData);
            }
        }
        
        return data;
    }
    
    private boolean isEmptyRow(Map<String, String> rowData) {
        return rowData.values().stream().allMatch(value -> value == null || value.trim().isEmpty());
    }
    
    public void close() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}