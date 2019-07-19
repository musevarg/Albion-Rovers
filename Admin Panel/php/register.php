<?php

require ( '../db.php' ) ;

$email = $_GET['email'];
$password = $_GET['pass'];
$subType = $_GET['subType'];

if(isset($email) && isset($password) && isset($subType))
{

		$q = "SELECT * FROM Users WHERE email='$email'";
		$r = @mysqli_query($conn, $q);
		
		if(mysqli_num_rows($r) != 0)
		{
			$myObj->status = "FAILED";
			$myObj->message = "Email already registered";
	  		$myJSON = json_encode($myObj);
	  		echo $myJSON;
		}
		else
		{
			if ($subType == "Full"){
				$date = date("Y-m-d h:m:s");
				$subEnd = date('Y-m-d h:m:s', strtotime($date. ' + 30 days'));
				$myObj->subscription = "Paid account activated";
			}
			else
			{
				$subEnd = null;
				$myObj->subscription = "Free account activated";
			}

			$q = "INSERT INTO Users (email, pass, created, subType, subEnd) VALUES ('$email', SHA1('$password'), NOW(), '$subType', '$subEnd')" ;  
	    	$r = @mysqli_query($conn, $q);

	    	$myObj->status = "OK";
			$myObj->message = "Thank you for registering.";
			$myJSON = json_encode($myObj);
			echo $myJSON;
		}

}
else
{
  $myObj->status = "REGISTRATION FAILED";
  $myObj->message = "Something went wrong...";
  $myJSON = json_encode($myObj);
  echo $myJSON;
}

 # Close database connection.
 mysqli_close( $link ) ; 

?>