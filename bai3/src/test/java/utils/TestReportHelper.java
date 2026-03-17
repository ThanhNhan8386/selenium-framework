package utils;

import org.testng.ITestResult;
import org.testng.Reporter;

public class TestReportHelper {
    
    public static void logTestInfo(String description, String sheetName, String username, String password) {
        String logMessage = String.format(
            "<div style='margin: 10px 0; padding: 10px; border-left: 3px solid #007bff; background-color: #f8f9fa;'>" +
            "<strong>Test Case:</strong> %s<br/>" +
            "<strong>Sheet:</strong> %s<br/>" +
            "<strong>Username:</strong> '%s'<br/>" +
            "<strong>Password:</strong> '%s'" +
            "</div>", 
            description, sheetName, username, password
        );
        
        Reporter.log(logMessage, true);
    }
    
    public static void logTestResult(boolean success, String message) {
        String color = success ? "#28a745" : "#dc3545";
        String icon = success ? "✓" : "✗";
        String status = success ? "PASSED" : "FAILED";
        
        String logMessage = String.format(
            "<div style='margin: 5px 0; padding: 8px; border-left: 3px solid %s; background-color: %s;'>" +
            "<strong style='color: %s;'>%s %s:</strong> %s" +
            "</div>", 
            color, success ? "#d4edda" : "#f8d7da", color, icon, status, message
        );
        
        Reporter.log(logMessage, true);
    }
    
    public static void logStepInfo(String step) {
        String logMessage = String.format(
            "<div style='margin: 5px 0; padding: 5px; color: #6c757d; font-style: italic;'>" +
            "→ %s" +
            "</div>", 
            step
        );
        
        Reporter.log(logMessage, true);
    }
}