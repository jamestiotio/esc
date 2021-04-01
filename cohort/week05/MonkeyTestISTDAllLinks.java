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

public class MonkeyTestISTDAllLinks {
    public static void main(String[] args) throws InterruptedException {
        // System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");
        WebDriver driver = new FirefoxDriver();

        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        // WebDriver driver = new ChromeDriver();

        driver.get("https://istd.sutd.edu.sg/");

        // Get all the links
        java.util.List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("There are " + links.size() + " links in total.");

        // Print all the links
        for (int i = 0; i < links.size(); i = i + 1) {
            System.out.println(i + " " + links.get(i).getText());
            System.out.println(i + " " + links.get(i).getAttribute("href"));
        }

        // Maximize the browser window
        driver.manage().window().maximize();

        // Click and visit all links in the ISTD website's main page
        for (int i = 0; i < links.size(); i++) {
            int currentLink = i + 1;
            System.out.println(
                    "Attempting to visit link " + currentLink + " out of " + links.size() + "...");
            System.out.println("*** Navigating to" + " " + links.get(i).getAttribute("href"));
            // if (links.get(i).getAttribute("href") == null
            // || links.get(i).getAttribute("href").equals("https://sudiptac.bitbucket.io"))
            if (links.get(i).getAttribute("href") == null) continue;
            boolean staleElementLoaded = true;
            // The loop checks whether the elements are properly loaded
            while (staleElementLoaded) {
                try {
                    // Navigate to the link
                    driver.navigate().to(links.get(i).getAttribute("href"));
                    Thread.sleep(3000);
                    // Click the back button in browser
                    driver.navigate().back();
                    links = driver.findElements(By.tagName("a"));
                    System.out
                            .println("*** Navigated to" + " " + links.get(i).getAttribute("href"));
                    staleElementLoaded = false;
                } catch (StaleElementReferenceException e) {
                    staleElementLoaded = true;
                }
            }
        }

        // Invoke quit() instead of close() for a proper and safe closure
        driver.quit();
    }
}
