package clientSide.servlets;

import clientSide.Security;
import clientSide.entities.Post;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/pages/addPost.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String type = req.getParameter("type");
        String salary = req.getParameter("salary");
        String email = req.getParameter("email");
        String workTime = req.getParameter("workTime");

        Security.addPost(title,description,type,salary,email, workTime);

        resp.sendRedirect("/home");
    }
}
