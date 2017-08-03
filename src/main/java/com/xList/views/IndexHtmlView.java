package com.xList.views;

public class IndexHtmlView {

    private static IndexHtmlView ourInstance;

    private final String topHtml;
    private final String bottomHtml;
    private final String loginForm;
    private final String logoutButton;

    public static IndexHtmlView getInstance(){
        if (ourInstance==null){
            ourInstance = new IndexHtmlView();
        }

        return ourInstance;
    }

    private IndexHtmlView() {
        PathHtml pathHtml =PathHtml.getInstance();
        this.loginForm=pathHtml.getPartialHtml("login-form.html");
        this.topHtml=pathHtml.getPartialHtml("top.html");
        this.bottomHtml=pathHtml.getPartialHtml("bottom.html");
        this.logoutButton=pathHtml.getPartialHtml("logout-button.html");
    }

    public String getTopHtml() {
        return topHtml;
    }

    public String getBottomHtml() {
        return bottomHtml;
    }

    public String getLoginForm() {
        return loginForm;
    }

    public String getLogoutButton() {
        return logoutButton;
    }
}
