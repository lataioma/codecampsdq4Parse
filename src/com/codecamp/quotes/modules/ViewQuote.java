package com.codecamp.quotes.modules;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import com.codecamp.quotes.R;
import com.parse.*;

/**
 * Created by david on 4/25/14.
 */
public class ViewQuote extends Activity {
    private Context mContext;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_quote);
        mContext = this;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Quote");
        query.getInBackground(getIntent().getExtras().getString("objectId"), new GetCallback<ParseObject>() {
            public void done(ParseObject quote, ParseException e) {
                if (e == null) {
                    ((TextView)findViewById(R.id.tvQuoteText)).setText(quote.getString("content"));
                    ParseImageView imageView = (ParseImageView)findViewById(R.id.ivQuoteImage);
                    imageView.setPlaceholder(getResources().getDrawable(R.drawable.ic_launcher));
                    imageView.setParseFile(quote.getParseFile("emoti"));
                    imageView.loadInBackground();
                } else {
                    ((Activity)mContext).finish();
                }
            }
        });
    }
}