package com.example.customlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SubActivity extends AppCompatActivity {

    private EditText txtId;
    private EditText txtName1;
    private EditText txtPhone1;
    private Button btnOk;
    private Button btnCancel;
    private ImageView Image1;

    private MyDB data;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        txtId = findViewById(R.id.txtId);
        txtName1 = findViewById(R.id.txtName1);
        txtPhone1 = findViewById(R.id.txtPhone1);
        Image1 = findViewById(R.id.ivImage);
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            int id = bundle.getInt("Id");
            String image = bundle.getString("Image");
            String name = bundle.getString("Name");
            String phone = bundle.getString("Phone");
            txtId.setText(String.valueOf(id));
            txtName1.setText(name);
            txtPhone1.setText(phone);
            btnOk.setText("Edit");
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            String id = txtId.getText().toString();
            String name = txtName1.getText().toString();
            String phone = txtPhone1.getText().toString();
            @Override
            public void onClick(View v) {

                if(name.isEmpty() || phone.isEmpty() || id.isEmpty()) {
                    Toast.makeText(v.getContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
                else {
                    int idInt = Integer.parseInt(id);
                    Intent resultIntent = new Intent();
                    Bundle b = new Bundle();
                    b.putInt("Id",idInt);
                    b.putString("Name",name);
                    b.putString("Phone",phone);
                    resultIntent.putExtras(b);
                    setResult(150, resultIntent);
                    finish();
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setResult(100, new Intent());
                finish();
            }
        });


    }
}