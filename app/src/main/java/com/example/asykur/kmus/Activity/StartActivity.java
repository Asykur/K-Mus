package com.example.asykur.kmus.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.asykur.kmus.Database.DBKamusHelper;
import com.example.asykur.kmus.Pojo.DataKamus;
import com.example.asykur.kmus.R;
import com.example.asykur.kmus.Utils.AppPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity {

    @BindView (R.id.pgLoading) ProgressBar pgLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ButterKnife.bind(this);
        new LoadDataKamus().execute();
    }

    private class LoadDataKamus extends AsyncTask<Void,Integer,Void>{

        final String TAG = LoadDataKamus.class.getSimpleName();
        DBKamusHelper kamusHelper;
        AppPreference appPreference;

        @Override
        protected void onPreExecute() {
            kamusHelper = new DBKamusHelper(StartActivity.this);
            appPreference = new AppPreference(StartActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firstRun = appPreference.getFirstRun();
            if (firstRun){
                ArrayList<DataKamus> dataKamusEN = preloadRawEN();
                ArrayList<DataKamus> dataKamusIDN = preloadRawIDN();
                kamusHelper.open();
                kamusHelper.beginTransaction();
                try {
                    for (DataKamus kamusEN : dataKamusEN){
                        kamusHelper.insertTransactionEN(kamusEN);
                    }
                    for (DataKamus kamusIDN : dataKamusIDN){
                        kamusHelper.insertTransactionIDN(kamusIDN);
                    }
                }
                catch (Exception e){
                    Log.e(TAG, "doInBackground: Exception");
                }
                kamusHelper.setTransactionSuccess();
                kamusHelper.endTransaction();
                kamusHelper.close();
                appPreference.setFirstRun(false);
            }
            else {
                try {
                    synchronized (this){
                        this.wait(1000);
                    }
                }catch (Exception e){

                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(StartActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public ArrayList<DataKamus> preloadRawEN(){
        ArrayList<DataKamus> loadDataKamus = new ArrayList<>();
        String line ;
        BufferedReader reader;
        try {
            Resources resources = getResources();
            InputStream inputStream = resources.openRawResource(R.raw.english_indonesia);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitStr = line.split("\t");
                DataKamus dataKamus;
                dataKamus = new DataKamus(splitStr[0],splitStr[1]);
                loadDataKamus.add(dataKamus);
                count++;
            }while (line!=null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return loadDataKamus;
    }

    public ArrayList<DataKamus> preloadRawIDN(){
        ArrayList<DataKamus> loadDataKamus = new ArrayList<>();
        String line ;
        BufferedReader reader;
        try {
            Resources resources = getResources();
            InputStream inputStream = resources.openRawResource(R.raw.indonesia_english);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitStr = line.split("\t");
                DataKamus dataKamus;
                dataKamus = new DataKamus(splitStr[0],splitStr[1]);
                loadDataKamus.add(dataKamus);
                count++;
            }while (line!=null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return loadDataKamus;
    }
}
