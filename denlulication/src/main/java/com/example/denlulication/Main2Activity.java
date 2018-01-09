package com.example.denlulication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.denlulication.Bean.RegistBean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class Main2Activity extends AppCompatActivity {
    private Button zhu;
    private EditText ed1;
    private EditText ed2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ed1 = (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText1);
        zhu = (Button) findViewById(R.id.zhu);

        zhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile= ed1.getText().toString().trim();
                String password= ed2.getText().toString().trim();
                switch (view.getId()){
                    case R.id.zhu:
                        boolean flag=checkData(mobile,password);
                        if(flag){
                            register(mobile,password);
                        }
                        break;
                }
            }


        });
    }
    private boolean checkData(String mobile, String password) {
        if (TextUtils.isEmpty(mobile)||TextUtils.isEmpty(password)){
            Toast.makeText(this,"不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length()<6){
            Toast.makeText(this,"密码不能小于6位",Toast.LENGTH_SHORT).show();
            return  false;
        }
        return true;
    };
    private void register(String mobile, String password) {
        RequestParams params = new RequestParams("http://120.27.23.105/user/reg");

        params.addQueryStringParameter("mobile",mobile);
        params.addQueryStringParameter("password",password);
        x.http().get(params, new Callback.CommonCallback<String>(

        ) {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                RegistBean registBean = gson.fromJson(result, RegistBean.class);
                Toast.makeText(Main2Activity.this,registBean.getMsg(),Toast.LENGTH_SHORT).show();
                if(registBean.getCode().equals("0")){
                    finish();
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
}
