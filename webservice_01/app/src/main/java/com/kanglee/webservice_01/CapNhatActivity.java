package com.kanglee.webservice_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CapNhatActivity extends AppCompatActivity {

    EditText txtSuaTen, txtSuaNamSinh, txtSuaDiaChi;
    Button btnSua, btnSuaHuy;
    int id= 0;
    String urlCapNhat = "http://192.168.100.2/webservice_android_ontapthi/update.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat);



        //Toast.makeText(this, sinhVien.getHoTen(), Toast.LENGTH_SHORT).show();

        addControls();
        addEvents();
    }

    private void addEvents() {
        Intent intent = getIntent();
        SinhVien sinhVien = (SinhVien) intent.getSerializableExtra("dataSinhVien");


        id = sinhVien.getId();
        txtSuaTen.setText(sinhVien.getHoTen());
        txtSuaNamSinh.setText(sinhVien.getNamSinh()+ "");
        txtSuaDiaChi.setText(sinhVien.getDiaChi());

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = txtSuaTen.getText().toString().trim();
                String ns = txtSuaNamSinh.getText().toString().trim();
                String dc = txtSuaDiaChi.getText().toString().trim();

                if (ten.matches("") || ns.equals("") || dc.length() == 0){
                    Toast.makeText(CapNhatActivity.this, "Thong tin hem du nha kuuuu", Toast.LENGTH_LONG).show();
                }
                else {
                    CapNhat(urlCapNhat);

                }
            }
        });



    }

    private void addControls() {

        txtSuaTen = findViewById(R.id.txtSuaTen);
        txtSuaNamSinh = findViewById(R.id.txtSuaNamSinh);
        txtSuaDiaChi = findViewById(R.id.txtSuaDiaChi);

        btnSuaHuy = findViewById(R.id.btnSuaHuy);
        btnSua = findViewById(R.id.btnSua_Sua);

    }

    private void CapNhat(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("successs")){
                    Toast.makeText(CapNhatActivity.this, "Cap nhat thanh cong", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(CapNhatActivity.this, MainActivity.class));
                }else {
                    Toast.makeText(CapNhatActivity.this, "Loi Cap nhat", Toast.LENGTH_LONG).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CapNhatActivity.this, "Loi Cap nhat", Toast.LENGTH_LONG).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idSV",String.valueOf(id));
                params.put("hotenSV", txtSuaTen.getText().toString().trim());
                params.put("namsinhSV", txtSuaNamSinh.getText().toString().trim());
                params.put("diachiSV", txtSuaDiaChi.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
