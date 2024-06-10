package com.example.contactmanagement;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEmployeeActivity extends AppCompatActivity {
    private EditText etEmployeeName, etEmployeeContact, etEmployeeEmail;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private Button btnSave;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        etEmployeeName = findViewById(R.id.etEmployeeName);
        etEmployeeContact = findViewById(R.id.etEmployeeContact);
        etEmployeeEmail = findViewById(R.id.etEmployeeEmail);
        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        btnSave = findViewById(R.id.btnSave);
        dbHelper = new DatabaseHelper(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEmployee();
            }
        });
    }

    private void saveEmployee() {
        String employeeName = etEmployeeName.getText().toString().trim();
        String employeeContact = etEmployeeContact.getText().toString().trim();
        String employeeEmail = etEmployeeEmail.getText().toString().trim();
        String employeeGender = ((RadioButton) findViewById(rgGender.getCheckedRadioButtonId())).getText().toString();

        if (employeeName.isEmpty() || employeeContact.isEmpty() || employeeEmail.isEmpty() || employeeGender.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EMPLOYEE_NAME, employeeName);
        values.put(DatabaseHelper.COLUMN_EMPLOYEE_CONTACT, employeeContact);
        values.put(DatabaseHelper.COLUMN_EMPLOYEE_EMAIL, employeeEmail);
        values.put(DatabaseHelper.COLUMN_EMPLOYEE_GENDER, employeeGender);

        long result = db.insert(DatabaseHelper.TABLE_EMPLOYEES, null, values);

        if (result != -1) {
            Toast.makeText(this, "Nhân viên đã được thêm", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Lỗi khi thêm nhân viên", Toast.LENGTH_SHORT).show();
        }
    }
}
