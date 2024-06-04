package com.example.calendarnotes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<String> arraywork;
    ArrayAdapter<String> arrAdapater;
    EditText edtwork, edthour, edtmi;
    TextView txtdate;
    Button btnwork;
    SharedPreferences sharedPreferences;

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

        // Khởi tạo các view và biến
        edthour = findViewById(R.id.edthour);
        edtmi = findViewById(R.id.edtmi);
        edtwork = findViewById(R.id.edtwork);
        btnwork = findViewById(R.id.btnadd);
        lv = findViewById(R.id.listView1);
        txtdate = findViewById(R.id.txtdate);

        // Khai báo SharedPreferences
        sharedPreferences = getSharedPreferences("workList", Context.MODE_PRIVATE);

        // Khai báo mảng ArrayList kiểu String rỗng
        arraywork = new ArrayList<>();

        // Khai báo ArrayAdapter, đưa mảng dữ liệu vào ArrayAdapter
        arrAdapater = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arraywork);

        // Đưa Adapter có dữ liệu lên ListView
        lv.setAdapter(arrAdapater);

        // Lấy ngày giờ hệ thống
        Date currentDate = Calendar.getInstance().getTime();

        // Format theo định dạng dd/MM/yyyy
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");

        // Hiển thị lên TextView
        txtdate.setText("Hôm Nay: " + simpleDateFormat.format(currentDate));

        // Tải lại dữ liệu từ SharedPreferences
        loadData();

        // Viết phương thức khi Click vào Button btnwork
        btnwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Nếu 1 trong 3 EditText không có nội dung thì hiện lên thông báo bằng hộp thoại
                if (edtwork.getText().toString().equals("") || edthour.getText().toString().equals("") || edtmi.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Info missing");
                    builder.setMessage("Please enter all information of the work");
                    builder.setPositiveButton("Continue", (dialog, which) -> {
                        // TODO Auto-generated method stub
                    });
                    builder.show();
                } else {
                    // Lấy nội dung công việc và thời gian ra từ EditText và đưa vào mảng arraywork
                    String str = edtwork.getText().toString() + " - " + edthour.getText().toString() + ":" + edtmi.getText().toString();

                    // Để thêm dữ liệu cho ListView, chúng ta cần 2 bước:
                    // 1. Cập nhật dữ liệu cho mảng
                    arraywork.add(str);

                    // 2. Cập nhật lại Adapter
                    arrAdapater.notifyDataSetChanged();

                    // Lưu dữ liệu vào SharedPreferences
                    saveData();

                    // Xóa nội dung trong các EditText
                    edthour.setText("");
                    edtmi.setText("");
                    edtwork.setText("");
                }
            }
        });

        // Viết phương thức khi Click vào một dòng trên ListView để xóa dòng đó
        lv.setOnItemClickListener((parent, view, position, id) -> {
            arraywork.remove(position);
            arrAdapater.notifyDataSetChanged();
            saveData();
        });
    }

    // Lưu dữ liệu vào SharedPreferences
    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(arraywork);
        editor.putStringSet("workSet", set);
        editor.apply();
    }

    // Tải dữ liệu từ SharedPreferences
    private void loadData() {
        Set<String> set = sharedPreferences.getStringSet("workSet", new HashSet<>());
        arraywork.clear();
        arraywork.addAll(set);
        arrAdapater.notifyDataSetChanged();
    }
}
