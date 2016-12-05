package ua.kiev.prog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Room", urlPatterns = "/room")
public class RoomServlet extends HttpServlet {
    private UserList users = UserList.getInstance();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String room = request.getParameter("room");
        users.changeRoom(login, room);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
