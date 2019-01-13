package clientSide.servlets;

import clientSide.Security;
import clientSide.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");

        User user = Security.registerUser(username,firstName,lastName,email, password);
        if (user == null) {
            resp.setStatus(401);
            return;
        }

        req.getSession().setAttribute("user", user);
        resp.sendRedirect("/home");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/pages/signup.jsp")
                .forward(req, resp);
    }
}
