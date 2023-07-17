package main.java.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StatusCheck {
    public static void main(String[] args) throws InterruptedException, IOException {
        StatusCheck sc = new StatusCheck();
        // Launching Shopify  Url
        File chromeDriverFile = new File("src/properties/chromedriver_114");
        System.setProperty("webdriver.chrome.driver", chromeDriverFile.getAbsolutePath());
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://partners.shopify.com/2208731/stores");

        // Enter Email and Click Continue
        WebElement emailField = driver.findElement(By.xpath("//*[@id='account_email']"));
        emailField.sendKeys("anubha.pandya@razorpay.com");
        Thread.sleep(10000);
        WebElement continueWithEmail = driver.findElement(By.xpath("//button[@type='submit'] | //button[@name='commit']"));
        continueWithEmail.click();


        // Enter Password and Click Continue
        Thread.sleep(10000);
        WebElement passwordField = driver.findElement(By.xpath("//input[@id=\"account_password\" and @label='Password']"));
        passwordField.sendKeys("Razorpay123");
        WebElement logIn = driver.findElement(By.xpath("//button[@type='submit' and @name='commit']"));
        System.out.println("Elemnet found");
        logIn.click();
        System.out.println("Elemnet clicked");
        Thread.sleep(15000);

        WebElement storeStatus = driver.findElement(By.xpath("//button[@id='Activator-type'and @type='button']"));
        storeStatus.click();
        WebElement accessRequested = driver.findElement(By.xpath("//label[@class='Polaris-Choice' and @for='PolarisCheckbox4']"));
        accessRequested.click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@class='Polaris-ResourceList__HeaderTitleWrapper']")).click();
        driver.findElement(By.xpath("//div[@class='Polaris-ResourceList__HeaderTitleWrapper']")).click();
        Thread.sleep(3000);

        List<WebElement> merchantNameList = driver.findElements(By.xpath("//div[@class='Polaris-Box']/div/div/p"));
        List<WebElement> accessPendingList = driver.findElements(By.xpath("//*[@class=\"Polaris-Badge Polaris-Badge--statusAttention\"]/span[2]"));
        String[] assortedData = new String[merchantNameList.size()];

        for (int i = 0; i < merchantNameList.size(); i++) {
            String merchantName = merchantNameList.get(i).getText();
            String accessStatus = accessPendingList.get(i).getText();
            assortedData[i] = merchantName + "," + accessStatus;
        }

        String filePath = "src/main/java/test/status.csv";
        writeArrayToCSV(filePath, assortedData);

        driver.quit();
    }

    public static void writeArrayToCSV(String filePath, String[] data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String item : data) {
                writer.append(item);
                writer.append("\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    }
