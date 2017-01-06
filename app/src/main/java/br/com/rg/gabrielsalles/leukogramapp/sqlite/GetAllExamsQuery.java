package br.com.rg.gabrielsalles.leukogramapp.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;

import br.com.rg.gabrielsalles.leukogramapp.otherFiles.Exam;

/**
 * Created by gabriel on 04/11/16.
 */

public class GetAllExamsQuery extends AsyncTask<String, Void, Exam[]> {

    private Context mContext;

    public GetAllExamsQuery(Context context) {
        mContext = context;
    }


    @Override
    protected Exam[] doInBackground(String... params) {

        SQLiteDatabase sqLiteDatabase = mContext.openOrCreateDatabase("bloodExamDB", mContext.MODE_PRIVATE, null);
        String query;
        query = "create table if not exists exam (exam_patientId varchar, exam_date varchar, cell0 integer, cell1 integer, cell2 integer, cell3 integer, cell4 integer, cell5 integer, cell6 integer, cell7 integer, cell8 integer, cell9 integer, cell10 integer, cell11 integer, totalCells integer);";
        sqLiteDatabase.execSQL(query);
        query = "SELECT exam_patientId, exam_date, patient_firstName, patient_lastName, patient_sex, patient_birth, " +
                "cell0, cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, totalCells " +
                "FROM exam, patient " +
                "WHERE exam_patientId = patient_id";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            ArrayList<Exam> exams = new ArrayList<>();
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                exams.add(new Exam(
                        cursor.getString(0),  cursor.getString(1),  cursor.getString(2),  cursor.getString(3),
                        cursor.getString(4),  cursor.getString(5),  cursor.getString(6),  cursor.getString(7),
                        cursor.getString(8),  cursor.getString(9),  cursor.getString(10), cursor.getString(11),
                        cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                        cursor.getString(16), cursor.getString(17), cursor.getString(18)
                ));
            }
            Exam[] returnData = exams.toArray(new Exam[exams.size()]);
            cursor.close();
            return returnData;
        } else {
            cursor.close();
            Exam[] returnData = {new Exam("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")};
            return returnData;
        }
    }
}
