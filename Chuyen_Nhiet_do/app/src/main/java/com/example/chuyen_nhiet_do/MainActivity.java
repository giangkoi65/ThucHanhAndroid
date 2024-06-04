package com.example.chuyen_nhiet_do;

import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edtdoC, edtdoF;
    Button btnfc, btncf;

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
        edtdoC = findViewById(R.id.edtdoC);
        edtdoF = findViewById(R.id.edtdoF);
        btncf = findViewById(R.id.btncf);
        btnfc = findViewById(R.id.btnfc);
        btncf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doC = edtdoC.getText().toString().trim();
                if (!doC.isEmpty()) {
                    try {
                        double C = Double.parseDouble(doC);
                        DecimalFormat dcf = new DecimalFormat("#.00");
                        double result = C * 1.8 + 32;
                        edtdoF.setText(dcf.format(result));
                    } catch (NumberFormatException e) {
                        // Xử lý khi chuỗi không thể chuyển đổi thành số
                        edtdoF.setText("");
                        e.printStackTrace();
                    }
                } else {
                    // Xử lý khi EditText edtdoC trống
                    edtdoF.setText("");
                }
            }
        });

        btnfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doF = edtdoF.getText().toString().trim();
                if (!doF.isEmpty()) {
                    try {
                        double F = Double.parseDouble(doF);
                        DecimalFormat dcf = new DecimalFormat("#.00");
                        double result = (F - 32) / 1.8;
                        edtdoC.setText(dcf.format(result));
                    } catch (NumberFormatException e) {
                        // Xử lý khi chuỗi không thể chuyển đổi thành số
                        edtdoC.setText("");
                        e.printStackTrace();
                    }
                } else {
                    // Xử lý khi EditText edtdoF trống
                    edtdoC.setText("");
                }
            }
        });
    }
}
