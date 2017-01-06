package br.com.rg.gabrielsalles.leukogramapp.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by gabriel on 23/10/16.
 */

public class SqliteQueries extends AsyncTask <String, Void, String[]> {

    private Context mContext;
    private final int CREATE_TABLES         = 0;
    private final int REQUEST_USER_DATA     = 1;
    private final int UPDATE_USER_DATA      = 2;
    private final int SET_PATIENT_DATA      = 3;
    private final int DELETE_PATIENT_DATA   = 4;
    private final int REQUEST_PATIENTS_IDS  = 5;
    private final int REQUEST_PATIENT_BY_ID = 6;
    private final int SAVE_EXAM_DATA        = 7;
    private final int DELETE_EXAMS          = 8;
    private int mOption;

    public SqliteQueries (Context context, int option) {
        mContext = context;
        mOption = option;
    }

    @Override
    protected String[] doInBackground(String... params) {

        // Will contain the raw JSON response as a string.

        switch (mOption) {
            case CREATE_TABLES:
                createTables();
                break;
            case REQUEST_USER_DATA:
                return requestUserData();
            case UPDATE_USER_DATA:
                String[] userDataArray = {params[0], params[1], params[2]};
                updateUserData(userDataArray);
                break;
            case SET_PATIENT_DATA:
                String[] patientDataArray = {params[0], params[1], params[2], params[3], params[4]};
                setPatientData(patientDataArray);
                break;
            case DELETE_PATIENT_DATA:
                break;
            case REQUEST_PATIENTS_IDS:
                return requestPatientsIds();
            case REQUEST_PATIENT_BY_ID:
                return requestPatientById(params[0]);
            case SAVE_EXAM_DATA:
                String[] examDataArray = {
                        params[0], params[1], params[2], params[3], params[4],
                        params[5], params[6], params[7], params[8], params[9],
                        params[10], params[11], params[12], params[13]
                };
                saveExamData(examDataArray);
                break;
            case DELETE_EXAMS:
                deleteExams(params[0]);
                break;
        }
        return null;
    }

    private void createTables() {
        SQLiteDatabase sqLiteDatabase = mContext.openOrCreateDatabase("bloodExamDB", mContext.MODE_PRIVATE, null);
        String query;

        query = "create table if not exists user (user_firstName varchar, user_lastName varchar, user_id varchar);";
        sqLiteDatabase.execSQL(query);
        query = "create table if not exists patient (patient_firstName varchar, patient_lastName varchar, patient_id varchar, patient_sex varchar, patient_birth varchar);";
        sqLiteDatabase.execSQL(query);
        query = "create table if not exists exam (exam_patientId varchar, exam_date varchar, cell0 integer, cell1 integer, cell2 integer, cell3 integer, cell4 integer, cell5 integer, cell6 integer, cell7 integer, cell8 integer, cell9 integer, cell10 integer, cell11 integer, totalCells integer);";
        sqLiteDatabase.execSQL(query);
    }

