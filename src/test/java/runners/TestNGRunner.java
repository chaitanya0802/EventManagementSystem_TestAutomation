package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",      //featurefile
    glue = {"stepDefinations", "hooks"},           
    tags = "@booking or @contact",        //bothrun         
	plugin = {
		    "pretty",
		    "html:target/cucumber-report/cucumber.html",
		    "json:target/cucumber-report/cucumber.json",
		    "rerun:target/rerun.txt",
		    "plugins.CucumberExtentReportPlugin",
		    "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
		},

    monochrome = true
)
public class TestNGRunner extends AbstractTestNGCucumberTests {
}
