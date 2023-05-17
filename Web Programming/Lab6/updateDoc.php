<?php
require_once "DBUtils.php";
global $connection;

$docID = $_POST['docID'];
$author = $_POST['author'];
$title = $_POST['title'];
$number_of_pages = $_POST['number_of_pages'];
$type = $_POST['type'];
$format = $_POST['format'];

$sql_update_query = "UPDATE document SET author = '$author', title = '$title', number_of_pages = '$number_of_pages', type = '$type', format = '$format' WHERE docID = '$docID';";
$con = mysqli_query($connection, $sql_update_query);

if ($con) {
    echo "Document updated successfully!";
    header("Location: showDocuments.html");
} 
else {
    echo "Error in updating document!";
}

mysqli_close($connection);
?>