package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestDataProvider;
import utils.TestReportHelper;

public class LoginTest extends BaseTest {
    
    @Test(dataProvider = "smokeTestData", dataProviderClass = TestDataProvider.class, 
          groups = {"smoke"}, description = "Smoke Test Cases from Excel")
    public void testLoginSmoke(String username, String password, String expectedUrl, 
                              String expectedError, String description, String sheetName, String testName) {
        
        // Log thông tin test case vào TestNG report
        TestReportHelper.logTestInfo(description, sheetName, username, password);
        
        System.out.println("\n=== CHẠY TEST: " + description + " ===");
        System.out.println("Sheet: " + sheetName);
        System.out.println("Username: '" + username + "', Password: '" + password + "'");
        
        try {
            // Thực hiện đăng nhập
            TestReportHelper.logStepInfo("Thực hiện đăng nhập với username: " + username);
            loginPage.login(username, password);
            
            // Kiểm tra kết quả dựa trên loại test case
            if (!expectedUrl.isEmpty()) {
                // Test case thành công - kiểm tra URL
                TestReportHelper.logStepInfo("Kiểm tra URL chứa: " + expectedUrl);
                boolean isLoginSuccessful = loginPage.isLoginSuccessful(expectedUrl);
                
                Assert.assertTrue(isLoginSuccessful, 
                    "Đăng nhập thất bại. URL hiện tại: " + loginPage.getCurrentUrl() + 
                    ", URL mong đợi chứa: " + expectedUrl);
                
                String successMessage = "Đăng nhập thành công - URL chứa: " + expectedUrl;
                System.out.println("✓ PASSED: " + successMessage);
                TestReportHelper.logTestResult(true, successMessage);
                
            } else if (!expectedError.isEmpty()) {
                // Test case thất bại - kiểm tra error message
                TestReportHelper.logStepInfo("Kiểm tra error message chứa: " + expectedError);
                String actualError = loginPage.getErrorMessage();
                
                Assert.assertTrue(actualError.contains(expectedError), 
                    "Error message không đúng. Thực tế: '" + actualError + 
                    "', Mong đợi chứa: '" + expectedError + "'");
                
                String successMessage = "Hiển thị error message đúng: " + actualError;
                System.out.println("✓ PASSED: " + successMessage);
                TestReportHelper.logTestResult(true, successMessage);
            }
            
        } catch (Exception e) {
            String errorMessage = "Test thất bại: " + e.getMessage();
            System.out.println("✗ FAILED: " + errorMessage);
            TestReportHelper.logTestResult(false, errorMessage);
            throw e;
        }
    }
    
    @Test(dataProvider = "regressionTestData", dataProviderClass = TestDataProvider.class, 
          groups = {"regression"}, description = "Regression Test Cases from Excel")
    public void testLoginRegression(String username, String password, String expectedUrl, 
                                   String expectedError, String description, String sheetName, String testName) {
        
        // Log thông tin test case vào TestNG report
        TestReportHelper.logTestInfo(description, sheetName, username, password);
        
        System.out.println("\n=== CHẠY TEST: " + description + " ===");
        System.out.println("Sheet: " + sheetName);
        System.out.println("Username: '" + username + "', Password: '" + password + "'");
        
        try {
            // Thực hiện đăng nhập
            TestReportHelper.logStepInfo("Thực hiện đăng nhập với username: " + username);
            loginPage.login(username, password);
            
            // Kiểm tra kết quả dựa trên loại test case
            if (!expectedUrl.isEmpty()) {
                // Test case thành công - kiểm tra URL
                TestReportHelper.logStepInfo("Kiểm tra URL chứa: " + expectedUrl);
                boolean isLoginSuccessful = loginPage.isLoginSuccessful(expectedUrl);
                
                Assert.assertTrue(isLoginSuccessful, 
                    "Đăng nhập thất bại. URL hiện tại: " + loginPage.getCurrentUrl() + 
                    ", URL mong đợi chứa: " + expectedUrl);
                
                String successMessage = "Đăng nhập thành công - URL chứa: " + expectedUrl;
                System.out.println("✓ PASSED: " + successMessage);
                TestReportHelper.logTestResult(true, successMessage);
                
            } else if (!expectedError.isEmpty()) {
                // Test case thất bại - kiểm tra error message
                TestReportHelper.logStepInfo("Kiểm tra error message chứa: " + expectedError);
                String actualError = loginPage.getErrorMessage();
                
                Assert.assertTrue(actualError.contains(expectedError), 
                    "Error message không đúng. Thực tế: '" + actualError + 
                    "', Mong đợi chứa: '" + expectedError + "'");
                
                String successMessage = "Hiển thị error message đúng: " + actualError;
                System.out.println("✓ PASSED: " + successMessage);
                TestReportHelper.logTestResult(true, successMessage);
            }
            
        } catch (Exception e) {
            String errorMessage = "Test thất bại: " + e.getMessage();
            System.out.println("✗ FAILED: " + errorMessage);
            TestReportHelper.logTestResult(false, errorMessage);
            throw e;
        }
    }
}