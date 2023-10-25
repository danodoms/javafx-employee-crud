/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import java.sql.*;
/**
 * FXML Controller class
 *
 * @author admin
 */
public class EmployeeMgmtController implements Initializable {

    @FXML
    private TableColumn<Employee, Integer> col_id;
    @FXML
    private TableColumn<Employee, String> col_fname;
    @FXML
    private TableColumn<Employee, String> col_mname;
    @FXML
    private TableColumn<Employee, String> col_lname;
    @FXML
    private TableColumn<Employee, String> col_position;
    @FXML
    private TableColumn<Employee, String> col_shift;
    @FXML
    private TableView<Employee> tableView;
    @FXML
    private TextField id_field;
    @FXML
    private TextField fname_field;
    @FXML
    private TextField mname_field;
    @FXML
    private TextField lname_field;
    @FXML
    private TextField position_field;
    @FXML
    private TextField shift_field;
    @FXML
    private Button insertBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private TextField searchField;
    @FXML
    private Button exportBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tableView.setItems(getEmployee());
        //System.out.println(data.get(0).getFname());
        col_id.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        col_fname.setCellValueFactory(new PropertyValueFactory<Employee, String>("fname"));
        col_mname.setCellValueFactory(new PropertyValueFactory<Employee, String>("mname"));
        col_lname.setCellValueFactory(new PropertyValueFactory<Employee, String>("lname"));
        col_position.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
        col_shift.setCellValueFactory(new PropertyValueFactory<Employee, String>("shift"));
    }    
    
//     ObservableList<Employee> data = FXCollections.observableArrayList(
//                new Employee(1, "Dominador Jr.", "Garcia", "Dano", "Programmer", "Regular"),
//                new Employee(2, "Nicole Ann", "Magno", "Cabantac", "UX Designer", "Regular"),
//                new Employee(3, "Jessa Mae", "", "Roxas", "Systems Analyst", "Regular")
//        );
//     
     
    @FXML
     public void editRow(){
        Employee selectedItem = tableView.getSelectionModel().getSelectedItem();
        id_field.setText(selectedItem.getId()+"");
        fname_field.setText(selectedItem.getFname());
        mname_field.setText(selectedItem.getMname());
        lname_field.setText(selectedItem.getLname());
        position_field.setText(selectedItem.getPosition());
        shift_field.setText(selectedItem.getShift());

     }

//    @FXML
//    private void updateRow(ActionEvent event){
//        int index = tableView.getSelectionModel().getSelectedIndex();
//        Employee selectedItem = data.get(index);
//        
//        data.get(index).setId(Integer.parseInt(id_field.getText()));
//        data.get(index).setFname(fname_field.getText());
//        data.get(index).setMname(mname_field.getText());
//        data.get(index).setLname(lname_field.getText());
//        data.get(index).setPosition(position_field.getText());
//        data.get(index).setShift(shift_field.getText());
//        
//        tableView.refresh();
//        System.out.println(index);
//    }
    
        @FXML
    private void updateRow(ActionEvent event){
        int index = tableView.getSelectionModel().getSelectedIndex();
        
        Employee selectedItem = getEmployee().get(index);
        
        selectedItem.setId(Integer.parseInt(id_field.getText()));
        selectedItem.setFname(fname_field.getText());
        selectedItem.setMname(mname_field.getText());
        selectedItem.setLname(lname_field.getText());
        selectedItem.setPosition(position_field.getText());
        selectedItem.setShift(shift_field.getText());
        
        tableView.refresh();
        System.out.println(index);
    }

    @FXML
    private void filterTable(KeyEvent event) {
         ObservableList<Employee> filteredData = FXCollections.observableArrayList();
         String keyword = searchField.getText();

    for (Employee employee : getEmployee()) {
        // Check if any of the columns contain the search keyword (case-insensitive).
        if (employee.getFname().toLowerCase().contains(keyword.toLowerCase())
            || employee.getMname().toLowerCase().contains(keyword.toLowerCase())
            || employee.getLname().toLowerCase().contains(keyword.toLowerCase())
            || employee.getPosition().toLowerCase().contains(keyword.toLowerCase())
            || employee.getShift().toLowerCase().contains(keyword.toLowerCase())) {
            filteredData.add(employee);
        }
    }

    // Update the TableView with the filtered data.
    tableView.setItems(filteredData);
    }
