package Lesson12_SeleniumPractice;

import com.sun.xml.internal.bind.v2.TODO;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;

public class TestWeb {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        js = (JavascriptExecutor) driver;
    }

    // Task 1
    @Test
    public void getLength() {
        driver.get("https://shop.demoqa.com/");
//3. Get Page Title name and Title length
        String pageTitle = driver.getTitle();
        int pageTitleLength = pageTitle.length();
//4. Print Page Title and Title length on the Eclipse Console.
        System.out.println("The page title is: " + pageTitle + ". The page title length is: " + pageTitleLength);
//5. Get Page URL and verify if it is a correct page opened
        Assert.assertEquals(driver.getCurrentUrl(), "https://shop.demoqa.com/");
//6. Get Page Source (HTML Source code) and Page Source length
        String pageSource = driver.getPageSource();
//7. Print Page Length.
        System.out.println("The Page Source length is: " + pageSource.length());
//8. Close the Browser.
    }

    // Task 2
    @Test
    public void newWindow() {
        driver.get("https://www.toolsqa.com/automation-practice-switch-windows/");
//3. Click “New Window”
        driver.switchTo().newWindow(WindowType.TAB);
//4. Close the browser using close() command
        driver.close();
    }

    // Task 3
    @Test
    public void browserNavigation() {
        driver.get("https://demoqa.com/");
//3. Click on Registration link using ToDo
        driver.findElement(By.xpath("//a[@href = \"https://www.toolsqa.com/selenium-training/\"]")).click();
        ArrayList<String> browserTabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(1));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Go To Registration')]"))).click();
//4. Come back to Home page (Use ‘Back’ command)
        driver.navigate().back();
//5. Again go back to Registration page (This time use ‘Forward’ command)
        driver.navigate().forward();
//6. Again come back to Home page (This time use ‘To’ command)
        driver.navigate().to("https://demoqa.com/");
//7. Refresh the Browser (Use ‘Refresh’ command)
        driver.navigate().refresh();
    }

    // Task 4
    @Test
    public void startReset() {
        driver.get("https://demoqa.com/progress-bar");
//3. Click Start
        WebElement startButton = driver.findElement(By.id("startStopButton"));
        startButton.click();
//4. Add WebDriverWait for waiting “Reset” button is displayed
//5. Click reset
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resetButton"))).click();
//6. Add WebDriverWait for waiting “Start” button is displayed
//    Пункт 6 нужно выполнить повторно инициализацию элемента (т к он исчезал с экрана)
//    Если этого не сделать будет словлен StaleElementReference Exception
//    Напомню инициализация выглядит
//    WebElement nameOfElement = driver.findElement("bla-bla-bla)
        startButton = driver.findElement(By.id("startStopButton"));
        wait.until(ExpectedConditions.visibilityOf(startButton));

        Assert.assertTrue(startButton.isDisplayed());
    }

    // Task 5
    @Test
    public void waitForButton() {
        driver.get("https://demoqa.com/dynamic-properties");
//3. Add WebDriverWait for waiting to button “Visible After 5 Seconds” to be displayed
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("enableAfter")));
        Assert.assertTrue(driver.findElement(By.id("enableAfter")).isDisplayed());
    }

    @AfterMethod
    public void quit() {
        driver.quit();
    }
}
