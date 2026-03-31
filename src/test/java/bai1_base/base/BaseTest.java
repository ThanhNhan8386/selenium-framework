package bai1_base.base;

import bai1_base.factory.DriverFactory;
import bai1_base.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;

/**
 * BaseTest thiết lập cấu hình chạy ban đầu và dọn dẹp sau test cho toàn bộ Framework.
 */
public abstract class BaseTest {
    // ThreadLocal giúp chạy song song nhiều browser mà không đụng độ hay dính dữ liệu chéo nhau
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    @BeforeMethod
    @Parameters({"browser", "env"})
    public void setUp(@Optional("chrome") String browser, @Optional("dev") String env) {
        // Set environment property
        System.setProperty("env", env);

        // Sử dụng DriverFactory để khởi tạo WebDriver
        WebDriver driver = DriverFactory.createDriver(browser);
        
        // Lưu driver theo thread
        tlDriver.set(driver);
        
        // Cấu hình cửa sổ trình duyệt và thời gian chờ
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        
        // Mở URL mặc định theo ConfigReader
        getDriver().get(bai5_config.utils.ConfigReader.getInstance().getBaseUrl());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Chụp screenshot nếu bài test fail
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtil.takeScreenshot(getDriver(), result.getName());
        }
        
        // Đóng trình duyệt và xoá dữ liệu driver khởi tạo bên cache của Thread này
        if (getDriver() != null) {
            getDriver().quit();
            tlDriver.remove();
        }
    }

    /**
     * Truy xuất tài nguyên driver riêng biệt cho từng Thread.
     * @return WebDriver
     */
    public static WebDriver getDriver() {
        return tlDriver.get();
    }
}

