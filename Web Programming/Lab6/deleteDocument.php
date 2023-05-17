<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Delete Document</title>
        <link rel="stylesheet" href="styles/confirm-delete.css">
    </head>
    <body>
        <div class="container">
            <h3>Confirm the deletion of the document with id <?php echo trim($_GET['id']); ?> </h3>
            <form action="deleteDoc.php" method="post">
                <input type="hidden" name="id" value="<?php echo trim($_GET['id']); ?>">
                <button type="submit" class="yes">Yes</button>
            </form>
            <button class="no" onclick="window.location.href='showDocuments.html'">
                No, Cancel
            </button>
        </div> 
    </body>
</html>