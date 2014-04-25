package com.codecamp.quotes.modules;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.codecamp.quotes.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 4/25/14.
 */
public class QuotesListActivity extends ListActivity {
    Context mContext = null;
    private ArrayAdapter<String> adapter;
    private List<String> objects =new ArrayList<String>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quoteslist);
        mContext = this;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Quote");
        query.include("user");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> quotesListOjects, ParseException e) {
                if (e == null) {
                    List<String> quotes = new ArrayList<String>();
                    for (ParseObject quote : quotesListOjects) {
                        quotes.add(quote.getString("content"));
                        objects.add(quote.getObjectId());
                    }
                    adapter = new ArrayAdapter<String>(mContext,
                            android.R.layout.simple_list_item_1, quotes);
                    setListAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, ViewQuote.class);
        i.putExtra("objectId", objects.get(position));
        startActivity(i);
    }
}