@contact
Feature: Contact Us Functionality

Background:
Given launched the browser and opened the web application

@tc007
Scenario: Submit Contact Us form with empty name
    When user enters details "" "chaitanya@email.com" "regarding ticket" "My ticket is not resolved"
    And user clicks Send Message Now
    Then required message for name should be shown
    
@tc008
Scenario: Submit Contact Us form with invalid email
    When user enters details "chaitanya" "abc" "regarding ticket" "My ticket is not resolved"
    And user clicks Send Message Now
    Then message for invalid email should be shown
    
@tc009
Scenario: Get status if message is sent successfully
    When user enters details "chaitanya" "chaitanya@email.com" "regarding ticket" "My ticket is not resolved"
    And user clicks Send Message Now
    Then status of sent message should be shown
    
