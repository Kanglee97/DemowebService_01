package com.kanglee.webservice_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<SinhVien> arrayList;
    SinhVienAdapter adapter;

    String urlGetData = "http://192.168.100.2/webservice_android_ontapthi/getdata.php";
    String urlDelete = "http://192.168.100.2/webservice_android_ontapthi/Xoa.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();



    }

    private void addEvents() {
    }

    private void addControls() {

        listView = findViewById(R.id.lst);
        arrayList = new ArrayList<>();
        adapter = new SinhVienAdapter(this,R.layout.dong_sinh_vien_listview,arrayList);
        listView.setAdapter(adapter);

        ReadJson(urlGetData);
    }

    private void ReadJson(String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                arrayList.clear();
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        arrayList.add(new SinhVien(jsonObject.getInt("ID"),
                                jsonObject.getString("HoTen"),
                                jsonObject.getInt("NamSinh"),
                                jsonObject.getString("DiaChi")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ////txtloi.setText(error.toString());
                        //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.them_sinhvien,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnThemSinhVien){
            startActivity(new Intent(MainActivity.this, ThemSVActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
    public void DeleteHocSinh(final int idsv){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("delete")){
                    Toast.makeText(MainActivity.this, "Xoa thanh cong", Toast.LENGTH_LONG).show();
                    ReadJson(urlGetData);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Xoa that bai", Toast.LENGTH_LONG).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parmas = new HashMap<>();
                parmas.put("idCuaSinhVien", String.valueOf(idsv));
                return parmas;
            }
        };
        requestQueue.add(stringRequest);
    }
}
