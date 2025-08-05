package utils;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Set;
import java.util.*;

public class ReusableFunctions {

    private static final Logger logger = LoggerUtil.getLogger(ReusableFunctions.class);
    private WebDriver driver;

    public ReusableFunctions(WebDriver driver) {
        this.driver = driver;
    }
    
    // Open application URL from config
    public void openApplicationUrl() {
        String url = ConfigReader.getProperty("baseurl");
        if (url == null || url.isEmpty()) {
            logger.error("Base URL is not specified in the config.properties file.");
            throw new RuntimeException("Base URL is missing.");
        }

        driver.get(url);
        logger.info("Opened URL: " + url);
    }
    
    // Close the browser
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed successfully");
        }
    }

    // Wait for visibility
    public void waitForElementToBeVisible(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(element));
            logger.info("Element is visible: " + element.toString());
        } catch (TimeoutException e) {
            logger.error("Element not visible: " + element.toString(), e);
            throw e;
        }
    }

    // Wait for clickability
    public void waitForElementToBeClickable(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            logger.info("Element is clickable: " + element.toString());
        } catch (TimeoutException e) {
            logger.error("Element not clickable: " + element.toString(), e);
            throw e;
        }
    }
    
    //implicit wait
    public void setImplicitWait(long seconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
        logger.info("Implicit wait set to " + seconds + " seconds");
    }

    // Click the element
    public void clickElement(WebElement element) {
        try {
            waitForElementToBeClickable(element);
            element.click();
            logger.info("Clicked element: " + element.toString());
        } catch (Exception e) {
            logger.error("Failed to click element: " + element.toString(), e);
            throw e;
        }
    }

    // Enter text
    public void enterText(WebElement element, String text) {
        try {
            waitForElementToBeVisible(element);
            element.clear();
            element.sendKeys(text);
            logger.info("Entered text '" + text + "' into element: " + element.toString());
        } catch (Exception e) {
            logger.error("Failed to enter text: " + element.toString(), e);
            throw e;
        }
    }
    
 // get text
    public String getText(WebElement element) {
        String text = null;
        try {
            // Wait for presence (even if not visible) to avoid TimeoutException
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(getByFromElement(element)));

            Thread.sleep(300);
            if (element.isDisplayed()) {
                text = element.getText().trim();
            }

            if (text == null || text.isEmpty()) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                text = (String) js.executeScript("return arguments[0].innerText || arguments[0].textContent;", element);
                if (text != null) text = text.trim();
            }

            logger.info("Got text '" + text + "' from element: " + element.toString());
        } catch (Exception e) {
            logger.error("Failed to get text: " + element.toString(), e);
            throw new RuntimeException("Error while fetching text from element", e);
        }

        return text;
    }
    
    // Convert WebElement to By (for logging or ExpectedConditions)
    private By getByFromElement(WebElement element) {
        String desc = element.toString();
        try {
            String locator = desc.substring(desc.indexOf("->") + 3, desc.length() - 1).trim();
            String[] parts = locator.split(": ");
            String strategy = parts[0].trim();
            String value = parts[1].trim();

            switch (strategy) {
                case "id": return By.id(value);
                case "name": return By.name(value);
                case "class name": return By.className(value);
                case "tag name": return By.tagName(value);
                case "link text": return By.linkText(value);
                case "partial link text": return By.partialLinkText(value);
                case "css selector": return By.cssSelector(value);
                case "xpath": return By.xpath(value);
                default: throw new IllegalArgumentException("Unknown locator strategy: " + strategy);
            }
        } catch (Exception e) {
            logger.error("Failed to parse WebElement to By from: " + desc, e);
            throw new RuntimeException("Unable to derive By locator from WebElement", e);
        }
    }


    
 // Select option
    public void selectOption(WebElement element, String option) {
        try {
            waitForElementToBeVisible(element);
            Select sel = new Select(element);
            sel.selectByVisibleText(option); // Match with dropdown text
            logger.info("Selected option '" + option + "' from element: " + element.toString());
        } catch (NoSuchElementException e) {
            logger.error("Option '" + option + "' not found in dropdown: " + element.toString(), e);

            // Print all available options
            Select sel = new Select(element);
            List<WebElement> allOptions = sel.getOptions();
            logger.error("Available options are:");
            for (WebElement opt : allOptions) {
                logger.error("Option: " + opt.getText());
            }

            throw e;
        } catch (Exception e) {
            logger.error("Failed to select option from dropdown: " + element.toString(), e);
            throw e;
        }
    }


    // Use ScreenshotUtils to capture and log screenshot
    public void captureAndLogScreenshot(String scenarioName) {
        try {
            String path = ScreenshotUtils.captureScreenshot(driver, scenarioName);
            logger.info("Screenshot taken for scenario [" + scenarioName + "] saved at: " + path);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot for scenario: " + scenarioName, e);
        }
    }

    // Scroll down and click
    public void scrollDownAndClick(String xpath) throws InterruptedException {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,2000)");
            Thread.sleep(2000);
            WebElement element = driver.findElement(By.xpath(xpath));
            js.executeScript("arguments[0].click();", element);
            logger.info("Scrolled and clicked element with XPath: " + xpath);
        } catch (Exception e) {
            logger.error("Scroll and click failed for XPath: " + xpath, e);
            throw e;
        }
    }

    // Window handling
    public void handleWindowsAndSwitchBack(String xpath) {
        try {
            driver.findElement(By.xpath(xpath)).click();
            String mainWindow = driver.getWindowHandle();
            Set<String> allWindows = driver.getWindowHandles();

            for (String window : allWindows) {
                driver.switchTo().window(window);
                logger.info("Switched to window: " + driver.getTitle());
            }

            driver.switchTo().window(mainWindow);
            logger.info("Switched back to main window.");
        } catch (Exception e) {
            logger.error("Window handling failed.", e);
            throw e;
        }
    }

    // Alert handling with input
    public void handleAlert(String value) throws InterruptedException {
        try {
            Alert alert = driver.switchTo().alert();
            Thread.sleep(2000);
            alert.sendKeys(value);
            alert.accept();
            logger.info("Alert handled with value: " + value);
        } catch (NoAlertPresentException e) {
            logger.error("No alert present to handle.", e);
            throw e;
        }
    }

    // Print and log page title
    public void printPageTitle() {
        try {
            String title = driver.getTitle();
            logger.info("Page title: " + title);
            System.out.println(title);
        } catch (Exception e) {
            logger.error("Failed to get page title.", e);
        }
    }
    
    //sleep
    public void wait(int time) {
    	try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}