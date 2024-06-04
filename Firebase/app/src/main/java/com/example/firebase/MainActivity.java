package com.example.firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editTextName, editTextEmail;
    private Button buttonAdd, buttonUpdate, buttonDelete;
    private ListView listViewStudents;
    private List<Student> studentList;
    private FirebaseDatabaseHelper databaseHelper;
    private StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize Firebase
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        listViewStudents = findViewById(R.id.listViewStudents);
        studentList = new ArrayList<>();
        databaseHelper = new FirebaseDatabaseHelper();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStudent();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent();
            }
        });

        // Initialize adapter
        studentAdapter = new StudentAdapter(this, studentList);
        listViewStudents.setAdapter(studentAdapter);

        loadStudents();
    }

    private void addStudent() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String id = databaseHelper.getReference().push().getKey();
        Student student = new Student(id, name, email);
        databaseHelper.addStudent(student);
    }

    private void updateStudent() {
        // Get selected student ID
        String id = "0";
                String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        Student student = new Student(id, name, email);
        databaseHelper.updateStudent(id, student);
    }

    private void deleteStudent() {
        // Get selected student ID
        String id = "0";
    }

    private void loadStudents() {
        databaseHelper.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studentList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Student student = postSnapshot.getValue(Student.class);
                    studentList.add(student);
                }
                // Notify adapter of data change
                studentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
