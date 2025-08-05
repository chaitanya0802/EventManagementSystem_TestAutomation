package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.ReusableFunctions;

public class BookingPageFactory {
	
	public WebDriver driver;
	public ReusableFunctions rf;
	
	public BookingPageFactory(){}
    
	public BookingPageFactory(WebDriver driver) {
		this.driver= driver;
		rf = new ReusableFunctions(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="firstName")
	WebElement firstName;

	@FindBy(id="lastName")
	WebElement lastName;
	
	@FindBy(id="phoneNo")
	WebElement phoneNo;
	
	@FindBy(id="emaiId")
	WebElement emailId;
	
	@FindBy(id="eventType")
	WebElement eventType;
	
	@FindBy(id="eventDate")
	WebElement eventDate;
	
	@FindBy(id="eventTime")
	WebElement eventTime;

	@FindBy(id="guestCount")
	WebElement guestCount;
	
	@FindBy(id="vegFood")
	WebElement vegFood;
	
	@FindBy(id="nonVegFood")
	WebElement nonVegFood;
	
	@FindBy(id="address")
	WebElement address;
	
	@FindBy(id="city")
	WebElement city;
	
	@FindBy(id="pincode")
	WebElement pincode;
	
	@FindBy(id="eventDetail")
	WebElement eventDetail;
	
	@FindBy(id="book-now")
	WebElement bookNow;
	
	@FindBy(id="bookingconfirm")
	WebElement bookingConfirmMsg;
	
//error elements 
	@FindBy(id="emailErr")
	WebElement emailErr;
	
	@FindBy(id="fnameErr")
	WebElement fnameErr;
	
	@FindBy(id="guestCountErr")
	WebElement guestCountErr;
	
	@FindBy(id="phoneErr")
	WebElement phoneErr;
	
	@FindBy(id="pincodeErr")
	WebElement pincodeErr;
	
	//to enter valid details in all fields
	public void enterAllValidDetails(String[] data) {
		//enter details by using reusable functions
		rf.enterText(firstName, data[0]);
		rf.enterText(lastName, data[1]);
		rf.enterText(phoneNo, data[2]);
		rf.enterText(emailId, data[3]);
		rf.selectOption(eventType, data[4]);
		rf.enterText(eventDate, data[5]);
		rf.enterText(eventTime, data[6]);
		rf.enterText(guestCount, data[7]);
		if(data[8].equalsIgnoreCase("vegFood")) {
			rf.clickElement(vegFood);
		}else if(data[8].equalsIgnoreCase("nonVegFood")) {
			rf.clickElement(nonVegFood);
		}
		rf.enterText(address, data[9]);
		rf.selectOption(city, data[10]);
		rf.enterText(pincode, data[11]);
		rf.enterText(eventDetail, data[12]);
	}
	
	//to click on book now button
	public void clickBookNow() {
		rf.clickElement(bookNow);
	}
	
	//to return confirm message
	public String getMessage() {
		return rf.getText(bookingConfirmMsg);
		
	}
	
	
	//enter all data except first name 
	public void enterAllValidDetailsExFN(String[] data) {
		//enter details by using reusable functions
		//empty
		rf.enterText(firstName, "");
		rf.enterText(lastName, data[1]);
		rf.enterText(phoneNo, data[2]);
		rf.enterText(emailId, data[3]);
		rf.selectOption(eventType, data[4]);
		rf.enterText(eventDate, data[5]);
		rf.enterText(eventTime, data[6]);
		rf.enterText(guestCount, data[7]);
		if(data[8].equalsIgnoreCase("vegFood")) {
			rf.clickElement(vegFood);
		}else if(data[8].equalsIgnoreCase("nonVegFood")) {
			rf.clickElement(nonVegFood);
		}
		rf.enterText(address, data[9]);
		rf.selectOption(city, data[10]);
		rf.enterText(pincode, data[11]);
		rf.enterText(eventDetail, data[12]);
	}
	
	//check for the required name error 
	public String checkRequiredNameError() {
		return rf.getText(fnameErr);
	}
	
	//enter all data and invalid email id
	public void enterAllValidDetailsAndInvalidEmail(String[] data) {
		//enter details by using reusable functions
		rf.enterText(firstName, data[0]);
		rf.enterText(lastName, data[1]);
		rf.enterText(phoneNo, data[2]);
		//invalid
		rf.enterText(emailId, "abc");
		rf.selectOption(eventType, data[4]);
		rf.enterText(eventDate, data[5]);
		rf.enterText(eventTime, data[6]);
		rf.enterText(guestCount, data[7]);
		if(data[8].equalsIgnoreCase("vegFood")) {
			rf.clickElement(vegFood);
		}else if(data[8].equalsIgnoreCase("nonVegFood")) {
			rf.clickElement(nonVegFood);
		}
		rf.enterText(address, data[9]);
		rf.selectOption(city, data[10]);
		rf.enterText(pincode, data[11]);
		rf.enterText(eventDetail, data[12]);
	}
	
	//check for the invalid email error 
	public String checkInvalidEmailError() {
		return rf.getText(emailErr);
	}
	
	//enter all data and zero guests
	public void enterAllValidDetailsAndZeroGuests(String[] data) {
		//enter details by using reusable functions
		rf.enterText(firstName, data[0]);
		rf.enterText(lastName, data[1]);
		rf.enterText(phoneNo, data[2]);
		rf.enterText(emailId, data[3]);
		rf.selectOption(eventType, data[4]);
		rf.enterText(eventDate, data[5]);
		rf.enterText(eventTime, data[6]);
		//zero guests
		rf.enterText(guestCount, "0");
		if(data[8].equalsIgnoreCase("vegFood")) {
			rf.clickElement(vegFood);
		}else if(data[8].equalsIgnoreCase("nonVegFood")) {
			rf.clickElement(nonVegFood);
		}
		rf.enterText(address, data[9]);
		rf.selectOption(city, data[10]);
		rf.enterText(pincode, data[11]);
		rf.enterText(eventDetail, data[12]);
	}
	
	//check for the zero guests error 
	public String checkInvalidZeroGuestsError() {
		return rf.getText(guestCountErr);
	}

	//enter all data and invalid phone no
	public void enterAllValidDetailsAndInvalidPhoneNo(String[] data) {
		//enter details by using reusable functions
		rf.enterText(firstName, data[0]);
		rf.enterText(lastName, data[1]);
		//invalid
		rf.enterText(phoneNo, "abc");
		rf.enterText(emailId, data[3]);
		rf.selectOption(eventType, data[4]);
		rf.enterText(eventDate, data[5]);
		rf.enterText(eventTime, data[6]);
		rf.enterText(guestCount, data[7]);
		if(data[8].equalsIgnoreCase("vegFood")) {
			rf.clickElement(vegFood);
		}else if(data[8].equalsIgnoreCase("nonVegFood")) {
			rf.clickElement(nonVegFood);
		}
		rf.enterText(address, data[9]);
		rf.selectOption(city, data[10]);
		rf.enterText(pincode, data[11]);
		rf.enterText(eventDetail, data[12]);
	}
	
	//check for the invalid phone error 
	public String checkInvalidPhoneNoError() {
		return rf.getText(phoneErr);
	}
	

	//enter all data and invalid pincode
	public void enterAllValidDetailsAndInvalidPincode(String[] data) {
		//enter details by using reusable functions
		rf.enterText(firstName, data[0]);
		rf.enterText(lastName, data[1]);
		rf.enterText(phoneNo, data[2]);
		rf.enterText(emailId, data[3]);
		rf.selectOption(eventType, data[4]);
		rf.enterText(eventDate, data[5]);
		rf.enterText(eventTime, data[6]);
		rf.enterText(guestCount, data[7]);
		if(data[8].equalsIgnoreCase("vegFood")) {
			rf.clickElement(vegFood);
		}else if(data[8].equalsIgnoreCase("nonVegFood")) {
			rf.clickElement(nonVegFood);
		}
		rf.enterText(address, data[9]);
		rf.selectOption(city, data[10]);
		//invalid
		rf.enterText(pincode, "abc");
		rf.enterText(eventDetail, data[12]);
	}
	
	//check for the invalid phone error 
	public String checkInvalidPincodeError() {
		return rf.getText(pincodeErr);
	}
}
