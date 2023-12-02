const API_KEY = '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';
const API_URL = "https://api.openai.com/v1/chat/completions"; 

var submitBtn = document.getElementById("submitBtn");
var chatPrompt = document.getElementById('queryText');
var ChatDialogueDiv = document.getElementById('responseText');
submitBtn.addEventListener("click",function() {
  var prompt = chatPrompt.value;
  if (prompt != null && prompt != ""){
    createChatDiv(prompt,"user");
    sendPrompt(prompt,createChatDiv);
    chatPrompt.value = "";
  }
});

function createChatDiv(message,type){
  var newChat = document.createElement('p');
  newChat.classList.add("chat");
  newChat.innerHTML = message;
  if (type === "user")
    newChat.classList.add("userChat");
  else
  newChat.classList.add("systemChat");

  ChatDialogueDiv.appendChild(newChat);
}

function sendPrompt(prompt, onSuccessCallBack){
  var request = {
    "model": "gpt-3.5-turbo",
    "messages": [{
      "role":"system",
      "content":`You are a support bot for a website which acts as a portal for a doctor to manage their patients. 
      Take the following JSON of questions and anwsers detailing how to navigate the site. 
      If a question is missing, you must exclusively respond with : "Sorry, I can't help with that. Please email our support team at support@healthai.net".
      In the response strings, any braces {} you find will contain a list of different variables that fit the same question / response structure. It has a structure of {keyword: {variables list}}
      {
        "I want to access my patient details":"You can access patient details by navigating to the dashboard, and selecting your desired patient from the list. ",
        "I want to update my current mobile number":"You can update your current mobile number by navigating to your profile page. Under contact Details, you can edit and save your mobile number."
        ,"I want to add my mobile number":"You can add your mobile number by navigating to your profile page. Under contact Details, you can enter and save your mobile number",
        "I want to add my academic title":"You can add your academic title by navigating to your profile page. Under Doctor Details, you can enter your academic title.",
        "I want to add my specialties":"You can add your specialties by navigating to your profile page. Under Doctor Details, you can enter your specialties.",
        "I want to view a patient's {contact details: phone, email} ":"You can access a patients contact details by navigating to the dashboard, and selecting your desired patient from the list. Their contact details will be listed under "Patient Details".",
        "I want to view a patient's {form: medical info, form details, health information} details. How I can I access these?":"You can access a patients form by navigating to the dashboard, and selecting your desired patient from the list. Then select "View form" under Patient details.".
        "I want to edit a patient's {form:medical info, form details, health info}. How can I do this?":"Sorry, you are unable to edit a patient's form."
      }

      Please note a doctor can only edit their Academic titles, specialties, and phone number. Everything else is view-only.
      `
    },
      {
        "role": "user",
        "content": prompt,
    }],
  }

  const xhttp = new XMLHttpRequest();
  xhttp.onload = function() {
    var jsonResponse = JSON.parse(this.responseText);

    onSuccessCallBack(jsonResponse.choices[0].message.content,"system");
  }
  xhttp.open("POST",API_URL,true);
  xhttp.setRequestHeader("Content-Type", "application/json");
  xhttp.setRequestHeader("Authorization", "Bearer " + API_KEY);
  xhttp.send(JSON.stringify(request));
}