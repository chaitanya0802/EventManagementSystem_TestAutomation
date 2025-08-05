package stepDefinations;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import drivers.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.ContactPageFactory;
import utils.ReusableFunctions;

public class ContactTest {
	
	public static WebDriver driver;
	public static ContactPageFactory cpf;
	public static ReusableFunctions rf;
	
	@Given("launched the browser and opened the web application")
	public void launched_the_browser_and_opened_the_web_application() throws InterruptedException {
		driver = DriverFactory.getDriver();
		// initialize
		rf = new ReusableFunctions(driver);
		//open url
		rf.openApplicationUrl();
		Thread.sleep(1500);	
		//scroll
		WebElement element = driver.findElement(By.id("contact_name"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); window.scrollBy(0, -100);", element);

		cpf = new ContactPageFactory(driver);
	}

	@When("user enters details {string} {string} {string} {string}")
	public void user_enters_details(String string, String string2, String string3, String string4) {
	    cpf.enterDetails(string, string2, string3, string4);
	}

	@When("user clicks Send Message Now")
	public void user_clicks_send_message_now() {
	    cpf.clickSendButton();
	}

	@Then("required message for name should be shown")
	public void required_message_for_name_should_be_shown() {
		String msg =  cpf.getReqNameMessage();
	    //check
	    Assert.assertEquals(msg, "Your name is required");
	}

	@Then("message for invalid email should be shown")
	public void message_for_invalid_email_should_be_shown() {
		String msg =  cpf.getInvalidEmailMessage();
	    //check
	    Assert.assertEquals(msg, "Invalid Email");
	}

	@Then("status of sent message should be shown")
	public void status_of_sent_message_should_be_shown() {
		String msg = null;
		try {
			msg = cpf.getSentStatus();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    //check
	    Assert.assertEquals(msg, "Your message has been sent !");
	}
	
}
