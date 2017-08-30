package com.xList.service;

import com.xList.dao.CRUDrepository.UserDao;
import com.xList.dao.entities.User;
import com.xList.dao.repository.UserImplementation;
import com.xList.views.IndexHtmlView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

public class IndexTemplate {


    private Logger logger = Logger.getLogger("com.xList.service");

    interface FormField<E> {
        String check(E e);
    }

    private PrintWriter out;

    public IndexTemplate(PrintWriter out) {
        this.out = out;
    }

    public void showLoginForm() {
        if (out == null)
            return;
        out.println(IndexHtmlView.getInstance().getLoginForm());
    }

    public void showRegisterForm() {
        if (out == null) return;
        out.println(IndexHtmlView.getInstance().getRegisterForm());
    }

    public boolean checkLogin(HttpServletRequest request, HttpSession session) {
        if (out == null) return false;
        try {
            String userName = new String(request.getParameter("emailLogin").getBytes("iso-8859-1"),
                    "UTF-8");
            String loginPassword = new String(request.getParameter("loginPassword").getBytes("iso-8859-1"),
                    "UTF-8");
            UserDao userDao = new UserImplementation();
            User user = userDao.findByUsername(userName);
            if (user == null) {
                showErrorLoginForm();
                return false;
            } else if (user.getPassword().equals(loginPassword)) {
                session.setAttribute("username", user.getUsername());
                session.setAttribute("user_id", user.getId());
                session.setAttribute("name", user.getName());
                logger.fine("User has logged\t"+ user);
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        showErrorLoginForm();
        return false;
    }

    private void showErrorLoginForm() {
        String loginForm = IndexHtmlView.getInstance().getLoginForm();
        loginForm = loginForm.replace("form-group", "form-group has-error");
        loginForm = loginForm.replace("<!--insert-help-block-here-->"
                , "<span id=\"helpBlock1\" class=\"help-block\">Адреса ел. пошти або пароль є невірними!</span>");
        out.println(loginForm);
    }

    public boolean checkRegistration(HttpServletRequest request) {
        if (out == null) return false;
        String regForm = IndexHtmlView.getInstance().getRegisterForm();
        try {
            String id = request.getParameter("userId");
            String userName = new String(request.getParameter("emailLogin").getBytes("iso-8859-1"),
                    "UTF-8");
            regForm = checkFormField(1, regForm, userName, f -> {
                if (f.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
                        ) {
                    return null;
                }
                return "Невірний e-mail!";
            });
            String loginPassword = new String(request.getParameter("loginPassword").getBytes("iso-8859-1"),
                    "UTF-8");
            regForm = checkFormField(2, regForm, loginPassword, f -> {
                if (f.length() >= 6) {
                    return null;
                }
                return "Пароль повинен містити не менше 6 символів";
            });
            regForm = checkFormField(2, regForm, loginPassword, f -> {
                if (f.length() <= 20) {
                    return null;
                }
                return "Пароль не повинен перевищувати 20 символів";
            });
            String loginPasswordRepeat = new String(request.getParameter("loginPassword2").getBytes("iso-8859-1"),
                    "UTF-8");
            regForm = checkFormField(3, regForm, loginPassword.equals(loginPasswordRepeat), f -> {
                if (f) {
                    return null;
                }
                return "Паролі не співпадають!";
            });
            String registerName = new String(request.getParameter("registerName").getBytes("iso-8859-1"),
                    "UTF-8");
            regForm = checkFormField(4, regForm, registerName, f -> {
                if (f.length() >= 3) {
                    return null;
                }
                return "Ім'я повинно містити не менше 3 символів!";
            });

            if (!regForm.contains("has-error")) {
                User user;
                if (id == "") {
                    user = new User(userName, loginPassword, registerName);
                } else {
                    user = new User(Long.parseLong(id), userName, loginPassword, registerName);
                }
                System.out.println("profile\t" + user);
                UserDao userDao = new UserImplementation();
                userDao.registerUser(user);
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        out.println(regForm);
        return false;
    }

    private <E> String checkFormField(int fieldNumber, String form, E field, FormField<E> ff) {
        String msg = ff.check(field);
        form = form.replace("xkeepvall" + fieldNumber, "value=\"" + String.valueOf(field) + "\"");
        if (msg != null) {
            form = form.replace("form-group" + fieldNumber, "has-error");
            form = form.replace("<!--insert-help-block-here" + fieldNumber + "-->",
                    "<span class=\"help-block\">" + msg + "</span>");
        }
        return form;
    }

    public void showUserProlife(String username) {
        if (out == null) return;
        UserDao userDao = new UserImplementation();
        User user = userDao.findByUsername(username);
        if (user == null) return;
        String registrationForm = IndexHtmlView.getInstance().getRegisterForm();
        registrationForm = registrationForm.replace("xkeepvall1", "value=\"" + user.getUsername() + "\"");
        registrationForm = registrationForm.replace("xkeepvall2", "value=\"" + user.getPassword() + "\"");
        registrationForm = registrationForm.replace("xkeepvall3", "value=\"" + user.getPassword() + "\"");
        registrationForm = registrationForm.replace("xkeepvall4", "value=\"" + user.getName() + "\"");
        registrationForm = registrationForm.replace("xkeepvall5", "value=\"" + String.valueOf(user.getId()) + "\"");
        registrationForm = registrationForm.replace("Зареєструватись", "Зберегти");
        out.println(registrationForm);
    }

    public String getLoggedUserBar(HttpServletRequest request){
        String rightBar = IndexHtmlView.getInstance().getLogoutButton();
        String url = "//" + request.getServerName()
                + ((request.getServerPort()==8080)? "" : ":"+request.getServerPort())
                + "/note/search";
        rightBar = rightBar.replace("insert-search-notes-url-here",url);
        return rightBar;
    }

}
