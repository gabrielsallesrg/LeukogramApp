package br.com.rg.gabrielsalles.leukogramapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import br.com.rg.gabrielsalles.leukogramapp.profiles.ChoosePatient;
import br.com.rg.gabrielsalles.leukogramapp.profiles.MainUser;
import br.com.rg.gabrielsalles.leukogramapp.results.Results;
import br.com.rg.gabrielsalles.leukogramapp.sqlite.SqliteQueries;

public class MainActivity extends DrawerActivity {

    private final int CREATE_TABLES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        RelativeLayout r1 = (RelativeLayout) findViewById(R.id.content_drawer);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.content_main, null);
        r1.addView(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        setTitle(getResources().getString(R.string.app_name));

        SqliteQueries sqliteQueries = new SqliteQueries(this, CREATE_TABLES);
        sqliteQueries.execute();

        LinearLayout newExamBtn     = (LinearLayout) findViewById(R.id.newExamBtn);
        LinearLayout manageExamsBtn = (LinearLayout) findViewById(R.id.manageExamsBtn);
        LinearLayout userProfileBtn = (LinearLayout) findViewById(R.id.userProfileBtn);
        LinearLayout settingsBtn    = (LinearLayout) findViewById(R.id.settingsBtn);

        newExamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChoosePatient.class);
                startActivity(intent);
            }
        });

        manageExamsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Results.class);
                startActivity(intent);
            }
        });

        userProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainUser.class);
                startActivity(intent);
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
