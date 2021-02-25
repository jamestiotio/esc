import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FillMessageText {
    static String myUserName = "escistd50.003";
    static String myPassword = "SUTD@Singapore";

    public static void main(String[] args) throws InterruptedException {
        // System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");
        WebDriver driver = new FirefoxDriver();
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        // WebDriver driver = new ChromeDriver();

        driver.get("https://statcounter.com/login/");

        // Get the user name field of the account page
        WebElement username = driver.findElement(By.id("username"));

        Thread.sleep(3000);

        // Send my user name to fill up the box
        username.sendKeys(myUserName);

        Thread.sleep(3000);

        // Locate the "Next" button in the account page
        WebElement password = driver.findElement(By.id("password"));

        // Write password
        password.sendKeys(myPassword);

        Thread.sleep(3000);

        // Login and :)
        WebElement nextButton = driver.findElement(By.className("submit"));
        nextButton.click();

        Thread.sleep(3000);

        // Click project name
        WebElement project = driver.findElement(By.id("project-name-p12207705"));
        project.click();
    }
}