package clientSide.servlets;

import clientSide.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GuestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        user.setUserName("Anonymous");
        req.getSession().setAttribute("user", user);

        req.getRequestDispatcher("WEB-INF/pages/home.jsp")
                .forward(req, resp);
    }
}

