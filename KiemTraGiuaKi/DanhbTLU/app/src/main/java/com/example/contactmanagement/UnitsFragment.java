package com.example.contactmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class UnitsFragment extends Fragment {
    private ListView listViewUnits;
    private List<String> unitsList;
    private ArrayAdapter<String> adapter;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_units, container, false);

        listViewUnits = view.findViewById(R.id.listViewUnits);
        FloatingActionButton fabAddUnit = view.findViewById(R.id.fabAddUnit);
        dbHelper = new DatabaseHelper(getContext());

        loadUnits();

        listViewUnits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedUnit = unitsList.get(position);
                Intent intent = new Intent(getActivity(), UnitDetailActivity.class);
                intent.putExtra("unitName", selectedUnit);
                startActivity(intent);
            }
        });

        fabAddUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddUnitActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void loadUnits() {
        unitsList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_UNITS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String unitName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_UNIT_NAME));
                unitsList.add(unitName);
            } while (cursor.moveToNext());
            cursor.close();
        }

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, unitsList);
        listViewUnits.setAdapter(adapter);
    }
}
