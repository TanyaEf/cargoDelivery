package com.voroniuk.delivery.web.command;

import com.voroniuk.delivery.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutCommand extends Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String forward = Path.COMMAND__MAIN;

        req.getSession().setAttribute("user", null);

        return forward;

    }
}
