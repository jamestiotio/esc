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

public class ExplicitWaitStatCounterLogin {
    static String myUserName = "escistd50.003";
    static String myPassword = "SUTD@Singapore";

    public static void main(String[] args) throws InterruptedException {
        // System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");
        WebDriver driver = new FirefoxDriver();

        driver.get("https://statcounter.com/login/");

        // Get the user name field of the account page
        WebElement username = driver.findElement(By.id("username"));

        // Send my user name to fill up the box
        username.sendKeys(myUserName);

        // Locate the "Next" button in the account page
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(myPassword);

        // Login and :)
        WebElement nextButton = driver.findElement(By.className("submit"));
        nextButton.click();

        // Explicitly wait until the password field is present in the page
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            // Wait only until the project front page loads
            wait.until(ExpectedConditions.elementToBeClickable(By.id("project-name-p12207705")));
            // Click project link
            driver.findElement(By.id("project-name-p12207705")).click();
        } catch (Exception NoSuchElementException) {
            System.out.println("login/password name invalid");
        }
    }
}
