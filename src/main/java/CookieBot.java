import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CookieBot {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://orteil.dashnet.org/cookieclicker/beta/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // --- Wait for English selection ---
        WebElement englishBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(text(),'English')]")
                )
        );
        englishBtn.click();

        // --- Wait for cookie ---
        WebElement cookie = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.id("bigCookie")
                )
        );

        while (true) {
            cookie.click();

            // Read cookie count
            WebElement cookiesText = driver.findElement(By.id("cookies"));
            String number = cookiesText.getText().split(" ")[0].replace(",", "");

            int cookies = 0;
            try {
                cookies = Integer.parseInt(number);
            } catch (NumberFormatException ignored) {}

            // Try buying the first 4 upgrades
            for (int i = 0; i < 4; i++) {
                WebElement priceEl = driver.findElement(By.id("productPrice" + i));
                String priceText = priceEl.getText().replace(",", "");
                int price = 0;
                try {
                    price = Integer.parseInt(priceText);
                } catch (NumberFormatException ignored) {}

                if (cookies >= price) {
                    WebElement product = driver.findElement(By.id("product" + i));
                    product.click();
                    break;
                }
            }
        }
    }
}
