package drivers;

import java.time.Duration;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;
import utils.LoggerUtil;

public class DriverFactory {

    protected static WebDriver driver;
    private static final Logger logger = LoggerUtil.getLogger(DriverFactory.class);

    public DriverFactory(WebDriver driver) {
        DriverFactory.driver = driver;
    }

    // getDriver based on config
    public static WebDriver getDriver() {
    	if(driver!=null) {
    		return driver;
    	}
    	
        String browser = ConfigReader.getProperty("browser").toLowerCase();
        int implicitWait = ConfigReader.getIntProperty("implicitWait");

        logger.info("Launching browser: " + browser);

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                logger.info("Chrome browser launched");
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                logger.info("Firefox browser launched");
                break;

            default:
                logger.error("Unsupported browser: " + browser);
                throw new RuntimeException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        logger.info("Browser window maximized and implicit wait set to " + implicitWait + " seconds");
		return driver;
    }

    
}
