package hooks;

import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.MediaEntityBuilder;
import drivers.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.ExtentReportManager;
import utils.ScreenshotUtils;
import org.apache.logging.log4j.Logger;


import org.apache.logging.log4j.LogManager;

public class Hooks {
	
	private static final Logger logger = LogManager.getLogger(Hooks.class);
    private WebDriver driver = DriverFactory.getDriver();

    @Before
    public void setUp() {
        System.out.println("started");
    }


    @After
    public void afterScenario(Scenario scenario) {
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (scenario.isFailed()) {
            logger.info("Scenario failed: " + scenario.getName());

            try {
                String path = ScreenshotUtils.captureScreenshot(driver, scenario.getName());
                scenario.attach(ScreenshotUtils.captureScreenshotBytes(driver), "image/png", scenario.getName());

                //for extentreport screenshot path
                ExtentReportManager.getTest().fail("Screenshot for failure:",
                        MediaEntityBuilder.createScreenCaptureFromPath(path).build());

                logger.info("Screenshot attached for failed scenario: " + path);
            } catch (Exception e) {
                logger.error("Failed to capture or attach screenshot for failed scenario", e);
            }
        }
    }

}