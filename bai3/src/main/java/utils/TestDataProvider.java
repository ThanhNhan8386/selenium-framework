package utils;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestDataProvider {
    private static final String EXCEL_FILE_PATH = "src/test/resources/login_data.xlsx";
    
    @DataProvider(name = "smokeTestData")
    public static Object[][] getSmokeTestData(Method method) {
        return getTestDataFromSheets(new String[]{"SmokeCases"}, method);
    }
    
    @DataProvider(name = "regressionTestData") 
    public static Object[][] getRegressionTestData(Method method) {
        return getTestDataFromSheets(new String[]{"SmokeCases", "NegativeCases", "BoundaryCases"}, method);
    }
    
    private static Object[][] getTestDataFromSheets(String[] sheetNames, Method method) {
        List<Object[]> testData = new ArrayList<>();
        ExcelReader excelReader = new ExcelReader(EXCEL_FILE_PATH);
        
        try {
            int testIndex = 0;
            for (String sheetName : sheetNames) {
                List<Map<String, String>> sheetData = excelReader.getSheetData(sheetName);
                
                for (Map<String, String> row : sheetData) {
                    // Tạo test case với description để hiển thị trong TestNG report
                    String description = row.getOrDefault("description", "Test case " + testIndex);
                    
                    // Tạo test name duy nhất cho mỗi test case
                    String testName = String.format("[%s] %s", sheetName, description);
                    
                    testData.add(new Object[]{
                        row.get("username"),
                        row.get("password"), 
                        row.getOrDefault("expected_url", ""),
                        row.getOrDefault("expected_error", ""),
                        description,
                        sheetName,
                        testName // Thêm testName để sử dụng trong TestNG
                    });
                    testIndex++;
                }
            }
        } finally {
            excelReader.close();
        }
        
        return testData.toArray(new Object[0][]);
    }
}