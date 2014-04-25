package com.codecamp.quotes.modules;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.codecamp.quotes.MyActivity;
import com.codecamp.quotes.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity {
    Context mContext = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mContext = this;
        Button btnLogin = (Button) findViewById(R.id.btnLoginLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(((EditText) findViewById(R.id.etLoginUser)).getText().toString(), ((EditText) findViewById(R.id.etLoginUser)).getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null) {
                            Toast.makeText(mContext,getString(R.string.welcome)+" : "+user.getUsername(),Toast.LENGTH_LONG).show();
                            startActivity(new Intent(mContext, MyActivity.class));
                        } else {
                           Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    public void goSignUp(View view) {
        startActivity(new Intent(mContext, SignUpActivity.class));
    }
}