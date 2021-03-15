import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
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

public class LoginBotWithInvalidValidUser {
    // Only the last final entries of both arrays are valid
    private static String[] usernames = {"", "su@mymail.sutd.edu.sg", "su@@sutd.edu.sg",
            "esc@istd50/?10.3", "escistd50.003", "escistd50.003"};
    private static String[] passwords = {"", "Some Password with Whitespaces",
            "aaaaaaaaaaaaaaaaaaaaaaa", "$$6**@I.do.not.know", "wrong_password", "SUTD@Singapore"};

    @Test
    public void testInvalidInputs() throws InterruptedException {
        // System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");
        WebDriver driver = new FirefoxDriver();

        for (int i = 0; i < usernames.length - 1; i++) {
            driver.get("https://statcounter.com/login/");

            // Get the user name field of the account page
            WebElement username = driver.findElement(By.id("username"));

            // Send my user name to fill up the box
            username.sendKeys(usernames[i]);

            // Locate the "Next" button in the account page
            WebElement password = driver.findElement(By.id("password"));
            password.sendKeys(passwords[i]);

            // Login and :)
            WebElement nextButton = driver.findElement(By.className("submit"));
            nextButton.click();

            // Explicitly wait until the password field is present in the page
            try {
                WebDriverWait wait = new WebDriverWait(driver, 10);
                // Wait only until the project front page loads
                wait.until(
                        ExpectedConditions.elementToBeClickable(By.id("project-name-p12207705")));
                // Click project link
                driver.findElement(By.id("project-name-p12207705")).click();
                fail("NoSuchElementException should have been raised.");
            } catch (Exception NoSuchElementException) {
                System.out.println("login/password name invalid");
            }
        }
    }

    @Test
    public void testValidInput() throws InterruptedException {
        // System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");
        WebDriver driver = new FirefoxDriver();

        driver.get("https://statcounter.com/login/");

        // Get the user name field of the account page
        WebElement username = driver.findElement(By.id("username"));

        // Send my user name to fill up the box
        username.sendKeys(usernames[usernames.length - 1]);

        // Locate the "Next" button in the account page
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(passwords[passwords.length - 1]);

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
            fail("NoSuchElementException should not have been raised.");
        }
    }
}
