package prog.kiev.ua;

import java.sql.*;
import java.util.Scanner;

public class Apartments {
    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/Apartments";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "password";

    static Connection conn;

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        try {
            try{
                conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
                while (true) {
                    System.out.println("1: Show all apartments");
                    System.out.println("2: Show apartments by district");
                    System.out.println("3: Show apartments by price");
                    System.out.println("4: Show apartments for area");
                    System.out.println("5: Show apartments for selected number of rooms");
                    System.out.print("-> ");

                    String s = sc.nextLine();

                    switch (s) {
                        case "1":
                            sendRequest("SELECT * FROM apartments");
                            break;
                        case "2":
                            sendRequest("SELECT * FROM apartments ORDER BY district");
                            break;
                        case "3":
                            sendRequest("SELECT * FROM apartments ORDER BY price");
                            break;
                        case "4":
                            System.out.println("Enter desired area");
                            int area = Integer.parseInt(sc.nextLine());
                            sendRequest("SELECT * FROM apartments WHERE area >= " + area);
                            break;
                        case "5":
                            System.out.println("Enter desired number of rooms");
                            int rooms = Integer.parseInt(sc.nextLine());
                            sendRequest("SELECT * FROM apartments WHERE rooms = " + rooms);
                            break;
                        default:
                            return;
                        }
                    }
                }finally {
                sc.close();
                if (conn != null) conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return;
        }
    }

    private static void sendRequest(String statement) throws SQLException{
        PreparedStatement ps = conn.prepareStatement(statement);
        try{
            ResultSet rs = ps.executeQuery();
            try {
                while (rs.next()) {
                    String district = rs.getString("district");
                    int area = rs.getInt("area");
                    int rooms = rs.getInt("rooms");
                    int price = rs.getInt("price");

                    System.out.println("District: " + district + ", area: " + area
                            + "sq m, number of rooms: " + rooms + ", price: " + price);
                }
            } finally {
                rs.close();
            }
        } finally {
            ps.close();
        }
    }
}