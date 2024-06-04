package com.example.qlsv;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtmalop,edttenlop,edtsiso;
    Button btninsert,btndelete,btnupdate,btnquery;
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        edtmalop = findViewById(R.id.edtmalop);
        edttenlop = findViewById(R.id.edttenlop);
        edtsiso = findViewById(R.id.edtsiso);
        btninsert = findViewById(R.id.btninsert);
        btndelete = findViewById(R.id.btndelete);
        btnupdate = findViewById(R.id.btnupdate);
        btnquery = findViewById(R.id.btnquery);
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mylist);
        lv.setAdapter(myadapter);
        mydatabase = openOrCreateDatabase("qlsinhvien.db",MODE_PRIVATE,null);

        try {
            String sql = "Create table tbllop(malop TEXT primary key, tenlop TEXT, siso INTERGER)";
            mydatabase.execSQL(sql);
        }
        catch (Exception e) {
            Log.e("Loi ","Table da ton tai");
        }
        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = edtmalop.getText().toString();
                String tenlop = edttenlop.getText().toString();
                int siso = Integer.parseInt(edtsiso.getText().toString());
                ContentValues myvalue = new ContentValues();
                myvalue.put("malop",malop);
                myvalue.put("tenlop",tenlop);
                myvalue.put("siso",siso);
                String msg = "";
                if(mydatabase.insert("tbllop",null,myvalue) == -1) {
                    msg = "Them du lieu that bai";
                }
                else {
                    msg ="Them du lieu thanh cong";
                    edtmalop.setText("");
                    edttenlop.setText("");
                    edtsiso.setText("");

                    mylist.clear();
                    Cursor c = mydatabase.query("tbllop",null,null,null,null,null,null,null);
                    c.moveToNext();
                    String data = "";
                    while (!c.isAfterLast())
                    {
                        data = c.getString(0) + "-"+c.getString(1) + "-"+c.getString(2);
                        c.moveToNext();
                        mylist.add(data);
                    }
                    c.close();
                    myadapter.notifyDataSetChanged();
                }
                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = edtmalop.getText().toString();
                int n = mydatabase.delete("tbllop","malop = ?",new String[]{malop});
                String msg ="";
                if(n == 0)
                {
                    msg = "Khong co ban ghi nao duoc xoa";
                }
                else
                {
                    msg = n+" ban ghi duoc xoa";
                }
                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int siso = Integer.parseInt(edtsiso.getText().toString());
                String malop = edtmalop.getText().toString();
                ContentValues myvalue = new ContentValues();
                myvalue.put("siso",siso);
                int n = mydatabase.update("tbllop",myvalue,"malop = ?",new String[]{malop});
                String msg ="";
                if ( n == 0)
                {
                    msg = "Khong co ban ghi de sua";
                }
                else
                {
                    msg = n + " ban ghi duoc sua";
                    edtmalop.setText("");
                    edttenlop.setText("");
                    edtsiso.setText("");

                    mylist.clear();
                    Cursor c = mydatabase.query("tbllop",null,null,null,null,null,null,null);
                    c.moveToNext();
                    String data = "";
                    while (!c.isAfterLast())
                    {
                        data = c.getString(0) + "-"+c.getString(1) + "-"+c.getString(2);
                        c.moveToNext();
                        mylist.add(data);
                    }
                    c.close();
                    myadapter.notifyDataSetChanged();
                }
                Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
            }


        });
        btnquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mylist.clear();
                Cursor c = mydatabase.query("tbllop",null,null,null,null,null,null,null);
                c.moveToNext();
                String data = "";
                while (c.isAfterLast() == false)
                {
                    data = c.getString(0) + "-"+c.getString(1) + "-"+c.getString(2);
                    c.moveToNext();
                    mylist.add(data);
                }
                c.close();
                myadapter.notifyDataSetChanged();
            }
        });
        }
        };

