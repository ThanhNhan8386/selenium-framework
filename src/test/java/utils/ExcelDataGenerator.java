package utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

public class ExcelDataGenerator {
    public static void main(String[] args) {
        try (Workbook workbook = new XSSFWorkbook()) {
            
            // Sheet 1: SmokeCases (3 dòng - header + 2 dòng data)
            Sheet smokeSheet = workbook.createSheet("SmokeCases");
            Row smokeH = smokeSheet.createRow(0);
            smokeH.createCell(0).setCellValue("username");
            smokeH.createCell(1).setCellValue("password");
            smokeH.createCell(2).setCellValue("expected_url");
            smokeH.createCell(3).setCellValue("description");
            
            Row smokeR1 = smokeSheet.createRow(1);
            smokeR1.createCell(0).setCellValue("standard_user");
            smokeR1.createCell(1).setCellValue("secret_sauce");
            smokeR1.createCell(2).setCellValue("inventory.html");
            smokeR1.createCell(3).setCellValue("Đăng nhập thành công với standard_user");
            
            Row smokeR2 = smokeSheet.createRow(2);
            smokeR2.createCell(0).setCellValue("visual_user");
            smokeR2.createCell(1).setCellValue("secret_sauce");
            smokeR2.createCell(2).setCellValue("inventory.html");
            smokeR2.createCell(3).setCellValue("Đăng nhập thành công với visual_user");

            // Sheet 2: NegativeCases (5 dòng - header + 4 dòng data)
            Sheet negativeSheet = workbook.createSheet("NegativeCases");
            Row negH = negativeSheet.createRow(0);
            negH.createCell(0).setCellValue("username");
            negH.createCell(1).setCellValue("password");
            negH.createCell(2).setCellValue("expected_error");
            negH.createCell(3).setCellValue("description");
            
            Row negR1 = negativeSheet.createRow(1);
            negR1.createCell(0).setCellValue("locked_out_user");
            negR1.createCell(1).setCellValue("secret_sauce");
            negR1.createCell(2).setCellValue("locked out");
            negR1.createCell(3).setCellValue("Đăng nhập bằng user bị khoá");
            
            Row negR2 = negativeSheet.createRow(2);
            negR2.createCell(0).setCellValue("standard_user");
            negR2.createCell(1).setCellValue("wrongpass");
            negR2.createCell(2).setCellValue("do not match");
            negR2.createCell(3).setCellValue("Đăng nhập sai password");
            
            Row negR3 = negativeSheet.createRow(3);
            negR3.createCell(0).setCellValue("");
            negR3.createCell(1).setCellValue("secret_sauce");
            negR3.createCell(2).setCellValue("Username is required");
            negR3.createCell(3).setCellValue("Đăng nhập trống username");

            Row negR4 = negativeSheet.createRow(4);
            negR4.createCell(0).setCellValue("standard_user");
            negR4.createCell(1).setCellValue("");
            negR4.createCell(2).setCellValue("Password is required");
            negR4.createCell(3).setCellValue("Đăng nhập trống password");

            // Sheet 3: BoundaryCases 
            Sheet boundarySheet = workbook.createSheet("BoundaryCases");
            Row boundH = boundarySheet.createRow(0);
            boundH.createCell(0).setCellValue("username");
            boundH.createCell(1).setCellValue("password");
            boundH.createCell(2).setCellValue("expected_error");
            boundH.createCell(3).setCellValue("description");
            
            Row boundR1 = boundarySheet.createRow(1);
            boundR1.createCell(0).setCellValue("longuser".repeat(10));
            boundR1.createCell(1).setCellValue("secret_sauce");
            boundR1.createCell(2).setCellValue("do not match");
            boundR1.createCell(3).setCellValue("Username rất dài");

            Row boundR2 = boundarySheet.createRow(2);
            boundR2.createCell(0).setCellValue("standard_user");
            boundR2.createCell(1).setCellValue("longpass".repeat(10));
            boundR2.createCell(2).setCellValue("do not match");
            boundR2.createCell(3).setCellValue("Password rất dài");
            
            Row boundR3 = boundarySheet.createRow(3);
            boundR3.createCell(0).setCellValue("   ");
            boundR3.createCell(1).setCellValue("   ");
            boundR3.createCell(2).setCellValue("Username and password do not match");
            boundR3.createCell(3).setCellValue("Tài khoản toàn dấu cách khoảng trắng");

            try (FileOutputStream fos = new FileOutputStream("src/test/resources/testdata/login_data.xlsx")) {
                workbook.write(fos);
            }
            System.out.println("Tạo login_data.xlsx thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
