<?php

session_start();
if(!isset($_SESSION['userid']))
    {
    	header('Location: login.php');
    	exit;
    }

	require('db.php');

	$table = "";
	$modals = "";

function getNews()
{
	require('db.php');
	$query = "SELECT * FROM News ORDER BY id";

	$result = $conn->query($query);

		$table = '<table class="table table-hover"><thead><th scope="col">ID</th><th scope="col">Date Posted</th><th scope="col">Headline</th><th scope="col">Summary</th><th scope="col">Picture</th><th scope="col"><button class="btn btn-light" data-toggle="modal" data-target="#addModal"><img width="20px" src="https://www.pngrepo.com/download/138538/plus-sign.png"></button></th></tr></thead><tbody>';

	while($row = $result->fetch_assoc()){
		$table = $table . '<tr><th scope="row">' . $row["id"] . '</th><td width="150px">'. $row["datePosted"] .'</td><td>'. $row["headline"] .'</td><td>'. $row["summary"] .'</td><td><img width="150px" src="'. $row["picture"] .'"/></td><td><button class="btn btn-light" data-toggle="modal" data-target="#modal'.$row["id"].'"><img width="20px" src="pen.png"></button></td></tr>';
	
	$modals = $modals . '<div class="modal fade" id="modal'.$row["id"].'"tabindex="-1" role="dialog" aria-hidden="true"><div class="modal-dialog" role="document"><div class ="modal-content"><div class="modal-header"> <h5 class="modal-title" id="exampleModalLabel">Update Record</h5><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="modal-body"> <form action="" method="post"> <input type="hidden" name="updateID" value="'.$row["id"].'"> <div class="form-group"> <label>Date Posted</label> <input type="date" class="form-control" name="updateDatePosted" value="'.$row["datePosted"].'"> </div><div class="form-group"> <label>Headline</label> <input type="text" class="form-control" name="updateHeadline" value="'.$row["headline"].'"> </div><div class="form-group"> <label>Summary</label> <textarea rows="8" class="form-control" name="updateSummary">'.$row["summary"].'</textarea></div><div class ="form-group"> <label">Picture</label> <input type="text" class="form-control" name="updatePicture" value="'.$row["picture"].'"> </div><input name="updateNews" type="submit" class="btn btn-success" value="Update Record" /> <button  class="btn btn-danger" type="button" data-dismiss="modal" data-toggle="modal" data-target="#deleteModal'.$row["id"].'">Delete Record</button> <button type="button" class="btn btn-dark" data-dismiss="modal">Close</button></form></div></div></div></div>';

	$modals = $modals . '<div class="modal fade" id="deleteModal'.$row["id"].'" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><h4 class="modal-title">Delete Record</h4><button type="button" class="close" data-dismiss="modal">&times;</button></div><div class="modal-body"><p>Are you sure you wish to delete record? This cannot be undone.</p></div><div class="modal-footer"><form action="" method="post"><input name="deleteID" type="hidden" value="'.$row["id"].'"><button type="submit" name="deleteNews" class="btn btn-danger">Delete</button><button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button></form></div></div></div></div></div>';
	}

	$modals = $modals . '<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-hidden="true"><div class="modal-dialog" role="document"><div class ="modal-content"><div class="modal-header"> <h5 class="modal-title" id="exampleModalLabel">Add Record</h5><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="modal-body"> <form action="" method="post"> <div class="form-group"> <label>Date Posted</label> <input type="date" class="form-control" name="updateDatePosted"> </div><div class="form-group"> <label>Headline</label> <input type="text" class="form-control" name="updateHeadline"> </div><div class="form-group"> <label>Summary</label> <textarea rows="8" class="form-control" name="updateSummary"></textarea></div><div class ="form-group"> <label">Picture</label> <input type="text" class="form-control" name="updatePicture"> </div><input name="addNews" type="submit" class="btn btn-success" value="Add Record" /> <button type="button" class="btn btn-dark" data-dismiss="modal">Close</button></form></div></div></div></div>';

	$table = $table . '</tbody></table>';
	return $table.$modals;
}

if(isset($_POST['addNews'])){

	$query = "INSERT INTO News(datePosted, headline, summary, picture) VALUES ('".mysqli_real_escape_string($conn,$_POST['updateDatePosted'])."','".mysqli_real_escape_string($conn,$_POST['updateHeadline'])."','".mysqli_real_escape_string($conn,$_POST['updateSummary'])."','".mysqli_real_escape_string($conn,$_POST['updatePicture'])."')";

if (mysqli_query($conn, $query)) {
	$table = getNews();
} else
{
	$table = "Error ! <br>".mysqli_error($conn);
}

}

