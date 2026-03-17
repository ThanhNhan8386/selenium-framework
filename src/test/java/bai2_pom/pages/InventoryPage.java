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

    @FindBy(css = ".inventory_item button")
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
        for (int i = 0; i < itemNames.size(); i++) {
            if (getText(itemNames.get(i)).equals(name)) {
                waitAndClick(addToCartButtons.get(i));
                break;
            }
        }
        return this;
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

