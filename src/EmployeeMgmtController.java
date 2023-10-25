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
    private TableColumn<Employee, Integer> col_status;
    @FXML
    private TextField status_field;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showEmployee();
        //System.out.println(data.get(0).getFname());
        col_id.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        col_fname.setCellValueFactory(new PropertyValueFactory<Employee, String>("fname"));
        col_mname.setCellValueFactory(new PropertyValueFactory<Employee, String>("mname"));
        col_lname.setCellValueFactory(new PropertyValueFactory<Employee, String>("lname"));
        col_position.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
        col_shift.setCellValueFactory(new PropertyValueFactory<Employee, String>("shift"));
        col_status.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("status"));
    }    
    
//     ObservableList<Employee> data = FXCollections.observableArrayList(
//                new Employee(1, "Dominador Jr.", "Garcia", "Dano", "Programmer", "Regular"),
//                new Employee(2, "Nicole Ann", "Magno", "Cabantac", "UX Designer", "Regular"),
//                new Employee(3, "Jessa Mae", "", "Roxas", "Systems Analyst", "Regular")
//        );
//     
     
    
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeeact5to8";
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
    @FXML
     public void editRow(){
        Employee selectedItem = tableView.getSelectionModel().getSelectedItem();
        id_field.setText(selectedItem.getId()+"");
        fname_field.setText(selectedItem.getFname());
        mname_field.setText(selectedItem.getMname());
        lname_field.setText(selectedItem.getLname());
        position_field.setText(selectedItem.getPosition());
        shift_field.setText(selectedItem.getShift());
        status_field.setText(selectedItem.getStatus()+"");

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
    private void filterTable(KeyEvent event) {
         ObservableList<Employee> filteredData = FXCollections.observableArrayList();
         String keyword = searchField.getText();

    for (Employee employee : getEmployee()) {
        // Check if any of the columns contain the search keyword (case-insensitive).
        if (employee.getFname().toLowerCase().contains(keyword.toLowerCase())
            || employee.getMname().toLowerCase().contains(keyword.toLowerCase())
            || employee.getLname().toLowerCase().contains(keyword.toLowerCase())
            || employee.getPosition().toLowerCase().contains(keyword.toLowerCase())
            || employee.getShift().toLowerCase().contains(keyword.toLowerCase())){
            filteredData.add(employee);
        }
    } 
     
    }
     
     
    public void showEmployee(){
        ObservableList<Employee> employees = getEmployee();
        tableView.setItems(employees);
    }
    
private void clearFields() {
    id_field.clear();
    fname_field.clear();
    mname_field.clear();
    lname_field.clear();
    position_field.clear();
    shift_field.clear();
    status_field.clear();
}

    
    
  @FXML
private void insertRecord(){
   String query = "INSERT INTO employee (fname, mname, lname, position, shift, status) VALUES ('" 
            + fname_field.getText() + "', '"
            + mname_field.getText() + "', '"
            + lname_field.getText() + "', '"
            + position_field.getText() + "', '"
            + shift_field.getText() + "', '"
            + status_field.getText() + "')";

   executeQuery(query);
   showEmployee();
   clearFields();
}

@FXML
private void updateRow(ActionEvent event) {
    Employee selectedItem = tableView.getSelectionModel().getSelectedItem();
    
    if (selectedItem != null) {
        String query = "UPDATE employee SET " +
            "fname = '" + fname_field.getText() + "', " +
            "mname = '" + mname_field.getText() + "', " +
            "lname = '" + lname_field.getText() + "', " +
            "position = '" + position_field.getText() + "', " +
            "shift = '" + shift_field.getText() + "', " +
            "status = '" + status_field.getText() + "' " +
            "WHERE id = " + selectedItem.getId();
    
        executeQuery(query);
        showEmployee();
        clearFields();
    } else {
        // Handle case when no row is selected or handle error.
        // You can show a message or perform other actions here.
    }
}


@FXML
private void deactivate(ActionEvent event) {
    Employee selectedItem = tableView.getSelectionModel().getSelectedItem();
    
    if (selectedItem != null) {
        int id = selectedItem.getId();
        String query = "UPDATE employee SET status = 0 WHERE id = " + id;
    
        executeQuery(query);
        showEmployee();
        clearFields();
    } else {
        // Handle case when no row is selected or handle error.
        // You can show a message or perform other actions here.
    }
}


    
    private void executeQuery(String query){
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
            ResultSet rs = statement.executeQuery("SELECT * FROM employee WHERE status = 1");
            
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