if(isset($_POST['updateNews'])){

	$query = "UPDATE News SET datePosted='".mysqli_real_escape_string($conn,$_POST['updateDatePosted'])."', headline='".mysqli_real_escape_string($conn,$_POST['updateHeadline'])."', summary='".mysqli_real_escape_string($conn,$_POST['updateSummary'])."', picture='".mysqli_real_escape_string($conn,$_POST['updatePicture'])."' WHERE id='".mysqli_real_escape_string($conn,$_POST['updateID'])."'";

if (mysqli_query($conn, $query)) {
	$table = getNews();
} else
{
	$table = "Error ! <br>".mysqli_error($conn);
}

}

if(isset($_POST['deleteNews'])){

	$query = "DELETE FROM News WHERE id='".mysqli_real_escape_string($conn,$_POST['deleteID'])."'";

if (mysqli_query($conn, $query)) {
	$table = getNews();
} else
{
	$table = "Error ! <br>".mysqli_error($conn);
}

}

if(isset($_POST['NewsButton'])){
	$table = getNews();
}

function getPlayers()
{
	require('db.php');
	$query = "SELECT * FROM Players ORDER BY id";

	$result = $conn->query($query);

	$table = '<table class="table table-hover"><thead><tr><th scope="col">ID</th><th scope="col">Name</th><th scope="col">Date of Birth</th><th scope="col">Place of Birth</th><th scope="col">Height</th><th scope="col">Position</th><th scope="col">Nationality</th><th scope="col">Join Date</th><th scope="col">Contract Until</th><th scope="col">Player Picture</th><th scope="col"><button class="btn btn-light" data-toggle="modal" data-target="#addModal"><img width="20px" src="https://www.pngrepo.com/download/138538/plus-sign.png"></button></th></tr></thead><tbody>';

	while($row = $result->fetch_assoc()){ 

		$table = $table . '<tr><th scope="row">' . $row["id"] . '</th><td>'. $row["name"] .'</td><td>'. $row["dob"] .'</td><td>'. $row["pob"] .'</td><td>'. $row["height"] .'</td><td>'. $row["position"] .'</td><td>'. $row["nationality"] .'</td><td>'. $row["joined"] .'</td><td>'. $row["contract"] . '</td><td><img width="150px" src="'. $row["picture"] .'"/></td><td><button class="btn btn-light" data-toggle="modal" data-target="#modal'.$row["id"].'"><img width="20px" src="pen.png"></button></td></tr>';

		$modals = $modals . '<div class="modal fade" id="modal'.$row["id"].'"tabindex="-1" role="dialog" aria-hidden="true"><div class="modal-dialog" role="document"><div class ="modal-content"><div class="modal-header"> <h5 class="modal-title" id="exampleModalLabel">Update Record</h5><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="modal-body"> <form action="" method="post"> <input type="hidden" name="updateID" value="'.$row["id"].'"> <div class="form-group"> <label>Player Name</label> <input type="text" class="form-control" name="updateName" value="'.$row["name"].'"> </div><div class="form-group"> <label>Date of Birth</label> <input type="date" class="form-control" name="updateDob" value="'.$row["dob"].'"> </div><div class="form-group"> <label>Place of Birth</label> <input type="text" class="form-control" name="updatePob" value="'.$row["pob"].'"></div><div class ="form-group"> <label">Height</label> <input type="text" class="form-control" name="updateHeight" value="'.$row["height"].'"> </div><div class ="form-group"> <label>Position</label> <input type="text" class ="form-control" name="updatePosition" value="'.$row["position"].'"> </div><div class="form-group"> <label>Nationality</label> <input type="text" class="form-control" name="updateNationality" value="'.$row["nationality"].'"> </div><div class="form-group"> <label>Join Date </label> <input type="text" class="form-control" name="updateJoined" value="'.$row["joined"].'"></div><div class="form-group"> <label>Contract Until</label> <input type="text" class="form-control" name="updateContract" value="'.$row["contract"].'"> </div><div class="form-group"> <label>Picture </label> <input type="text" class="form-control" name="updatePicture" value="'.$row["picture"].'"> </div><button name="updatePlayer" type="submit" class="btn btn-success">Update Record</button> <button  class="btn btn-danger" type="button" data-dismiss="modal" data-toggle="modal" data-target="#deleteModal'.$row["id"].'">Delete Record</button> <button type="button" class="btn btn-dark" data-dismiss="modal">Close</button></form></div></div></div></div>';

		$modals = $modals . '<div class="modal fade" id="deleteModal'.$row["id"].'" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><h4 class="modal-title">Delete Record</h4><button type="button" class="close" data-dismiss="modal">&times;</button></div><div class="modal-body"><p>Are you sure you wish to delete record? This cannot be undone.</p></div><div class="modal-footer"><form action="" method="post"><input name="deleteID" type="hidden" value="'.$row["id"].'"><button type="submit" name="deletePlayer" class="btn btn-danger">Delete</button><button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button></form></div></div></div></div></div>';

	}

	$modals = $modals . '<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-hidden="true"><div class="modal-dialog" role="document"><div class ="modal-content"><div class="modal-header"> <h5 class="modal-title" id="exampleModalLabel">Update Record</h5><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="modal-body"> <form action="" method="post"> <div class="form-group"> <label>Player Name</label> <input type="text" class="form-control" name="updateName"> </div><div class="form-group"> <label>Date of Birth</label> <input type="date" class="form-control" name="updateDob"> </div><div class="form-group"> <label>Place of Birth</label> <input type="text" class="form-control" name="updatePob"></div><div class ="form-group"> <label">Height</label> <input type="text" class="form-control" name="updateHeight"> </div><div class ="form-group"> <label>Position</label> <input type="text" class ="form-control" name="updatePosition"> </div><div class="form-group"> <label>Nationality</label> <input type="text" class="form-control" name="updateNationality"> </div><div class="form-group"> <label>Join Date </label> <input type="text" class="form-control" name="updateJoined"></div><div class="form-group"> <label>Contract Until</label> <input type="text" class="form-control" name="updateContract"> </div><div class="form-group"> <label>Picture </label> <input type="text" class="form-control" name="updatePicture"> </div><input name="addPlayer" type="submit" class="btn btn-success" value="Add Record" /> <button type="button" class="btn btn-dark" data-dismiss="modal">Close</button></form></div></div></div></div>';

	$table = $table . '</tbody></table>';
	return $table.$modals;	
}

