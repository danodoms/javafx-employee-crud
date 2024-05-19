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
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private TableColumn<Assignment, String> col_position;
    @FXML
    private TableColumn<Assignment, String> col_shift;
    @FXML
    private TextField id_field;
    @FXML
    private TextField fname_field;
    @FXML
    private TextField mname_field;
    @FXML
    private TextField lname_field;
    private TextField position_field;
    private TextField shift_field;
    @FXML
    private Button insertBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    private TextField status_field;
    @FXML
    private ChoiceBox<Shift> shiftFilter_choiceBox;
    @FXML
    private TableColumn<Assignment, String> col_department;
    @FXML
    private TableColumn<Employee, String> col_suffix;
    @FXML
    private TableColumn<Employee, String> col_privilege;
    @FXML
    private ChoiceBox<String> privilegeFilter_choiceBox;
    @FXML
    private ChoiceBox<Department> departmentFilter_choiceBox;
    @FXML
    private TextField suffix_field;
    @FXML
    private TextField privilege_field;
    @FXML
    private TableColumn<Assignment, Integer> col_assignment_id;
    @FXML
    private TableView<Employee> user_table;
    @FXML
    private TableView<Assignment> assignment_table;
    
    dbMethods dbMethods = new dbMethods();
    @FXML
    private ImageView themeToggle;
    
    private String lightMode = getClass().getResource("employeemgmt.css").toExternalForm();
private String darkMode = getClass().getResource("darkMode.css").toExternalForm();

Image lightModeIcon = new Image(getClass().getResource("icons8-sun-50-dark.png").toExternalForm());
Image darkModeIcon = new Image(getClass().getResource("icons8-moon-48-light.png").toExternalForm());


    private boolean isLightMode = true;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        themeToggle.setImage(lightModeIcon);
        // TODO
        showUserTable();
        
//        departmentFilter_choiceBox.setValue("All");
//        departmentFilter_choiceBox.getItems().addAll("All","Administrative", "Instruction");
//        
//        shiftFilter_choiceBox.setValue("All");


        privilegeFilter_choiceBox.setOnAction(this::showFilteredEmployeeTable);
        shiftFilter_choiceBox.setOnAction(this::showFilteredEmployeeTable);
        departmentFilter_choiceBox.setOnAction(this::showFilteredEmployeeTable);
        
        
//        privilegeFilter_choiceBox.setOnAction(event -> showFilteredEmployeeTable());
//        shiftFilter_choiceBox.setOnAction(event -> showFilteredEmployeeTable());
//        departmentFilter_choiceBox.setOnAction(event -> showFilteredEmployeeTable());
//        
        
        
//        privilegeFilter_choiceBox.setOnAction(event -> {
//            System.out.println("Privilege Filter Action Handler Called");
//            showFilteredEmployeeTable();
//        });
//
//        shiftFilter_choiceBox.setOnAction(event -> {
//            System.out.println("Shift Filter Action Handler Called");
//            showFilteredEmployeeTable();
//        });
//
//        departmentFilter_choiceBox.setOnAction(event -> {
//            System.out.println("Department Filter Action Handler Called");
//            showFilteredEmployeeTable();
//        });



      
        privilegeFilter_choiceBox.setValue("All");
        privilegeFilter_choiceBox.getItems().addAll("All","employee","admin","records officer");
        
        shiftFilter_choiceBox.setValue(new Shift("All"));
        shiftFilter_choiceBox.getItems().addAll(new Shift("All"));
        shiftFilter_choiceBox.getItems().addAll(dbMethods.getShift());
        
        departmentFilter_choiceBox.setValue(new Department("All"));
        departmentFilter_choiceBox.getItems().addAll(new Department("All"));
        departmentFilter_choiceBox.getItems().addAll(dbMethods.getDepartment());
        
        //USER TABLE
        col_id.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        col_fname.setCellValueFactory(new PropertyValueFactory<Employee, String>("fname"));
        col_mname.setCellValueFactory(new PropertyValueFactory<Employee, String>("mname"));
        col_lname.setCellValueFactory(new PropertyValueFactory<Employee, String>("lname"));
        col_suffix.setCellValueFactory(new PropertyValueFactory<Employee, String>("suffix"));
        col_privilege.setCellValueFactory(new PropertyValueFactory<Employee, String>("privilege"));

        //ASSIGNMENT TABLE
        col_assignment_id.setCellValueFactory(new PropertyValueFactory<Assignment, Integer>("id"));
        col_position.setCellValueFactory(new PropertyValueFactory<Assignment, String>("position"));
        col_department.setCellValueFactory(new PropertyValueFactory<Assignment, String>("department"));
        col_shift.setCellValueFactory(new PropertyValueFactory<Assignment, String>("shift"));
        
        
        
        
    }    
    
    
    

    @FXML
     public void updateFields(){
        Employee selectedItem = user_table.getSelectionModel().getSelectedItem();
        id_field.setText(selectedItem.getId()+"");
        fname_field.setText(selectedItem.getFname());
        mname_field.setText(selectedItem.getMname());
        lname_field.setText(selectedItem.getLname());
        suffix_field.setText(selectedItem.getSuffix());
        privilege_field.setText(selectedItem.getPrivilege());
        
        showAssignmentTable(selectedItem.getId());

     }

     
     
    public void showUserTable(){
        ObservableList<Employee> employees = dbMethods.getEmployee();
        user_table.setItems(employees);
    }
    
        public void showAssignmentTable(int user_id){
        ObservableList<Assignment> assignments = dbMethods.getAssignmentByUserId(user_id);
        assignment_table.setItems(assignments);
    }
    
