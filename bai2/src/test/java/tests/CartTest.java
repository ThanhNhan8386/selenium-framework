package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;

public class CartTest extends BaseTest {

    @Test
    public void testAddItemToCart() {
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addFirstItemToCart();
        Assert.assertEquals(inventoryPage.getCartItemCount(), 1, "Cart should contain 1 item");
    }

    @Test
    public void testFluentInterfaceAddToCart() {
        CartPage cartPage = loginPage.login("standard_user", "secret_sauce")
                                   .addFirstItemToCart()
                                   .goToCart();
        Assert.assertEquals(cartPage.getItemCount(), 1, "Cart should contain 1 item");
    }

    @Test
    public void testAddSpecificItemByName() {
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addItemByName("Sauce Labs Backpack");
        CartPage cartPage = inventoryPage.goToCart();
        Assert.assertTrue(cartPage.getItemNames().contains("Sauce Labs Backpack"), 
                         "Cart should contain Sauce Labs Backpack");
    }

    @Test
    public void testRemoveItemFromCart() {
        CartPage cartPage = loginPage.login("standard_user", "secret_sauce")
                                   .addFirstItemToCart()
                                   .goToCart();
        Assert.assertEquals(cartPage.getItemCount(), 1, "Cart should initially contain 1 item");
        
        cartPage.removeFirstItem();
        Assert.assertEquals(cartPage.getItemCount(), 0, "Cart should be empty after removing item");
    }

    @Test
    public void testEmptyCartItemCount() {
        CartPage cartPage = loginPage.login("standard_user", "secret_sauce").goToCart();
        Assert.assertEquals(cartPage.getItemCount(), 0, "Empty cart should return 0 items");
    }

    @Test
    public void testMultipleItemsInCart() {
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addItemByName("Sauce Labs Backpack");
        inventoryPage.addItemByName("Sauce Labs Bike Light");
        
        CartPage cartPage = inventoryPage.goToCart();
        Assert.assertEquals(cartPage.getItemCount(), 2, "Cart should contain 2 items");
        Assert.assertEquals(cartPage.getItemNames().size(), 2, "Should have 2 item names");
    }
}