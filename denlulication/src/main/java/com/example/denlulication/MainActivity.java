package com.example.denlulication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.denlulication.Bean.LoginBean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText ed1;
    private EditText ed2;
    private Button dl;
    private Button zc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setOnClick();
    }
    private void setOnClick() {
        dl.setOnClickListener(this);
        zc.setOnClickListener(this);


    }

    private void initView() {
        ed1= (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText1);
        dl= (Button) findViewById(R.id.dl);
        zc = (Button) findViewById(R.id.zc);
    }


    @Override
    public void onClick(View view) {
        String mobile = ed1.getText().toString().trim();
        String password = ed2.getText().toString().trim();
        switch (view.getId()){
            case R.id.dl:
                boolean flag=checkData(mobile,password);
                if (flag){
                    register(mobile,password);
                }
                break;
            case R.id.zc:
                Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);

                break;

        }



    }

    private void register(String mobile, String password) {
        RequestParams params = new RequestParams("http://120.27.23.105/user/login");

        params.addQueryStringParameter("mobile",mobile);
        params.addQueryStringParameter("password",password);

        x.http().get(params,new Callback.CommonCallback<String>(){

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(result, LoginBean.class);
                if (loginBean.getCode().equals("0")){
                    Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private boolean checkData(String mobile, String password) {
        if(TextUtils.isEmpty(mobile)||TextUtils.isEmpty(password)){
            Toast.makeText(this,"不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length()<6){
            Toast.makeText(this,"密码长度不能小于6",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}
