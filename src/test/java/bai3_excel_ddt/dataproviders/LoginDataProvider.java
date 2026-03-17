package bai3_excel_ddt.dataproviders;

import bai3_excel_ddt.utils.ExcelReader;
import org.testng.annotations.DataProvider;

public class LoginDataProvider {

    private static final String EXCEL_PATH = "src/test/resources/login_data.xlsx";

    @DataProvider(name = "SmokeData")
    public static Object[][] getSmokeData() {
        return ExcelReader.getData(EXCEL_PATH, "SmokeCases");
    }

    @DataProvider(name = "NegativeData")
    public static Object[][] getNegativeData() {
        return ExcelReader.getData(EXCEL_PATH, "NegativeCases");
    }

    @DataProvider(name = "BoundaryData")
    public static Object[][] getBoundaryData() {
        return ExcelReader.getData(EXCEL_PATH, "BoundaryCases");
    }
}
