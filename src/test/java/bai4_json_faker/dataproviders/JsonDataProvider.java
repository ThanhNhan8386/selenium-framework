package bai4_json_faker.dataproviders;

import bai4_json_faker.models.UserData;
import bai4_json_faker.utils.JsonReader;
import org.testng.annotations.DataProvider;

import java.util.List;

public class JsonDataProvider {
    @DataProvider(name = "jsonUsers")
    public static Object[][] getJsonUsers() {
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
    }
}

