package bai2_pom.tests;

import bai1_base.base.BaseTest;
import bai2_pom.pages.CartPage;
import bai2_pom.pages.InventoryPage;
import bai2_pom.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CartTest extends BaseTest {

    @Test
    public void testAddItemToCart() {
        System.out.println("[INFO] Test: Thêm 1 sản phẩm vào giỏ hàng");
        LoginPage loginPage = new LoginPage(getDriver());
        int count = loginPage.login("standard_user", "secret_sauce")
                .addFirstItemToCart()
                .getCartItemCount();
        
        Assert.assertEquals(count, 1, "Số lượng trên Cart Badge phải là 1");
    }

    @Test
    public void testRemoveItemFromCart() {
        System.out.println("[INFO] Test: Xoá 1 sản phẩm khỏi giỏ hàng");
        LoginPage loginPage = new LoginPage(getDriver());
        CartPage cartPage = loginPage.login("standard_user", "secret_sauce")
                .addFirstItemToCart()
                .goToCart()
                .removeFirstItem();
        
        Assert.assertEquals(cartPage.getItemCount(), 0, "Cart Count phải trống rỗng bằng 0");
    }

    @Test
    public void testAddMultipleItemsAndVerify() {
        System.out.println("[INFO] Test: Thêm nhiều SP & kiểm chứng tên sản phẩm");
        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce")
                .addItemByName("Sauce Labs Backpack")
                .addItemByName("Sauce Labs Bike Light");
        
        Assert.assertEquals(inventoryPage.getCartItemCount(), 2, "Cart badge count phải bằng 2");
        
        List<String> cartItemNames = inventoryPage.goToCart().getItemNames();
        System.out.println("[DEBUG] Items in cart: " + cartItemNames);
        
        Assert.assertTrue(cartItemNames.contains("Sauce Labs Backpack"), "Không có balo");
        Assert.assertTrue(cartItemNames.contains("Sauce Labs Bike Light"), "Không có đèn xe");
    }
}
