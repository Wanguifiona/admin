import dao.Sql2oBookingDao;
import dao.Sql2oInvoiceDao;
import dao.Sql2oUserDao;
import models.Booking;
import models.Invoice;
import models.User;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {

        port(getHerokuAssignedPort());
        staticFileLocation("/public");

        String connectionString = "jdbc:postgresql://ec2-54-85-113-73.compute-1.amazonaws.com:5432/d1ngi3fj0iumcd";
        Sql2o sql2o = new Sql2o(connectionString, "lkyizybqkygkvk", "81e47c09943f1d05aabf6e71a744af70e738472850013f97193555842d8aaaa9");

//        String connectionString = "jdbc:postgresql://localhost:5432/user_global";
//        Sql2o sql2o = new Sql2o(connectionString, "adamu", "Adamu");

        Sql2oUserDao sql2oUserDao = new Sql2oUserDao(sql2o);
        Sql2oBookingDao sql2oBookingDao = new Sql2oBookingDao(sql2o);
        Sql2oInvoiceDao sql2oInvoiceDao = new Sql2oInvoiceDao(sql2o);


        get("/dashboard", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            List<User> userList = sql2oUserDao.getAll();
            List<Invoice> invoiceList = sql2oInvoiceDao.findAll();

            model.put("new", userList);
            model.put("total", userList.size());
            model.put("orders", invoiceList.size());

            return new ModelAndView(model, "dashboard.hbs");
        }, new HandlebarsTemplateEngine());

        get("/users", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            List<User> userList = sql2oUserDao.getAll();
            model.put("users", userList);
            return new ModelAndView(model, "users.hbs");
        }, new HandlebarsTemplateEngine());

        get("/bookings", (request, response) -> {
            List<Invoice> invoiceList = sql2oInvoiceDao.findAll();

            Map<String, List<Invoice> > model = new HashMap<String, List<Invoice>>();
            model.put("All", invoiceList);
            return new ModelAndView(model, "bookings.hbs");
        }, new HandlebarsTemplateEngine());

        //login
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return  new ModelAndView(model, "login.hbs");
        }, new HandlebarsTemplateEngine());

//        //login-form
//        post("/login/new", (request, response) -> {
//            Map<String,Object> model = new HashMap<>();
//            String name = request.queryParams("name");
//            String email = request.queryParams("email");
//            String password = request.queryParams("password");
//
//            User newUser = new User(name, email,password);
//            Sql2oUserDao.save(newUser);
//            response.redirect("/users");
//            return null;
//        }, new HandlebarsTemplateEngine());


        get("/logout", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "login.hbs");
        }, new HandlebarsTemplateEngine());

        get("/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return  new ModelAndView(model,"delete.hbs");
        }, new HandlebarsTemplateEngine());
      }
    }
