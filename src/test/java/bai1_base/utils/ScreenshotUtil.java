package bai1_base.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    /**
     * Chụp lại hình ảnh màn hình của tình trạng Driver và in ra console đường dẫn file.
     * @param driver WebDriver hiện tại
     * @param testName Tên bài test
     */
    public static void takeScreenshot(WebDriver driver, String testName) {
        if (driver == null) return;

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE); // Selenium interface TakesScreenshot
        String fileName = testName + "_" + timestamp + ".png";
        
        try {
            Path destDir = Paths.get("target", "screenshots");
            if (!Files.exists(destDir)) {
                Files.createDirectories(destDir);
            }
            Path destFile = destDir.resolve(fileName);
            // Copy từ thư mục temp của OS/Selenium qua target dự án
            Files.copy(src.toPath(), destFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("[INFO] Screenshot saved successfully at: " + destFile.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to save screenshot: " + e.getMessage());
        }
    }
}
