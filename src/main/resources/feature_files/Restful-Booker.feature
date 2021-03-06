Feature:  You are working in the backend team that exposes the service:
  https://jsonplaceholder.typicode.com/ which has the following endpoints:
#
#  1. Make posts (createBooking): https://restful-booker.herokuapp.com/booking
#  2. Get restful-booker website: https://restful-booker.herokuapp.com/ping
#  3. List of users Id: https://restful-booker.herokuapp.com/booking/

  @RestF
  Scenario Outline: Test that new user can be booked with a POST request
    Given  Restful-booker service is up and running
    When I book a new user with the following details "<firstname>","<lastname>", "<totalprice>", "<depositpaid>", "<checkin>", "<checkout>" and "<additionalneeds>",
    Then i should get the new user details with "<bookingid>", "<firstname>", "<lastname>", "<totalprice>","<depositpaid>" and "<checkin>" returned with status code of 200
    And I should be able to assess the generated "bookingid" of the new booked user.
    Examples:
      | bookingid | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | 35        | Mustee    | Jay      | 800        | false       | 2022-02-03 | 2022-02-05 | Dinner          |

  @RestTok
  Scenario Outline: Test that a user token can be generated with a POST request
    Given  Restful-booker service is up and running
    When I book a new user with the following details "<username>" and "<password>",
    Then i should get the new "token" generated returned with status code of 200
    Examples:
      | username | password    |
      | admin    | password123 |


  @Rest
  Scenario Outline: Test that a booked user  be partially updated with a PATCH request requiring authorization code or token
    Given  Restful-booker service is up and running
    When I partially update a booked user account with the following details "<totalprice>", "<depositpaid>", "<checkin>", "<checkout>" and "<additionalneeds>"
    Then i should get the new user details with "<totalprice>","<depositpaid>", "<checkin>", "<checkout>" and "<additionalneeds>" returned with status code of 200
    Examples:
      | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | 950        | true        | 2022-02-10 | 2022-03-30 | Dinner          |
#      | 800        | false       | 2022-02-03 | 2022-02-05 | Breakfast       |

  @RestA
  Scenario Outline: Test that existing booked users can be retrieved with a GET request requiring authorization code or token
    Given service is up and running
    When i search with the id of a booked user with a GET method
    Then i should get the correct "<firstname>", "<lastname>", "<totalprice>", "<depositpaid>" and "<checkin>" returned with status code of 200
    Examples:
      | firstname | lastname | totalprice | depositpaid | checkin    |
      | Mustee    | Jay      | 800        | false       | 2022-02-03 |


  @RestMA
  Scenario Outline: Test that a new booked user info can be obtained with a GET using a generated userID
    Given  I book a new user with the following details "<firstname>","<lastname>", "<totalprice>", "<depositpaid>", "<checkin>", "<checkout>" and "<additionalneeds>",
    When I should be able to assess the generated "bookingid" of the new booked user.
    And i search with the "bookingid" of a booked user with a GET method
    Then i should get the correct "<firstname>", "<lastname>", "<totalprice>", "<depositpaid>" and "<checkin>" returned with status code of 200
    Examples:
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | Mustee    | Jay      | 800        | true        | 2022-02-03 | 2022-03-15 | Breakfast       |

  @RestMA
  Scenario Outline: Test that a booked user information can be updated with a PUT Method
    Given I book a new user with the following details "<firstname>","<lastname>", "<totalprice>", "<depositpaid>", "<checkin>", "<checkout>" and "<additionalneeds>",
    And I should be able to assess the generated "bookingid" of the new booked user.
    And i search with the "bookingid" of a booked user with a GET method
    When I update a booked user account with the user generated "bookingid" and the following details "<firstname>", "<lastname>", "<totalprice>", "<depositpaid>", "<checkin>", "<checkout>" and "<additionalneeds>"
    Then i should get the new user details with "<firstname>", "<lastname>", "<totalprice>","<depositpaid>", "<checkin>", "<checkout>" and "<additionalneeds>" returned with status code of 200
    Examples:
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | Mustee    | Jay      | 990        | true        | 2022-03-10 | 2022-04-12 | Dinner          |
