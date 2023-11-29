const API_KEY = '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';
const API_URL = "https://api.openai.com/v1/chat/completions";

var submitBtn = document.getElementById("submitBtn");
var chatPrompt = document.getElementById('queryText');
var ChatDialogueDiv = document.getElementById('responseText');
submitBtn.addEventListener("click", function () {
  var prompt = chatPrompt.value;
  if (prompt != null && prompt != "") {
    createChatDiv(prompt, "user");
    sendPrompt(prompt, createChatDiv);
    chatPrompt.value = "";
  }
});

function createChatDiv(message, type) {
  var newChat = document.createElement('p');
  newChat.classList.add("chat");
  newChat.innerHTML = message;
  if (type === "user")
    newChat.classList.add("userChat");
  else
    newChat.classList.add("systemChat");

  ChatDialogueDiv.appendChild(newChat);
}

function sendPrompt(prompt, onSuccessCallBack) {
  var request = {
    "model": "gpt-3.5-turbo",
    "messages": [{
      "role": "system",
      "content": "You are a not yet implemented into our system. You will eventually become a system support bot who will provide users with assistance across our app and website. For now, exclusively respond with 'I'm not quite ready to be used. Check back soon!'"
    },
    {
      "role": "user",
      "content": prompt,
    }],
  }

  const xhttp = new XMLHttpRequest();
  xhttp.onload = function () {
    var jsonResponse = JSON.parse(this.responseText);

    onSuccessCallBack(jsonResponse.choices[0].message.content, "system");
  }
  xhttp.open("POST", API_URL, true);
  xhttp.setRequestHeader("Content-Type", "application/json");
  xhttp.setRequestHeader("Authorization", "Bearer " + API_KEY);
  xhttp.send(JSON.stringify(request));
}