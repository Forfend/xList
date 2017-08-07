package com.xList.service;

import com.xList.dao.CRUDrepository.UserDao;
import com.xList.dao.entities.User;
import com.xList.dao.repository.UserImplementation;
import com.xList.views.IndexHtmlView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class IndexTemplate {

    private PrintWriter out;

    public IndexTemplate(PrintWriter out) {
        this.out = out;
    }

    public void showLoginForm() {
        if (out == null)
            return;
        out.println(IndexHtmlView.getInstance().getLoginForm());
    }

    public boolean checkLogin(HttpServletRequest request, HttpSession session){
        if (out==null) return false;
        try {
            String userName = new String(request.getParameter("emailLogin").getBytes("iso-8859-1"),
                    "UTF-8");
            String loginPassword = new String(request.getParameter("loginPassword").getBytes("iso-8859-1"),
                    "UTF-8");
            UserDao userDao = new UserImplementation();
            User user = userDao.findByUsername(userName);
            if (user == null){
                showLoginForm();
                return false;
            }else  if(user.getPassword().equals(loginPassword)){
                session.setAttribute("username",user.getUsername());
                session.setAttribute("user_id",user.getId());
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
