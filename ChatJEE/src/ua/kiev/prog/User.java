package ua.kiev.prog;

public class User {
    private String login;
    private String pass;
    private String status = "OFFLINE";
    private String room;

    public User(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    @Override
    public boolean equals(Object obj) {
        User user = (User)obj;
        return (this.login.equals(user.login));
    }
    public String getLogin(){
        return login;
    }
    public String getPass(){
        return pass;
    }
    public String getStatus(){
        return this.status;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getRoom() {
        return room;
    }
    public void setRoom(String room) {
        this.room = room;
    }
}