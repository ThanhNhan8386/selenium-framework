package bai5_config.tests;

import bai1_base.base.BaseTest;
import bai5_config.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConfigDemoTest extends BaseTest {

    @Test
    public void testConfigData() {
        System.out.println("[INFO] URL Môi Trường: " + ConfigReader.getInstance().getBaseUrl());
        System.out.println("[INFO] Implicit Timeout Wait: " + ConfigReader.getInstance().getExplicitWait());
        Assert.assertTrue(getDriver().getCurrentUrl().contains("saucedemo"), "Phải mở ra đúng baseUrl theo config properties.");
    }
}
