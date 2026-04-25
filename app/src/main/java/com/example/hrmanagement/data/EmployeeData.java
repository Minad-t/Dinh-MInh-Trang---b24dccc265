package com.example.hrmanagement.data;

import com.example.hrmanagement.model.Employee;
import com.example.hrmanagement.model.Intern;
import com.example.hrmanagement.model.Manager;
import com.example.hrmanagement.model.Staff;

import java.util.ArrayList;

public class EmployeeData {
    public static ArrayList<Employee> employeeList = new ArrayList<>();

    static {
        employeeList.add(new Staff("NV001", "Nguyễn Văn A", 10000000, 26));
        employeeList.add(new Manager("NV002", "Trần Thị B", 20000000, 5000000));
        employeeList.add(new Intern("NV003", "Lê Văn C", 0, 100));
    }
}