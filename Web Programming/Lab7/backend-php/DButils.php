<?php


class DBUtils {
    private $host = 'localhost';
    private $dbName = 'documents';
    private $dbUserName = 'root';
    private $dbPassword = '';
    private $charset = 'utf8';	
    private $pdo, $error;

    public function __construct()
    {
		$dsn = "mysql:host=$this->host;dbname=$this->dbName;$this->charset";
		$opt = array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
			PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
			PDO::ATTR_EMULATE_PREPARES   => false);
		try {
			$this->pdo = new PDO($dsn, $this->dbUserName, $this->dbPassword, $opt);		
		}
		catch(PDOException $e){
			$this->error = $e->getMessage();
			echo "Error connecting to DB: " . $this->error;
		}        
    }

    public function filterDocuments($type, $format) {
        $query = $this->pdo->query("SELECT * FROM document WHERE type LIKE '%$type%' AND format LIKE '%$format%';"); 
        return $query->fetchAll(PDO::FETCH_ASSOC);
    }

    public function getAllDocuments() {
        $query = $this->pdo->query("SELECT * FROM document;");
        return $query->fetchAll(PDO::FETCH_ASSOC);
    }

    public function getDocument($id) {
        $query = $this->pdo->query("SELECT * FROM document WHERE docID = '$id';");
        return $query->fetchAll(PDO::FETCH_ASSOC);
    }

    public function addDocument($author, $title, $number_of_pages, $type, $format) {
        $query = $this->pdo->exec("INSERT INTO document (author, title, number_of_pages, type, format) VALUES ('$author', '$title', '$number_of_pages', '$type', '$format');");
        return $query;
    }

    public function deleteDocument($id) {
        $query = $this->pdo->exec("DELETE FROM document WHERE docID = '$id';");
        return $query;
    }

    public function updateDocument($id, $author, $title, $number_of_pages, $type, $format) {
        $query = $this->pdo->exec("UPDATE document SET author = '$author', title = '$title', number_of_pages = '$number_of_pages', type = '$type', format = '$format' WHERE docID = '$id';");
        return $query;
    }
}

?>