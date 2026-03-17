package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(className = "inventory_list")
    private WebElement inventoryList;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(css = "button[id^='add-to-cart']")
    private List<WebElement> addToCartButtons;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartLink;

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(inventoryList));
            return inventoryList.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public InventoryPage addFirstItemToCart() {
        if (!addToCartButtons.isEmpty()) {
            addToCartButtons.get(0).click();
        }
        return this;
    }

    public InventoryPage addItemByName(String itemName) {
        for (WebElement item : inventoryItems) {
            WebElement nameElement = item.findElement(org.openqa.selenium.By.className("inventory_item_name"));
            if (nameElement.getText().equals(itemName)) {
                WebElement addButton = item.findElement(org.openqa.selenium.By.cssSelector("button[id^='add-to-cart']"));
                addButton.click();
                break;
            }
        }
        return this;
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(cartBadge.getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public CartPage goToCart() {
        cartLink.click();
        return new CartPage(driver);
    }
}