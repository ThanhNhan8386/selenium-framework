package bai1_base.tests;

import bai1_base.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoTest extends BaseTest {

    @Test
    public void testLoginFailureForScreenshot() {
        Assert.assertTrue(false, "Cố tình fail DemoTest để check tính năng chụp Screenshot tự động.");
    }
}
