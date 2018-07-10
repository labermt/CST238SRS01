# CST238SRS01
Happy Birthday 2 You

Name: Silverio Reyes

Notes: In developing the application to validate 3 input fields in order to save the contents to persistent storage, I learned how to implement static spinners which are used to display fields of options from a set. For example, I have two sets, one for birth months and another for birthdays. The effect displays a drop-down menu with the known available values. This was very cool and interesting to implement once you have added a spinner to your layout. 

The key classes that were used where: Spinner, SpinnerAdapter, and AdapterView.OnitemSelectedListener. I learned how to populate the spinner classes statically and dynamically. Since the resource values would never change I decided to populate them defined in my string resource file using the ArrayAdapter.

I also ensured to make the layouts responsive for any devices and also when the device rotates to landscape.

Issues: There are several methods and algorithms for reading and writing contents to persistent storage. Upon developing this application, I knew that this would be a difficult task for me since I have never done File I/O in java before. I was tempted to use SQLite as there were many examples online but I felt that there was an easier way.

The main issue I had was thinking how I was going to append the contents and what delimiter should I add so I can read the contents and store them in an array list. I also felt that parsing and using a delimiter as a way to iterate through the file was a bit outdated. I spoke to the professor and brainstormed some ideas: The option that I gravitated towards was serializing and de-serializing the birthday entries when reading and writing the contents to persistent storage using the devices internal storage file system.

I essentially had to create JSON objects that map key/value pairs of the content I was going to store. Since these are birthday entries I had to serialized the JSON object with its own key/value pair to gain access to the elements of the entries. This was very tricky to implement because you have to make sure that it is a valid JSON request/response in order to use the JSON object and JSON array libraries. I had many debugging errors due to my content not being in a valid JSON form. A good source that validates your JSON file is https://jsonlint.com/

Here is an example of a valid JSON array:
{
	"BirthdayEntries": [{
			"Name": "silver",
			"BirthMonth": "March",
			"BirthDay": "20"
		},
		{
			"Name": "Kanye",
			"BirthMonth": "June",
			"BirthDay": "8"
		}
	]
}

The other issue I had was when I was deserializing the JSON content had to do with the type of file I used. Since my file was a txt file, I added complexity because once I read the contents into a string, it added quotations outside of the entire JSON object. I could not parse it properly into an array that I had implemented. I was unaware until later on that there is a JSON file extensions, however im not sure how it will handle since I handle it for a txt file extension. Instead, I instantiated a hashmap that had two parameters (a string and a list of strings). The list of strings where instantiated array list which stored the Names, Birthmonths, and Birthdays. Once I did this I created a file channel for reading, writing, mapping, and manipulating the file. 

Once I obtained the key value of the array I began parsing by iterating it inside of a JSON array. I then serialized it back once there were no birthday matches using the method discussed already.

After going through some time in understanding the complexities, as for self-recommendation, I would like to investigate patterns/algorithms that are more efficient for serializing and de-serializing array objects to JSON format. I also would like to see if my algorithms will still work if I change the files txt extension to .json.

