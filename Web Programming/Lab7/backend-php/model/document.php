<?php

class Document implements JsonSerializable {
    private $docID;
    private $title;
    private $author;
    private $number_of_pages;
    private $type;
    private $format;


    function __construct($id, $author, $title, $number_of_pages, $type, $format)
    {
        $this->docID = $id;
        $this->title = $title;
        $this->author = $author;
        $this->number_of_pages = $number_of_pages;
        $this->type = $type;
        $this->format = $format;        
    }

    public function get_id(){
        return $this->docID;
    }

    public function get_title(){
        return $this->title;
    }

    public function get_author(){
        return $this->author;
    }

    public function get_number_of_pages(){
        return $this->number_of_pages;
    }

    public function get_type(){
        return $this->type;
    }

    public function get_format(){
        return $this->format;
    }

    public function set_title($newTitle){
        $this->title = $newTitle;
    }

    public function set_author($newAuthor){
        $this->author = $newAuthor;
    }

    public function set_number_of_pages($newNumberPages){
        $this->number_of_pages = $newNumberPages;
    }

    public function set_type($newType){
        $this->type = $newType;
    }

    public function set_format($newFormat){
        $this->format = $newFormat;
    }

    public function jsonSerialize()
    {
        $vars = get_object_vars($this);
        return $vars;

    }
}

?>