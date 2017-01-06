package br.com.rg.gabrielsalles.leukogramapp.profiles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.concurrent.ExecutionException;

import br.com.rg.gabrielsalles.leukogramapp.DrawerActivity;
import br.com.rg.gabrielsalles.leukogramapp.MainActivity;
import br.com.rg.gabrielsalles.leukogramapp.R;
import br.com.rg.gabrielsalles.leukogramapp.sqlite.SqliteQueries;

/**
 * Created by gabriel on 15/10/16.
 */

public class MainUser extends DrawerActivity {

    private final int REQUEST_USER_DATA    = 1;
    private final int UPDATE_USER_DATA     = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_user);
        RelativeLayout r1 = (RelativeLayout) findViewById(R.id.content_drawer);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_main_user, null);
        r1.addView(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        setTitle(getString(R.string.user_profile));
        SqliteQueries sqliteQueries = new SqliteQueries(getApplicationContext(), REQUEST_USER_DATA);
        try {
            String[] userData = sqliteQueries.execute().get();
            EditText firstNameET = ((EditText) findViewById(R.id.firstNameText));
            EditText lastNameET  = ((EditText) findViewById(R.id.lastNameText));
            EditText userIdET    = ((EditText) findViewById(R.id.userIdText));

            firstNameET.setText(userData[0]);
            lastNameET.setText(userData[1]);
            userIdET.setText(userData[2]);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void saveAndNext(View view) {
        String firstName = ((EditText) findViewById(R.id.firstNameText)).getText().toString();
        String lastName = ((EditText) findViewById(R.id.lastNameText)).getText().toString();
        String userId = ((EditText) findViewById(R.id.userIdText)).getText().toString();
        /**
         * Save to SQLite
         */
        SqliteQueries sqliteQueries = new SqliteQueries(getApplicationContext(), UPDATE_USER_DATA);
        sqliteQueries.execute(firstName, lastName, userId);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}