
<?php

if ($_SERVER['REQUEST_METHOD']=='POST'){

  $email = $_POST['loginEmail'];
  $pswrd = $_POST['loginPassword'];
  
	if ($email == "admin" && $pswrd == "admin") {
		session_start();
		$_SESSION["userid"] = "admin";
		header('Location: index.php');
	} else {
	    $error = '<h4 style="color:rgb(196,45,45);"><center>Wrong Username or Password</center></h4>';
	}  

}

?>


<html>

<head>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	
	<title>Admin Panel</title>

	<link rel="stylesheet" href="style.css">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

	<script type="text/javascript" src="http://code.jquery.com/jquery-2.2.3.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

</head>

<body>

	<div class="logo"></div>

  <div class="container">
    <div class="row">
      <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
        <div class="card card-signin my-5">
          <div class="card-body">
            <h5 class="card-title text-center"><strong>SIGN IN</strong></h5>
            <form class="form-signin" action="" method="post">
              <div class="form-label-group">
                <input type="text" id="inputEmail" name="loginEmail" class="form-control" placeholder="Username" required autofocus>
                <label for="inputEmail">Username</label>
              </div>

              <div class="form-label-group">
                <input type="password" id="inputPassword" name="loginPassword" class="form-control" placeholder="Password" required>
                <label for="inputPassword">Password</label>
              </div>

              <div class="custom-control custom-checkbox mb-3">
                <label><?php echo $error ?></label>
              </div>
              <input class="btn btn-lg btn-primary submitBtn btn-block text-uppercase" type="submit" name="loginBtn" value="Sign In">
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>




</html>

<style>
:root {
  --input-padding-x: 1.5rem;
  --input-padding-y: .75rem;
}

body {
  background: #ffcf2f;
  background: repeating-linear-gradient(120deg, #ffcf2f, #ffcf2f 10px, #ffd96a 10px, #ffd96a 20px);
}

.logo
{
	height: 180px;
	width: 150px;
	margin: auto;
	margin-top: 30px;
	background-image: url('logo.png');
	background-repeat: no-repeat;
	background-size: 100%;
}

.card-title
{
	color: #c42d2d;
}

.submitBtn
{
	background-color: #c42d2d;
	border: none;
}

.submitBtn:hover
{
	background-color: #ffd96a;
}

#inputEmail:focus
{
	border-color: #ffd96a;
	box-shadow: 0px 0px 2px 2px #ffd96a;
}

#inputPassword:focus
{
	border-color: #ffd96a;
	box-shadow: 0px 0px 2px 2px #ffd96a;
}

.card-signin {
  border: 0;
  border-radius: 1rem;
  box-shadow: 0 0.5rem 1rem 0 rgba(0, 0, 0, 0.1);
}

.card-signin .card-title {
  margin-bottom: 2rem;
  font-weight: 300;
  font-size: 1.5rem;
}

.card-signin .card-body {
  padding: 2rem;
}

.form-signin {
  width: 100%;
}

.form-signin .btn {
  font-size: 80%;
  border-radius: 5rem;
  letter-spacing: .1rem;
  font-weight: bold;
  padding: 1rem;
  transition: all 0.2s;
}

.form-label-group {
  position: relative;
  margin-bottom: 1rem;
}

.form-label-group input {
  height: auto;
  border-radius: 2rem;
}

.form-label-group>input,
.form-label-group>label {
  padding: var(--input-padding-y) var(--input-padding-x);
}

.form-label-group>label {
  position: absolute;
  top: 0;
  left: 0;
  display: block;
  width: 100%;
  margin-bottom: 0;
  /* Override default `<label>` margin */
  line-height: 1.5;
  color: #495057;
  border: 1px solid transparent;
  border-radius: .25rem;
  transition: all .1s ease-in-out;
}

.form-label-group input::-webkit-input-placeholder {
  color: transparent;
}

.form-label-group input:-ms-input-placeholder {
  color: transparent;
}

.form-label-group input::-ms-input-placeholder {
  color: transparent;
}

.form-label-group input::-moz-placeholder {
  color: transparent;
}

.form-label-group input::placeholder {
  color: transparent;
}

.form-label-group input:not(:placeholder-shown) {
  padding-top: calc(var(--input-padding-y) + var(--input-padding-y) * (2 / 3));
  padding-bottom: calc(var(--input-padding-y) / 3);
}

.form-label-group input:not(:placeholder-shown)~label {
  padding-top: calc(var(--input-padding-y) / 3);
  padding-bottom: calc(var(--input-padding-y) / 3);
  font-size: 12px;
  color: #777;
}