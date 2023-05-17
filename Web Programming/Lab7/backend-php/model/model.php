<?php

require_once "../DButils.php";
require_once "document.php";

class Model {
    private $database;

    public function __construct()
    {
        $this->database = new DBUtils();
    }

    public function getAllDocuments() {
        $documents = $this->database->getAllDocuments();
        $documentsArray = array();
        foreach ($documents as $document) {
            $documentObject = new Document($document['docID'], $document['author'], $document['title'], $document['number_of_pages'], $document['type'], $document['format']);
            array_push($documentsArray, $documentObject);
        }
        return $documentsArray;
    }

    public function filterDocuments($type, $format) {
        $documents = $this->database->filterDocuments($type, $format);
        $documentsArray = array();
        foreach ($documents as $document) {
            $documentObject = new Document($document['docID'], $document['author'], $document['title'], $document['number_of_pages'], $document['type'], $document['format']);
            array_push($documentsArray, $documentObject);
        }
        return $documentsArray;
    }

    public function getDocument($id) {
        $document = $this->database->getDocument($id);
        $documentObject = new Document($document[0]['docID'], $document[0]['author'], $document[0]['title'], $document[0]['number_of_pages'], $document[0]['type'], $document[0]['format']);
        return $documentObject;
    }

    public function addDocument($author, $title, $number_of_pages, $type, $format) {
        return $this->database->addDocument($author, $title, $number_of_pages, $type, $format);
    }

    public function deleteDocument($id) {
        return $this->database->deleteDocument($id);
    }

    public function updateDocument($id, $author, $title, $number_of_pages, $type, $format) {
        return $this->database->updateDocument($id, $author, $title, $number_of_pages, $type, $format);
    }
}

?>