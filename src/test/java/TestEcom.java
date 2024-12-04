import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class TestEcom {
    public static void main(String[] args) {

        // Load properties file from resources directory
        Properties props = new Properties();
        try (
                InputStream input = TestEcom.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }

            // Load the properties from the file
            props.load(input);
        } catch (
                IOException ex) {
            ex.printStackTrace();
        }

        // Retrieve the values
        String url = props.getProperty("WEBSITE_URL");
        String username = props.getProperty("USERNAME");
        String password = props.getProperty("PASSWORD");

        // Set up the FirefoxDriver
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();

        try {

            //1. Login Functionality

            // Open the application
            driver.get(url);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

            // Navigate to log in page
            driver.findElement(By.xpath("/html/body/div[2]/header/div[1]/div/ul/li[2]/a")).click();

            // Enter valid login credentials
            WebElement webElement1 = driver.findElement(By.cssSelector("#email"));
            webElement1.clear();
            webElement1.sendKeys(username);

            WebElement webElement2 = driver.findElement(By.xpath("//input[@name= 'login[password]']"));
            webElement2.clear();
            webElement2.sendKeys(password);

            //Click the "Login" button
            driver.findElement(By.cssSelector("button[class='action login primary']")).click();
            Thread.sleep(3000);

            //2. Product Search Functionality

            //search for a product

            WebElement webElement3 = driver.findElement(By.cssSelector("#search"));
            webElement3.clear();
            webElement3.sendKeys("Fusion Backpack");
            Thread.sleep(3000);

            driver.findElement(By.cssSelector("button[class='action search']")).click();
            Thread.sleep(3000);

            WebElement product = driver.findElement(By.cssSelector("a.product-item-link[href='https://magento.softwaretestingboard.com/fusion-backpack.html']"));
            Thread.sleep(3000);
            if (product.isDisplayed()) {
                System.out.println("Product 'Fusion Backpack' is displayed in search results.");
            } else {
                System.out.println("Product 'Fusion Backpack' is NOT displayed in search results.");
            }
            Thread.sleep(3000);

            // Select Prod

            driver.findElement(By.cssSelector("a.action[href='https://magento.softwaretestingboard.com/driven-backpack.html#reviews']")).click();
            Thread.sleep(3000);
            //Select prod

            driver.findElement(By.cssSelector("#product-addtocart-button")).click();
            Thread.sleep(3000);



            //3. Card details & Order place functionality


            //Move to cart
            driver.findElement(By.xpath("/html/body/div[2]/header/div[2]/div[1]/a/span[2]")).click();
            Thread.sleep(3000);
            driver.findElement(By.cssSelector("#top-cart-btn-checkout")).click();
            Thread.sleep(3000);

            //Fill the required cart info

            WebElement webElement4 = driver.findElement(By.cssSelector("input#PRS8B07"));
            Thread.sleep(3000);
            webElement4.clear();
            webElement4.sendKeys("New City Street");
            Thread.sleep(3000);

            WebElement webElement5 = driver.findElement(By.cssSelector("#D14A4R1"));
            Thread.sleep(3000);
            webElement5.clear();
            webElement5.sendKeys("Dhaka");
            Thread.sleep(3000);


            //State selection

            WebElement menuDropdown = driver.findElement(By.cssSelector("#FBMXNL6"));
            menuDropdown.click();
            Thread.sleep(3000);

            //Create action object
            Actions actions = new Actions(driver);
            Thread.sleep(3000);  // Adding this delay to observe the result properly


            //Simulate pressing the s key from keyboard
            actions.sendKeys("a").perform();
            Thread.sleep(3000);
            actions.sendKeys(Keys.ENTER).perform();
            Thread.sleep(3000);


            WebElement webElement6 = driver.findElement(By.cssSelector("#TSW2JRI"));
            webElement6.clear();
            webElement6.sendKeys("12345-6789");
            Thread.sleep(3000);

            WebElement webElement7 = driver.findElement(By.cssSelector("#BJLCX0W"));
            webElement7.clear();
            webElement7.sendKeys("01999999999999");
            Thread.sleep(3000);

            driver.findElement(By.xpath("/html/body/div[2]/main/div[2]/div/div[2]/div[4]/ol/li[2]/div/div[3]/form/div[1]/table/tbody/tr[1]/td[1]/input")).click();
            Thread.sleep(3000);
            driver.findElement(By.xpath("/html/body/div[2]/main/div[2]/div/div[2]/div[4]/ol/li[2]/div/div[3]/form/div[3]/div/button/span")).click();
            Thread.sleep(3000);

            // verifying the actual price
            WebElement priceElement = driver.findElement(By.xpath("/html/body/div[3]/main/div[2]/div/div[2]/aside/div[2]/div/div/div[1]/table/tbody/tr[1]/td/span"));
            Thread.sleep(3000);
            String actualPrice = priceElement.getText();
            System.out.println("Retrieved Price: " + actualPrice);
            Thread.sleep(3000);

            // Define the expected price
            String expectedPrice = "$36";
            Thread.sleep(3000);

            // Verify the price
            if (actualPrice.equals(expectedPrice)) {
                System.out.println("Price matches the expected value!");
            } else {
                System.out.println("Price mismatch! Expected: " + expectedPrice + ", but got: " + actualPrice);
            }
            Thread.sleep(3000);

            // Place order
            driver.findElement(By.cssSelector("button[class='action primary checkout']")).click();
            Thread.sleep(3000);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            driver.quit();
        }


    }
}
