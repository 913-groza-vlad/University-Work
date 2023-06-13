using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Lab9.DataAbstractionLayer;
using Lab9.Models;

namespace Lab9.Controllers
{
    public class DocumentsController : Controller
    {
        // GET: Documents
        public ActionResult Documents()
        {
            return View("Documents");
        }

        [HttpGet]
        public string getDocuments() 
        {
            DAL dal = new DAL();
            List<Document> list = dal.getAllDocuments();

            // set a border between every row of the table
            string result = "<table><thead><th>Author</th><th>Title</th><th>Number of Pages</th><th>Type</th><th>Format</th><th>Operations</th></thead>";
            foreach (Document doc in list) {
                result += "<tr><td>" + doc.Author + "</td><td>" + doc.Title + "</td><td>" + doc.NumberOfPages + "</td><td>" + doc.Type + "</td><td>" + doc.Format + "</td><td>";
                result += "<a href='" + Url.Action("DeleteDocView", "Documents", new { id = doc.Id }) + "'>Delete</a><br>";
                result += "<a href='" + Url.Action("UpdateDocView", "Documents", new { id = doc.Id }) + "'>Update</a>";
                result +="</td></tr>";
            }

            result += "</table>";
            return result;
        }

        public ActionResult AddDocument()
        {
            return View("AddNewDocument");
        }

        public ActionResult SaveAddedDocument()
        {
            Document doc = new Document();
            // doc.Id = int.Parse(Request.Params["docID"]);
            doc.Author = Request.Params["author"];
            doc.Title = Request.Params["title"];
            doc.NumberOfPages = int.Parse(Request.Params["number_of_pages"]);
            doc.Type = Request.Params["type"];
            doc.Format = Request.Params["format"];

            DAL dal = new DAL();
            dal.addDocument(doc);
            return RedirectToAction("Documents");
        }


        public ActionResult DeleteDocView(int id)
        {
            return View("DeleteDoc", id);
        }

        [HttpPost]
        public ActionResult DeleteDocument(int id)
        {
            DAL dal = new DAL();
            dal.deleteDocument(id);
            return RedirectToAction("Documents");
        }

        public ActionResult UpdateDocView(int id)
        {
            DAL dal = new DAL();
            Document document = dal.getDocument(id);
            return View("UpdateDoc", document);
        }

        [HttpPost]
        public ActionResult UpdateDocument(Document document)
        {
            DAL dal = new DAL();
            document.NumberOfPages = int.Parse(Request.Params["Number_of_pages"]);
            dal.updateDocument(document);
            return RedirectToAction("Documents");
        }
    }
}