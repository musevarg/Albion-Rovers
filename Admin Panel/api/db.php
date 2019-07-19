<?php

$servername = "localhost";
$username = "u969528694_albi";
$password = "albionrovers";
$dbname = "u969528694_rover";

$conn = mysqli_connect($servername, $username, $password, $dbname);

if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
?>