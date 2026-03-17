$base = "c:\NAMCUOI\PROJECTTESTTUAN9\src\test\java"
md $base\bai1_base\base -Force | Out-Null
md $base\bai2_pom\pages -Force | Out-Null
md $base\bai3_excel_ddt\utils -Force | Out-Null
md $base\bai3_excel_ddt\tests -Force | Out-Null
md $base\bai4_json_faker\utils -Force | Out-Null
md $base\bai4_json_faker\models -Force | Out-Null
md $base\bai4_json_faker\dataproviders -Force | Out-Null
md $base\bai4_json_faker\tests -Force | Out-Null
md $base\bai5_config\utils -Force | Out-Null
md $base\bai5_config\tests -Force | Out-Null
md $base\bai6_retry\utils -Force | Out-Null
md $base\bai6_retry\tests -Force | Out-Null

Function Fix-Imports($file, $newPkg) {
    if (Test-Path $file) {
        $content = Get-Content $file -Raw
        $content = $content -replace "package .*;", "package $newPkg;"
        
        $content = $content -replace "import framework.base.BasePage;", "import bai1_base.base.BasePage;"
        $content = $content -replace "import framework.base.BaseTest;", "import bai1_base.base.BaseTest;"
        
        $content = $content -replace "import framework.pages.LoginPage;", "import bai2_pom.pages.LoginPage;"
        $content = $content -replace "import framework.pages.InventoryPage;", "import bai2_pom.pages.InventoryPage;"
        $content = $content -replace "import framework.pages.CartPage;", "import bai2_pom.pages.CartPage;"
        $content = $content -replace "import framework.pages.CheckoutPage;", "import bai2_pom.pages.CheckoutPage;"
        
        $content = $content -replace "import framework.utils.ExcelReader;", "import bai3_excel_ddt.utils.ExcelReader;"
        $content = $content -replace "import framework.utils.JsonReader;", "import bai4_json_faker.utils.JsonReader;"
        $content = $content -replace "import framework.models.UserData;", "import bai4_json_faker.models.UserData;"
        $content = $content -replace "import framework.utils.TestDataFactory;", "import bai4_json_faker.utils.TestDataFactory;"
        $content = $content -replace "import tests.dataproviders.JsonDataProvider;", "import bai4_json_faker.dataproviders.JsonDataProvider;"
        
        $content = $content -replace "import framework.utils.ConfigReader;", "import bai5_config.utils.ConfigReader;"
        $content = $content -replace "import framework.utils.RetryAnalyzer;", "import bai6_retry.utils.RetryAnalyzer;"
        $content = $content -replace "import framework.utils.RetryListener;", "import bai6_retry.utils.RetryListener;"
        
        $content = $content -replace "tests.dataproviders.LoginDataProvider.class", "bai4_json_faker.dataproviders.JsonDataProvider.class"
        
        $content = $content -replace "framework.utils.ConfigReader.getInstance", "bai5_config.utils.ConfigReader.getInstance"
        
        # fix the data provider class in json if userlogin was pointing to tests.dataproviders.JsonDataProvider.class
        $content = $content -replace "dataProviderClass = tests.dataproviders.JsonDataProvider.class", "dataProviderClass = bai4_json_faker.dataproviders.JsonDataProvider.class"
        
        $content = $content -replace "framework.utils.ScreenshotUtil", "utils.ScreenshotUtil"
        
        Set-Content -Path $file -Value $content
        
        $newName = (Split-Path $file -Leaf)
        $newPath = (Join-Path (Join-Path $base ($newPkg -replace '\.', '\')) $newName)
        Move-Item $file $newPath -Force
    }
}

Fix-Imports "$base\bai1_base\BasePage.java" "bai1_base.base"
Fix-Imports "$base\bai1_base\BaseTest.java" "bai1_base.base"

Fix-Imports "$base\bai2_pom\LoginPage.java" "bai2_pom.pages"
Fix-Imports "$base\bai2_pom\InventoryPage.java" "bai2_pom.pages"
Fix-Imports "$base\bai2_pom\CartPage.java" "bai2_pom.pages"
Fix-Imports "$base\bai2_pom\CheckoutPage.java" "bai2_pom.pages"

Fix-Imports "$base\bai3_excel_ddt\ExcelReader.java" "bai3_excel_ddt.utils"
Fix-Imports "$base\bai3_excel_ddt\LoginDDTTest.java" "bai3_excel_ddt.tests"

Fix-Imports "$base\bai4_json_faker\JsonReader.java" "bai4_json_faker.utils"
Fix-Imports "$base\bai4_json_faker\UserData.java" "bai4_json_faker.models"
Fix-Imports "$base\bai4_json_faker\JsonDataProvider.java" "bai4_json_faker.dataproviders"
Fix-Imports "$base\bai4_json_faker\UserLoginTest.java" "bai4_json_faker.tests"
Fix-Imports "$base\bai4_json_faker\CheckoutTest.java" "bai4_json_faker.tests"

Fix-Imports "$base\bai5_config\ConfigReader.java" "bai5_config.utils"

Fix-Imports "$base\bai6_retry\RetryAnalyzer.java" "bai6_retry.utils"
Fix-Imports "$base\bai6_retry\RetryListener.java" "bai6_retry.utils"
Fix-Imports "$base\bai6_retry\FlakySimulationTest.java" "bai6_retry.tests"
