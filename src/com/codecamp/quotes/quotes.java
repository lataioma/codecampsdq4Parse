package com.codecamp.quotes;

import android.app.Application;
import com.parse.Parse;

public class quotes extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "b1vDkkbdTPoLOHFI1GvsubdusEMmD5hpAWMpg8rT", "aI7j1uGi2zqmZt7ZXN2YWYTDmx20rxufEs7ZkPY5");
    }
}
