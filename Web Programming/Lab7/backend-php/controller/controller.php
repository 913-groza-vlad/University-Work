<?php

header("Access-Control-Allow-Origin: *");

require_once '../model/model.php';
require_once '../view/view.php';

class Controller {
    private $model;
    private $view;

    public function __construct()
    {
        $this->model = new Model();
        $this->view = new View();
    }

    public function service(){
        if (isset($_GET['action']) && !empty($_GET['action'])){
            if( $_GET['action'] == "getAllDocuments"){
                $this->{$_GET['action']}();
            }
            else if($_GET['action'] == 'addDocument'){
                $this->{$_GET['action']}($_GET['author'], $_GET['title'], $_GET['number_of_pages'], $_GET['type'], $_GET['format']);
            }
            else if($_GET['action'] == 'deleteDocument'){
                $this->{$_GET['action']}($_GET['id']);
            }
            else if($_GET['action'] == 'updateDocument'){
                $this->{$_GET['action']}($_GET['id'], $_GET['author'], $_GET['title'], $_GET['number_of_pages'], $_GET['type'], $_GET['format']);
            }
            else if($_GET['action'] == "filterDocuments"){
                $this->{$_GET['action']}($_GET['type'], $_GET['format']);
            }
            else if ($_GET['action'] == "getDocument"){
                $this->{$_GET['action']}($_GET['id']);
            }
        }
    }

    public function getAllDocuments() {
        $documents = $this->model->getAllDocuments();
        $this->view->output($documents);
    }

    public function filterDocuments($type, $format) {
        $documents = $this->model->filterDocuments($type, $format);
        $this->view->output($documents);
    }

    public function getDocument($id) {
        $document = $this->model->getDocument($id);
        $this->view->output($document);
    }

    public function addDocument($author, $title, $number_of_pages, $type, $format) {
        $result = $this->model->addDocument($author, $title, $number_of_pages, $type, $format);
        if($result > 0) {
            $message = "Successfully added document";
        }
        else {
            $message = "Error adding document";
        }
        $this->view->getResult($message);
    }

    public function deleteDocument($id) {
        $result = $this->model->deleteDocument($id);
        if($result > 0) {
            $message = "Document successfully removed";
        }
        else {
            $message = "Error adding document";
        }
        $this->view->getResult($message);
    }

    public function updateDocument($id, $author, $title, $number_of_pages, $type, $format) {
        $result = $this->model->updateDocument($id, $author, $title, $number_of_pages, $type, $format);
        if($result > 0) {
            $message = "Document successfully updated";
        }
        else {
            $message = "Error updating document";
        }
        $this->view->getResult($message);
    }
}

$controller = new Controller();
$controller->service();

?>