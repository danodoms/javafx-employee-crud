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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tableView.setItems(data);
        System.out.println(data.get(0).getFname());
        col_id.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        col_fname.setCellValueFactory(new PropertyValueFactory<Employee, String>("fname"));
        col_mname.setCellValueFactory(new PropertyValueFactory<Employee, String>("mname"));
        col_lname.setCellValueFactory(new PropertyValueFactory<Employee, String>("lname"));
        col_position.setCellValueFactory(new PropertyValueFactory<Employee, String>("position"));
        col_shift.setCellValueFactory(new PropertyValueFactory<Employee, String>("shift"));
    }    
    
     ObservableList<Employee> data = FXCollections.observableArrayList(
                new Employee(1, "Dominador Jr.", "Garcia", "Dano", "Programmer", "Regular"),
                new Employee(2, "Nicole Ann", "Magno", "Cabantac", "UX Designer", "Regular"),
                new Employee(3, "Jessa Mae", "", "Roxas", "Systems Analyst", "Regular")
        );
     
     
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

    @FXML
    private void updateRow(ActionEvent event){
        int index = tableView.getSelectionModel().getSelectedIndex();
        Employee selectedItem = data.get(index);
        
        data.get(index).setId(Integer.parseInt(id_field.getText()));
        data.get(index).setFname(fname_field.getText());
        data.get(index).setMname(mname_field.getText());
        data.get(index).setLname(lname_field.getText());
        data.get(index).setPosition(position_field.getText());
        data.get(index).setShift(shift_field.getText());
        
        tableView.refresh();
        System.out.println(index);
    }

    @FXML
    private void filterTable(KeyEvent event) {
         ObservableList<Employee> filteredData = FXCollections.observableArrayList();
         String keyword = searchField.getText();

    for (Employee employee : data) {
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
}
