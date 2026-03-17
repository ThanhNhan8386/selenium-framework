package bai1_base.base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage chứa các hàm khởi tạo và xử lý chung cho toàn bộ các Page Object.
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    /**
     * Khởi tạo driver, thời gian chờ là 15 giây và khởi tạo các elements qua PageFactory.
     * @param driver WebDriver instance truyền từ Test class
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(bai5_config.utils.ConfigReader.getInstance().getExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    /**
     * Chờ element có thể click được và sau đó thực hiện click.
     * Sử dụng: khi cần tương tác với các nút bấm, đường dẫn.
     * Tránh lỗi: ElementClickInterceptedException hoặc ElementNotInteractableException.
     * @param element WebElement cần tương tác
     */
    public void waitAndClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    /**
     * Chờ element hiển thị trên màn hình, xóa dữ liệu cũ và nhập text mới.
     * Sử dụng: khi cần nhập thông tin vào ô input, textbox.
     * Tránh lỗi: StaleElement hoặc field đang disable/ẩn.
     * @param element WebElement để điền dữ liệu
     * @param text Dữ liệu đầu vào cần truyền
     */
    public void waitAndType(WebElement element, String text) {
        WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
        visibleElement.clear();
        visibleElement.sendKeys(text);
    }

    /**
     * Chờ hiển thị element và trích xuất nội dung văn bản.
     * Sử dụng: khi parse dữ liệu văn bản từ UI để assert (ví dụ: message, label).
     * Tránh lỗi: Lấy phải text rỗng khi element chưa sẵn sàng, hoặc khoảng trắng dư thừa.
     * @param element WebElement cần lấy text
     * @return Chuỗi nội dung đã bỏ đi khoảng trắng ở hai đầu (trim)
     */
    public String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText().trim();
    }

    /**
     * Kiểm tra element có đang hiển thị hay không (dùng theo Locator).
     * Sử dụng: Kiểm tra state hiển thị của element không bắt buộc tồn tại trên DOM.
     * Tránh lỗi: Ngăn chặn throw các exception gây gãy luồng test bằng cách return false.
     * @param locator By locator của element cần kiểm tra
     * @return true nếu có, trả lại false nếu không tìm thấy hay chần chờ quá lố (timeout)
     */
    public boolean isElementVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
            return false;
        }
    }

    /**
     * Cuộn trang đến đúng vị trí của phần tử mong muốn qua Javascript.
     * Sử dụng: khi element nằm ngoài viewport che khuất, không thể nhấp/xem.
     * Tránh lỗi: ElementNotInteractableException do nằm ngoài màn hình hiển thị.
     * @param element WebElement cần điều hướng tới
     */
    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Chờ trạng thái của trang web hoàn thiện (Document ReadyState là "complete").
     * Sử dụng: khi tải lại trang, chuyển trang mới, hoặc click một liên kết lớn.
     * Tránh lỗi: Selenium thao tác trước khi DOM/tài nguyên trên trang được render đầy đủ.
     */
    public void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Chờ tới khi phần tử hiển thị và lấy giá trị của thuộc tính (attribute).
     * Sử dụng: lúc cần kiểm tra class css, placeholder, src của ảnh hay href của link.
     * Tránh lỗi: Tương tác nhầm trạng thái ban đầu khi JS framework sinh ra thay đổi thuôc tính chớp nhoáng.
     * @param element WebElement cần kiểm tra
     * @param attr Tên của attribute (vd: "class", "value")
     * @return Giá trị chuỗi của attribute đó
     */
    public String getAttribute(WebElement element, String attr) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getAttribute(attr);
    }
}

