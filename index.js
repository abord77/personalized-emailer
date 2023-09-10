const eventscheckbox = document.getElementById('events-checkbox');
		const events_inputFieldContainer = document.getElementById('events-input-field-container');
		
		eventscheckbox.addEventListener('change', function() {
			if (this.checked) {
				events_inputFieldContainer.classList.add('show');
			} else {
				events_inputFieldContainer.classList.remove('show');
			}
		});

        const personalcheckbox = document.getElementById('personal-events');
		const personal_inputFieldContainer = document.getElementById('personal-input-field-container');
		
		personalcheckbox.addEventListener('change', function() {
			if (this.checked) {
				personal_inputFieldContainer.classList.add('show');
			} else {
			    personal_inputFieldContainer.classList.remove('show');
			}
		});

// const btn = document.querySelector('#dark-mode-btn');
// const body = document.querySelector('body');

// btn.addEventListener('click', function() {
//	body.classList.toggle('dark-mode');
//});

function changeId() {
	const myDiv = document.getElementById("dark-mode-apply");
	myDiv.classList.toggle("dark-mode");
  }


  const form = document.getElementById('input-form');
  form.addEventListener('submit', (event) => {
    event.preventDefault();
    const formData = new FormData(event.target);
    const email = formData.get('email');
	const fname = formData.get('fname');
	const lname = formData.get('lname');

	const weather = formData.get('weather-label');

	const events_check = formData.get('events-label');
	const events_link = formData.get('events-input');

	const checkbox = formData.get('checkbox-input');

	const personal = formData.get('personal-toggle');
	const personalcal = formData.get('personalcal-input');

    console.log(email, fname, lname, weather, events_check, events_link, checkbox, personalcal);
    // You can now store the data into JavaScript variables or do something else with it
  });



  // JavaScript code
load("js.jar");

var test = Packages.test;

function callJavaFunction() {
  var result = test.myJavaFunction("Message from JavaScript");
  var jsResult = Context.javaToJS(result, this);
  return jsResult;
}

var myResult = callJavaFunction();
console.log(myResult);


// JavaScript code
function callJavaFunction() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
	  if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
		var result = xhr.responseText;
		console.log(result);
	  }
	};
	xhr.open('GET', 'http://example.com/myWebService/myJavaFunction', true);
	xhr.send();
  }
  