package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.Collections;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();

            // Disable password manager popups and save password prompts
            options.addArguments("--disable-save-password-bubble");
            options.addArguments("--disable-autofill");
            options.addArguments("--disable-notifications");
            options.addArguments("--incognito");

            // Remove "Chrome is being controlled by automated test software" infobar
            options.setExperimentalOption("excludeSwitches",
                    Collections.singletonList("enable-automation"));

            // Headless mode for CI/CD
            if (System.getenv("CI") != null) {
                options.addArguments("--headless");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");
                // Add user-agent to avoid detection (optional)
                options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36");
            }

            driver = new ChromeDriver(options);
        }
        // Add other browsers if needed (firefox, edge)

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15)); // increased to 15s

        // Navigate to the site
        driver.get("https://www.saucedemo.com/");

        // Explicit wait for the login page to load (ensures page is ready)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}