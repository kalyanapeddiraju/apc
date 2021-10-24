
This repo has tests for below public APIs

apiv4.ordering.co/v400/en/demo/users
https://jsonplaceholder.typicode.com/  

Description :

Bleow Endpint converd

POST: 
• https://apiv4.ordering.com/v400/en/demo/users
GET:
•	https://jsonplaceholder.typicode.com/users
•	https://jsonplaceholder.typicode.com/Posts
•	https://jsonplaceholder.typicode.com/comments

Technologes ussed:

• Maven
• Java
• Rest-assured
• EtentReports
• CirleCI
• Assertions / Annotations


Key Components of Framework:
o	RequestBuilder: This class take inputs from test class and set the required parameters for any API call.
o	RestResponse: This class will return the response by taking values from the RequestBuilder class for further validations Helpers
o	Helpers: Common funntions used for curret scripting
o	ExtentReport & ExtentTestManager: To generat Extent report html and log the test details.

Report: 
apc/TestReport/Test-Automaton-Report.html

