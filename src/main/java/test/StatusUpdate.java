package main.java.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StatusUpdate {
        public static void main(String[] args) throws InterruptedException, IOException {

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

            String csvFilePath = "src/main/java/test/status.csv";
            List<String[]> csvData = readCSV(csvFilePath);

            WebElement storeStatus = driver.findElement(By.xpath("//button[@id='Activator-type'and @type='button']"));
            storeStatus.click();
            WebElement accessRequested = driver.findElement(By.xpath("//label[@class='Polaris-Choice' and @for='PolarisCheckbox4']"));
            accessRequested.click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//div[@class='Polaris-ResourceList__HeaderTitleWrapper']")).click();
            driver.findElement(By.xpath("//div[@class='Polaris-ResourceList__HeaderTitleWrapper']")).click();
            Thread.sleep(2000);
            List<WebElement> merchantNameList = driver.findElements(By.xpath("//div[@class='Polaris-Box']/div/div/p"));
            List<WebElement> accessPendingList = driver.findElements(By.xpath("//*[@class=\"Polaris-Badge Polaris-Badge--statusAttention\"]/span[2]"));
            merchantNameList.addAll(accessPendingList);

            for(int i=0;i< merchantNameList.size() ;i++){
                String[] merchantName = {merchantNameList.get(i).getText()};
                String[] accessStatus =   {accessPendingList.get(i).getText()};
                concatenateArrays(merchantName,accessStatus);
            }


            List<String[]> newColumnsData = new ArrayList<>();

            for (int i = 0; i < merchantNameList.size(); i++) {
                String[] rowData = {merchantNameList.get(i).getText()};
                if (csvData.size() > i) {
                    String[] existingRowData = csvData.get(i);
                    rowData = concatenateArrays(existingRowData, rowData);
                }
                newColumnsData.add(rowData);
            }

            updateCSV(csvFilePath, newColumnsData);

            // Close the WebDriver
            driver.quit();
        }

    public static List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void updateCSV(String filePath, List<String[]> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            for (String[] row : data) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < row.length; i++) {
                    sb.append(row[i]);
                    if (i != row.length - 1) {
                        sb.append(",");
                    }
                }
                bw.newLine();
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] concatenateArrays(String[] arr1, String[] arr2) {
        String[] result = new String[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }
}

