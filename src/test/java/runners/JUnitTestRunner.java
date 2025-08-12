package runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"stepDefinations", "hooks"},
    tags = "@booking or @contact",
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
public class JUnitTestRunner {
}
