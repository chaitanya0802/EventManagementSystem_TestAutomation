package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ReusableFunctions;

public class ContactPageFactory {
	
	public WebDriver driver;
	public ReusableFunctions rf;
	
	public ContactPageFactory(){}
    
	public ContactPageFactory(WebDriver driver) {
		this.driver= driver;
		rf = new ReusableFunctions(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="contact_name")
	WebElement contact_name;

	@FindBy(id="contact_email")
	WebElement contact_email;
	
	@FindBy(id="contact_subject")
	WebElement contact_subject;
	
	@FindBy(id="contact_message")
	WebElement contact_message;
	
	@FindBy(id="message")
	WebElement messagebtn;
	
	@FindBy(id="mesgtab")
	WebElement status;

//error
	@FindBy(id="contactNameErr")
	WebElement contactNameErr;
	
	@FindBy(id="contactEmailErr")
	WebElement contactEmailErr;
	
	//enter form details w
	public void enterDetails(String name, String email, String subject, String message) {
		rf.enterText(contact_name, name);
		rf.enterText(contact_email, email);
		rf.enterText(contact_subject, subject);
		rf.enterText(contact_message, message);
	}
	
	
	//click send
	public void clickSendButton() {
		rf.clickElement(messagebtn);
	}
	
	//get required error for name
	public String getReqNameMessage() {
		return rf.getText(contactNameErr);
		
	}
	
	//get invalid error for email
	public String getInvalidEmailMessage() {
		return rf.getText(contactEmailErr);
		
	}
	
	public String getSentStatus() throws InterruptedException {
		//wait for 4 sec
		Thread.sleep(4000);
		return rf.getText(status);
	}
}