if(isset($_POST['PlayersButton'])){

	$table = getPlayers();
}

if(isset($_POST['updatePlayer'])){

	$query = "UPDATE Players SET name='".mysqli_real_escape_string($conn,$_POST['updateName'])."', dob='".mysqli_real_escape_string($conn,$_POST['updateDob'])."', pob='".mysqli_real_escape_string($conn,$_POST['updatePob'])."', height='".mysqli_real_escape_string($conn,$_POST['updateHeight'])."', position='".mysqli_real_escape_string($conn,$_POST['updatePosition'])."', nationality='".mysqli_real_escape_string($conn,$_POST['updateNationality'])."', joined='".mysqli_real_escape_string($conn,$_POST['updateJoined'])."', contract='".mysqli_real_escape_string($conn,$_POST['updateContract'])."', picture='".mysqli_real_escape_string($conn,$_POST['updatePicture'])."' WHERE id='".mysqli_real_escape_string($conn,$_POST['updateID'])."'";

if (mysqli_query($conn, $query)) {
	$table = getPlayers();
} else
{
	$table = "Error ! <br>".mysqli_error($conn);
}

}

if(isset($_POST['addPlayer'])){

	$query = "INSERT INTO Players(name, dob, pob, height, position, nationality, joined, contract, picture) VALUES ('".mysqli_real_escape_string($conn,$_POST['updateName'])."','".mysqli_real_escape_string($conn,$_POST['updateDob'])."','".mysqli_real_escape_string($conn,$_POST['updatePob'])."','".mysqli_real_escape_string($conn,$_POST['updateHeight'])."','".mysqli_real_escape_string($conn,$_POST['updatePosition'])."','".mysqli_real_escape_string($conn,$_POST['updateNationality'])."','".mysqli_real_escape_string($conn,$_POST['updateJoined'])."','".mysqli_real_escape_string($conn,$_POST['updateContract'])."','".mysqli_real_escape_string($conn,$_POST['updatePicture'])."')";

if (mysqli_query($conn, $query)) {
	$table = getPlayers();
} else
{
	$table = "Error ! <br>".mysqli_error($conn);
}

}

if(isset($_POST['deletePlayer'])){

	$query = "DELETE FROM Players WHERE id='".mysqli_real_escape_string($conn,$_POST['deleteID'])."'";

if (mysqli_query($conn, $query)) {
	$table = getPlayers();
} else
{
	$table = "Error ! <br>".mysqli_error($conn);
}
}

