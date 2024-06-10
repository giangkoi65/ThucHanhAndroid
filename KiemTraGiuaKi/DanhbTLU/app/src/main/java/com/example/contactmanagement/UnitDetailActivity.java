package com.example.contactmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UnitDetailActivity extends AppCompatActivity {
    private TextView tvUnitName, tvUnitContact;
    private Button btnViewEmployees;
    private DatabaseHelper dbHelper;
    private String unitName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_detail);

        tvUnitName = findViewById(R.id.tvUnitName);
        tvUnitContact = findViewById(R.id.tvUnitContact);
        btnViewEmployees = findViewById(R.id.btnViewEmployees);
        dbHelper = new DatabaseHelper(this);

        unitName = getIntent().getStringExtra("unitName");

        loadUnitDetails();

        btnViewEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnitDetailActivity.this, EmployeesFragment.class);
                intent.putExtra("unitName", unitName);
                startActivity(intent);
            }
        });
    }

    private void loadUnitDetails() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_UNITS, null, DatabaseHelper.COLUMN_UNIT_NAME + "=?", new String[]{unitName}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String unitContact = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_UNIT_CONTACT));
            tvUnitName.setText(unitName);
            tvUnitContact.setText(unitContact);
            cursor.close();
        }
    }
}
