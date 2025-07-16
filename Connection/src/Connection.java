

import java.sql.*;
import java.util.Scanner;

public class Connection {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        java.sql.Connection con = null;

        try {
            // Connect to MySQL DB, connect your JAR file
            con = DriverManager.getConnection("jdbc:mysql://localhost/sampledb", "root", "yourpassword");

            if (con != null) {
                System.out.println("Connected to the database!");

                while (true) {
                    System.out.println("Welcome, please choose your operation:");
                    System.out.println("1. Insert");
                    System.out.println("2. Select");
                    System.out.println("3. Update");
                    System.out.println("4. Delete");
                    System.out.println("5. Exit");
                    System.out.print("Enter your choice: ");
                    int choice = sc.nextInt();
                    sc.nextLine();

                    switch (choice) {
                        case 1:
                            // Insert operation
                            System.out.print("Enter username: ");
                            String username = sc.nextLine();

                            System.out.print("Enter password: ");
                            String password = sc.nextLine();

                            System.out.print("Enter full name: ");
                            String fullname = sc.nextLine();

                            System.out.print("Enter email: ");
                            String email = sc.nextLine();

                            String insertSql = "INSERT INTO Users (username, password, fullname, email) VALUES (?, ?, ?, ?)";
                            PreparedStatement insertpst = con.prepareStatement(insertSql);
                            insertpst.setString(1, username);
                            insertpst.setString(2, password);
                            insertpst.setString(3, fullname);
                            insertpst.setString(4, email);

                            int rowsInserted = insertpst.executeUpdate();
                            if (rowsInserted > 0)
                                System.out.println("User inserted successfully.");
                            break;

                        case 2:
                            // Select operation
                            System.out.print("Enter username to search: ");
                            String searchUser = sc.nextLine();

                            String selectSql = "SELECT * FROM Users WHERE username = ?";
                            PreparedStatement selectpst = con.prepareStatement(selectSql);
                            selectpst.setString(1, searchUser);

                            ResultSet rs = selectpst.executeQuery();
                            if (rs.next()) {
                                System.out.println(" User Details ");
                                System.out.println("Username: " + rs.getString("username"));
                                System.out.println("Full Name: " + rs.getString("fullname"));
                                System.out.println("Email: " + rs.getString("email"));
                            } else {
                                System.out.println(" User not found.");
                            }
                            break;

                        case 3:
                            // Update operation
                            System.out.print("Enter username to update: ");
                            String updateUser = sc.nextLine();

                            System.out.print("Enter new email: ");
                            String newEmail = sc.nextLine();

                            String updateSql = "UPDATE Users SET email = ? WHERE username = ?";
                            PreparedStatement updatepst = con.prepareStatement(updateSql);
                            updatepst.setString(1, newEmail);
                            updatepst.setString(2, updateUser);

                            int rowsUpdated = updatepst.executeUpdate();
                            if (rowsUpdated > 0)
                                System.out.println(" User updated successfully.");
                            else
                                System.out.println(" User not found.");
                            break;

                        case 4:
                            // Delete operation
                            System.out.print("Enter username to delete: ");
                            String deleteUser = sc.nextLine();

                            String deleteSql = "DELETE FROM Users WHERE username = ?";
                            PreparedStatement deletepst = con.prepareStatement(deleteSql);
                            deletepst.setString(1, deleteUser);

                            int rowsDeleted = deletepst.executeUpdate();
                            if (rowsDeleted > 0)
                                System.out.println(" User deleted successfully.");
                            else
                                System.out.println(" User not found.");
                            break;

                        case 5:
                            System.out.println("Thank you!");
                            sc.close();
                            con.close();
                            System.exit(0);

                        default:
                            System.out.println(" Invalid choice. Please try again.");
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println(" Database connection failed.");
            ex.printStackTrace();
        }
    }
}
