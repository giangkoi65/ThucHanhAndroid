package com.example.contactmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class EmployeesFragment extends Fragment {
    private ListView listViewEmployees;
    private List<String> employeesList;
    private ArrayAdapter<String> adapter;
    private DatabaseHelper dbHelper;
    private EditText etSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employees, container, false);

        listViewEmployees = view.findViewById(R.id.listViewEmployees);
        FloatingActionButton fabAddEmployee = view.findViewById(R.id.fabAddEmployee);
        etSearch = view.findViewById(R.id.etSearch);
        dbHelper = new DatabaseHelper(getContext());

        loadEmployees();

        listViewEmployees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedEmployee = employeesList.get(position);
                Intent intent = new Intent(getActivity(), EmployeeDetailActivity.class);
                intent.putExtra("employeeName", selectedEmployee);
                startActivity(intent);
            }
        });

        fabAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEmployeeActivity.class);
                startActivity(intent);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    private void loadEmployees() {
        employeesList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_EMPLOYEES, null, null, null, null, null, DatabaseHelper.COLUMN_EMPLOYEE_NAME);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String employeeName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMPLOYEE_NAME));
                employeesList.add(employeeName);
            } while (cursor.moveToNext());
            cursor.close();
        }

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, employeesList);
        listViewEmployees.setAdapter(adapter);
    }
}
