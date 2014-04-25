package com.codecamp.quotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.codecamp.quotes.modules.AddQuoteActivity;
import com.codecamp.quotes.modules.LoginActivity;
import com.codecamp.quotes.modules.QuotesListActivity;
import com.parse.*;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ParseUser.getCurrentUser()==null){
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }
        setContentView(R.layout.main);
        ParseAnalytics.trackAppOpened(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Quote");
        query.countInBackground(new CountCallback() {
            public void done(int count, ParseException e) {
                if (e == null) {
                    ((TextView)findViewById(R.id.tvQuotesCounter)).setText(String.valueOf(count));
                }
            }
        });
    }

    public void openForm(View view) {
        startActivity(new Intent(this, AddQuoteActivity.class));
    }

    public void viewQuotes(View view) {
        startActivity(new Intent(this, QuotesListActivity.class));
    }
}
