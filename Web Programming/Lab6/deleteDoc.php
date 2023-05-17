
<?php
require_once "DBUtils.php";
global $connection;

if (isset($_POST['id']) && !empty(trim($_POST['id']))) {
    $id = $_POST['id'];
    $con = mysqli_query($connection, "DELETE FROM document WHERE docID = '$id';");
    if ($con) {
        echo "Document deleted successfully!";
        header("Location: showDocuments.html");
    } else {
        echo "Error: document could not be deleted!";
    }
    mysqli_close($connection);
} else {
    echo "Error: document could not be deleted!";
}

?>