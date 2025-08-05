package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",      //featurefile
    glue = {"stepDefinations", "hooks"},           
    tags = "@booking or @contact",        //bothrun         
	plugin = {
		    "pretty",
		    "html:target/cucumber-reports/cucumber.html",
		    "json:target/cucumber-reports/cucumber.json",
		    "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
		},

    monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
