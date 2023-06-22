package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class BankLoginTest {

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "http://localhost:9999";
        open("/");
    }

    @AfterAll
    static void teardown() {
        cleanDatabase();
    }

    @Test
    @DisplayName("Позитивный тест: успешный вход в систему")
    void shouldLoginSuccessfully() {
        LoginPage loginPage = new LoginPage();
        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfoWithTestData();
        VerificationPage verificationPage = loginPage.validLogin(authInfo);
        DataHelper.VerificationCode verificationCode = SQLHelper.getVerificationCode();
        DashboardPage dashboardPage = verificationPage.validVerify(verificationCode);
        assertTrue(dashboardPage.isPageDisplayed());
    }

}