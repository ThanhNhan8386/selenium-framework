package bai2_pom.pages;

import bai1_base.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(className = "inventory_list")
    private WebElement inventoryList;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = ".inventory_item button[id*='add-to-cart']")
    private List<WebElement> addToCartButtons;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartLink;

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isElementVisible(By.className("inventory_list"));
    }

    public InventoryPage addFirstItemToCart() {
        if (!addToCartButtons.isEmpty()) {
            waitAndClick(addToCartButtons.get(0));
        }
        return this;
    }

    public InventoryPage addItemByName(String name) {
        try {
            // Sử dụng XPath để tìm button chính xác dựa trên tên item
            String xpath = String.format("//div[contains(@class,'inventory_item_name') and text()='%s']/ancestor::div[contains(@class,'inventory_item')]//button[contains(text(),'Add to cart')]", name);
            WebElement addButton = driver.findElement(By.xpath(xpath));
            
            waitAndClick(addButton);
            
            // Đợi một chút để UI cập nhật
            Thread.sleep(500);
            
        } catch (Exception e) {
            // Fallback: Thử XPath khác
            try {
                String xpath2 = String.format("//div[@class='inventory_item_name' and text()='%s']/../following-sibling::div//button[contains(@id,'add-to-cart')]", name);
                WebElement addButton2 = driver.findElement(By.xpath(xpath2));
                
                waitAndClick(addButton2);
                Thread.sleep(500);
                
            } catch (Exception e2) {
                System.out.println("[ERROR] Could not add item to cart: " + name);
            }
        }
        return this;
    }

    private void waitForCartBadgeUpdate(int expectedCount) {
        int maxAttempts = 20; // Tăng số lần thử
        int attempts = 0;
        while (attempts < maxAttempts) {
            int currentCount = getCartItemCount();
            System.out.println("[DEBUG] Waiting for cart count " + expectedCount + ", current: " + currentCount);
            if (currentCount == expectedCount) {
                System.out.println("[DEBUG] Cart count updated successfully to: " + expectedCount);
                break;
            }
            try {
                Thread.sleep(100); // Giảm thời gian chờ
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            attempts++;
        }
        if (attempts >= maxAttempts) {
            System.out.println("[DEBUG] Timeout waiting for cart count to update to: " + expectedCount);
        }
    }

    public int getCartItemCount() {
        if (isElementVisible(By.className("shopping_cart_badge"))) {
            return Integer.parseInt(getText(cartBadge));
        }
        return 0; // Trả về 0 nếu không có item nào trong giỏ hàng
    }

    public CartPage goToCart() {
        waitAndClick(cartLink);
        return new CartPage(driver);
    }
}

