package br.com.rg.gabrielsalles.leukogramapp.results;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

import br.com.rg.gabrielsalles.leukogramapp.R;

/**
 * Created by gabriel on 05/11/16.
 */

public class ResultDetails extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_exam_fragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button endExamBtn = (Button) findViewById(R.id.endExamBtn);
        endExamBtn.setVisibility(View.GONE);
        Bundle extras = getIntent().getExtras();
        final String[] examData = extras.getStringArray("localExamData");
        TextView patientName = (TextView) findViewById(R.id.patientName);
        patientName.setText(getString(R.string.patient) + ": " + examData[2] + " " + examData[3]);
        TextView patientId = (TextView) findViewById(R.id.patientId);
        patientId.setText(getString(R.string.patientId) + ": " + examData[0]);

        TextView counterTotal = (TextView) findViewById(R.id.counterTotal);
        Double total = 0.0;
        for (int i = 6; i < 17; i++){
            total += Integer.parseInt(examData[i]);
        }

        int realTotalCells = Integer.parseInt(examData[18]);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(0);
        counterTotal.setText(Integer.toString(realTotalCells) + " / μL");
        df.setMaximumFractionDigits(2);


        TextView cell0 = (TextView) findViewById(R.id.counterCell0);
        cell0.setText(df.format(Integer.valueOf(examData[6]) / total * realTotalCells) + " / μL");
        TextView counterCell0Percent = (TextView) findViewById(R.id.counterCell0Percent);
        counterCell0Percent.setText(df.format(Integer.valueOf(examData[6])   / total * 100) + "%");
        TextView cell1 = (TextView) findViewById(R.id.counterCell1);
        cell1.setText(df.format(Integer.valueOf(examData[7]) / total * realTotalCells) + " / μL");
        TextView counterCell1Percent = (TextView) findViewById(R.id.counterCell1Percent);
        counterCell1Percent.setText(df.format(Integer.valueOf(examData[7])   / total * 100) + "%");
        TextView cell2 = (TextView) findViewById(R.id.counterCell2);
        cell2.setText(df.format(Integer.valueOf(examData[8]) / total * realTotalCells) + " / μL");
        TextView counterCell2Percent = (TextView) findViewById(R.id.counterCell2Percent);
        counterCell2Percent.setText(df.format(Integer.valueOf(examData[8])   / total * 100) + "%");
        TextView cell3 = (TextView) findViewById(R.id.counterCell3);
        cell3.setText(df.format(Integer.valueOf(examData[9]) / total * realTotalCells) + " / μL");
        TextView counterCell3Percent = (TextView) findViewById(R.id.counterCell3Percent);
        counterCell3Percent.setText(df.format(Integer.valueOf(examData[9])   / total * 100) + "%");
        TextView cell4 = (TextView) findViewById(R.id.counterCell4);
        cell4.setText(df.format(Integer.valueOf(examData[10]) / total * realTotalCells) + " / μL");
        TextView counterCell4Percent = (TextView) findViewById(R.id.counterCell4Percent);
        counterCell4Percent.setText(df.format(Integer.valueOf(examData[10])   / total * 100) + "%");
        TextView cell5 = (TextView) findViewById(R.id.counterCell5);
        cell5.setText(df.format(Integer.valueOf(examData[11]) / total * realTotalCells) + " / μL");
        TextView counterCell5Percent = (TextView) findViewById(R.id.counterCell5Percent);
        counterCell5Percent.setText(df.format(Integer.valueOf(examData[11])   / total * 100) + "%");
        TextView cell6 = (TextView) findViewById(R.id.counterCell6);
        cell6.setText(df.format(Integer.valueOf(examData[12]) / total * realTotalCells) + " / μL");
        TextView counterCell6Percent = (TextView) findViewById(R.id.counterCell6Percent);
        counterCell6Percent.setText(df.format(Integer.valueOf(examData[12])   / total * 100) + "%");
        TextView cell7 = (TextView) findViewById(R.id.counterCell7);
        cell7.setText(df.format(Integer.valueOf(examData[13]) / total * realTotalCells) + " / μL");
        TextView counterCell7Percent = (TextView) findViewById(R.id.counterCell7Percent);
        counterCell7Percent.setText(df.format(Integer.valueOf(examData[13])   / total * 100) + "%");
        TextView cell8 = (TextView) findViewById(R.id.counterCell8);
        cell8.setText(df.format(Integer.valueOf(examData[14]) / total * realTotalCells) + " / μL");
        TextView counterCell8Percent = (TextView) findViewById(R.id.counterCell8Percent);
        counterCell8Percent.setText(df.format(Integer.valueOf(examData[14])   / total * 100) + "%");
        TextView cell9 = (TextView) findViewById(R.id.counterCell9);
        cell9.setText(df.format(Integer.valueOf(examData[15]) / total * realTotalCells) + " / μL");
        TextView counterCell9Percent = (TextView) findViewById(R.id.counterCell9Percent);
        counterCell9Percent.setText(df.format(Integer.valueOf(examData[15])   / total * 100) + "%");
//        TextView cell10 = (TextView) findViewById(R.id.counterCell10);
//        cell10.setText(df.format(Integer.valueOf(examData[16]) / total * realTotalCells) + " / μL");
        TextView cell11 = (TextView) findViewById(R.id.counterCell11);
        cell11.setText(df.format(Integer.valueOf(examData[17]) / total * realTotalCells) + " / μL");
        TextView counterCell11Percent = (TextView) findViewById(R.id.counterCell11Percent);
        counterCell11Percent.setText("0%");


        setTitle(getString(R.string.results));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return false;
        }
    }
}