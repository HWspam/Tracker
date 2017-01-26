package com.company;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {

    static HashMap<String, User> userList = new HashMap<>();

    public static void main(String[] args) {
        Spark.init();

        Spark.get(
                "/",
                (((request, response) -> {

                    Session session = request.session();
                    String name = session.attribute("loginName");
                    User user = userList.get(name);


                    HashMap n = new HashMap();
                    if(user ==null||user.name.isEmpty()) {
                        return new ModelAndView(n, "thing.html");

                    }
                    n.put("name", name);
                    n.put("messages", user.messageList);


                    return new ModelAndView(n, "home.html");
                })),

                new MustacheTemplateEngine()


        );

        Spark.post(
                "/create-user",
                (((request, response) ->{

                    String name = request.queryParams("loginName");

                    User user = userList.get(name);
                    if(user == null){
                        user = new User(name);
                        userList.put(name, user);
                    }
                    Session session = request.session();
                    session.attribute("loginName", name);


                    response.redirect("/");
                    return "";
                }))

        );

        Spark.post(
                "/create-message",
                (((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("loginName");
                    User user = userList.get(name);
                    if(user == null) {
                        throw new Exception("No User Found");

                    }

                    String message = request.queryParams("message");
                    Messages messages = new Messages(message);

                    user.messageList.add(messages);

                    response.redirect("/");
                    return "";
                }))
        );

        Spark.post(
                "/delete-message",
                (((request, response) -> {
                    Session session = request.session();
                    String message = session.attribute("loginName");
                    User user = userList.get(message);
                    if(user == null) {
                        throw new Exception("No Current Messages");
                    }
                    String delete = request.queryParams("numberDelete");
                    int e = Integer.parseInt(delete)-1;


                    user.messageList.remove(e);

                    response.redirect("/");
                    return "";
                }))
        );

        Spark.post(
                "/edit-message",
                (((request, response) ->{
                    Session session = request.session();
                    String message = session.attribute("loginName");
                    User user = userList.get(message);
                    if(user == null) {
                        throw new Exception("No Current Messages");
                    }
                    String edit = request.queryParams("numberEdit");
                    int f =Integer.parseInt(edit)-1;



                    user.messageList.remove(f);

                    String edited = request.queryParams("messageEdit");
                    Messages any = new Messages(edited);

                    user.messageList.add(f, any);



                    response.redirect("/");
                    return "";
                }))
        );

        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );



    }
}