function getFixtures()
{
	require('db.php');
	$query = "SELECT * FROM Fixtures ORDER BY id";

	$result = $conn->query($query);

	$table = '<table class="table table-hover"><thead><tr><th scope="col">ID</th><th scope="col">Competition</th><th scope="col">Day</th><th scope="col">Date</th><th scope="col">Time</th><th scope="col">Opponent</th><th scope="col">Home/Away</th><th scope="col">Score (Rovers)</th><th scope="col">Opponent Score</th><th scope="col"><button class="btn btn-light" data-toggle="modal" data-target="#addModal"><img width="20px" src="https://www.pngrepo.com/download/138538/plus-sign.png"></button></th></tr></thead><tbody>';

	while($row = $result->fetch_assoc()){
		$table = $table . '<tr><th scope="row">' . $row["id"] . '</th><td width="150px">'. $row["competition"] .'</td><td>'. $row["day"] .'</td><td>'. $row["date"] .'</td><td>'. $row["time"] .'</td><td>'. $row["opponent"] .'</td><td>'. $row["homeaway"] .'</td><td>'. $row["score"] .'</td><td>'. $row["scoreO"] . '</td><td><button class="btn btn-light" data-toggle="modal" data-target="#modal'.$row["id"].'"><img width="20px" src="pen.png"></button></td></tr>';
	
	$daysel = array("Monday"=>"","Tuesday"=>"","Wednesday"=>"","Thursday"=>"","Friday"=>"","Saturday"=>"","Sunday"=>"");
	$daysel[$row["day"]] = "selected";
	$homesel = array("Home"=>"","Away"=>"");
	$homesel[$row["homeaway"]] = "selected";

	$modals = $modals . '<div class="modal fade" id="modal'.$row["id"].'" tabindex="-1" role="dialog" aria-hidden="true"> <div class="modal-dialog" role="document"> <div class="modal-content"> <div class="modal-header"> <h5 class="modal-title" id="exampleModalLabel">Update Record</h5> <button type="button" class="close" data-dismiss="modal" aria-label="Close"> <span aria-hidden="true">&times;</span> </button> </div><div class="modal-body"> <form action="" method="post"> <input type="hidden" name="updateID" value="'.$row["id"].'"> <div class="form-group"> <label>Competition</label> <input type="text" class="form-control" name="updateCompetition" value="'.$row["competition"].'"> </div><div class="form-group"> <label>Day</label> <select class="form-control" name="updateDay"> <option '.$daysel["Monday"].' >Monday</option> <option '.$daysel["Tuesday"].'>Tuesday</option> <option '.$daysel["Wednesday"].'>Wednesday</option> <option '.$daysel["Thursday"].'>Thursday</option> <option '.$daysel["Friday"].'>Friday</option> <option '.$daysel["Saturday"].'>Saturday</option> <option '.$daysel["Sunday"].'>Sunday</option> </select> </div><div class="form-group"> <label>Date</label> <input type="date" class="form-control" name="updateDate" value="'.$row["date"].'"> </div><div class="form-group"> <label">Time</label> <input type="time" class="form-control" name="updateTime" value="'.$row["time"].'"> </div><div class="form-group"> <label>Opponent</label> <input type="text" class="form-control" name="updateOpponent" value="'.$row["opponent"].'"> </div><div class="form-group"> <label>Home/Away</label> <select class="form-control" name="updateHomeAway"> <option '. $homesel["Home"] .' >Home</option> <option '. $homesel["Away"] .' >Away</option> </select> </div><div class="form-group"> <label>Score (Rovers)</label> <input type="text" class="form-control" name="updateScore" value="'.$row["score"].'"> </div><div class="form-group"> <label>Opponent Score</label> <input type="text" class="form-control" name="updateScoreO" value="'.$row["scoreO"].'"> </div><button name="updateFixture" type="submit" class="btn btn-success">Update Record</button> <button  class="btn btn-danger" type="button" data-dismiss="modal" data-toggle="modal" data-target="#deleteModal'.$row["id"].'">Delete Record</button> <button type="button" class="btn btn-dark" data-dismiss="modal">Close</button></form> </div></div></div></div>';

	$modals = $modals . '<div class="modal fade" id="deleteModal'.$row["id"].'" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><h4 class="modal-title">Delete Record</h4><button type="button" class="close" data-dismiss="modal">&times;</button></div><div class="modal-body"><p>Are you sure you wish to delete record? This cannot be undone.</p></div><div class="modal-footer"><form action="" method="post"><input name="deleteID" type="hidden" value="'.$row["id"].'"><button type="submit" name="deleteFixture" class="btn btn-danger">Delete</button><button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button></form></div></div></div></div></div>';
	}

	$modals = $modals . '<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-hidden="true"> <div class="modal-dialog" role="document"> <div class="modal-content"> <div class="modal-header"> <h5 class="modal-title" id="exampleModalLabel">Add Record</h5> <button type="button" class="close" data-dismiss="modal" aria-label="Close"> <span aria-hidden="true">&times;</span> </button> </div><div class="modal-body"> <form action="" method="post"><div class="form-group"> <label>Competition</label> <input type="text" class="form-control" name="updateCompetition"> </div><div class="form-group"> <label>Day</label> <select class="form-control" name="updateDay"> <option>Monday</option> <option>Tuesday</option> <option>Wednesday</option> <option>Thursday</option> <option>Friday</option> <option>Saturday</option> <option>Sunday</option> </select> </div><div class="form-group"> <label>Date</label> <input type="date" class="form-control" name="updateDate"> </div><div class="form-group"> <label">Time</label> <input type="time" class="form-control" name="updateTime"> </div><div class="form-group"> <label>Opponent</label> <input type="text" class="form-control" name="updateOpponent"> </div><div class="form-group"> <label>Home/Away</label> <select class="form-control" name="updateHomeAway"> <option >Home</option> <option>Away</option> </select> </div><div class="form-group"> <label>Score (Rovers)</label> <input type="text" class="form-control" name="updateScore"> </div><div class="form-group"> <label>Opponent Score</label> <input type="text" class="form-control" name="updateScoreO"> </div><input name="addPlayer" type="submit" class="btn btn-success" value="Add Record" /> <button type="button" class="btn btn-dark" data-dismiss="modal">Close</button></form> </div></div></div></div>';

	$table = $table . '</tbody></table>';
	return $table.$modals;
}

