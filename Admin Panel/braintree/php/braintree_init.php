<?php 
	session_start();
	require_once("../lib/autoload.php");
	// if(file_exists(__DIR__ . "/../.env"));
	// {
	// 	$dotenv= new Dotenv\Dotenv(__DIR__ . "/../");
	// 	$dotenv->load();
	// }
	Braintree_Configuration::environment("sandbox");
	Braintree_Configuration::merchantId("zwzjqp4pj5jsmsn2");
	Braintree_Configuration::publicKey("s7zsjrf3pbtrkfwj");
	Braintree_Configuration::privateKey("095a5f68b16c9be484bb23ee862e3fb5");
 ?>