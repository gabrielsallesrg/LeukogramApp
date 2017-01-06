package br.com.rg.gabrielsalles.leukogramapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;

import java.util.ArrayList;
import java.util.Random;

import br.com.rg.gabrielsalles.leukogramapp.profiles.ChoosePatient;
import br.com.rg.gabrielsalles.leukogramapp.profiles.MainUser;
import br.com.rg.gabrielsalles.leukogramapp.results.Results;
import br.com.rg.gabrielsalles.leukogramapp.sqlite.SqliteQueries;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                .enableDumpapp(new SampleDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.new_exam) {
            Intent intent = new Intent(getApplicationContext(), ChoosePatient.class);
            startActivity(intent);
        } else if (id == R.id.manage_exams) {
            Intent intent = new Intent(getApplicationContext(), Results.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.user_profile) {
            Intent intent = new Intent(getApplicationContext(), MainUser.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_send) {
            SqliteQueries newPatient = new SqliteQueries(getApplicationContext(), 3);
            SqliteQueries newExam = new SqliteQueries(getApplicationContext(), 7);
            Random r = new Random();
            int i = r.nextInt(1001 - 50) + 50;
            newPatient.execute("João " + Integer.toString(i), "Alvares "  + Integer.toString(i),  Integer.toString(i), "04/11/2016", "M");
            newExam.execute(Integer.toString(i), "10", "10", "10", "10", "10", "10", "10", "10", "10", "10", "10", "10");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static class SampleDumperPluginsProvider implements DumperPluginsProvider {
        private final Context mContext;

        public SampleDumperPluginsProvider(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public Iterable<DumperPlugin> get() {
            ArrayList<DumperPlugin> plugins = new ArrayList<>();
            for (DumperPlugin defaultPlugin : Stetho.defaultDumperPluginsProvider(mContext).get()) {
                plugins.add(defaultPlugin);
            }
            return plugins;
        }
    }
}