if(isset($_POST['FixturesButton'])){
	$table = getFixtures();
} 

if(isset($_POST['updateFixture'])){

	$query = "UPDATE Fixtures SET competition='".mysqli_real_escape_string($conn,$_POST['updateCompetition'])."', day='".mysqli_real_escape_string($conn,$_POST['updateDay'])."', date='".mysqli_real_escape_string($conn,$_POST['updateDate'])."', time='".mysqli_real_escape_string($conn,$_POST['updateTime'])."', opponent='".mysqli_real_escape_string($conn,$_POST['updateOpponent'])."', homeaway='".mysqli_real_escape_string($conn,$_POST['updateHomeAway'])."', score='".mysqli_real_escape_string($conn,$_POST['updateScore'])."', scoreO='".mysqli_real_escape_string($conn,$_POST['updateScoreO'])."' WHERE id='".mysqli_real_escape_string($conn,$_POST['updateID'])."'";

if (mysqli_query($conn, $query)) {
	$table = getFixtures();
} else
{
	$table = "Error ! <br>".mysqli_error($conn);
}

}

if(isset($_POST['deleteFixture'])){

	$query = "DELETE FROM Fixtures WHERE id='".mysqli_real_escape_string($conn,$_POST['deleteID'])."'";

if (mysqli_query($conn, $query)) {
	$table = getFixtures();
} else
{
	$table = "Error ! <br>".mysqli_error($conn);
}
}

if(isset($_POST['addFixture'])){

	$query = "INSERT INTO Fixtures(competition, day, date, time, opponent, homeaway, score, scoreO) VALUES ('".mysqli_real_escape_string($conn,$_POST['updateCompetition'])."','".mysqli_real_escape_string($conn,$_POST['updateDay'])."','".mysqli_real_escape_string($conn,$_POST['updateDate'])."','".mysqli_real_escape_string($conn,$_POST['updateTime'])."','".mysqli_real_escape_string($conn,$_POST['updateOpponent'])."','".mysqli_real_escape_string($conn,$_POST['updateHomeAway'])."','".mysqli_real_escape_string($conn,$_POST['updateScore'])."','".mysqli_real_escape_string($conn,$_POST['updateScoreO'])."')";

if (mysqli_query($conn, $query)) {
	$table = getFixtures();
} else
{
	$table = "Error ! <br>".mysqli_error($conn);
}

}

