using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Lab9.DataAbstractionLayer;
using Lab9.Models;

namespace Lab9.Controllers
{

    public class AuthenticateController : Controller
    {
        private DAL dal = new DAL();

        // GET: Authenticate
        public ActionResult Login()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Login(LoginModel model)
        {
            if (!ModelState.IsValid)
            {
                return View(model);
            }

            bool isValidLogin = dal.login(model.Username, model.Password);

            if (isValidLogin)
            {
                // Redirect to the Documents view upon successful login
                return RedirectToAction("Documents", "Documents");
            }

            ModelState.AddModelError("", "Invalid login.");
            return View(model);
        }
    }
}