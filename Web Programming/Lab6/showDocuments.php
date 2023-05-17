<?php
require_once "DBUtils.php";
global $connection;
$con = mysqli_query($connection, "SELECT * FROM document;");

if ($con) {
   $rows = mysqli_num_rows($con);
   $documents = array();
   $type = $_GET['type'];
   $format = $_GET['format'];
   for ($i = 0; $i < $rows; $i++) {
      $row = mysqli_fetch_array($con);
      if (str_contains($row['type'], $type) && str_contains($row['format'], $format)) {
         array_push($documents, array("id" => (int)$row['docID'], 
                                    "author" => $row['author'], 
                                    "title" => $row['title'], 
                                    "number_of_pages" => (int)$row['number_of_pages'], 
                                    "type" => $row['type'], 
                                    "format" => $row['format']));
      }
   }
   mysqli_free_result($con);
   echo json_encode($documents);
}

mysqli_close($connection);
?>