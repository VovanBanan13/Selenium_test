package ru.home;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;
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
    void test_url_start() {
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
    void loginTestWithoutWait() {
        loginPage = new LoginPage(driver);
        loginPage.inputLogin(login);
        loginPage.inputPasswd(password);
        loginPage.clickSubmit();

        String title = driver.getTitle();
        Assert.assertEquals(title, "Mirapolis LMS");
    }
}
