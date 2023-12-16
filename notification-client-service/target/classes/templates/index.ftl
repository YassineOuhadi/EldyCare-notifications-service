<html>
<head>
	<script src="/lib/jquery-3.1.1.js" type="text/javascript"></script>
	<script src="/lib/sockjs-0.3.4.js" type="text/javascript"></script> 
	<script src="/lib/stomp.js" type="text/javascript"></script> 
</head>
<body>
	<h2>Hello World</h2>
	Message: <input type="text" id="message" readonly></input>
</body>

<script type="text/javascript">
    $(document).ready(function() {
        connectStomp();
    });

    var stompClient1 = null;

    /* RabbiMQ Broker from Queue*/
    function connectStomp() {
        console.log('Connecting to Stomp...');
        var socket = new SockJS('/broker');
        stompClient1 = Stomp.over(socket);

        stompClient1.connect('guest', 'guest', function(frame) {
            console.log('Connected: ' + frame);
            stompClient1.subscribe('/queue/notificationQueue', function(message){
                $("#message").val(message.body);
            });
        }, function(error) {
            console.error('Stomp.js Connection Error:', error);
        }, '/eldycare_broker');  // Use the correct virtual host here

    }

    function disconnect() {
        stompClient1.disconnect();
        console.log("Disconnected");
    }
</script>


</html>