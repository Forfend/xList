package com.xList.servlets;

import com.xList.dao.CRUDrepository.UserDao;
import com.xList.dao.entities.User;
import com.xList.dao.repository.UserImplementation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(name = "SharedServlet",value = {"/noteajax/*"})
public class SharedServlet extends HttpServlet {

    private Logger logger = Logger.getLogger("com.xList.servlets");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        String searchText = new String(request.getParameter("search-sharing-user").getBytes("UTF-8"),
                "UTF-8");
        logger.fine("AJAX output\t" + searchText);
        UserDao userDao = new UserImplementation();
        List<User> users = userDao.findByUsernameByName(searchText);
        if (users!=null){
            String resStr = "[";
            resStr  += users.stream()
                    .map(u->"{\"name\" : \"" + u.getName() + "\", \"username\" : \"" + u.getUsername() +
                            "\",  \"id\" : \"" + u.getId() + "\"}")
                    .collect(Collectors.joining(","));
            resStr +="]";
            writer.println(resStr);
        }
        writer.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
