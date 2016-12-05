package ua.kiev.prog;

import java.util.ArrayList;

public class UserList {
    private static final UserList userList = new UserList();
    private final ArrayList<User> users = new ArrayList<>();

    private UserList(){
        users.add(new User("test", "test"));
    }

    public static UserList getInstance(){
        return userList;
    }

    public synchronized boolean logIn(User user){
        if(!users.contains(user)) {
            users.add(user);
            user.setStatus("ONLINE");
            System.out.println("new user added " + user.getLogin());
            return true;
        }

        for(User u: users){
            if(user.getLogin().equals(u.getLogin()) && user.getPass().equals(u.getPass()) && "OFFLINE".equals(u.getStatus())) {
                u.setStatus("ONLINE");
                System.out.println("user logged in as " + user.getLogin());
                return true;
            }
        }
        System.out.println("Wrong password, " + user.getLogin() + " OR user is already logged in " + user.getStatus());
        return false;
    }

    public void changeStatus(String login, String status) {
        for (User user : users) {
            if (user.getLogin().equals(login))
                user.setStatus(status);
        }
    }

    public void changeRoom(String login, String room) {
        for (User user : users) {
            if (user.getLogin().equals(login))
                user.setRoom(room);
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(User user: users)
            sb.append(user.getLogin() + " " + user.getStatus() + "nextuser");
        return sb.toString();
    }
}