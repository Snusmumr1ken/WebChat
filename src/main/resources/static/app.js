var socket = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    socket = new WebSocket('ws://localhost:8080/message');

    socket.onopen = function (event) {
        console.log("Connected");
        sendMessage('connected.');
    }

    socket.onmessage = function (messageEvent) {
        console.log("new message! : " + messageEvent.data)
        showMessage(JSON.parse(messageEvent.data), Date.now());
    }
}

function showMessage(message, timestamp) {
    let minutes = new Date(timestamp).getMinutes();
    if (minutes < 10 || minutes === 0) minutes = "0" + minutes;

    let hours = new Date(timestamp).getHours();
    if (hours < 10 || hours === 0) hours = "0" +hours;

    let time = "<em style='color: darkturquoise; font-style: normal;'>" + hours + ":" + minutes + "</em> ";

    $("#chat").append(time + " " + message.name + ": " + message.message + "<br>");
}

function sendMessage(message) {
    const name = $("#name-input").val();
    socket.send(JSON.stringify({'name': name, 'message': message, 'timestamp': Date.now()}));
    console.log("New message sent: " + message);
}

function disconnect() {
    if (socket !== null) {
        sendMessage('left chat.')
        socket.close();
    }
    setConnected(false);
    console.log("Disconnected");
}

function openChat() {
    let name = document.getElementById("name-input").value;
    $("#welcome-text").append(name + "!");
    $("#chatbox").show();
    $("#my-form").hide();
}

function closeChat() {
    $("#my-form").show();
    $("#welcome-text").text("Welcome, ");
    $("#chatbox").hide();
}

function loadChatHistory() {
    fetch("/messages")
        .then((response) => {
            if (!response.ok) {
                throw Error('HTTP error: ' + response.status);
            }
            return response.json();
        })
        .then((messages) => {
            messages.forEach((message) => {
                showMessage(message, message.timestamp);
            });
        });
}

function clearChat() {
    $("#chat").text("");
}

const beforeUnloadListener = () => {
    disconnect();
};

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });

    $( "#connect" ).click(function() {
        connect();
        loadChatHistory();
        openChat();
        addEventListener("beforeunload", beforeUnloadListener, {capture: true});
    });

    $( "#disconnect" ).click(function() {
        clearChat();
        closeChat();
        disconnect();
    });

    $( "#send-message" ).click(function() {
        sendMessage($("#message-input").val());
        $("#message-input").val('');
    });
});
