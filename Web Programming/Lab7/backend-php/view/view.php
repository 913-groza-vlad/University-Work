<?php

require_once "../model/document.php";

class View {
    public function __construct() {}

    public function output($parameters) {
        echo json_encode($parameters);
    }

    public function outputDocument($document) {
        echo json_encode($document);
    }

    public function getResult($result) {
        echo "{\"result\" : \"$result\"}";
    }
}

?>