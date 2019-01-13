package clientSide.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("user") == null) {
            resp.setStatus(401);
            return;
        }

        req.getRequestDispatcher("WEB-INF/pages/home.jsp")
                .forward(req, resp);
    }

}
