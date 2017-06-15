package com.example.kh.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.kh.myapplication.Module.MyVolley;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="vo cong vinh";
    @BindView(R.id.btnInsert)
    Button btnInsert;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    private String name="", email="";
    String url = "http://192.168.1.10/DuLieu/getdata.php";
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MyVolley.getInstance(this).startVolley();
        builder  =new AlertDialog.Builder(MainActivity.this);
    }

    @OnClick(R.id.btnInsert)
    public void Insert(){


        try{
            name  =  etName.getText().toString().trim();
            email  =etEmail.getText().toString().trim();
        }catch (Exception e){

        }
        Log.i(TAG, "Insert: insert"+ name+" "+ email);
        StringRequest stringRequest  =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "Insert: insert1"+response);

                builder.setTitle("Server Respond");
                builder.setMessage(response);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        etName.setText("");
                        etEmail.setText("");
                    }
                });
              AlertDialog alertDialog=  builder.create();
                alertDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Insert: fail");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                Log.i(TAG, "Insert: insert2"+name+"eL: "+email);
                param.put("name",name);
                param.put("email", email);
                return param;
            }
        };
        MyVolley.getInstance(this).addRequest(stringRequest);
        Log.i(TAG, "Insert: insert3");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyVolley.getInstance(this).stopVolley();
    }
}
