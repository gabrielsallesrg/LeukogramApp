package br.com.rg.gabrielsalles.leukogramapp.profiles;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import br.com.rg.gabrielsalles.leukogramapp.DrawerActivity;
import br.com.rg.gabrielsalles.leukogramapp.R;
import br.com.rg.gabrielsalles.leukogramapp.sqlite.SqliteQueries;

/**
 * Created by gabriel on 15/10/16.
 */

public class MainPatient extends DrawerActivity implements View.OnClickListener{
    private TextView mDate;
    private DatePickerDialog mDatePickerDialog;
    private SimpleDateFormat mDateFormat;
    private EditText firstName;
    private EditText lastName;
    private EditText patientId;
    private Spinner sexSpinner;
    private TextView date;
    private final int SET_PATIENT_DATA     = 3;
    private final int DELETE_PATIENT_DATA  = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_patient);

        RelativeLayout r1 = (RelativeLayout) findViewById(R.id.content_drawer);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_main_patient, null);
        r1.addView(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);


        mDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        mDate = (TextView) findViewById(R.id.date);
        firstName = (EditText) findViewById(R.id.firstNameText);
        lastName = (EditText) findViewById(R.id.lastNameText);
        patientId = (EditText) findViewById(R.id.patientIdText);
        sexSpinner = (Spinner) findViewById(R.id.patientSexSpinner);
        date = (TextView) findViewById(R.id.date);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sex, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(adapter);

        mDate.setText(underlineDate("01/01/1986"));
        setDateTimeField();
        setTitle(getString(R.string.patient_data));
        //  getSupportActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void setDateTimeField() {
        mDate.setOnClickListener(this);

        GregorianCalendar newCalendar = new GregorianCalendar();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mDate.setText(underlineDate(mDateFormat.format(newDate.getTime())));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
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


    @Override
    public void onClick(View view) {
        if (view == mDate) mDatePickerDialog.show();
    }

    public void save(View view) {
        String strFistName = firstName.getText().toString();
        String strLastName = lastName.getText().toString();
        String strPatientId = patientId.getText().toString();
        String strSex = sexSpinner.getSelectedItem().toString();

        if (strPatientId.trim().length() == 0) {
            Toast.makeText(this, "É necessário um ID", Toast.LENGTH_SHORT).show();
        } else {
            String strDate = date.getText().toString();
            Date birthDate = new Date(0);
            try {
                birthDate = new Date(mDateFormat.parse(strDate).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SqliteQueries sqliteQueries = new SqliteQueries(getApplicationContext(), SET_PATIENT_DATA);
            sqliteQueries.execute(strFistName, strLastName, strPatientId, strDate, strSex);


            Intent intent = new Intent(getApplicationContext(), ChoosePatient.class);
            startActivity(intent);
            finish();
        }
    }
}
