package Lesson09_SeleniumWebDriverIntro;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class FirstTest {
    @Test
    public void loginTestChrome() {
        System.setProperty("webdriver.chrome.driver", "/Users/alukr/IdeaProjects/ZadornaLearning_Maven/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.get("http://www.i.ua/");
        driver.findElement(By.xpath("//input[@name='login']")).sendKeys("AlnTest");
        driver.findElement(By.xpath("//input[@name='pass']")).sendKeys("GetHired@123");
        driver.findElement(By.xpath("//input[@title='Вхід на пошту']")).click();
        driver.quit();
    }

    @Test
    public void loginTestFirefoxManager() {
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();

        driver.get("http://www.i.ua/");
        driver.findElement(By.xpath("//input[@name='login']")).sendKeys("AlnTest");
        driver.findElement(By.xpath("//input[@name='pass']")).sendKeys("GetHired@123");
        driver.findElement(By.xpath("//input[@title='Вхід на пошту']")).click();
        driver.quit();
    }
}
