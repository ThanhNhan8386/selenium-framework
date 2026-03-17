package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * BaseTest class cung cấp setup và teardown cho tất cả test cases
 * Sử dụng ThreadLocal để hỗ trợ parallel execution
 */
public class BaseTest {
    
    // ThreadLocal để hỗ trợ parallel execution
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    
    /**
     * Lấy WebDriver instance từ ThreadLocal
     * @return WebDriver instance cho thread hiện tại
     */
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
    
    /**
     * Setup WebDriver trước mỗi test method
     * Nhận parameters từ testng.xml: browser và env
     * 
     * @param browser Loại browser cần khởi tạo (chrome, firefox, edge)
     * @param env Environment để test (dev, staging, prod)
     */
    @BeforeMethod
    @Parameters({"browser", "env"})
    public void setUp(@Optional("chrome") String browser, @Optional("dev") String env) {
        WebDriver driver = createDriver(browser);
        driverThreadLocal.set(driver);
        
        // Configure browser window
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        
        System.out.println("Started " + browser + " browser for environment: " + env);
    }
    
    /**
     * Cleanup sau mỗi test method
     * Chụp screenshot CHỈ KHI test FAIL và đóng browser
     * 
     * @param result ITestResult chứa thông tin về kết quả test
     * @param method Method object chứa thông tin về test method
     */
    @AfterMethod
    public void tearDown(ITestResult result, Method method) {
        WebDriver driver = getDriver();
        
        if (driver != null) {
            // Chụp screenshot CHỈ KHI test FAIL
            if (result.getStatus() == ITestResult.FAILURE) {
                takeScreenshot(method.getName() + "_FAILED");
                System.out.println("Test FAILED - Screenshot captured for: " + method.getName());
            }
            
            // Đóng browser
            driver.quit();
            driverThreadLocal.remove();
        }
    }
    
    /**
     * Tạo WebDriver instance dựa trên browser type
     * 
     * @param browserName Tên browser (chrome, firefox, edge)
     * @return WebDriver instance
     */
    private WebDriver createDriver(String browserName) {
        WebDriver driver;
        
        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");
                driver = new ChromeDriver(chromeOptions);
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
                
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }
        
        return driver;
    }
    
    /**
     * Chụp screenshot và lưu vào thư mục target/screenshots/
     * Tên file: {testName}_{timestamp}.png
     * 
     * @param testName Tên của test method
     */
    private void takeScreenshot(String testName) {
        try {
            WebDriver driver = getDriver();
            if (driver != null) {
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
                
                // Tạo thư mục screenshots nếu chưa tồn tại
                Path screenshotDir = Paths.get("target/screenshots");
                if (!Files.exists(screenshotDir)) {
                    Files.createDirectories(screenshotDir);
                }
                
                // Tạo tên file với timestamp
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                String fileName = testName + "_" + timestamp + ".png";
                Path destPath = screenshotDir.resolve(fileName);
                
                // Copy file screenshot sử dụng Java NIO
                Files.copy(sourceFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Screenshot saved: " + destPath.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
        }
    }
}