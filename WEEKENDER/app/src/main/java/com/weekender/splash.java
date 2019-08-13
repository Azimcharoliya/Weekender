package com.weekender;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash extends AppCompatActivity {

    private static int timeout=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run()
                                      {
                                          Intent i = new Intent(splash.this,login.class);
                                          startActivity(i);
                                          finish();
                                      }
                                  }
                ,timeout);
    }
}
