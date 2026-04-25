package com.example.hrmanagement.model;

public class Manager extends Employee {
    private double allowance;

    public Manager(String id, String name, double baseSalary, double allowance) {
        super(id, name, baseSalary);
        setAllowance(allowance);
    }

    public double getAllowance() {
        return allowance;
    }

    public void setAllowance(double allowance) {
        if (allowance >= 0) {
            this.allowance = allowance;
        } else {
            this.allowance = 0;
        }
    }

    @Override
    public double calculateSalary() {
        return getBaseSalary() + allowance;
    }

    @Override
    public String getType() {
        return "Manager";
    }
}