package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.Login;

/** Servlet that manages the log in behavior of the webpage. */
@WebServlet("/login")
public class LogInServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {   
    // Get user's email address if logged in
    UserService userService = UserServiceFactory.getUserService();
    String emailAddress = null;
    User currentUser = userService.getCurrentUser();
    if (currentUser != null) {
      emailAddress = currentUser.getEmail();
    }
    
    // Send logout URL if user is logged in, otherwise send login URL
    String url = emailAddress == null ? userService.createLoginURL("/") : userService.createLogoutURL("/");
    
    response.setContentType("application/json;");
    response.getWriter().println((new Gson()).toJson(new Login(emailAddress, url)));
  }

}
