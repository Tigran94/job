package clientSide.servlets;

import clientSide.Security;
import clientSide.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/pages/profile.jsp")
                .forward(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        User user= (User) req.getSession().getAttribute("user");

        if(!user.getPassword().equals(currentPassword)){
            resp.setStatus(401);
            return;
        }

        Security.changePassword(user,newPassword);

        req.getSession().setAttribute("changedPassword","asdasd");

        resp.sendRedirect("/profile");

    }
}
