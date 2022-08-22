package Lesson11_SeleniumPractice_Waits;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

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

    //Task A)
    @Test
    public void deleteAllBooks() {

        driver.get("https://demoqa.com/login");
        driver.findElement(By.id("userName")).sendKeys("TestAln");
        driver.findElement(By.id("password")).sendKeys("TestFort@123");
        driver.findElement(By.id("login")).click();

//3. Go to Book Store (Here you can catch ElementClickIntercepted Exception, to
//avoid this use scroll or click using JavaScriptExecutor)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gotoStore"))).click();

        WebElement gitPocketGuideBook = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//a[contains(text(), 'Git Pocket Guide')]")));

        js.executeScript("arguments[0].scrollIntoView();", gitPocketGuideBook);

//4. Choose Git Pocket Guide
        gitPocketGuideBook.click();

//5. Click “Add to your Collection”
        WebElement interactionsButton = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//div[contains(text(), 'Interactions')]")));

        js.executeScript("arguments[0].scrollIntoView();", interactionsButton);

        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//button[contains(text(), 'Add To Your Collection')]"))).click();

//6. Handle alert similar as from last lesson (Tap Ok)
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        driver.switchTo().defaultContent();

//7. Click Back To Store
        driver.findElement(By.xpath("//button[contains(text(), 'Back To Book Store')]")).click();

//8. Click "You Don’t Know JS”
        WebElement YouDontKnowJSBook = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//a[contains(text(), \"You Don't Know JS\")]")));
        js.executeScript("arguments[0].scrollIntoView();", YouDontKnowJSBook);
        YouDontKnowJSBook.click();

//9. “Add to your Collection”
        interactionsButton = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//div[contains(text(), 'Interactions')]")));
        js.executeScript("arguments[0].scrollIntoView();", interactionsButton);

        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//button[contains(text(),'Add To Your Collection')]"))).click();

//10.Handle Alert
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        driver.switchTo().defaultContent();

//11. Go to “Profile”
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//span[contains(text(), 'Profile')]"))).click();

//12.Click “Delete All Books"*/
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
        wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//button[contains(text(), 'Delete All Books')]"))).click();

        driver.findElement(By.id("closeSmallModal-ok")).click();

        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        driver.switchTo().defaultContent();

        List<WebElement> listOfBooks = driver.findElements(By.xpath("//div[@class='rt-td']/img"));

        Assert.assertEquals(listOfBooks.size(), 0);
    }

    // Task B)
    @Test
    public void addProduct() {

        driver.get("https://www.saucedemo.com/");

//2. Login with standard_user
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

//3. Add to cart first 4 products
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket")).click();

//4. Navigate to cart
        driver.findElement(By.xpath("//a[contains(@class, 'shopping_cart_link')]")).click();

//5. Tap Checkout
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
        driver.findElement(By.id("checkout")).click();

//6. Fill user data (First Name / Last Name / Zip)
        driver.findElement(By.id("first-name")).sendKeys("FirstName");
        driver.findElement(By.id("last-name")).sendKeys("LastName");
        driver.findElement(By.id("postal-code")).sendKeys("10001");

//7. Tap Continue
        driver.findElement(By.id("continue")).click();

//8. Tap Finish
        driver.findElement(By.id("finish")).click();

//9. Tap Back To Home
        driver.findElement(By.id("back-to-products")).click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
    }

    // Task C)
    @Test
    public void manageToDoList() {

        driver.get("http://webdriveruniversity.com/To-Do-List/index.html");

//2. Add new To Do “MAKE HOMEWORK”
        driver.findElement(By.xpath("//input[contains(@placeholder, 'Add new todo')]")).sendKeys("MAKE HOMEWORK");
        driver.findElement(By.xpath("//input[contains(@placeholder, 'Add new todo')]")).sendKeys(Keys.ENTER);

//3. Add new To Do “Practice Automation”
        driver.findElement(By.xpath("//input[contains(@placeholder, 'Add new todo')]")).sendKeys("Practice Automation");
        driver.findElement(By.xpath("//input[contains(@placeholder, 'Add new todo')]")).sendKeys(Keys.ENTER);

//4. Mark Practice magic as resolver
        driver.findElement(By.xpath("//li[contains(text(), 'Practice magic')]")).click();

//5. Если получится - удалить из списка “Buy New Robes” (там нужен будет mouse hover)
        WebElement buyNewRobes = driver.findElement(By.xpath("//li[contains(text(), 'Buy new robes')]"));
        Actions action = new Actions(driver);
        action.moveToElement(buyNewRobes).perform();
        driver.findElement(By.xpath("//li[contains(text(), 'Buy new robes')]//i[contains(@class,'fa fa-trash')]")).click();

        List<String> listOfTasks = driver.findElements(By.xpath("//li"))
                .stream()
                .map(i -> i.getText())
                .collect(Collectors.toList());
        Assert.assertEquals(listOfTasks.toString(), "[Go to potion class, Buy new robes, Practice magic, MAKE HOMEWORK, Practice Automation]");
    }

    @AfterMethod
    public void quit() {
        driver.quit();
    }
}
