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
import pages.BookingPageFactory;
import utils.ExcelUtils;
import utils.ReusableFunctions;

public class BookingTest {
	
	public static WebDriver driver;
	public static BookingPageFactory bpf;
	public static ReusableFunctions rf;
	public static String excelfilepath = "src/test/resources/test-data/bookingdata.xlsx";
	
	
	@Given("launched the browser and opened the application")
	public void launched_the_browser_and_opened_the_application() throws InterruptedException {
		driver = DriverFactory.getDriver();
		rf = new ReusableFunctions(driver);
		//open url
		rf.openApplicationUrl();
		Thread.sleep(1500);
		//scroll
		WebElement element = driver.findElement(By.id("tm-section-4"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); window.scrollBy(0, -100);", element);

		bpf = new BookingPageFactory(driver);
	}
	
	@When("I enter the all valid details")
	public void i_enter_the_all_valid_details() {
		//take data from excel file and fill in form
		ExcelUtils.loadExcel(excelfilepath);
		String[] data = ExcelUtils.getRowData("Sheet1", 1);	//first row
		
		bpf.enterAllValidDetails(data);
	}

	@When("click on book now button")
	public void click_on_book_now_button() {
		bpf.clickBookNow();
	}

	@Then("I see confirmation message with details")
	public void i_see_confirmation_message_with_details() {
	    String msg =  bpf.getMessage();
	    //check
	    Assert.assertEquals(msg, "Your Booking has been Confirmed !");
	  
	}

	@When("user fills all fields except First Name")
	public void user_fills_all_fields_except_first_name() {
		//take data from excel file and fill in form
		ExcelUtils.loadExcel(excelfilepath);
		String[] data = ExcelUtils.getRowData("Sheet1", 1);	//first row
		
		bpf.enterAllValidDetailsExFN(data);
	}

	@When("user clicks Book Now")
	public void user_clicks_book_now() {
		//click
		bpf.clickBookNow();
	}

	@Then("validation error of required should be shown")  //for name field
	public void validation_error_of_required_should_be_shown() {
		
		String error = bpf.checkRequiredNameError();
	    Assert.assertEquals(error, "Your first name is required");
	}

	@When("user enters all correct details but invalid booking email")
	public void user_enters_all_correct_details_but_invalid_booking_email() {
		//take data from excel file and fill in form
		ExcelUtils.loadExcel(excelfilepath);
		String[] data = ExcelUtils.getRowData("Sheet1", 1);	//first row
		
		bpf.enterAllValidDetailsAndInvalidEmail(data);
	}

	@Then("validation error for invalid email id should be shown")
	public void validation_error_for_invalid_email_id_should_be_shown() {
	    String error = bpf.checkInvalidEmailError();
	    Assert.assertEquals(error, "Invalid Email Id");
	}

	@When("user enters all correct details but zero guests")
	public void user_enters_all_correct_details_but_zero_guests() {
		ExcelUtils.loadExcel(excelfilepath);
		String[] data = ExcelUtils.getRowData("Sheet1", 1);	//first row
		
		bpf.enterAllValidDetailsAndZeroGuests(data);
	}

	@Then("validation error for {int} guests should be shown")
	public void validation_error_for_guests_should_be_shown(Integer int1) {
		
		String error = bpf.checkInvalidZeroGuestsError();
	    Assert.assertEquals(error, "Guests Cannot be Zero");
	}

	@When("user enters all correct details but invalid phone number")
	public void user_enters_all_correct_details_but_invalid_phone_number() {
		ExcelUtils.loadExcel(excelfilepath);
		String[] data = ExcelUtils.getRowData("Sheet1", 1);	//first row 
		
		bpf.enterAllValidDetailsAndInvalidPhoneNo(data);
	}

	@Then("validation error for invalid phone number should be shown")
	public void validation_error_for_invalid_phone_number_should_be_shown() {
		String error = bpf.checkInvalidPhoneNoError();
	    Assert.assertEquals(error, "Invalid Phone Number");
	}

	@When("user enters all correct details but invalid pincode")
	public void user_enters_all_correct_details_but_invalid_pincode() {
		ExcelUtils.loadExcel(excelfilepath);
		String[] data = ExcelUtils.getRowData("Sheet1", 1);	//first row 
		
		bpf.enterAllValidDetailsAndInvalidPincode(data);
	}

	@Then("validation error for invalid pincode should be shown")
	public void validation_error_for_invalid_pincode_should_be_shown() {
		String error = bpf.checkInvalidPincodeError();
	    Assert.assertEquals(error, "Invalid Pincode");
	}
}
