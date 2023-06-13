using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using System.Xml.Linq;
using MySql.Data.MySqlClient;
using Lab9.Models;
using Org.BouncyCastle.Asn1;

namespace Lab9.DataAbstractionLayer
{
    public class DAL
    {
        public MySqlConnection getConnection()
        {
            string myConnectionString;
            myConnectionString = "Server=localhost;Database=documents;Uid=root;Pwd=;";
            return new MySqlConnection(myConnectionString);

        }

        public bool login(string username, string password)
        {

            List<String> users = new List<String>();

            try
            {
                MySqlConnection connection = getConnection();
                connection.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = connection;
                cmd.CommandText = "SELECT * from users where username = @username AND password = @password";

                cmd.Parameters.AddWithValue("@username", username);
                cmd.Parameters.AddWithValue("@password", password);
                Console.Write(username);
                Console.Write(password);
                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    users.Add(myreader.GetString("username"));
                }
                myreader.Close();
            }
            catch (MySqlException e)
            {
                Console.Write(e.Message);
                return false;
            }

            return users.Count == 1;
        }

        public List<Document> getAllDocuments()
        {
            List<Document> docs = new List<Document>();

            try
            {
                MySqlConnection connection = getConnection();
                connection.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = connection;
                cmd.CommandText = "SELECT * FROM document";

                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    Document doc = new Document();
                    doc.Id = myreader.GetInt32("docID");
                    doc.Author = myreader.GetString("author");
                    doc.Title = myreader.GetString("title");
                    doc.NumberOfPages = myreader.GetInt32("number_of_pages");
                    doc.Type = myreader.GetString("type");
                    doc.Format = myreader.GetString("format");
                    docs.Add(doc);
                }
                myreader.Close();
                connection.Close();
            }
            catch (MySqlException e)
            {
                Console.Write(e.Message);
            }

            return docs;
        }

        public bool deleteDocument(int id)
        {

            try
            {
                MySqlConnection connection = getConnection();
                connection.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = connection;
                cmd.CommandText = "DELETE FROM document WHERE docID = " + id;
                int rowCount = cmd.ExecuteNonQuery();

                connection.Close();
                return rowCount == 1;
            }
            catch (MySqlException e)
            {
                Console.Write(e.Message);
            }

            return false;
        }

        public bool addDocument(Document document)
        {
            try
            {
                MySqlConnection connection = getConnection();
                connection.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = connection;
                cmd.CommandText = "INSERT INTO document(author, title, number_of_pages, type, format) VALUES ('" +
                    document.Author + "', '" + document.Title + "', '" + document.NumberOfPages + "', '" + document.Type + "', '" + document.Format + "')";
                int rowCount = cmd.ExecuteNonQuery();

                connection.Close();
                return rowCount == 1;
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }

            return false;
        }

        public bool updateDocument(Document document)
        {
            try
            {
                MySqlConnection connection = getConnection();
                connection.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = connection;
                cmd.CommandText = "UPDATE document SET author='" + document.Author + "', title='" + document.Title + "', number_of_pages=" + document.NumberOfPages + ", type='" + document.Type + "', format='" + document.Format + "' WHERE docID='" + document.Id + "' ";
                cmd.Parameters.AddWithValue("@author", document.Author);
                cmd.Parameters.AddWithValue("@title", document.Title);
                cmd.Parameters.AddWithValue("@number_of_pages", document.NumberOfPages);
                cmd.Parameters.AddWithValue("@type", document.Type);
                cmd.Parameters.AddWithValue("@format", document.Format);
                cmd.Parameters.AddWithValue("@id", document.Id);

                int rowCount = cmd.ExecuteNonQuery();

                connection.Close();
                return rowCount == 1;
            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }

            return false;
        }

        public Document getDocument(int id)
        {
            Document doc = new Document();
            try
            {
                MySqlConnection connection = getConnection();
                connection.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = connection;
                cmd.CommandText = "SELECT * FROM document WHERE docID = '" + id + "' ";

                MySqlDataReader myreader = cmd.ExecuteReader();

                if (myreader.Read())
                {
                    doc.Id = myreader.GetInt32("docID");
                    doc.Author = myreader.GetString("author");
                    doc.Title = myreader.GetString("title");
                    doc.NumberOfPages = myreader.GetInt32("number_of_pages");
                    doc.Type = myreader.GetString("type");
                    doc.Format = myreader.GetString("format");
                }
                myreader.Close();
                connection.Close();
            }
            catch (MySqlException e)
            {
                Console.Write(e.Message);
            }

            return doc;
        }

        public List<string> getFormats()
        {
            List<string> formats = new List<string>();

            try
            {
                MySqlConnection connection = getConnection();
                connection.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = connection;
                cmd.CommandText = "SELECT DISTINCT format FROM document";

                MySqlDataReader myreader = cmd.ExecuteReader();
                while (myreader.Read())
                {
                    formats.Add(myreader.GetString("format"));
                    System.Diagnostics.Debug.WriteLine(myreader.GetString("format"));
                }

                myreader.Close();
                connection.Close();
            }
            catch (MySqlException e)
            {
                Console.Write(e.Message);
            }

            return formats;
        }


        public List<string> getTypes()
        {
            List<string> types = new List<string>();

            try
            {
                MySqlConnection connection = getConnection();
                connection.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = connection;
                cmd.CommandText = "SELECT DISTINCT type from document";

                MySqlDataReader myreader = cmd.ExecuteReader();
                while (myreader.Read())
                {
                    types.Add(myreader.GetString("type"));
                    System.Diagnostics.Debug.WriteLine(myreader.GetString("type"));
                }

                myreader.Close();
                connection.Close();
            }
            catch (MySqlException e)
            {
                Console.Write(e.Message);
            }

            return types;
        }


        public List<Document> getDocumentsOfFormat(string format)
        {
            List<Document> documentList = new List<Document>();
            try
            {
                MySqlConnection connection = getConnection();
                connection.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = connection;
                cmd.CommandText = "SELECT * FROM document WHERE format='" + format + "'";

                MySqlDataReader myreader = cmd.ExecuteReader();

                while (myreader.Read())
                {
                    Document document = new Document();
                    document.Id = myreader.GetInt32("id");
                    document.Author = myreader.GetString("author");
                    document.Title = myreader.GetString("title");
                    document.NumberOfPages = myreader.GetInt32("numberPages");
                    document.Type = myreader.GetString("type");
                    document.Format = myreader.GetString("format");
                    documentList.Add(document);
                }

                myreader.Close();
                connection.Close();

            }
            catch (MySqlException e)
            {
                Console.Write(e.Message);
            }

            return documentList;
        }


        public List<Document> getDocumentsOfType(string type)
        {
            List<Document> documentList = new List<Document>();
            try
            {
                MySqlConnection connection = getConnection();
                connection.Open();

                MySqlCommand cmd = new MySqlCommand();
                cmd.Connection = connection;

                cmd.CommandText = "SELECT * FROM document WHERE type='" + type + "'";

                MySqlDataReader myreader = cmd.ExecuteReader();
                while (myreader.Read())
                {
                    Document document = new Document();
                    document.Id = myreader.GetInt32("id");
                    document.Author = myreader.GetString("author");
                    document.Title = myreader.GetString("title");
                    document.NumberOfPages = myreader.GetInt32("numberPages");
                    document.Type = myreader.GetString("type");
                    document.Format = myreader.GetString("format");
                    documentList.Add(document);
                }

                myreader.Close();
                connection.Close();

            }
            catch (MySqlException ex)
            {
                Console.Write(ex.Message);
            }

            return documentList;
        }
    }
}