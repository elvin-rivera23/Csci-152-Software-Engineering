<?php
	$dbhost = "localhost";
	$user = "root";
	$password = "";
	$db = "seem website";

	$conn = mysql_connect($dbhost, $user, $password);
	mysql_select_db($db);

	if ($conn == false) {
		die("Connection failed");
	}
	echo "connected";

	if(isset($_POST['submitbtn'])) {
		$UserName = mysql_real_escape_string($_POST['uname']);
		$Email = mysql_real_escape_string($_POST['email']);
		$Date = $_POST['bday'];
		$Password = mysql_real_escape_string($_POST['psw']);
		$sql = "INSERT INTO user accounts (UserName, Password, Email, Birthday) VALUES ('$UserName', '$Password', '$Email', '$Date') ";

		if(mysql_query($sql)) {
			echo "Records added";
		}
		else {
			echo "ERROR";
		}
	}
	header("Location:http://localhost/registerPage.html")
?>
