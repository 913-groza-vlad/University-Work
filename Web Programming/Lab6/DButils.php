<?php

$host = 'localhost';
$dbName = 'documents';
$dbUserName = 'root';
$dbPassword = '';

$connection = mysqli_connect($host, $dbUserName, $dbPassword, $dbName);
if ($connection === false) {
    die("Could not connect to the database." . mysqli_connect_error());
}

?>