//    
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/v11_attendance_system";
//    private static final String DB_USER = "root";
//    private static final String DB_PASSWORD = "";
//    
//    public Connection getConnection(){
//        Connection connection;
//        try{
//            connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
//            return connection;
//        }catch(Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }
    
    
    @FXML
    private void exportToDb() {
        String dbName = "employeeDump";
        createDatabase(dbName);
        String jdbcUrl = "jdbc:mysql://localhost:3306/"+ dbName;
        String user = "root";
        String password = "";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
            
            ObservableList<Employee> items = tableView.getItems();

            String insertQuery = "INSERT INTO employee (id, fname, mname, lname, position, shift) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            for (Employee employee : items) {
                preparedStatement.setInt(1, employee.getId());
                preparedStatement.setString(2, employee.getFname());
                preparedStatement.setString(3, employee.getMname());
                preparedStatement.setString(4, employee.getLname());
                preparedStatement.setString(5, employee.getPosition());
                preparedStatement.setString(6, employee.getShift());
                preparedStatement.executeUpdate();
            }

            preparedStatement.close();
            connection.close();

            System.out.println("Data exported to MySQL database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public static void createDatabase(String databaseName) {
    String jdbcUrl = "jdbc:mysql://localhost:3306/";
    String user = "root";
    String password = "";

    Connection connection = null;
    Statement statement = null;

    try {
        // Connect to MySQL server (without specifying a database)
        connection = DriverManager.getConnection(jdbcUrl, user, password);

        // Create the database
        statement = connection.createStatement();
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + databaseName;
        statement.executeUpdate(createDatabaseSQL);

        System.out.println("Database '" + databaseName + "' created successfully.");

        // Use the newly created database
        jdbcUrl = "jdbc:mysql://localhost:3306/" + databaseName;
        connection = DriverManager.getConnection(jdbcUrl, user, password);

        // Create the "employee" table
        statement = connection.createStatement();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS employee ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "fname VARCHAR(255),"
                + "mname VARCHAR(255),"
                + "lname VARCHAR(255),"
                + "position VARCHAR(255),"
                + "shift VARCHAR(255)"
                + "status INT"
                + ")";
        statement.executeUpdate(createTableSQL);

        System.out.println("Table 'employee' created successfully.");
    } catch (SQLException e) {
        System.err.println("Error creating the database or table: " + e.getMessage());
    } finally {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}

    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeeAct5to8";
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
    
    public ObservableList<Employee> getEmployee(){
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        try (Connection connection = getConnection();
            Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT * FROM employee");
            
            while (rs.next()) {
                  employees.add(new Employee(
                                    rs.getInt("id"),
                           rs.getString("fname"),
                          rs.getString("mname"),
                          rs.getString("lname"),
                          rs.getString("position"),
                          rs.getString("shift"),
                           rs.getInt("status")
                          
                  ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
        
    }
    

    
//     public static void createDatabase(String databaseName) {
//        String jdbcUrl = "jdbc:mysql://localhost:3306/";
//        String user = "root";
//        String password = "";
//
//        Connection connection = null;
//        Statement statement = null;
//
//        try {
//            // Connect to MySQL server (without specifying a database)
//            connection = DriverManager.getConnection(jdbcUrl, user, password);
//
//            // Create the database
//            statement = connection.createStatement();
//            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + databaseName;
//            statement.executeUpdate(createDatabaseSQL);
//
//            System.out.println("Database '" + databaseName + "' created successfully.");
//        } catch (SQLException e) {
//            System.err.println("Error creating the database: " + e.getMessage());
//        } finally {
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                System.err.println("Error closing resources: " + e.getMessage());
//            }
//        }
//    }
    
}
