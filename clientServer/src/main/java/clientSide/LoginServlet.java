package clientSide;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Users user = Security.login(username, password);
        if (user == null) {
            resp.setStatus(401);
            return;
        }

        req.getSession().setAttribute("user", user);
        resp.sendRedirect("/home");
    }

}
