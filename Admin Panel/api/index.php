<?php

require 'vendor/autoload.php';

use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;
 
$app = new Slim\App();

/*--------LANDING PAGE ----------*/

$app->get('/', function(Request $request, Response $response){
    


$myObj->apiName = "Albion Rovers API";
$myObj->version = "1.0";
$myObj->dateCreated = "2019-02-01";
$myObj->lastUpdated = "2019-02-01";
$myObj->author = "EC1529114";
$myObj->contactAuthor = "ec1529114@edinburghcollege.ac.uk";

$myJSON = json_encode($myObj);
echo $myJSON;

});

/*--------GET ALL PLAYERS--------*/

$app->get('/players', function(){

require_once('db.php');

$query = "SELECT * FROM Players ORDER BY id";

$result = $conn->query($query);

while($row = mysqli_fetch_assoc($result)){
	$data[] = $row;
}

echo json_encode($data);

});

/*--------GET ONE PLAYER--------*/

$app->get('/players/{playerId}', function(Request $request, Response $response){

require_once('db.php');

	//$playerId = $args['playerId'];
	$playerId = $request->getAttribute('playerId');

$query = "SELECT * FROM Players WHERE id='".$playerId."'";

$result = $conn->query($query);


 while($row = mysqli_fetch_assoc($result)){
	$data[] = $row;
}

echo json_encode($data);

});

/*--------GET ALL FIXTURES--------*/

$app->get('/fixtures', function(){

require_once('db.php');

$query = "SELECT * FROM Fixtures ORDER BY id";

$result = $conn->query($query);

while($row = mysqli_fetch_assoc($result)){
	$data[] = $row;
}

echo json_encode($data);

});

/*--------GET ONE FIXTURE--------*/

$app->get('/fixtures/{fixtureId}', function(Request $request, Response $response){

require_once('db.php');

$fixtureId = $request->getAttribute('fixtureId');

$query = "SELECT * FROM Fixtures WHERE id='".$fixtureId."'";

$result = $conn->query($query);


 while($row = mysqli_fetch_assoc($result)){
	$data[] = $row;
}

echo json_encode($data);

});

/*--------GET ALL NEWS--------*/

$app->get('/news', function(){

require_once('db.php');

$query = "SELECT * FROM News ORDER BY id";

$result = $conn->query($query);

while($row = mysqli_fetch_assoc($result)){
	$data[] = $row;
}

echo json_encode($data);

});

/*--------GET ONE NEWS--------*/

$app->get('/news/{newsId}', function(Request $request, Response $response){

require_once('db.php');

$newsId = $request->getAttribute('newsId');

$query = "SELECT * FROM News WHERE id='".$newsId."'";

$result = $conn->query($query);


 while($row = mysqli_fetch_assoc($result)){
	$data[] = $row;
}

echo json_encode($data);

});


$app->run();