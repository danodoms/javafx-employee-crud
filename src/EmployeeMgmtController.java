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
import javafx.scene.control.ChoiceBox;
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
    private TextField status_field;
    @FXML
    private ChoiceBox<String> shiftFilter_choiceBox;
    @FXML
    private TableColumn<Employee, String> col_department;
    @FXML
    private TextField department_field;
    @FXML
    private TableColumn<Employee, String> col_suffix;
    @FXML
    private TableColumn<Employee, String> col_privilege;
    @FXML
    private ChoiceBox<String> privilegeFilter_choiceBox;
    @FXML
    private ChoiceBox<String> departmentFilter_choiceBox;
    @FXML
    private TextField suffix_field;
    @FXML
    private TextField privilege_field;

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
        col_suffix.setCellValueFactory(new PropertyValueFactory<Employee, String>("suffix"));
        col_position.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
        col_department.setCellValueFactory(new PropertyValueFactory<Employee, String>("department"));
        col_shift.setCellValueFactory(new PropertyValueFactory<Employee, String>("shift"));
        col_privilege.setCellValueFactory(new PropertyValueFactory<Employee, String>("privilege"));
    }    
    
    
    
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
    @FXML
     public void updateFields(){
        Employee selectedItem = tableView.getSelectionModel().getSelectedItem();
        id_field.setText(selectedItem.getId()+"");
        fname_field.setText(selectedItem.getFname());
        mname_field.setText(selectedItem.getMname());
        lname_field.setText(selectedItem.getLname());
        suffix_field.setText(selectedItem.getSuffix());
        position_field.setText(selectedItem.getPosition());
        department_field.setText(selectedItem.getDepartment());
        shift_field.setText(selectedItem.getShift());
        privilege_field.setText(selectedItem.getPrivilege());

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
            ResultSet rs = statement.executeQuery("SELECT u.user_id, u.user_fname, u.user_mname, u.user_lname, u.suffix, p.position_name, d.department_name, s.shift_name, u.user_type, u.user_status from user u, assignment a, shift s, position p, department d where u.user_id = a.user_id AND s.shift_id = a.shift_id AND a.position_id = p.position_id AND p.department_id = d.department_id AND u.user_status = 1 GROUP BY u.user_id;");
            
            while (rs.next()) {
                  employees.add(new Employee(
                                    rs.getInt("user_id"),
                           rs.getString("user_fname"),
                          rs.getString("user_mname"),
                          rs.getString("user_lname"),
                          rs.getString("suffix"),
                          rs.getString("position_name"),
                          rs.getString("department_name"),
                          rs.getString("shift_name"),
                           rs.getString("user_type"),
                          rs.getInt("user_status")
                  ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
        
    }
    
}
