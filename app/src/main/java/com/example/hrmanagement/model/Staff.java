package com.example.hrmanagement.model;

public class Staff extends Employee {
    private int workingDays;

    public Staff(String id, String name, double baseSalary, int workingDays) {
        super(id, name, baseSalary);
        setWorkingDays(workingDays);
    }

    public int getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(int workingDays) {
        if (workingDays >= 0 && workingDays <= 26) {
            this.workingDays = workingDays;
        } else {
            this.workingDays = 0;
        }
    }

    @Override
    public double calculateSalary() {
        return getBaseSalary() * workingDays / 26.0;
    }

    @Override
    public String getType() {
        return "Staff";
    }
}