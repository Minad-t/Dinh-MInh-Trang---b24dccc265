package com.example.hrmanagement.model;

public class Intern extends Employee {
    private int workingHours;

    public Intern(String id, String name, double baseSalary, int workingHours) {
        super(id, name, baseSalary);
        setWorkingHours(workingHours);
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        if (workingHours >= 0) {
            this.workingHours = workingHours;
        } else {
            this.workingHours = 0;
        }
    }

    @Override
    public double calculateSalary() {
        return workingHours * 30000;
    }

    @Override
    public String getType() {
        return "Intern";
    }
}