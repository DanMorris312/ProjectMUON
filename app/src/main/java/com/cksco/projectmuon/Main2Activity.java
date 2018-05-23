package com.cksco.projectmuon;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class Main2Activity extends AppCompatActivity {
    private MyGLCamSurf mRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main2);
        mRenderer =  findViewById(R.id.renderer_view);



    }
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onPause() {
        super.onPause();
        mRenderer.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
        mRenderer.onResume();

    }

}
