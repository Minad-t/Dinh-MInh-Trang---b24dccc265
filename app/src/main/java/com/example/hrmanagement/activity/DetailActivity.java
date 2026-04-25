package com.example.hrmanagement.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hrmanagement.R;
import com.example.hrmanagement.data.EmployeeData;
import com.example.hrmanagement.model.Employee;

public class DetailActivity extends AppCompatActivity {

    private TextView tvDetailInfo;
    private Button btnEdit, btnDelete;
    private Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvDetailInfo = findViewById(R.id.tvDetailInfo);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        employee = (Employee) getIntent().getSerializableExtra("employee");

        if (employee != null) {
            displayEmployeeInfo();
        }

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, AddEditEmployeeActivity.class);
            intent.putExtra("employee", employee);
            startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> showDeleteConfirmation());
    }

    private void displayEmployeeInfo() {
        tvDetailInfo.setText(employee.displayInfo());
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa nhân viên này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    deleteEmployee();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void deleteEmployee() {
        for (int i = 0; i < EmployeeData.employeeList.size(); i++) {
            if (EmployeeData.employeeList.get(i).getId().equals(employee.getId())) {
                EmployeeData.employeeList.remove(i);
                break;
            }
        }
        Toast.makeText(this, "Đã xóa nhân viên", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh info in case it was edited
        for (Employee e : EmployeeData.employeeList) {
            if (e.getId().equals(employee.getId())) {
                employee = e;
                displayEmployeeInfo();
                break;
            }
        }
    }
}