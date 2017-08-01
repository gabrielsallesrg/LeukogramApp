package br.com.rg.gabrielsalles.leukogramapp.cellsListing;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.rg.gabrielsalles.leukogramapp.R;
import br.com.rg.gabrielsalles.leukogramapp.otherFiles.Cell;
import br.com.rg.gabrielsalles.leukogramapp.sqlite.SqliteQueries;


/**
 * Created by gabriel on 15/10/16.
 */

public class CellsListing_Activity extends AppCompatActivity {
    //private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int cellsCount;
    private ArrayList<Cell> cellsArray = new ArrayList<>();
    private ViewPagerAdapter adapter;
    private final int SAVE_EXAM_DATA = 7;
    private int studentHelperOn = 1;
    private MediaPlayer mp;
    private int totalCellsInput = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cells_listing_activity);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager, savedInstanceState);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.parseColor("#BBBBBB"), Color.parseColor("#FFFFFF"));
        cellsCount = 0;
        populateCellArray();
//        toggleHideyBar();

        new AlertDialog.Builder(this)
                .setTitle(R.string.totalCellsTitle)
                .setMessage(R.string.totalCellsNumberMessage)
                .setView(R.layout.dialog_total_cells)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText totalCellsET = (EditText) ((AlertDialog) dialog).findViewById(R.id.totalCellsText);
                        if (!totalCellsET.getText().toString().equals("")) {
                            totalCellsInput = Integer.parseInt(totalCellsET.getText().toString());
                        }
                    }
                }).show();

    }

    public void toggleHideyBar() {
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("Testing immersive", "Turning immersive mode mode off. ");
        } else {
            Log.i("Testing immersive", "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        //END_INCLUDE (set_ui_flags)
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void setupViewPager(ViewPager viewPager, Bundle savedInstance) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new MainCells_Fragment(),  getString(R.string.main_cells));
        adapter.addFragment(new OtherCells_Fragment(), getString(R.string.other_cells));
        adapter.addFragment(new EndExam_Fragment(),    getString(R.string.end_exam_uppercase));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void populateCellArray() {
        cellsArray.add(new Cell(0,  getResources().getString(R.string.cell0),  0));
        cellsArray.add(new Cell(1,  getResources().getString(R.string.cell1),  0));
        cellsArray.add(new Cell(2,  getResources().getString(R.string.cell2),  0));
        cellsArray.add(new Cell(3,  getResources().getString(R.string.cell3),  0));
        cellsArray.add(new Cell(4,  getResources().getString(R.string.cell4),  0));
        cellsArray.add(new Cell(5,  getResources().getString(R.string.cell5),  0));
        cellsArray.add(new Cell(6,  getResources().getString(R.string.cell6),  0));
        cellsArray.add(new Cell(7,  getResources().getString(R.string.cell7),  0));
        cellsArray.add(new Cell(8,  getResources().getString(R.string.cell8),  0));
        cellsArray.add(new Cell(9,  getResources().getString(R.string.cell9),  0));
        cellsArray.add(new Cell(10, getResources().getString(R.string.cell10), 0));
        cellsArray.add(new Cell(11, getResources().getString(R.string.cell11), 0));
    }

    public void addCell(int pos, Button btn) {
        cellsArray.get(pos).addOne();
        btn.setText(cellsArray.get(pos).getPublishableName());
        if (pos < cellsArray.size() -1)
            cellsCount++;
        updateCellsCounterTable();
        if (cellsCount == 100) {
            playSound(1000);
            Toast.makeText(this, "100 células.\nPode concluir a contagem se desejar.", Toast.LENGTH_LONG).show();
            updateEndExamButton(true);
        }
    }

    public void minusCell(int pos, Button btn) {
        cellsArray.get(pos).minusOne();
        btn.setText(cellsArray.get(pos).getPublishableName());
        if (pos < cellsArray.size() -1)
            cellsCount--;
        updateCellsCounterTable();
        if (cellsCount < 100) {
            updateEndExamButton(false);
        }
    }

    public void updateEndExamButton(boolean clickable){
        Button endExamBtn = (Button) findViewById(R.id.endExamBtn);
        endExamBtn.setEnabled(clickable);
        endExamBtn.setClickable(clickable);
        Drawable icon = endExamBtn.getCompoundDrawables()[0];
        icon = DrawableCompat.wrap(icon);
        DrawableCompat.setTint(icon, Color.parseColor(clickable ? "#000000" : "#656565"));
    }

    public void updateCellsCounterTable() {

        TextView counterTotal = (TextView) findViewById(R.id.counterTotal);
        Double actualTotal = 0.0;

        for (int i = 0; i < 11; i++) {
            actualTotal += cellsArray.get(i).getCount();
        }
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(0);
        counterTotal.setText(df.format(actualTotal));
        df.setMaximumFractionDigits(2);

        Double total;
        if (actualTotal < 100.0)
            total = 100.0;
        else
            total = actualTotal;

        TextView counterCell0= (TextView) findViewById(R.id.counterCell0);
        counterCell0.setText(df.format(cellsArray.get(0).getCount()   / total * totalCellsInput) + " / μL");
        TextView counterCell0Percent= (TextView) findViewById(R.id.counterCell0Percent);
        counterCell0Percent.setText(df.format(cellsArray.get(0).getCount()   / total * 100) + "%");
        TextView counterCell1= (TextView) findViewById(R.id.counterCell1);
        counterCell1.setText(df.format(cellsArray.get(1).getCount()   / total * totalCellsInput) + " / μL");
        TextView counterCell1Percent= (TextView) findViewById(R.id.counterCell1Percent);
        counterCell1Percent.setText(df.format(cellsArray.get(1).getCount()   / total * 100) + "%");
        TextView counterCell2= (TextView) findViewById(R.id.counterCell2);
        counterCell2.setText(df.format(cellsArray.get(2).getCount()   / total * totalCellsInput) + " / μL");
        TextView counterCell2Percent= (TextView) findViewById(R.id.counterCell2Percent);
        counterCell2Percent.setText(df.format(cellsArray.get(2).getCount()   / total * 100) + "%");
        TextView counterCell3= (TextView) findViewById(R.id.counterCell3);
        counterCell3.setText(df.format(cellsArray.get(3).getCount()   / total * totalCellsInput) + " / μL");
        TextView counterCell3Percent= (TextView) findViewById(R.id.counterCell3Percent);
        counterCell3Percent.setText(df.format(cellsArray.get(3).getCount()   / total * 100) + "%");
        TextView counterCell4= (TextView) findViewById(R.id.counterCell4);
        counterCell4.setText(df.format(cellsArray.get(4).getCount()   / total * totalCellsInput) + " / μL");
        TextView counterCell4Percent= (TextView) findViewById(R.id.counterCell4Percent);
        counterCell4Percent.setText(df.format(cellsArray.get(4).getCount()   / total * 100) + "%");
        TextView counterCell5= (TextView) findViewById(R.id.counterCell5);
        counterCell5.setText(df.format(cellsArray.get(5).getCount()   / total * totalCellsInput) + " / μL");
        TextView counterCell5Percent= (TextView) findViewById(R.id.counterCell5Percent);
        counterCell5Percent.setText(df.format(cellsArray.get(5).getCount()   / total * 100) + "%");
        TextView counterCell6= (TextView) findViewById(R.id.counterCell6);
        counterCell6.setText(df.format(cellsArray.get(6).getCount()   / total * totalCellsInput) + " / μL");
        TextView counterCell6Percent= (TextView) findViewById(R.id.counterCell6Percent);
        counterCell6Percent.setText(df.format(cellsArray.get(6).getCount()   / total * 100) + "%");
        TextView counterCell7= (TextView) findViewById(R.id.counterCell7);
        counterCell7.setText(df.format(cellsArray.get(7).getCount()   / total * totalCellsInput) + " / μL");
        TextView counterCell7Percent= (TextView) findViewById(R.id.counterCell7Percent);
        counterCell7Percent.setText(df.format(cellsArray.get(7).getCount()   / total * 100) + "%");
        TextView counterCell8= (TextView) findViewById(R.id.counterCell8);
        counterCell8.setText(df.format(cellsArray.get(8).getCount()   / total * totalCellsInput) + " / μL");
        TextView counterCell8Percent= (TextView) findViewById(R.id.counterCell8Percent);
        counterCell8Percent.setText(df.format(cellsArray.get(8).getCount()   / total * 100) + "%");
        TextView counterCell9= (TextView) findViewById(R.id.counterCell9);
        counterCell9.setText(df.format(cellsArray.get(9).getCount()   / total * totalCellsInput) + " / μL");
        TextView counterCell9Percent= (TextView) findViewById(R.id.counterCell9Percent);
        counterCell9Percent.setText(df.format(cellsArray.get(9).getCount()   / total * 100) + "%");
//        TextView counterCell10= (TextView) findViewById(R.id.counterCell10);
//        counterCell10.setText(df.format(cellsArray.get(10).getCount() / total * totalCellsInput) + " / μL");
        TextView counterCell11= (TextView) findViewById(R.id.counterCell11);
        counterCell11.setText(df.format(cellsArray.get(11).getCount() / total * totalCellsInput) + " / μL");
        TextView counterCell11Percent= (TextView) findViewById(R.id.counterCell11Percent);
        //counterCell11Percent.setText(df.format(cellsArray.get(11).getCount()   / total * 100) + "%");
        counterCell11Percent.setText("0%");

        counterTotal.setText(df.format(totalCellsInput) + " / μL");
    }

    public String[] getIntentExtra() {
        Intent intent = getIntent();
        return intent.getStringArrayExtra("patientData");
    }

    public void endExam() {
        Intent intent = getIntent();
        String patientId = intent.getStringArrayExtra("patientData")[1];

        // SAVE IN DATABASE //
        SqliteQueries sqliteQueries = new SqliteQueries(this, SAVE_EXAM_DATA);
        sqliteQueries.execute(
                patientId,
                Integer.toString(cellsArray.get(0).getCount()),  Integer.toString(cellsArray.get(1).getCount()),
                Integer.toString(cellsArray.get(2).getCount()),  Integer.toString(cellsArray.get(3).getCount()),
                Integer.toString(cellsArray.get(4).getCount()),  Integer.toString(cellsArray.get(5).getCount()),
                Integer.toString(cellsArray.get(6).getCount()),  Integer.toString(cellsArray.get(7).getCount()),
                Integer.toString(cellsArray.get(8).getCount()),  Integer.toString(cellsArray.get(9).getCount()),
                Integer.toString(cellsArray.get(10).getCount()), Integer.toString(cellsArray.get(11).getCount()),
                Integer.toString(totalCellsInput)
        );

        finish();
    }

    public boolean isStudentHelperOn() {
        if (studentHelperOn == 1)
            return true;
        else
            return false;
    }

    public void setStudentHelperOff() {
        studentHelperOn = 0;
    }

    public void playSound(int pos) {

        if (mp != null) {
            mp.stop();
            mp.reset();
            mp.release();
        }

        switch (pos) {
            case 0:
                mp = MediaPlayer.create(this, R.raw.cell_0_sound);
                break;
            case 1:
                mp = MediaPlayer.create(this, R.raw.cell_1_sound);
                break;
            case 2:
                mp = MediaPlayer.create(this, R.raw.cell_2_sound);
                break;
            case 3:
                mp = MediaPlayer.create(this, R.raw.cell_3_sound);
                break;
            case 4:
                mp = MediaPlayer.create(this, R.raw.cell_4_sound);
                break;
            case 5:
                mp = MediaPlayer.create(this, R.raw.cell_5_sound);
                break;
            case 6:
                mp = MediaPlayer.create(this, R.raw.cell_6_sound);
                break;
            case 7:
                mp = MediaPlayer.create(this, R.raw.cell_7_sound);
                break;
            case 8:
                mp = MediaPlayer.create(this, R.raw.cell_8_sound);
                break;
            case 9:
                mp = MediaPlayer.create(this, R.raw.cell_9_sound);
                break;
            case 10:
                mp = MediaPlayer.create(this, R.raw.cell_10_sound);
                break;
            case 11:
                mp = MediaPlayer.create(this, R.raw.cell_11_sound);
                break;
            case 1000:
                mp = MediaPlayer.create(this, R.raw.cell_1000_sound);
                break;
            default:
                mp = MediaPlayer.create(this, R.raw.cell_1000_sound);
                break;
        }
        mp.start();
    }
}