    private String[] requestUserData() {

        SQLiteDatabase sqLiteDatabase = mContext.openOrCreateDatabase("bloodExamDB", mContext.MODE_PRIVATE, null);
        String query;

        query = "create table if not exists user (user_firstName varchar, user_lastName varchar, user_id varchar);";
        sqLiteDatabase.execSQL(query);
        query = "SELECT * from user";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            String[] returnData = {cursor.getString(0), cursor.getString(1), cursor.getString(2)};
            cursor.close();
            return returnData;
        } else {
            String[] returnData = {"", "", ""};
            cursor.close();
            return returnData;
        }
    }

    private void updateUserData(String[] userData) {

        SQLiteDatabase sqLiteDatabase = mContext.openOrCreateDatabase("bloodExamDB", mContext.MODE_PRIVATE, null);
        String query;

        query = "create table if not exists user (user_firstName varchar, user_lastName varchar, user_id varchar);";
        sqLiteDatabase.execSQL(query);
        query = "INSERT OR REPLACE INTO user (rowid, user_firstName, user_lastName, user_id) VALUES (1, '" + userData[0] + "', '" + userData[1] + "', '" + userData[2] + "');";
        sqLiteDatabase.execSQL(query);
    }

    private void setPatientData(String[] patientData) {

        SQLiteDatabase sqLiteDatabase = mContext.openOrCreateDatabase("bloodExamDB", mContext.MODE_PRIVATE, null);
        String query;

        query = "create table if not exists patient (patient_firstName varchar, patient_lastName varchar, patient_id varchar, patient_sex varchar, patient_birth varchar);";
        sqLiteDatabase.execSQL(query);
        query = "INSERT OR REPLACE INTO patient (patient_firstName, patient_lastName, patient_id, patient_sex, patient_birth) VALUES ('" + patientData[0] + "', '" + patientData[1] + "', '" + patientData[2] + "', '" + patientData[3] + "', '" + patientData[4] + "');";
        sqLiteDatabase.execSQL(query);
    }

    private String[] requestPatientsIds() {
        SQLiteDatabase sqLiteDatabase = mContext.openOrCreateDatabase("bloodExamDB", mContext.MODE_PRIVATE, null);
        String query;
        query = "create table if not exists patient (patient_firstName varchar, patient_lastName varchar, patient_id varchar, patient_sex varchar, patient_birth varchar);";
        sqLiteDatabase.execSQL(query);
        query = "SELECT patient_id from patient ORDER BY patient_id";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            ArrayList<String> patientsIds = new ArrayList<>();

            for (int i = 0; i < cursor.getCount(); i++) {
                patientsIds.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
            return patientsIds.toArray(new String[patientsIds.size()]);
        } else {
            cursor.close();
            String[] returnData = {""};
            return returnData;
        }
    }

    private String[] requestPatientById(String patientId) {
        SQLiteDatabase sqLiteDatabase = mContext.openOrCreateDatabase("bloodExamDB", mContext.MODE_PRIVATE, null);
        String query;
        query = "SELECT patient_firstName, patient_lastName, patient_sex, patient_birth varchar from patient where patient_id = '" + patientId + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            ArrayList<String> patientsIds = new ArrayList<>();

            String[] returnData = {cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)};
            cursor.close();
            return returnData;
        } else {
            cursor.close();
            String[] returnData = {""};
            return returnData;
        }
    }

    private void saveExamData(String[] examDataArray) {
        SQLiteDatabase sqLiteDatabase = mContext.openOrCreateDatabase("bloodExamDB", mContext.MODE_PRIVATE, null);
        String query;
        String date = getDateInString();
        String patientId = examDataArray[0];
        ArrayList<String> cellsAmount = new ArrayList<>();
        for (int i = 1; i < examDataArray.length; i++){
            cellsAmount.add(examDataArray[i]);
        }

        query = "create table if not exists exam (exam_patientId varchar, exam_date varchar, cell0 integer, cell1 integer, cell2 integer, cell3 integer, cell4 integer, cell5 integer, cell6 integer, cell7 integer, cell8 integer, cell9 integer, cell10 integer, cell11 integer, totalCells integer);";
        sqLiteDatabase.execSQL(query);
        query = "INSERT OR REPLACE INTO exam (exam_patientId, exam_date, cell0, cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, totalCells) " +
                "VALUES ('" + patientId + "', '" + date + "'";
        for (String cellAmount: cellsAmount) {
            query = query + ", " + cellAmount;
        }
        query = query + ");";
        sqLiteDatabase.execSQL(query);
    }

    private void deleteExams(String whereClause){
        String query = "DELETE FROM exam where " + whereClause + ";";
        SQLiteDatabase sqLiteDatabase = mContext.openOrCreateDatabase("bloodExamDB", mContext.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL(query);
    }

    private String getDateInString() {
        Calendar calendar = Calendar.getInstance();
        String date = Integer.toString(calendar.get(Calendar.YEAR));

        String dateAux = Integer.toString(calendar.get(Calendar.MONTH));
        if (dateAux.length() == 1) dateAux = "0" + dateAux;
        date += "-" + dateAux;

        dateAux = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        if (dateAux.length() == 1) dateAux = "0" + dateAux;
        date += "-" + dateAux;

        dateAux = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
        if (dateAux.length() == 1) dateAux = "0" + dateAux;
        date += " " + dateAux;

        dateAux = Integer.toString(calendar.get(Calendar.MINUTE));
        if (dateAux.length() == 1) dateAux = "0" + dateAux;
        date += ":" + dateAux;

        dateAux = Integer.toString(calendar.get(Calendar.SECOND));
        if (dateAux.length() == 1) dateAux = "0" + dateAux;
        date += ":" + dateAux;

        return date;
    }

}
