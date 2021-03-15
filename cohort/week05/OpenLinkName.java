import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class OpenLinkName {
    public static void main(String[] args) {
        // Set the firefox driver
        // System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        WebDriver driver = new FirefoxDriver();
        // WebDriver driver = new ChromeDriver();

        // Open my webpage
        driver.get("https://sudiptac.bitbucket.io/");

        // Click the link with name "ASSET research group"
        driver.findElement(By.linkText("ASSET Research Group")).click();

        // Click the link name "Sakshi Udeshi"
        driver.findElement(By.linkText("Sakshi Udeshi")).click();

        // Click the link name "Publications"
        driver.findElement(By.linkText("Publications")).click();
    }
}
