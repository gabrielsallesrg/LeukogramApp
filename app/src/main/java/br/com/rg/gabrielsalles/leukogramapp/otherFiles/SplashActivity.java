package br.com.rg.gabrielsalles.leukogramapp.otherFiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.rg.gabrielsalles.leukogramapp.MainActivity;

/**
 * Created by gabriel on 14/02/17.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}