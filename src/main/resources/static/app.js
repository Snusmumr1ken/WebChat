var stompClient = null;

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
    const socket = new SockJS('/gs-guide-websocket');
    const userName = $("#name-input").val();
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + userName + ', ' + frame);
        stompClient.subscribe('/topic/chat', function(message) {
            showMessage(JSON.parse(message.body));
        });
        sendMessage('connected.');
    });
}

function showMessage(message) {
    $("#chat").append(message.name + ': ' + message.message + "<br>");
}

function sendMessage(message) {
    const name = $("#name-input").val();
    stompClient.send("/app/message", {}, JSON.stringify({'name': name, 'message': message}));
}

function disconnect() {
    if (stompClient !== null) {
        sendMessage('left chat.')
        stompClient.disconnect();
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

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });

    $( "#connect" ).click(function() {
        connect();
        openChat();
    });

    $( "#disconnect" ).click(function() {
        closeChat();
        disconnect();
    });

    $( "#send-message" ).click(function() {
        sendMessage($("#message-input").val());
    });
});

