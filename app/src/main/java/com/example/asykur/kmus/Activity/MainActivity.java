package com.example.asykur.kmus.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.asykur.kmus.Adapter.KamusAdapter;
import com.example.asykur.kmus.Database.DBKamusHelper;
import com.example.asykur.kmus.Pojo.DataKamus;
import com.example.asykur.kmus.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnSwap)
    ImageView btnSwap;
    @BindView(R.id.tvInput1)
    TextView tv1;
    @BindView(R.id.tvInput2)
    TextView tv2;
    @BindView(R.id.rvData)
    RecyclerView recyclerView;
    @BindView(R.id.svKamus)
    SearchView svKamus;
    @BindView(R.id.cardResult)
    CardView cardResult;

    private String en = "English";
    private String id = "Indonesia";
    private int count = 0;
    KamusAdapter adapter;
    DBKamusHelper dbKamusHelper;
    ArrayList result = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tv1.setText(en);
        tv2.setText(id);

        dbKamusHelper = new DBKamusHelper(this);
        adapter = new KamusAdapter(MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllKamusEN();

        btnSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSwap.animate().rotation(btnSwap.getRotation()-360).start();

                count ++;
                if (count % 2 == 0){
                    tv1.setText(en);
                    tv2.setText(id);
                    svKamus.setQuery("", false);
                    svKamus.clearFocus();

                }else {
                    tv1.setText(id);
                    tv2.setText(en);
                    svKamus.setQuery("", false);
                    svKamus.clearFocus();
                }
                if (tv1.getText().toString().equalsIgnoreCase(en)){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getAllKamusEN();
                        }
                    },300);

                }else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getAllKamusIDN();
                        }
                    },300);

                }
            }
        });

    }

    private void getAllKamusEN() {

        recyclerView.setAdapter(adapter);
        dbKamusHelper.open();
        final ArrayList<DataKamus> allDataKamusEN = dbKamusHelper.getAllDataEN();
        adapter.addItem(allDataKamusEN);
//        dbKamusHelper.close();

        svKamus.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                adapter.addItem(dbKamusHelper.getArtibyKataEN(s));
                if (adapter.getItemCount() == 0){
                    adapter.addItem(allDataKamusEN);
                }else {
                    cardResult.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }

    private void getAllKamusIDN() {

        recyclerView.setAdapter(adapter);
        dbKamusHelper.open();
        final ArrayList<DataKamus> allDataKamusIDN = dbKamusHelper.getAllDataIDN();
        adapter.addItem(allDataKamusIDN);
//        dbKamusHelper.close();

        svKamus.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                adapter.addItem(dbKamusHelper.getArtibyKataIDN(s));
                if (adapter.getItemCount() == 0){
                    adapter.addItem(allDataKamusIDN);
                }else {
                    cardResult.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.menu_about){
            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }
}
