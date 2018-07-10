# CST238SRS01
Happy Birthday 2 You

Name: Steven Reeves

Notes: 

Create an app that allows you to enter a name, a birthday month and day (NOT the birth year).

	- Name added via editText
	- Month added via spinner
	- Date added via spinner 

Keep a count of how many people entered birthdays and display it in the app.

Each entry is placed into persistent storage. If 2 dates match, the names are displayed along with the count of entries. Clear the persistent storage to reset the app.

While the user enters data, validate field data and form level data.

	Name field - Anything not empty
	Month field - Any one of the 12 months, not "Select Month"
	Date field - Any one of the dates, not "Select date"

You must account for fields not being correctly entered as well as the form not being in a valid state.

	- Form checked for missing fields.
	- Form checked for invalid dates. 
	

For example, Feb 30th is not a valid date, regardless that each field is valid.

	Dates confirmed invalid - Feb 30, Apr 31, Jun 31, Sep 31, Nov 31 

You must allow the user to enter data in any sequence.

	- Data can be added in fields in any order

Issues:

	- 

Would be nice:

	- Make Date button bigger 
	- Error message on spinners
	- Keyboard hangs on entering name 