package com.codecamp.quotes.modules;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.codecamp.quotes.MyActivity;
import com.codecamp.quotes.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

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
                            Toast.makeText(mContext, getString(R.string.welcome) + " : " + user.getUsername(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(mContext, MyActivity.class));
                        } else {
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        TextView tvResetPassword = (TextView) findViewById(R.id.tvLoginResetPassword);
        tvResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    public void goSignUp(View view) {
        startActivity(new Intent(mContext, SignUpActivity.class));
    }

    private void resetPassword() {
        LayoutInflater li = LayoutInflater.from(mContext);
        View promptsView = li.inflate(R.layout.prompts_email, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);
        alertDialogBuilder.setView(promptsView);
        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.etLoginEmail);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getString(R.string.reset),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ParseUser.requestPasswordResetInBackground(userInput.getText().toString(), new RequestPasswordResetCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Toast.makeText(mContext, getString(R.string.restored), Toast.LENGTH_LONG).show();
                                        } else Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                )
                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }
                );

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}