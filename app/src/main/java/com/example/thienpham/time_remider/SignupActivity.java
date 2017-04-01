package com.example.thienpham.time_remider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import object.dao.userdao;
import object.database;
import object.dto.userdto;

import java.util.List;

public class SignupActivity extends AppCompatActivity {
    Button btSignup;
    EditText etUser, etPassword, etEmail, etNumber;
    boolean success = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btSignup=(Button) findViewById(R.id.btSignup);
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new CheckData().execute();
                    }
                });
            }

        });
    }
    class CheckData extends AsyncTask<String, String, String> {
        private ProgressDialog progress = new ProgressDialog(SignupActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setTitle(("Loading"));
            progress.setMessage("Kiểm tra thông tin đăng kí...");
            progress.show();
        }

        @Override
        protected String doInBackground(String... params) {

            if(database.checkServerAvailable() != true)
            {
                return "Không thể kết nối tới server!";
            }
            etUser = (EditText) findViewById(R.id.etName);
            etPassword = (EditText) findViewById(R.id.etPass);
            etEmail = (EditText) findViewById(R.id.etEmail);

            if(etUser.length() < 3)
                return "Tên tài khoản phải ít nhất 3 kí tự";
            if(etPassword.length() < 3)
                return "Mật khẩu phải có ít nhất 3 kí tự";
            if(etEmail.length() < 3)
                return "Email phải hơn 5 kí tự";

            List<userdto> listdto = userdao.getall();
            for(userdto dto : listdto)
            {
                if(dto.Name.equals(etUser.getText().toString()))
                    return "Tên tài khoản đã có người dùng";
            }

            userdto dto = new userdto();
            dto.Name = etUser.getText().toString();
            dto.Password = etPassword.getText().toString();
            dto.Email = etEmail.getText().toString();
            userdao.insert(dto);

            success = true;

            return "Đăng kí thành công";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.dismiss();
            Toast.makeText(SignupActivity.this, s, Toast.LENGTH_SHORT).show();
            if(success == true)
            {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