if(isset($_POST['ApiButton'])){

$table = '<center><h1>API Documentation</h1><br><p>All API calls return a JSON response.</p><p>You can call the API by sending HTTP GET requests to <code>https://www.sedhna.com/rovers/api/{query}</code></p><p>There are 3 types of call: <strong>News</strong>, <strong>Players</strong> and <strong>Fixtures</strong></p><table class="table table-striped"> <thead> <tr> <th scope="col">Action</th> <th scope="col">API Call</th> <th scope="col">Example</th> </tr></thead> <tbody> <tr> <th scope="row">Return API information (version, last updated)</th> <td><code>https://www.sedhna.com/rovers/api</code></td><td><code><a href="https://www.sedhna.com/rovers/api">https://www.sedhna.com/rovers/api</a></code></td></tr><tr> <th scope="row">Return all news in the database</th> <td><code>https://www.sedhna.com/rovers/api/news</code></td><td><code><a href="https://www.sedhna.com/rovers/api/news">https://www.sedhna.com/rovers/api/news</a></code></td></tr><tr> <th scope="row">Return a specific news by ID</th> <td><code>https://www.sedhna.com/rovers/api/news/{newsID}</code></td><td><code><a href="https://www.sedhna.com/rovers/api/news/1">https://www.sedhna.com/rovers/api/news/1</a></code></td></tr><tr> <th scope="row">Return all players in the database</th> <td><code>https://www.sedhna.com/rovers/api/players</code></td><td><code><a href="https://www.sedhna.com/rovers/api/players">https://www.sedhna.com/rovers/api/players</a></code></td></tr><tr> <th scope="row">Return a specific player by ID</th> <td><code>https://www.sedhna.com/rovers/api/players/{playerID}</code></td><td><code><a href="https://www.sedhna.com/rovers/api/players/1">https://www.sedhna.com/rovers/api/players/1</a></code></td></tr><tr> <th scope="row">Return all fixtures (past and future) in the database</th> <td><code>https://www.sedhna.com/rovers/api/fixtures</code></td><td><code><a href="https://www.sedhna.com/rovers/api/fixtures">https://www.sedhna.com/rovers/api/fixtures</a></code></td></tr><tr> <th scope="row">Return a specific fixture by ID</th> <td><code>https://www.sedhna.com/rovers/api/fixtures/{fixtureID}</code></td><td><code><a href="https://www.sedhna.com/rovers/api/fixtures/1">https://www.sedhna.com/rovers/api/fixtures/1</a></code></td></tr></tbody></table><p>For security reasons, users cannot be returned using the API. Users information can only be accessed from the admin panel.</p><p>Updating records in the admin panel will update the returned responses of the API.</p><br><br><p>The API was built using the <a href="https://www.slimframework.com">Slim Framwork</a>.</p></center>';

}  

if(isset($_POST['logoutButton'])){

session_destroy();
header('Location: login.php');

}     

?>

<html>

<head>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	
	<title>Admin Panel</title>

	<link rel="stylesheet" href="style.css">
	<link rel="stylesheet" href="bootstrap.min.css">

	<script type="text/javascript" src="jquery-2.2.3.min.js"></script>
	<script src="popper.min.js"></script>
	<script src="bootstrap.min.js"></script>

</head>

<body>

	<div id="container">

    <div id="sidebar">
        <div id="sidebar-elements">
            
        <div id="header"></div>

        <center><h1><span class="badge badge-dark">Admin Panel</span></h1></center>

        <div id="list">

        	<form action="" method="post">
	        <input type="submit" name="NewsButton" class="btn btn-light" value="News"/>
	    	</form>

        	<form action="" method="post">
	        <input type="submit" name="PlayersButton" class="btn btn-light" value="Players"/>
	    	</form>

        	<form action="" method="post">
	        <input type="submit" name="FixturesButton" class="btn btn-light" value="Fixtures"/>
	    	</form>
	        
        	<form action="" method="post">
	        <input type="submit" name="UsersButton" class="btn btn-light" value="Users"/>
	    	</form>

	    	<br>

	    	<form action="" method="post">
	        <input type="submit" name="ApiButton" class="btn btn-light" value="API Documentation"/>
	    	</form>

	   		<form action="" method="post">
	        <input type="submit" name="logoutButton" class="btn btn-light" value="Log Out"/>
	    	</form>

    	</div>
    	
    	</div>
    	
    	    	<div id="footer"><center><p>&copy;2019 EC1529114</p></center></div>

    </div>

    <div id="content">

	<?php echo $table; echo $modals; ?>

    </div>
    
</div>

</body>


</html>