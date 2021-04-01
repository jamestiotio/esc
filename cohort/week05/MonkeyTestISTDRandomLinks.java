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

import java.security.SecureRandom;

public class MonkeyTestISTDRandomLinks {
    public static void main(String[] args) throws InterruptedException {
        // System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");
        WebDriver driver = new FirefoxDriver();

        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        // WebDriver driver = new ChromeDriver();

        driver.get("https://istd.sutd.edu.sg/");

        SecureRandom rng = new SecureRandom();

        // Get all the links
        java.util.List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("There are " + links.size() + " links in total.");

        // Maximize the browser window
        driver.manage().window().maximize();

        while (true) {
            int nextLink = rng.nextInt(links.size());
            System.out
                    .println("*** Navigating to" + " " + links.get(nextLink).getAttribute("href"));

            if (links.get(nextLink).getAttribute("href") == null) continue;

            boolean staleElementLoaded = true;
            // The loop checks whether the elements are properly loaded
            while (staleElementLoaded) {
                try {
                    // Navigate to the link
                    driver.navigate().to(links.get(nextLink).getAttribute("href"));
                    Thread.sleep(3000);
                    // Click the back button in browser
                    driver.navigate().back();
                    links = driver.findElements(By.tagName("a"));
                    System.out.println(
                            "*** Navigated to" + " " + links.get(nextLink).getAttribute("href"));
                    staleElementLoaded = false;
                } catch (StaleElementReferenceException e) {
                    staleElementLoaded = true;
                }
            }
        }
    }
}
