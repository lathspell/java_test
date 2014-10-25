$(document).ready(function () {
    var wsocket = new WebSocket("ws://" + document.location.host + document.location.pathname + "chat/room");

    wsocket.onmessage = function (evt) {
        var msg = JSON.parse(evt.data);
        $('#chatResponse').append($('<span>' + msg.received + ': ' + msg.message + '</span><br/>'));
    };

    $('#chatSendButton').click(function () {
        wsocket.send('{"message":"' + $('#chatInput').val() + '"}');
    });

    $('#chatDisconnectButton').click(function () {
        wsocket.close();
        $('#chatResponse').append($('<span>disconnected</span>'));
    });
});
