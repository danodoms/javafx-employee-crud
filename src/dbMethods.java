
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author admin
 */
public class dbMethods {
        private static final String DB_URL = "jdbc:mysql://localhost:3306/attendance_system_javafxcrud";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    public Connection getConnection(){
        Connection connection;
        try{
            connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            return connection;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
       public void executeQuery(String query){
        Connection con = getConnection();
        Statement st;
        try{
            st = con.createStatement();
            st.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
       
           public ObservableList<Employee> getEmployee(){
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        try (Connection connection = getConnection();
            Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("select * from user where user_status = 1;");
            
            while (rs.next()) {
                  employees.add(new Employee(
                                    rs.getInt("user_id"),
                           rs.getString("user_fname"),
                          rs.getString("user_mname"),
                          rs.getString("user_lname"),
                          rs.getString("suffix"),
                           rs.getString("user_type"),
                          rs.getInt("user_status")
                  ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
        
    }
           
    public ObservableList<Assignment> getAssignmentByUserId(int user_id){
         ObservableList<Assignment> assignments = FXCollections.observableArrayList();
        try (Connection connection = getConnection();
            Statement statement = connection.createStatement()){
            
            String query = "SELECT a.assignment_id, p.position_name, d.department_name, s.shift_name from user u, assignment a, shift s, position p, department d where u.user_id = a.user_id AND s.shift_id = a.shift_id AND a.position_id = p.position_id AND p.department_id = d.department_id AND u.user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user_id);

            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                  assignments.add(new Assignment(
                                    rs.getInt("assignment_id"),
                           rs.getString("position_name"),
                          rs.getString("department_name"),
                          rs.getString("shift_name")
                  ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignments;
    }
}



