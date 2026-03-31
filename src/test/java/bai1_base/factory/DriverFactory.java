package bai1_base.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * DriverFactory quản lý việc khởi tạo WebDriver cho Chrome và Firefox
 */
public class DriverFactory {
    
    public static WebDriver createDriver(String browserName) {
        String headless = System.getProperty("headless");
        boolean isHeadless = "true".equals(headless);
        
        switch (browserName.toLowerCase()) {
            case "chrome":
                return createChromeDriver(isHeadless);
            case "firefox":
                return createFirefoxDriver(isHeadless);
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName + ". Supported browsers: chrome, firefox");
        }
    }
    
    private static WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        
        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }
        
        // Thêm các options khác để tối ưu performance
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        
        return new ChromeDriver(options);
    }
    
    private static WebDriver createFirefoxDriver(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        
        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
        }
        
        // Tối ưu Firefox cho automation
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("media.volume_scale", "0.0");
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
        
        return new FirefoxDriver(options);
    }
}