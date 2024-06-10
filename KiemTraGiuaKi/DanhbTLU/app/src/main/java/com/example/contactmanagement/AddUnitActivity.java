package com.example.contactmanagement;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddUnitActivity extends AppCompatActivity {
    private EditText etUnitName, etUnitContact;
    private Button btnSave;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unit);

        etUnitName = findViewById(R.id.etUnitName);
        etUnitContact = findViewById(R.id.etUnitContact);
        btnSave = findViewById(R.id.btnSave);
        dbHelper = new DatabaseHelper(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUnit();
            }
        });
    }

    private void saveUnit() {
        String unitName = etUnitName.getText().toString().trim();
        String unitContact = etUnitContact.getText().toString().trim();

        if (unitName.isEmpty() || unitContact.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_UNIT_NAME, unitName);
        values.put(DatabaseHelper.COLUMN_UNIT_CONTACT, unitContact);

        long result = db.insert(DatabaseHelper.TABLE_UNITS, null, values);

        if (result != -1) {
            Toast.makeText(this, "Đơn vị đã được thêm", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Lỗi khi thêm đơn vị", Toast.LENGTH_SHORT).show();
        }
    }
}
