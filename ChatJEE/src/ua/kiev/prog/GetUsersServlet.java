package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "GetUserList", urlPatterns = "/users")
public class GetUsersServlet extends HttpServlet{
    private UserList users = UserList.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(users.toString());

        if (json != null) {
            OutputStream outputStream = resp.getOutputStream();
            byte[] buf = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(buf);
        }
    }
}
