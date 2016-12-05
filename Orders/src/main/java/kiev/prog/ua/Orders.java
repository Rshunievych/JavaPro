package kiev.prog.ua;

import java.sql.*;
import java.util.Scanner;

public class Orders {
    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/Orders";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "password";

    static Connection conn;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            try {
                conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
                initDB();
                while (true) {
                    System.out.println("------------------");
                    System.out.println("1: Add product");
                    System.out.println("2: Add client");
                    System.out.println("3: Create order");
                    System.out.println("4: See all product");
                    System.out.println("5: Check your id");
                    System.out.println("6: Inspect your orders");
                    String s = sc.nextLine();

                    switch (s) {
                        case "1":
                            addProduct(sc);
                            break;
                        case "2":
                            addClient(sc);
                            break;
                        case "3":
                            createOrder(sc);
                            break;
                        case "4":
                            getProductList();
                            break;
                        case "5":
                            checkId(sc);
                            break;
                        case "6":
                            getOrders(sc);
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                if (conn != null) conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return;
        }
    }

    private static void initDB() throws SQLException {
        Statement st = conn.createStatement();
        try {
            st.execute("DROP TABLE IF EXISTS Clients");
            st.execute("CREATE TABLE Clients (client_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, firstName VARCHAR(20), " +
                    "lastName VARCHAR(20))");
            st.execute("DROP TABLE IF EXISTS Products");
            st.execute("CREATE TABLE Products (product_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(30) " +
                    "DEFAULT NULL, price INT)");
            st.execute("DROP TABLE IF EXISTS Orders");
            st.execute("CREATE TABLE Orders (order_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,product_id INT NOT NULL," +
                    " client_id INT NOT NULL, price INT, quantity INT)");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void addProduct(Scanner sc) throws SQLException {
        System.out.print("Enter product name: ");
        String name = sc.nextLine();
        System.out.print("Enter products price: ");
        int price = Integer.parseInt(sc.nextLine());
        PreparedStatement ps = conn.prepareStatement("INSERT INTO Products (name, price) VALUES(?, ?)");
        try {
            ps.setString(1, name);
            ps.setInt(2, price);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }
    }

    private static void addClient(Scanner sc) throws SQLException {
        System.out.print("Enter first name: ");
        String firstName = sc.nextLine();
        System.out.print("Enter last name: ");
        String lastName = sc.nextLine();

        PreparedStatement ps = conn.prepareStatement("INSERT INTO Clients (firstName, lastName) VALUES(?, ?)");
        try {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } finally {
            ps.close();
        }
    }

    private static void createOrder(Scanner sc) throws SQLException {
        System.out.println("Enter product's id");
        int productId = Integer.parseInt(sc.nextLine());
        System.out.println("Enter quantity");
        int quantity = Integer.parseInt(sc.nextLine());
        System.out.println("Enter your id");
        int clientId = Integer.parseInt(sc.nextLine());

        PreparedStatement ps = conn.prepareStatement("INSERT INTO Orders (product_id, client_id, quantity, price) " +
                "VALUES(?, ?, ?, ?)");
        try {
            ps.setInt(1, productId);
            ps.setInt(2, clientId);
            ps.setInt(3, quantity);
            ps.setInt(4, countPrice(quantity, productId));
            ps.executeUpdate(); // for INSERT, UPDATE & DELETE
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ps.close();
        }
    }

    private static int countPrice(int quantity, int productId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT price FROM Products WHERE product_id = ?");
        try {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return quantity * rs.getInt("price");
        } finally {
            ps.close();
        }
    }

    private static void getProductList() throws SQLException{
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Products");
        try{
            ResultSet rs = ps.executeQuery();
            try {
                while (rs.next()) {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    int id = rs.getInt("product_id");

                    System.out.println("Product: " + name + ", price: " + price
                            + ", product id: " + id);
                }
            } finally {
                rs.close();
            }
        } finally {
            ps.close();
        }
    }

    private static void checkId(Scanner sc) throws SQLException{
        System.out.print("Enter your first name: ");
        String firstName = sc.nextLine();
        System.out.print("and your last name: ");
        String lastName = sc.nextLine();

        PreparedStatement ps = conn.prepareStatement("SELECT client_id FROM Clients WHERE firstname = ? AND lastName = ?");
        try{
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ResultSet rs = ps.executeQuery();
            try {
                rs.next();
                int id = rs.getInt("client_id");
                System.out.println("Your id is: " + id);
            } finally {
                rs.close();
            }
        } finally {
            ps.close();
        }
    }

    private static void getOrders(Scanner sc) throws SQLException {
        System.out.println("Enter your id");
        int clientId = sc.nextInt();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Orders WHERE client_id = ?");
        ps.setInt(1, clientId);
        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");
                System.out.println("Order id: " + orderId + ", product id: " + productId + ", quantity: "
                        + quantity + ", price: " + price);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ps.close();
        }
    }
}