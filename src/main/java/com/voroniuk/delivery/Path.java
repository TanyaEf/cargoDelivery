package com.voroniuk.delivery;

public class Path {

    //pages
    public static final String PAGE__MAIN = "/WEB-INF/jsp/main.jsp";
    public static final String PAGE__REGISTER = "/WEB-INF/jsp/register.jsp";
    public static final String PAGE__USER_ACCOUNT = "/WEB-INF/jsp/user_account.jsp";
    public static final String PAGE__MANAGER_ACCOUNT = "/WEB-INF/jsp/manager_account.jsp";
    public static final String PAGE__ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
    public static final String PAGE__REPORT = "/WEB-INF/jsp/report_page.jsp";
    public static final String PAGE__EDIT = "/WEB-INF/jsp/edit.jsp";

    //commands
    public static final String COMMAND__MAIN = "/controller?command=main";
    public static final String COMMAND__ACCOUNT = "/controller?command=account";
    public static final String COMMAND__MANAGER_ACCOUNT = "/controller?command=manager_account";
    public static final String COMMAND__USER_ACCOUNT = "/controller?command=user_account";
    public static final String COMMAND__REPORT = "/controller?command=report";
    public static final String COMMAND__EDIT = "/controller?command=edit";

}
