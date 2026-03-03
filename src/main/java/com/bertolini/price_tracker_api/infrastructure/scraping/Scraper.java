package com.bertolini.price_tracker_api.infrastructure.scraping;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;

@Component
public class Scraper {

    WebDriver driver;

    public boolean connect() {
        try {
            FirefoxOptions options = new FirefoxOptions();
//            options.addArguments("--headless");
            driver =  new FirefoxDriver(options);
            return true;
        } catch (Exception e) {
            System.err.println("Conection to Selenium WebDriver Failed " + Arrays.toString(e.getStackTrace()));
        }
        return false;
    }

    public String getElements(String url, String xpath) {
        try {
            driver.get(url);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            WebElement elementPrice = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))
            );
            return elementPrice.getText();

        } catch (Exception e) {
            System.err.println("Couldn't find price: " + e.getMessage());
        }
        return null;
    }

    public void disconnect() {
        driver.quit();
    }
}
