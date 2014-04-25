package com.codecamp.quotes.modules;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.codecamp.quotes.MyActivity;
import com.codecamp.quotes.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends Activity {
    private Context mContext;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mContext=this;
    }

    public void doSignUp(View view) {
        ParseUser user = new ParseUser();
        user.setUsername(((EditText) findViewById(R.id.etSignUpUser)).getText().toString());
        user.setPassword(((EditText) findViewById(R.id.etSignUpPassword)).getText().toString());
        user.setEmail(((EditText) findViewById(R.id.etSignUpEmail)).getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    startActivity(new Intent(mContext, MyActivity.class));
                } else {
                    Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}