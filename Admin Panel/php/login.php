<?php

# Open database connection.
require ('../db.php');

$email = $_GET["email"];
$password = $_GET["pass"];

if(isset($email) && isset($password))
{
    $password = sha1($password);
    $q = "SELECT id, email, created, subType, subEnd FROM Users WHERE email='$email' AND pass='$password'";  
    $r = @mysqli_query($conn, $q) or die(mysql_error());
    if($r)
    {
      if ( @mysqli_num_rows( $r ) == 1 ) 
      {
        $row = @mysqli_fetch_assoc($r);
        $id = $row["id"];
        $email = $row["email"];
        $created = $row["created"];
        $subtype = $row["subType"];
        $subend = $row["subEnd"];
        $myObj->email = $email;
        $myObj->created = $created;
        $myObj->subtype = $subtype;
        $myObj->subend = $subend;
        $token = bin2hex(random_bytes(64));

        $q = "SELECT token FROM Tokens WHERE userId='$id'";  
        $r = @mysqli_query($conn, $q) or die(mysql_error());

        if($r)
        {
          if ( @mysqli_num_rows( $r ) > 0 ) 
          {
            $q = "UPDATE Tokens SET token='$token', created=NOW() WHERE userId='$id'" ;   
            $r = @mysqli_query($conn, $q) or die(mysql_error());

            if($r)
            {
              $myObj->status = "OK";
              $myObj->message = "Token already existing for this user, new token has been assigned";
              $myObj->token = $token;
              $myJSON = json_encode($myObj);
              echo $myJSON;
            }
            else
            {
              $myObj->status = "ERROR";
              $myObj->message = "Could not update token table";
              $myJSON = json_encode($myObj);
              echo $myJSON;
            }
          }
          else
          {
            $q = "INSERT INTO Tokens (token, userId, created) VALUES ('$token', '$id', NOW())" ;   
            $r = @mysqli_query($conn, $q) or die(mysql_error());

            if($r)
            {
                $myObj->status = "OK";
                $myObj->message = "No token found, new token assigned";
                $myObj->token = $token;
                $myJSON = json_encode($myObj);
                echo $myJSON;
            }
            else
            {
              $myObj->status = "ERROR";
              $myObj->message = "Account found but token could not be added to the db";
              $myJSON = json_encode($myObj);
              echo $myJSON;
            }
          }
        }
}

      
    # Or on failure set error message.
      else
      {
        $myObj->status = "FAILED";
        $myObj->message = "Could not find account, or there is a duplicate record...";
        $myJSON = json_encode($myObj);
        echo $myJSON;
      }
    }
    else
    {
      $myObj->status = "FAILED";
      $myObj->message = "Could not run query";
      $myJSON = json_encode($myObj);
      echo $myJSON;
    }
    

}
else
{
  $myObj->status = "FAILED";
  $myObj->message = "Please fill out both fields";
  $myJSON = json_encode($myObj);

  echo $myJSON;
}

  # Close database connection.
  mysqli_close( $conn ) ; 

?>