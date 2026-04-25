package com.example.hrmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrmanagement.R;
import com.example.hrmanagement.adapter.EmployeeAdapter;
import com.example.hrmanagement.data.EmployeeData;
import com.example.hrmanagement.model.Employee;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvEmployees;
    private EmployeeAdapter adapter;
    private EditText etSearch;
    private Spinner spinnerFilter;
    private Button btnSort;
    private FloatingActionButton fabAdd;

    private ArrayList<Employee> displayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupRecyclerView();
        setupSearch();
        setupFilter();
        setupSort();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditEmployeeActivity.class);
            startActivity(intent);
        });
    }

    private void initViews() {
        rvEmployees = findViewById(R.id.rvEmployees);
        etSearch = findViewById(R.id.etSearch);
        spinnerFilter = findViewById(R.id.spinnerFilter);
        btnSort = findViewById(R.id.btnSort);
        fabAdd = findViewById(R.id.fabAdd);
    }

    private void setupRecyclerView() {
        displayList.addAll(EmployeeData.employeeList);
        adapter = new EmployeeAdapter(displayList, employee -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("employee", employee);
            startActivity(intent);
        });
        rvEmployees.setLayoutManager(new LinearLayoutManager(this));
        rvEmployees.setAdapter(adapter);
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupFilter() {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.filter_types, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(spinnerAdapter);

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupSort() {
        btnSort.setOnClickListener(v -> {
            Collections.sort(displayList, (e1, e2) -> Double.compare(e2.calculateSalary(), e1.calculateSalary()));
            adapter.notifyDataSetChanged();
        });
    }

    private void filterData() {
        String query = etSearch.getText().toString().toLowerCase().trim();
        String typeFilter = spinnerFilter.getSelectedItem().toString();

        displayList.clear();
        for (Employee e : EmployeeData.employeeList) {
            boolean matchesSearch = e.getName().toLowerCase().contains(query);
            boolean matchesType = typeFilter.equals("Tất cả") || e.getType().equals(typeFilter);

            if (matchesSearch && matchesType) {
                displayList.add(e);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        filterData();
    }
}