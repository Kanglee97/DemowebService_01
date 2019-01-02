package com.kanglee.webservice_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Map;

public class ThemSVActivity extends AppCompatActivity {

    EditText txtTen, txtNS, txtDC;
    Button btnThem, btnHuy;

    String urlInsert = "http://192.168.100.2/webservice_android_ontapthi/insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sv);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = txtTen.getText().toString().trim();
                String ns = txtNS.getText().toString().trim();
                String dc = txtDC.getText().toString().trim();
                if (ten.isEmpty() || ns.isEmpty() || dc.isEmpty()){
                    Toast.makeText(ThemSVActivity.this, "Loi Them", Toast.LENGTH_LONG).show();

                }
                else {
                    ThemSV(urlInsert);
                }

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Intent intent = getIntent();
        //SinhVien sinhVien = (SinhVien) intent.getSerializableExtra("dataSinhVien");

    }

    private void addControls() {

        txtTen = findViewById(R.id.txtNhapTen);
        txtNS = findViewById(R.id.txtNhapNS);
        txtDC = findViewById(R.id.txtNhapDC);

        btnThem = findViewById(R.id.btnThem);
        btnHuy = findViewById(R.id.btnHuy);


    }
    private void ThemSV(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")){
                    Toast.makeText(ThemSVActivity.this, "Thanh Cong", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ThemSVActivity.this, MainActivity.class));
                }
                else {
                    Toast.makeText(ThemSVActivity.this, "Loi Them", Toast.LENGTH_LONG).show();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ThemSVActivity.this, "Loi dich vu", Toast.LENGTH_LONG).show();
                        Log.d("Kang", "Loi\n" + error.toString());

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                String ten = txtTen.getText().toString();
                String ns = txtNS.getText().toString().trim();
                String dc = txtDC.getText().toString().trim();

                params.put("hotenSV",ten);
                params.put("namsinhSV", ns);
                params.put("diachiSV", dc);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
