package Lesson10_Locators_Css_Xpath;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class Locators {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
    }

    @Test
    public void depositReset() {

        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");

        driver.findElement(By.xpath("//*[contains(text(), 'Customer Login')]")).click();
        Select drpUserName = new Select(driver.findElement(By.id("userSelect")));
        drpUserName.selectByVisibleText("Harry Potter");
        driver.findElement(By.xpath("//*[contains(text(), 'Login')]")).click();

        driver.findElement(By.xpath("//*[contains(text(), 'Deposit')]")).click();
        driver.findElement(By.xpath("//input[@placeholder='amount']")).sendKeys("1000");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        driver.findElement(By.xpath("//*[contains(text(), 'Withdrawl')]")).click();
        driver.findElement(By.xpath("//input[@placeholder='amount']")).sendKeys("900");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        driver.findElement(By.xpath("//*[contains(text(), 'Transactions')]")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'Reset')]")).click();
        int countOfTransactions = driver.findElements(By.xpath("//tr[starts-with(@id,'anchor')]")).size();
        Assert.assertEquals(countOfTransactions, 0);
    }

    @Test
    public void addCustomer() {

        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");

        driver.findElement(By.xpath("//*[contains(text(), 'Bank Manager Login')]")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'Add Customer')]")).click();
        driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys("Bob");
        driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys("Doe");
        driver.findElement(By.xpath("//input[@placeholder='Post Code']")).sendKeys("10001");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.switchTo().alert().accept();
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//*[contains(text(), 'Open Account')]")).click();
        List<String> listOfUsers = driver.findElements(By.xpath("//option[@ng-repeat='cust in Customers']"))
                .stream()
                .map(i -> i.getText())
                .collect(Collectors.toList());
        Assert.assertTrue(listOfUsers.contains("Bob Doe"));
    }

    @Test
    public void createAccount() {

        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");

        driver.findElement(By.xpath("//*[contains(text(), 'Bank Manager Login')]")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'Open Account')]")).click();
        Select drpUserName = new Select(driver.findElement(By.id("userSelect")));
        drpUserName.selectByVisibleText("Harry Potter");
        Select drpCurrency = new Select(driver.findElement(By.id("currency")));
        drpCurrency.selectByVisibleText("Dollar");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        String confirmationMessage = driver.switchTo().alert().getText();
        Assert.assertEquals(confirmationMessage, "Account created successfully with account Number :1016");
    }

    @Test
    public void deleteAllAccounts() {

        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        driver.findElement(By.xpath("//*[contains(text(), 'Bank Manager Login')]")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'Customers')]")).click();
        List<WebElement> deleteButtons = driver.findElements(By.xpath("//*[contains(text(), 'Delete')]"));
        deleteButtons.forEach(i -> i.click());
        int countOfCustomers = driver.findElements(By.xpath("//option[@ng-repeat='cust in Customers']")).size();
        Assert.assertEquals(countOfCustomers, 0);
    }

    @AfterMethod
    public void quit() {
        driver.quit();
    }
}
