package ru.home;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginTest {

    final String login = "fominaelena";
    final String password = "1P73BP4Z";
    final String url_address = "https://lmslite47vr.demo.mirapolis.ru/mira";

    public static LoginPage loginPage;
    public static WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        driver = new ChromeDriver();
        driver.get(url_address);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @AfterAll
    static void tearDownComplete() {
        if (driver != null)
        {
            driver.quit();
        }
    }

    @Test
    void urlTest() {
        String title = driver.getTitle();
        Assert.assertEquals(title, "Авторизация");
    }

    @Test
    void loginTest() throws InterruptedException {
        loginPage = new LoginPage(driver);
        loginPage.inputLogin(login);
        loginPage.inputPasswd(password);
        loginPage.clickSubmit();
        WebElement wait = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("avatar-full-name")));

        String title = driver.getTitle();
        Assert.assertEquals(title, "Главная страница");
    }

    @Test
    void loginWithoutWaitTest() {
        loginPage = new LoginPage(driver);
        loginPage.inputLogin(login);
        loginPage.inputPasswd(password);
        loginPage.clickSubmit();

        String title = driver.getTitle();
        Assert.assertEquals(title, "Mirapolis LMS");
    }

    @Test
    void loginNullDataTest() {
        loginPage = new LoginPage(driver);
        loginPage.inputLogin("");
        loginPage.inputPasswd("");
        loginPage.clickSubmit();

        String message = driver.switchTo().alert().getText();
        Assert.assertTrue(message.contains("Неверные данные для авторизации"));
    }

    @Test
    void loginInvalidLoginTest() {
        loginPage = new LoginPage(driver);
        loginPage.inputLogin("default_login");
        loginPage.inputPasswd(password);
        loginPage.clickSubmit();

        String message = driver.switchTo().alert().getText();
        Assert.assertTrue(message.contains("Неверные данные для авторизации"));
    }

    @Test
    void loginInvalidPasswordTest() {
        loginPage = new LoginPage(driver);
        loginPage.inputLogin(login);
        loginPage.inputPasswd("default_password");
        loginPage.clickSubmit();

        String message = driver.switchTo().alert().getText();
        Assert.assertTrue(message.contains("Неверные данные для авторизации"));
    }

    @Test
    void loginUpperCaseLoginTest() {
        loginPage = new LoginPage(driver);
        String newLogin = login.toUpperCase();
        loginPage.inputLogin(newLogin);
        loginPage.inputPasswd(password);
        loginPage.clickSubmit();

        String message = driver.switchTo().alert().getText();
        Assert.assertTrue(message.contains("Неверные данные для авторизации"));
    }

    @Test
    void loginUpperCasePasswordTest() {
        loginPage = new LoginPage(driver);
        String newPassword = login.toUpperCase();
        loginPage.inputLogin(login);
        loginPage.inputPasswd(newPassword);
        loginPage.clickSubmit();

        String message = driver.switchTo().alert().getText();
        Assert.assertTrue(message.contains("Неверные данные для авторизации"));
    }

    @Test
    void loginLoginWithSpacesTest() {
        loginPage = new LoginPage(driver);
        loginPage.inputLogin("    " + login + "    ");
        loginPage.inputPasswd(password);
        loginPage.clickSubmit();

        String message = driver.switchTo().alert().getText();
        Assert.assertTrue(message.contains("Неверные данные для авторизации"));
    }
}
