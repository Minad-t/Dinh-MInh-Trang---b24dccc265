package com.example.hrmanagement.model;

import java.io.Serializable;

public abstract class Employee implements Serializable {
    private String id;
    private String name;
    private double baseSalary;

    public Employee(String id, String name, double baseSalary) {
        this.id = id;
        this.name = name;
        setBaseSalary(baseSalary);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        if (baseSalary > 0) {
            this.baseSalary = baseSalary;
        } else {
            this.baseSalary = 0;
        }
    }

    public abstract double calculateSalary();
    public abstract String getType();

    public String displayInfo() {
        return "Mã NV: " + id +
                "\nHọ tên: " + name +
                "\nLương cơ bản: " + String.format("%.0f", baseSalary) +
                "\nLoại NV: " + getType() +
                "\nLương thực nhận: " + String.format("%.0f", calculateSalary());
    }
}