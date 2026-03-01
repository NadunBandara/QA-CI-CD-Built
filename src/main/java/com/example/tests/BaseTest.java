package com.example.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
            options.addArguments("--incognito"); // Use incognito to avoid saved data

            // Remove "Chrome is being controlled by automated test software" infobar
            options.setExperimentalOption("excludeSwitches",
                    Collections.singletonList("enable-automation"));

            // Headless mode for CI/CD
            if (System.getenv("CI") != null) {
                options.addArguments("--headless");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");
            }

            driver = new ChromeDriver(options);
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}