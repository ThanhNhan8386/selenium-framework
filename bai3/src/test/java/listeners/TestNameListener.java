package listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNameListener implements ITestListener {
    
    @Override
    public void onTestStart(ITestResult result) {
        // Lấy parameters từ test method
        Object[] parameters = result.getParameters();
        if (parameters.length >= 7) {
            String description = (String) parameters[4]; // description parameter
            String testName = (String) parameters[6]; // testName parameter
            
            // Cập nhật tên test method để hiển thị trong report
            result.getMethod().setDescription(testName);
            
            // Set test name cho TestNG report
            System.setProperty("testng.test.name", testName);
        }
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        Object[] parameters = result.getParameters();
        if (parameters.length >= 7) {
            String testName = (String) parameters[6];
            System.out.println("✓ PASSED: " + testName);
        }
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        Object[] parameters = result.getParameters();
        if (parameters.length >= 7) {
            String testName = (String) parameters[6];
            System.out.println("✗ FAILED: " + testName);
            System.out.println("Error: " + result.getThrowable().getMessage());
        }
    }
}