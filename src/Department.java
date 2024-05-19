/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author admin
 */
public class Department {
    private int id;
    private String department_name;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public Department(int id, String department_name) {
        this.id = id;
        this.department_name = department_name;
    }

    public Department(String department_name) {
        this.department_name = department_name;
    }
    
    @Override
    public String toString() {
        return department_name;
    }
    
}
