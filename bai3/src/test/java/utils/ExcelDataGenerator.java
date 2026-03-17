package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelDataGenerator {
    
    public static void main(String[] args) {
        createLoginTestData();
    }
    
    public static void createLoginTestData() {
        Workbook workbook = new XSSFWorkbook();
        
        // Tạo SmokeCases sheet
        createSmokeCasesSheet(workbook);
        
        // Tạo NegativeCases sheet
        createNegativeCasesSheet(workbook);
        
        // Tạo BoundaryCases sheet
        createBoundaryCasesSheet(workbook);
        
        // Lưu file
        try (FileOutputStream fileOut = new FileOutputStream("src/test/resources/login_data.xlsx")) {
            workbook.write(fileOut);
            System.out.println("Đã tạo file login_data.xlsx thành công!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void createSmokeCasesSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("SmokeCases");
        
        // Tạo header
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("username");
        headerRow.createCell(1).setCellValue("password");
        headerRow.createCell(2).setCellValue("expected_url");
        headerRow.createCell(3).setCellValue("description");
        
        // Dữ liệu test thành công
        String[][] smokeData = {
            {"tomsmith", "SuperSecretPassword!", "/secure", "Đăng nhập thành công với tài khoản hợp lệ"},
            {"tomsmith", "SuperSecretPassword!", "/secure", "Kiểm tra đăng nhập lần 2"},
            {"tomsmith", "SuperSecretPassword!", "/secure", "Xác nhận tính ổn định của chức năng đăng nhập"}
        };
        
        for (int i = 0; i < smokeData.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < smokeData[i].length; j++) {
                row.createCell(j).setCellValue(smokeData[i][j]);
            }
        }
        
        // Auto-size columns
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    
    private static void createNegativeCasesSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("NegativeCases");
        
        // Tạo header
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("username");
        headerRow.createCell(1).setCellValue("password");
        headerRow.createCell(2).setCellValue("expected_error");
        headerRow.createCell(3).setCellValue("description");
        
        // Dữ liệu test thất bại
        String[][] negativeData = {
            {"tomsmith", "wrongpassword", "Your password is invalid!", "Đăng nhập với mật khẩu sai"},
            {"invaliduser", "SuperSecretPassword!", "Your username is invalid!", "Đăng nhập với username không tồn tại"},
            {"", "SuperSecretPassword!", "Your username is invalid!", "Đăng nhập với username rỗng"},
            {"tomsmith", "", "Your password is invalid!", "Đăng nhập với password rỗng"},
            {"", "", "Your username is invalid!", "Đăng nhập với cả username và password rỗng"}
        };
        
        for (int i = 0; i < negativeData.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < negativeData[i].length; j++) {
                row.createCell(j).setCellValue(negativeData[i][j]);
            }
        }
        
        // Auto-size columns
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    
    private static void createBoundaryCasesSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("BoundaryCases");
        
        // Tạo header
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("username");
        headerRow.createCell(1).setCellValue("password");
        headerRow.createCell(2).setCellValue("expected_error");
        headerRow.createCell(3).setCellValue("description");
        
        // Dữ liệu test biên
        String[][] boundaryData = {
            {"a".repeat(1000), "password123", "Your username is invalid!", "Username cực dài (1000 ký tự)"},
            {"user@#$%^&*()", "password123", "Your username is invalid!", "Username chứa ký tự đặc biệt"},
            {"tomsmith", "a".repeat(500), "Your password is invalid!", "Password cực dài (500 ký tự)"},
            {"admin'; DROP TABLE users; --", "password", "Your username is invalid!", "SQL Injection pattern trong username"}
        };
        
        for (int i = 0; i < boundaryData.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < boundaryData[i].length; j++) {
                row.createCell(j).setCellValue(boundaryData[i][j]);
            }
        }
        
        // Auto-size columns
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}