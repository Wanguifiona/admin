

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
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

        get("/dashboard", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "dashboard.hbs");
        }, new HandlebarsTemplateEngine());

        get("/users", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "users.hbs");
        }, new HandlebarsTemplateEngine());

        get("/bookings", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "bookings.hbs");
        }, new HandlebarsTemplateEngine());

        //login
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return  new ModelAndView(model, "login.hbs");
        }, new HandlebarsTemplateEngine());

        //login-form
//        post("/create/login/new", (request, response) -> {
//            Map<String,Object> model = new HashMap<String, Object>();
//            String name = request.queryParams("name");
//            String email = request.queryParams("email");
//            AdminUser users = new AdminUser(name,email);
//            SqlDaoLogin.save(); //save to db admin info
//            return new ModelAndView(model,"login-form.hbs");
//        }, new HandlebarsTemplateEngine());

        //login-view

//        get("/view/login",(request, response) -> {
//            Map<String,Object> model=new HashMap<String, Object>();
//            model.put("login",Login.all());
//            return new ModelAndView(model,"login-view.hbs");
//        },new HandlebarsTemplateEngine());

        get("/logout", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "logout.hbs");
        }, new HandlebarsTemplateEngine());

        get("/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return  new ModelAndView(model,"delete.hbs");
        }, new HandlebarsTemplateEngine());
      }
    }
