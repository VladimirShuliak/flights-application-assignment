## README ##
This README would normally document whatever steps are necessary to get your application up and running.

##Examples for testing###
* GET http://localhost:8080/tickets/11
* PUT http://localhost:8080/tickets/11

### Overview ###
*API service for a demo Flights app*

## Requirements ##

* Create an API service for a demo Flights app:
  Prerequisites:
  Create hardcoded data that will “act” as your datasource (no need to run external/internal databases).

Write an API that provides the following functionality:
a. Check if a ticket is available
A1. The user provides a TicketId (integer) and the API returns a response (boolean) whether the ticket is available or not (the response will be determined upon your predefined static data).
b. Provide check-in status
b1. User provides DestinationId (integer) and BaggageId (String), the API returns an answer if the checkin succeeded (boolean)
c. Create a local in-memory cache mechanism  that caches results and adds expiration dates to them (implement your own. Don't use any caching libraries as spring caching or anything else). (for example: if you have multiple requests that check if ticket is available for the same ticketId, the result should be returned from cache instead of from the in memory storage)
In addition, please take care of:
1. Basic validation  (1 validation method per every API method)
2. Minimum unit-testing (2 per API method)
3. Basic Logging
4. Clean code
5. Document your code where applicable


Write the code in Java, use maven/gradle as your building tool.
Use Spring boot as your application’s framework.
