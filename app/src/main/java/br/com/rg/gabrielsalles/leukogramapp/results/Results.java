package br.com.rg.gabrielsalles.leukogramapp.results;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.rg.gabrielsalles.leukogramapp.DrawerActivity;
import br.com.rg.gabrielsalles.leukogramapp.R;
import br.com.rg.gabrielsalles.leukogramapp.otherFiles.Exam;
import br.com.rg.gabrielsalles.leukogramapp.sqlite.GetAllExamsQuery;
import br.com.rg.gabrielsalles.leukogramapp.sqlite.SqliteQueries;

import static br.com.rg.gabrielsalles.leukogramapp.R.id.examDate;

/**
 * Created by gabriel on 03/11/16.
 */

public class Results extends DrawerActivity {

    private final String SEPARATE_VALUE = "\t";
    private static final String TAG = "RESULTS";
    private ArrayList<Exam> examsList;
    private ExamsListAdapter examsListAdapter;
    private final int REQUEST_USER_DATA = 1;
    private final int DELETE_EXAMS = 8;
    private final int REQUEST_WRITE_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout r1 = (RelativeLayout) findViewById(R.id.content_drawer);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_results, null);
        r1.addView(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final GetAllExamsQuery getAllExamsQuery = new GetAllExamsQuery(getApplicationContext());
        try {
            examsList = new ArrayList<>(Arrays.asList(getAllExamsQuery.execute().get()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        setTitle(getString(R.string.results));

        examsListAdapter = new ExamsListAdapter(this, R.layout.activity_result_lv_line, examsList);
        ListView examsListView = (ListView) findViewById(R.id.examsListView);
        examsListView.setAdapter(examsListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        View delete = (View) findViewById(R.id.toolbar_delete);
        int deleteId = delete.getId();
        View share  = (View) findViewById(R.id.toolbar_share);
        int shareId  = share.getId();

         if (id == deleteId) {
             new AlertDialog.Builder(this)
                     .setTitle(getResources().getString(R.string.alertdialog_delete_title))
                     .setMessage(getResources().getString(R.string.alertdialog_delete_message))
                     .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {

                         }
                     })
                     .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             // Deletar bagaças
                             DeleteExams();
                         }
                     })
                     .setIcon(android.R.drawable.ic_dialog_alert)
                     .show();
             return true;
         } else if (id == shareId) {
             if (Build.VERSION.SDK_INT < 23) {
                 ExportExams();
             } else {
                 checkRequestWritePermission();
             }
             return true;
        }
        return false;
    }

    private void checkRequestWritePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                ExportExams();
            } else {
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ExportExams();
                } else {
                    Toast.makeText(this, getString(R.string.permission_reason), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void DeleteExams() {
        String andOrSql = "";
        for (Exam exam: examsList) {
            if (exam.isChecked()) {
                andOrSql += " OR exam_patientId = '" + exam.getPatiendId() + "' AND exam_date = '" + exam.getExamDate() + "'";
            }
        }
        andOrSql = andOrSql.replaceFirst("OR", "");
        SqliteQueries sqliteQueries = new SqliteQueries(this, DELETE_EXAMS);
        try {
            sqliteQueries.execute(andOrSql).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, Results.class);
        startActivity(intent);
        finish();
    }

    private void ExportExams() {
        SqliteQueries sqliteQueries = new SqliteQueries(this, REQUEST_USER_DATA);
        String userName = "";
        String userId = "";
        try {
            String[] userData = sqliteQueries.execute().get();
            userName = userData[0] + " " + userData[1];
            userId   = userData[2];
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String stringForCsvFile = getString(R.string.user) + SEPARATE_VALUE + userName + "\n" + getString(R.string.id) + SEPARATE_VALUE + userId + "\n\n\n";

        double total = 0.0;

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(0);
        df.setMaximumFractionDigits(2);

        for (Exam exam: examsList) {

            if (exam.isChecked()) {
                double realTotalCells = exam.getDoubleTotalCells();
                for (int i = 0; i < 11; i++) {
                    total += exam.getCell(i);
                }
                stringForCsvFile += getString(R.string.patient)       + SEPARATE_VALUE + exam.getFirstName() + " " + exam.getLastName() + "\n" + getString(R.string.patientId)  + SEPARATE_VALUE + exam.getPatiendId()  + "\n\n";
                stringForCsvFile += getString(R.string.cell0)   + SEPARATE_VALUE + df.format(exam.getCell(0)  / total * realTotalCells) + " / μL" + SEPARATE_VALUE + df.format(exam.getCell(0)  / total * 100)  + "%" + "\n";
                stringForCsvFile += getString(R.string.cell1)    + SEPARATE_VALUE + df.format(exam.getCell(1)  / total * realTotalCells) + " / μL" + SEPARATE_VALUE + df.format(exam.getCell(1)  / total * 100)  + "%"  + "\n";
                stringForCsvFile += getString(R.string.cell2)    + SEPARATE_VALUE + df.format(exam.getCell(2)  / total * realTotalCells) + " / μL" + SEPARATE_VALUE + df.format(exam.getCell(2)  / total * 100)  + "%"  + "\n";
                stringForCsvFile += getString(R.string.cell3)     + SEPARATE_VALUE + df.format(exam.getCell(3)  / total * realTotalCells) + " / μL" + SEPARATE_VALUE + df.format(exam.getCell(3)  / total * 100)  + "%"  + "\n";
                stringForCsvFile += getString(R.string.cell4)   + SEPARATE_VALUE + df.format(exam.getCell(4)  / total * realTotalCells) + " / μL" + SEPARATE_VALUE + df.format(exam.getCell(4)  / total * 100)  + "%"  + "\n";
                stringForCsvFile += getString(R.string.cell5)     + SEPARATE_VALUE + df.format(exam.getCell(5)  / total * realTotalCells) + " / μL" + SEPARATE_VALUE + df.format(exam.getCell(5)  / total * 100)  + "%"  + "\n";
                stringForCsvFile += getString(R.string.cell6)        + SEPARATE_VALUE + df.format(exam.getCell(6)  / total * realTotalCells) + " / μL" + SEPARATE_VALUE + df.format(exam.getCell(6)  / total * 100)  + "%"  + "\n";
                stringForCsvFile += getString(R.string.cell7)  + SEPARATE_VALUE + df.format(exam.getCell(7)  / total * realTotalCells) + " / μL" + SEPARATE_VALUE + df.format(exam.getCell(7)  / total * 100)  + "%"  + "\n";
                stringForCsvFile += getString(R.string.cell8)     + SEPARATE_VALUE + df.format(exam.getCell(8)  / total * realTotalCells) + " / μL" + SEPARATE_VALUE + df.format(exam.getCell(8)  / total * 100)  + "%"  + "\n";
                stringForCsvFile += getString(R.string.cell9) + SEPARATE_VALUE + df.format(exam.getCell(9)  / total * realTotalCells) + " / μL" + SEPARATE_VALUE + df.format(exam.getCell(9)  / total * 100)  + "%"  + "\n";
                stringForCsvFile += getString(R.string.cell11)   + SEPARATE_VALUE + df.format(exam.getCell(11) / total * realTotalCells) + " / μL\n";
                stringForCsvFile += "Total"         + SEPARATE_VALUE + df.format(realTotalCells) + " / μL" +  SEPARATE_VALUE + "100%" +"\n\n\n";
            }
        }
        String filename = userName + "_" + userId + ".csv";
        if (filename.equals(" _.csv")){
            filename = "blood_exam.csv";
        }

        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/Android/data/br.com.gabrielsallesrg.bloodtestdrawer/files");
        dir.mkdirs();
        File file = new File(dir, filename);

        try {
            FileOutputStream f = new FileOutputStream(file);
            f.write(stringForCsvFile.getBytes());
            f.close();

            Intent intentShareFile = new Intent(Intent.ACTION_SEND);
            intentShareFile.setType("application/csv");
            intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getPath()));

            startActivity(Intent.createChooser(intentShareFile, "Share File"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i(TAG, "******* File not found. Did you" +
                    " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ExamsListAdapter extends ArrayAdapter<Exam> {

        private int layout;

        public ExamsListAdapter (Context context, int resource, List<Exam> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final Exam exam = getItem(position);

            // Check if an existing view is being reused, otherwise inflate a new view from custom row layout
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false);
            }

            final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            final TextView patientIdView = (TextView) convertView.findViewById(R.id.patientId);
            final TextView examDateView = (TextView) convertView.findViewById(examDate);
            final Button detailsButton = (Button) convertView.findViewById(R.id.detailsButton);
            checkBox.setChecked(exam.isChecked());
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox localCheckBox = (CheckBox) v;
                    if (localCheckBox.isChecked()) {
                        exam.setChecked(true);
                    } else {
                        exam.setChecked(false);
                    }
                }
            });
            patientIdView.setText(exam.getPatiendId());
            examDateView.setText(exam.getExamDate());
            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (exam.getPatiendId() != "") {
                        Intent intent = new Intent(getApplicationContext(), ResultDetails.class);
                        String[] localExamData = {
                                exam.getPatiendId(), exam.getExamDate(), exam.getFirstName(), exam.getLastName(),
                                exam.getSex(), exam.getBirthday(),
                                exam.getCell0(), exam.getCell1(), exam.getCell2(),  exam.getCell3(),
                                exam.getCell4(), exam.getCell5(), exam.getCell6(),  exam.getCell7(),
                                exam.getCell8(), exam.getCell9(), exam.getCell10(), exam.getCell11(),
                                exam.getTotalCells()
                        };
                        intent.putExtra("localExamData", localExamData);
                        startActivity(intent);
                    }
                }
            });

            return convertView;
        }
    }
}
