package main.java.test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class easyOnboarding {
    public static void main(String[] args) throws InterruptedException {

        easyOnboarding eob = new easyOnboarding();

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
        eob.clickElement(logIn);
        System.out.println("Elemnet clicked");
        Thread.sleep(15000);

        // Enter Store URL
        String filePath = "src/main/java/test/data.csv";
        List<String[]> data = readCSV(filePath);
        for (String[] row : data) {
            for (String value : row) {
                // Click on Add Stores
                WebElement addStore = driver.findElement(By.xpath("//*[@id=\"AppFrameMain\"]//button/span/span[contains(text(),'Add store')]"));
                addStore.click();

                // Select Request Access to store
                driver.findElement(By.xpath("//*[@id=\"Polarispopover4\"]/div/div/ul/li[2]/a/div/span")).click();
                Thread.sleep(3000);

                WebElement storeUrl = driver.findElement(By.xpath("//input[@id=\"PolarisTextField1\"]"));
                storeUrl.sendKeys(value);
                Thread.sleep(5000);

                System.out.println("Permission Provider screen");
                WebElement order = driver.findElement(By.xpath("(//span[@class='Polaris-Choice__Control'])[2]"));
                eob.clickElement(order);
                Thread.sleep(2000);
                driver.findElement(By.xpath("(//span[@class='Polaris-Choice__Control'])[50]")).click();
                Thread.sleep(2000);
                driver.findElement(By.xpath("(//span[@class='Polaris-Choice__Control'])[51]")).click();
                Thread.sleep(2000);
                driver.findElement(By.xpath("(//span[@class='Polaris-Choice__Control'])[35]")).click();
                Thread.sleep(2000);
                driver.findElement(By.xpath("(//span[@class='Polaris-Choice__Control'])[37]")).click();
                Thread.sleep(2000);
                driver.findElement(By.xpath("(//span[@class='Polaris-Choice__Control'])[38]")).click();
                Thread.sleep(2000);
                driver.findElement(By.xpath("//button[@id='create-new-store-button']")).click();
                Thread.sleep(2000);
            }
        }
    }

    public void clickElement(WebElement element)  {
        try {
            element.click();
        }
        catch (ElementClickInterceptedException e)
        {
            element.click();
        }
        catch (ElementNotInteractableException ex)
        {
            element.click();
        }
    }
    public static List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
