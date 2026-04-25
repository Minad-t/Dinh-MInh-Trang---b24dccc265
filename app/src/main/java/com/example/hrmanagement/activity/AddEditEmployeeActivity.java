package com.example.hrmanagement.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hrmanagement.R;
import com.example.hrmanagement.data.EmployeeData;
import com.example.hrmanagement.model.Employee;
import com.example.hrmanagement.model.Intern;
import com.example.hrmanagement.model.Manager;
import com.example.hrmanagement.model.Staff;

public class AddEditEmployeeActivity extends AppCompatActivity {

    private EditText etId, etName, etBaseSalary, etWorkingDays, etAllowance, etWorkingHours;
    private Spinner spinnerType;
    private LinearLayout layoutStaff, layoutManager, layoutIntern;
    private Button btnSave;

    private Employee existingEmployee = null;
    private int editIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_employee);

        initViews();
        setupTypeSpinner();

        existingEmployee = (Employee) getIntent().getSerializableExtra("employee");
        if (existingEmployee != null) {
            fillData(existingEmployee);
            for (int i = 0; i < EmployeeData.employeeList.size(); i++) {
                if (EmployeeData.employeeList.get(i).getId().equals(existingEmployee.getId())) {
                    editIndex = i;
                    break;
                }
            }
            etId.setEnabled(false); // ID should not be changed
            btnSave.setText("Cập nhật");
        }

        btnSave.setOnClickListener(v -> saveEmployee());
    }

    private void initViews() {
        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etBaseSalary = findViewById(R.id.etBaseSalary);
        etWorkingDays = findViewById(R.id.etWorkingDays);
        etAllowance = findViewById(R.id.etAllowance);
        etWorkingHours = findViewById(R.id.etWorkingHours);
        spinnerType = findViewById(R.id.spinnerType);
        layoutStaff = findViewById(R.id.layoutStaff);
        layoutManager = findViewById(R.id.layoutManager);
        layoutIntern = findViewById(R.id.layoutIntern);
        btnSave = findViewById(R.id.btnSave);
    }

    private void setupTypeSpinner() {
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = parent.getItemAtPosition(position).toString();
                layoutStaff.setVisibility(View.GONE);
                layoutManager.setVisibility(View.GONE);
                layoutIntern.setVisibility(View.GONE);

                if (type.equals("Staff")) {
                    layoutStaff.setVisibility(View.VISIBLE);
                } else if (type.equals("Manager")) {
                    layoutManager.setVisibility(View.VISIBLE);
                } else if (type.equals("Intern")) {
                    layoutIntern.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void fillData(Employee employee) {
        etId.setText(employee.getId());
        etName.setText(employee.getName());
        etBaseSalary.setText(String.valueOf(employee.getBaseSalary()));

        if (employee instanceof Staff) {
            spinnerType.setSelection(0);
            etWorkingDays.setText(String.valueOf(((Staff) employee).getWorkingDays()));
        } else if (employee instanceof Manager) {
            spinnerType.setSelection(1);
            etAllowance.setText(String.valueOf(((Manager) employee).getAllowance()));
        } else if (employee instanceof Intern) {
            spinnerType.setSelection(2);
            etWorkingHours.setText(String.valueOf(((Intern) employee).getWorkingHours()));
        }
    }

    private void saveEmployee() {
        String id = etId.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String baseSalaryStr = etBaseSalary.getText().toString().trim();

        if (id.isEmpty() || name.isEmpty() || baseSalaryStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        double baseSalary = Double.parseDouble(baseSalaryStr);
        if (baseSalary <= 0) {
            Toast.makeText(this, "Lương cơ bản phải > 0", Toast.LENGTH_SHORT).show();
            return;
        }

        String type = spinnerType.getSelectedItem().toString();
        Employee newEmployee = null;

        try {
            if (type.equals("Staff")) {
                int days = Integer.parseInt(etWorkingDays.getText().toString());
                if (days < 0 || days > 26) {
                    Toast.makeText(this, "Ngày công từ 0-26", Toast.LENGTH_SHORT).show();
                    return;
                }
                newEmployee = new Staff(id, name, baseSalary, days);
            } else if (type.equals("Manager")) {
                double allowance = Double.parseDouble(etAllowance.getText().toString());
                if (allowance < 0) {
                    Toast.makeText(this, "Phụ cấp không được âm", Toast.LENGTH_SHORT).show();
                    return;
                }
                newEmployee = new Manager(id, name, baseSalary, allowance);
            } else if (type.equals("Intern")) {
                int hours = Integer.parseInt(etWorkingHours.getText().toString());
                if (hours < 0) {
                    Toast.makeText(this, "Số giờ làm không được âm", Toast.LENGTH_SHORT).show();
                    return;
                }
                newEmployee = new Intern(id, name, baseSalary, hours);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Dữ liệu nhập không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editIndex == -1) {
            // Check for duplicate ID
            for (Employee e : EmployeeData.employeeList) {
                if (e.getId().equals(id)) {
                    Toast.makeText(this, "Mã nhân viên đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            EmployeeData.employeeList.add(newEmployee);
        } else {
            EmployeeData.employeeList.set(editIndex, newEmployee);
        }

        finish();
    }
}