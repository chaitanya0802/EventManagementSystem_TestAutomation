@booking
Feature: Event Booking Functionality

Background:
Given launched the browser and opened the application

@tc001
Scenario: Book the event with all valid details
	When I enter the all valid details 
	And click on book now button 
	Then I see confirmation message with details

@tc002
Scenario: Submit booking form with missing first name
    When user fills all fields except First Name
    And user clicks Book Now
    Then validation error of required should be shown
 
@tc003
Scenario: Submit booking form with invalid email
    When user enters all correct details but invalid booking email
    And user clicks Book Now
    Then validation error for invalid email id should be shown
 
 @tc004
Scenario: Submit booking form with zero guests
    When user enters all correct details but zero guests
    And user clicks Book Now
    Then validation error for 0 guests should be shown
 
 @tc005
Scenario: Submit booking form with invalid phone number
    When user enters all correct details but invalid phone number
    And user clicks Book Now
    Then validation error for invalid phone number should be shown
    
 @tc006
Scenario: Submit booking form with invalid pincode
    When user enters all correct details but invalid pincode 
    And user clicks Book Now
    Then validation error for invalid pincode should be shown