private void clearFields() {
    id_field.clear();
    fname_field.clear();
    mname_field.clear();
    lname_field.clear();
    privilege_field.clear();
    suffix_field.clear();
}

    
    
  @FXML
private void insertRecord(){
   String query = "INSERT INTO user (user_fname, password, user_mname, user_lname, suffix, user_type) VALUES ('" 
            + fname_field.getText() + "', '"
            + "password" + "', '"
            + mname_field.getText() + "', '"
            + lname_field.getText() + "', '"
            + suffix_field.getText() + "', '"
            + privilege_field.getText() + "')";

   dbMethods.executeQuery(query);
   showUserTable();
   clearFields();
}

@FXML
private void updateRow(ActionEvent event) {
    Employee selectedItem = user_table.getSelectionModel().getSelectedItem();
    
    if (selectedItem != null) {
        String query = "UPDATE user SET " +
            "user_fname = '" + fname_field.getText() + "', " +
            "user_mname = '" + mname_field.getText() + "', " +
            "user_lname = '" + lname_field.getText() + "', " +
            "suffix = '" + suffix_field.getText() + "', " +
            "user_type = '" + privilege_field.getText() + "'" +
            "WHERE user_id = " + selectedItem.getId();
    
        dbMethods.executeQuery(query);
        showUserTable();
        clearFields();
    } else {
        // Handle case when no row is selected or handle error.
        // You can show a message or perform other actions here.
    }
}


@FXML
private void deactivate(ActionEvent event) {
    Employee selectedItem = user_table.getSelectionModel().getSelectedItem();
    
    if (selectedItem != null) {
        int id = selectedItem.getId();
        String query = "UPDATE user SET user_status = 0 WHERE user_id = " + id;
    
        dbMethods.executeQuery(query);
        showUserTable();
        clearFields();
        assignment_table.getItems().clear();
    } else {
        // Handle case when no row is selected or handle error.
        // You can show a message or perform other actions here.
    }
}
    
public void showFilteredEmployeeTable(ActionEvent event){
    clearFields();
    ObservableList<Employee> filteredEmployees = dbMethods.getFilteredEmployee(generateEmployeeFilterQuery());
        user_table.setItems(filteredEmployees);
        assignment_table.getItems().clear();
        //clearFields();
    
    
}


public String generateEmployeeFilterQuery() {
    //String query = "SELECT u.user_id, u.user_fname, u.user_mname, u.user_lname, u.suffix, p.position_name, d.department_name, s.shift_name, u.user_type, u.user_status from user u, assignment a, shift s, position p, department d where u.user_id = a.user_id AND s.shift_id = a.shift_id AND a.position_id = p.position_id AND p.department_id = d.department_id AND u.user_status = 1";
    String query = "SELECT u.user_id, u.user_fname, u.user_mname, u.user_lname, u.suffix, p.position_name, d.department_name, s.shift_name, u.user_type, u.user_status from user u LEFT JOIN assignment a ON u.user_id = a.user_id left JOIN shift s ON a.shift_id = s.shift_id left JOIN position p ON p.position_id = a.position_id left JOIN department d ON d.department_id = p.department_id WHERE u.user_status = 1";
    String privilege = privilegeFilter_choiceBox.getValue() != null ? privilegeFilter_choiceBox.getValue() : "";
    String departmentId = departmentFilter_choiceBox.getValue() != null ? departmentFilter_choiceBox.getValue().getId() + "" : "";
    String shiftId = shiftFilter_choiceBox.getValue() != null ? shiftFilter_choiceBox.getValue().getId() + "" : "";
    
    if (!"All".equals(privilege)) {
        query += " AND u.user_type = " + "'"+privilege+"'";
    }
    
    if (!"0".equals(departmentId)) {
        query += " AND d.department_id = " + departmentId;
    }
    
    if (!"0".equals(shiftId)) {
        query += " AND s.shift_id = " + shiftId;
    }
    
    query += " GROUP BY u.user_id";
    
    System.out.println(query);
    
    return query;
}

//    @FXML
//    private void switchTheme(MouseEvent event) {
//        Scene scene = stage.getScene();
//         scene.getStylesheets().clear();
//        System.out.println("passs");
//    }
//
//@FXML
//private void switchTheme(MouseEvent event) {
//
//   Scene scene = themeToggle.getScene(); // Use your target control to get the Scene
//        
//        // Remove the first theme
//        scene.getStylesheets().remove(lightMode); 
//
//        // Add the second theme
//        scene.getStylesheets().add(darkMode); 
//        
//        
//        System.out.println("passs");
//}

@FXML
private void switchTheme(MouseEvent event) {
    Scene scene = themeToggle.getScene();

    if (scene != null) {
        ObservableList<String> stylesheets = scene.getStylesheets();

        if (isLightMode) {
            // If light mode is currently applied, switch to night mode
            scene.getStylesheets().remove(lightMode);
            scene.getStylesheets().add(darkMode); 
            themeToggle.setImage(darkModeIcon);
            isLightMode = false;
        } else {
            // If night mode is currently applied, switch to light mode
            scene.getStylesheets().remove(darkMode);
            scene.getStylesheets().add(lightMode); 
            themeToggle.setImage(lightModeIcon);
            isLightMode = true;
        }
    }
}



    

    
}
