package bai6_retry.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FlakySimulationTest {
    private static int callCount = 0;

    @Test
    public void testFlakyLogic() {
        callCount++;
        System.out.println("[FlakyTest] Đang chạy lần thứ " + callCount);
        
        if (callCount < 3) {
            Assert.fail("Cố tình fail lần " + callCount);
        }
        Assert.assertTrue(true);
    }
}

