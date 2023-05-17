<?php
require_once "DBUtils.php";
global $connection;

$author = $_POST['author'];
$title = $_POST['title'];
$number_of_pages = $_POST['number_of_pages'];
$type = $_POST['type'];
$format = $_POST['format'];

$con = mysqli_query($connection, "INSERT INTO document (author, title, number_of_pages, type, format) VALUES ('$author', '$title', '$number_of_pages', '$type', '$format');");
if ($con) {
    echo "Document added successfully!";
    header("Location: showDocuments.html");
} else {
    echo "Error: document could not be added!";
}
mysqli_close($connection);


?>