package com.xList.service;

import com.xList.views.IndexHtmlView;

import java.io.PrintWriter;

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
}
