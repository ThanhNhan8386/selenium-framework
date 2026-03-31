package bai4_json_faker.dataproviders;

import bai4_json_faker.models.UserData;
import bai4_json_faker.utils.JsonReader;
import org.testng.annotations.DataProvider;

import java.util.List;

public class JsonDataProvider {
    @DataProvider(name = "SmokeData")
    public static Object[][] getSmokeData() {
        return new Object[][] {
            {"standard_user", "secret_sauce", "inventory", "Valid login test"},
            {"performance_glitch_user", "secret_sauce", "inventory", "Performance user login test"}
        };
    }

    @DataProvider(name = "NegativeData") 
    public static Object[][] getNegativeData() {
        return new Object[][] {
            {"invalid_user", "secret_sauce", "Epic sadface", "Invalid username test"},
            {"standard_user", "wrong_password", "Epic sadface", "Invalid password test"},
            {"", "", "Epic sadface", "Empty credentials test"}
        };
    }

    @DataProvider(name = "BoundaryData")
    public static Object[][] getBoundaryData() {
        return new Object[][] {
            {"locked_out_user", "secret_sauce", "Epic sadface", "Locked user test"},
            {"problem_user", "secret_sauce", "inventory", "Problem user test"}
        };
    }
}

