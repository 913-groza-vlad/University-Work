<?php
require_once "DBUtils.php";
global $connection;

$id = "";
$author = "";
$title = "";
$number_of_pages = "";
$type = "";
$format = "";

if (isset($_GET['id']) && !empty(trim($_GET['id']))) {
    $id = trim($_GET['id']);
    $con = mysqli_query($connection, "SELECT * FROM document WHERE docID = '$id';");
    if ($con) {
        $row = mysqli_fetch_array($con);
        $author = $row['author'];
        $title = $row['title'];
        $number_of_pages = $row['number_of_pages'];
        $type = $row['type'];
        $format = $row['format'];
    } else {
        echo "Error: document could not be retrieved!";
    }
    mysqli_close($connection);
} else {
    echo "Error: document could not be retrieved!";
}

?>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Update Document</title>
        <link rel="stylesheet" href="styles/update-form.css">
    </head>

    <body>
    <div class="container">
        <h3>Update document '<?php echo $title; ?>'</h3>
        <p><b>Fill in the form to update the document.</b></p>

        <form action="updateDoc.php" method="post">
            <input type="hidden" name="docID" value="<?php echo trim($_GET['id']); ?>">
            <input type="text" name="author" placeholder="Author" value="<?php echo $author; ?>"><br>
            <input type="text" name="title" placeholder="Title" value="<?php echo $title; ?>"><br>
            <input type="number" name="number_of_pages" placeholder="Number of Pages" value="<?php echo $number_of_pages; ?>"><br>
            <input type="text" name="type" placeholder="Type" value="<?php echo $type; ?>"><br>
            <input type="text" name="format" placeholder="Format" value="<?php echo $format; ?>"><br>
            <div class="confirm-button">
                <button type="submit">Update Document</button>
                <button type="reset" onclick="window.location.href='showDocuments.html'">Cancel</button>
            </div>
        </form>
    </div>
    </body>

</html>