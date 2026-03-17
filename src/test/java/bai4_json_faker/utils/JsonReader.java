package bai4_json_faker.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import bai4_json_faker.models.UserData;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JsonReader {
    public static List<UserData> readUsers(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        try (java.io.InputStream is = JsonReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                System.err.println("Không tìm thấy file: " + fileName);
                return Collections.emptyList();
            }
            return mapper.readValue(is, new TypeReference<List<UserData>>() {});
        } catch (IOException e) {
            System.err.println("Lỗi đọc file JSON: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}

