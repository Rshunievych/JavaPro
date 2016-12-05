package ua.kiev.prog;

import com.sun.org.apache.xpath.internal.SourceTree;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ChangeStatus", urlPatterns = "/status")
public class ChangeStatusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String status = request.getParameter("status");

        UserList.getInstance().changeStatus(login, status);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}