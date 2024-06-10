package com.example.contactmanagement;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeDetailActivity extends AppCompatActivity {
    private TextView tvEmployeeName, tvEmployeeContact, tvEmployeeGender, tvEmployeeEmail;
    private Button btnEdit, btnDelete, btnBack;
    private DatabaseHelper dbHelper;
    private String employeeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);

        tvEmployeeName = findViewById(R.id.tvEmployeeName);
        tvEmployeeContact = findViewById(R.id.tvEmployeeContact);
        tvEmployeeGender = findViewById(R.id.tvEmployeeGender);
        tvEmployeeEmail = findViewById(R.id.tvEmployeeEmail);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.btnBack);
        dbHelper = new DatabaseHelper(this);

        employeeName = getIntent().getStringExtra("employeeName");

        loadEmployeeDetails();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement edit functionality here
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadEmployeeDetails() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_EMPLOYEES, null, DatabaseHelper.COLUMN_EMPLOYEE_NAME + "=?", new String[]{employeeName}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String employeeContact = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMPLOYEE_CONTACT));
            String employeeGender = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMPLOYEE_GENDER));
            String employeeEmail = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMPLOYEE_EMAIL));
            tvEmployeeName.setText(employeeName);
            tvEmployeeContact.setText(employeeContact);
            tvEmployeeGender.setText(employeeGender);
            tvEmployeeEmail.setText(employeeEmail);
            cursor.close();
        }
    }

    private void deleteEmployee() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(DatabaseHelper.TABLE_EMPLOYEES, DatabaseHelper.COLUMN_EMPLOYEE_NAME + "=?", new String[]{employeeName});

        if (result > 0) {
            Toast.makeText(this, "Nhân viên đã được xóa", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Lỗi khi xóa nhân viên", Toast.LENGTH_SHORT).show();
        }
    }
}
