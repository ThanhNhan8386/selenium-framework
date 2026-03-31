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
            {"", "secret_sauce", "", "Empty username test"}
        };
    }

    @DataProvider(name = "jsonUsers")
    public static Object[][] getJsonUsers() {
        try {
            List<UserData> users = JsonReader.readUsers("users.json");
            Object[][] data = new Object[users.size()][4];
            for (int i = 0; i < users.size(); i++) {
                UserData user = users.get(i);
                data[i][0] = user.getUsername();
                data[i][1] = user.getPassword();
                data[i][2] = user.isExpectSuccess();
                data[i][3] = user.getDescription();
            }
            return data; // username, password, expectSuccess, description
        } catch (Exception e) {
            // Fallback data if JSON reading fails
            return new Object[][] {
                {"standard_user", "secret_sauce", true, "Valid login from fallback"},
                {"invalid_user", "secret_sauce", false, "Invalid login from fallback"}
            };
        }
    }
}

