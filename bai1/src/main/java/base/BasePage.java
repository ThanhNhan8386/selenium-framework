package base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

/**
 * BasePage class cung cấp các method cơ bản để tương tác với web elements
 * Tất cả page objects sẽ kế thừa từ class này
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final int DEFAULT_TIMEOUT = 10;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    /**
     * Chờ element có thể click được và thực hiện click
     * Sử dụng khi cần click vào button, link hoặc element có thể tương tác
     * 
     * @param locator By locator của element cần click
     */
    public void waitAndClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    /**
     * Chờ element hiển thị và nhập text vào input field
     * Tự động clear text cũ trước khi nhập text mới
     * 
     * @param locator By locator của input element
     * @param text Text cần nhập vào field
     */
    public void waitAndType(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Lấy text content của element sau khi chờ element hiển thị
     * Sử dụng để verify text hoặc lấy thông tin từ element
     * 
     * @param locator By locator của element cần lấy text
     * @return String text content của element
     */
    public String getText(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getText();
    }

    /**
     * Kiểm tra element có hiển thị trên trang hay không
     * Xử lý StaleElementReferenceException khi DOM được render lại
     * 
     * @param locator By locator của element cần kiểm tra
     * @return boolean true nếu element hiển thị, false nếu không
     */
    public boolean isElementVisible(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return element.isDisplayed();
        } catch (StaleElementReferenceException e) {
            // Retry khi gặp StaleElementReferenceException
            try {
                WebElement element = driver.findElement(locator);
                return element.isDisplayed();
            } catch (NoSuchElementException | StaleElementReferenceException ex) {
                return false;
            }
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Scroll đến element để đưa element vào viewport
     * Sử dụng khi element nằm ngoài màn hình hiển thị
     * 
     * @param locator By locator của element cần scroll tới
     */
    public void scrollToElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    /**
     * Chờ trang web load hoàn tất
     * Kiểm tra document.readyState = 'complete' và jQuery ready (nếu có)
     */
    public void waitForPageLoad() {
        wait.until(webDriver -> {
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            String readyState = js.executeScript("return document.readyState").toString();
            
            // Kiểm tra jQuery nếu có
            Boolean jQueryReady = true;
            try {
                jQueryReady = (Boolean) js.executeScript("return jQuery.active == 0");
            } catch (WebDriverException e) {
                // jQuery không có sẵn, bỏ qua kiểm tra này
            }
            
            return "complete".equals(readyState) && jQueryReady;
        });
    }

    /**
     * Lấy giá trị attribute của element
     * Sử dụng để lấy value, class, id hoặc các attribute khác
     * 
     * @param locator By locator của element
     * @param attributeName Tên attribute cần lấy giá trị
     * @return String giá trị của attribute
     */
    public String getAttribute(By locator, String attributeName) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return element.getAttribute(attributeName);
    }
}