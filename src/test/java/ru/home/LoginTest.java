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
        loginPage.inputPassword(password);
        loginPage.clickSubmit();
        WebElement wait = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("avatar-full-name")));

        String title = driver.getTitle();
        Assert.assertTrue(title.contains("Главная страница"));
    }

    @Test
    void loginWithoutWaitTest() {
        loginPage = new LoginPage(driver);
        loginPage.inputLogin(login);
        loginPage.inputPassword(password);
        loginPage.clickSubmit();

        String title = driver.getTitle();
        Assert.assertEquals(title, "Mirapolis LMS");
    }

    @Test
    void loginNullDataTest() {
        loginPage = new LoginPage(driver);
        loginPage.inputLogin("");
        loginPage.inputPassword("");
        loginPage.clickSubmit();

        String message = driver.switchTo().alert().getText();
        Assert.assertTrue(message.contains("Неверные данные для авторизации"));
    }

    @Test
    void loginInvalidLoginTest() {
        loginPage = new LoginPage(driver);
        loginPage.inputLogin("default_login");
        loginPage.inputPassword(password);
        loginPage.clickSubmit();

        String message = driver.switchTo().alert().getText();
        Assert.assertTrue(message.contains("Неверные данные для авторизации"));
    }

    @Test
    void loginInvalidPasswordTest() {
        loginPage = new LoginPage(driver);
        loginPage.inputLogin(login);
        loginPage.inputPassword("default_password");
        loginPage.clickSubmit();

        String message = driver.switchTo().alert().getText();
        Assert.assertTrue(message.contains("Неверные данные для авторизации"));
    }

    @Test
    void loginChangeCaseLoginTest() {
        loginPage = new LoginPage(driver);
        loginPage.inputPassword(password);
        String newLogin = login.toUpperCase();
        if (newLogin.equals(login))
        {
            newLogin = login.toLowerCase();
            if (newLogin.equals(login))
            {
                loginPage.inputLogin(newLogin);
                loginPage.clickSubmit();
                String title = driver.getTitle();
                Assert.assertEquals(title, "Mirapolis LMS");
                return;
            }
        }
        loginPage.inputLogin(newLogin);
        loginPage.clickSubmit();

        String message = driver.switchTo().alert().getText();
        Assert.assertTrue(message.contains("Неверные данные для авторизации"));
    }  // Тест не проходит, поле не учитывает регистр букв у логина

    @Test
    void loginChangeCasePasswordTest() {
        loginPage = new LoginPage(driver);
        loginPage.inputLogin(login);
        String newPassword = password.toUpperCase();
        if (newPassword.equals(password))
        {
            newPassword = password.toLowerCase();
            if (newPassword.equals(password))
            {
                loginPage.inputPassword(newPassword);
                loginPage.clickSubmit();
                String title = driver.getTitle();
                Assert.assertEquals(title, "Mirapolis LMS");
                return;
            }
        }
        loginPage.inputPassword(newPassword);
        loginPage.clickSubmit();
        String message = driver.switchTo().alert().getText();
        Assert.assertTrue(message.contains("Неверные данные для авторизации"));
    }

    @Test
    void loginLoginWithSpacesTest() {
        loginPage = new LoginPage(driver);
        loginPage.inputLogin("    " + login + "    ");
        loginPage.inputPassword(password);
        loginPage.clickSubmit();

        String message = driver.switchTo().alert().getText();
        Assert.assertTrue(message.contains("Неверные данные для авторизации"));
    }  // Тест не проходит, поле не учитывает пробелы в начале и в конце логина

    @Test
    void loginPasswordWithSpacesTest() {
        loginPage = new LoginPage(driver);
        loginPage.inputLogin(login);
        loginPage.inputPassword("    " + password + "    ");
        loginPage.clickSubmit();

        String message = driver.switchTo().alert().getText();
        Assert.assertTrue(message.contains("Неверные данные для авторизации"));
    }  // Тест не проходит, поле не учитывает пробелы в начале и в конце пароля
}
