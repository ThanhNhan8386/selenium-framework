package bai4_json_faker.tests;

import bai1_base.base.BaseTest;
import bai4_json_faker.utils.TestDataFactory;
import org.testng.annotations.Test;

import java.util.Map;

public class CheckoutTest extends BaseTest {

    @Test
    public void testCheckoutWithFakerData() {
        Map<String, String> checkoutData = TestDataFactory.randomCheckoutData();
        System.out.println("[Faker Checkout Data] " + checkoutData);
        // Tại đây sẽ implement code điền check out thực tế với dữ liệu trả về như trên
        // VD: checkoutPage.fillInfo(checkoutData.get("firstName"), checkoutData.get("lastName"), ...)
    }
}

