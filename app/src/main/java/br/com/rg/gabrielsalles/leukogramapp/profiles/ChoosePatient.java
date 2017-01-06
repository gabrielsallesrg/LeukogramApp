package br.com.rg.gabrielsalles.leukogramapp.profiles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import br.com.rg.gabrielsalles.leukogramapp.DrawerActivity;
import br.com.rg.gabrielsalles.leukogramapp.R;
import br.com.rg.gabrielsalles.leukogramapp.cellsListing.CellsListing_Activity;
import br.com.rg.gabrielsalles.leukogramapp.sqlite.SqliteQueries;

/**
 * Created by gabriel on 23/10/16.
 */

public class ChoosePatient extends DrawerActivity  {
    private Spinner choosePatientSpinner;
    private TextView firstNameText;
    private TextView lastNameText;
    private TextView dateText;
    private TextView patientSexText;
    private final int REQUEST_USER_DATA    = 1;
    private final int UPDATE_USER_DATA     = 2;
    private final int SET_PATIENT_DATA     = 3;
    private final int DELETE_PATIENT_DATA  = 4;
    private final int REQUEST_PATIENTS_IDS = 5;
    private final int REQUEST_PATIENT_BY_ID = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_patient);

        RelativeLayout r1 = (RelativeLayout) findViewById(R.id.content_drawer);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_choose_patient, null);
        r1.addView(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        choosePatientSpinner = (Spinner) findViewById(R.id.choosePatientSpinner);
        firstNameText = (TextView) findViewById(R.id.firstNameText);
        lastNameText = (TextView) findViewById(R.id.lastNameText);
        dateText = (TextView) findViewById(R.id.dateText);
        patientSexText = (TextView) findViewById(R.id.patientSexText);

        //loadSpinnerData();


//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sex, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sexSpinner.setAdapter(adapter);
//
//        mDate.setText(underlineDate("01/01/1986"));
//        setDateTimeField();
        setTitle(getString(R.string.patient_data));

        final SqliteQueries sqliteQueries = new SqliteQueries(getApplicationContext(), REQUEST_PATIENTS_IDS);
        try {
            //ArrayList<String> patientsIds = new ArrayList<>();
            ArrayList<String> patientsIds = new ArrayList<>(Arrays.asList(sqliteQueries.execute().get()));

            Button startExamBtn = (Button) findViewById(R.id.startExam);
            if (patientsIds.size() == 1 && patientsIds.get(0).equals("")) {
                startExamBtn.setClickable(false);
                startExamBtn.setEnabled(false);
            } else {
                startExamBtn.setClickable(true);
                startExamBtn.setEnabled(true);
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, patientsIds);
            choosePatientSpinner.setAdapter(dataAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        choosePatientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final SqliteQueries sqliteQueries = new SqliteQueries(getApplicationContext(), REQUEST_PATIENT_BY_ID);
                try {
                    String[] patientData = sqliteQueries.execute(choosePatientSpinner.getSelectedItem().toString()).get();
                    if (patientData.length > 1) {
                        firstNameText.setText(patientData[0]);
                        lastNameText.setText(patientData[1]);
                        patientSexText.setText(patientData[2]);
                        dateText.setText(patientData[3]);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private SpannableString underlineDate(String date) {
        SpannableString content = new SpannableString(date);
        content.setSpan(new UnderlineSpan(), 0, date.length(), 0);
        return content;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_user_menu, menu);
        return true;
    }

    public void startExam(View view) {
        String strPatientId = choosePatientSpinner.getSelectedItem().toString();
        String strPatientName = getString(R.string.patient) + ": " + firstNameText.getText().toString() + " " + lastNameText.getText().toString();
        String strBirth = getString(R.string.birth) + ": " + dateText.getText().toString();
        String strSex = getString(R.string.sex) + ": " + patientSexText.getText().toString();

        String[] patientData = {strPatientName, strPatientId, strBirth, strSex};

        Intent intent = new Intent(getApplicationContext(), CellsListing_Activity.class);
        intent.putExtra("patientData", patientData);

        startActivity(intent);
        finish();
    }

    public void registerNewPatient(View view) {
        Intent intent = new Intent(getApplicationContext(), MainPatient.class);
        startActivity(intent);
        finish();
    }
}
