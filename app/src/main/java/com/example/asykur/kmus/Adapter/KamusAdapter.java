package com.example.asykur.kmus.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asykur.kmus.Pojo.DataKamus;
import com.example.asykur.kmus.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class KamusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<DataKamus> dataKamuses = new ArrayList<>();


    public KamusAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kamus,parent,false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final DataKamus datas = dataKamuses.get(position);
        VHolder vh = (VHolder) holder;
        vh.tvkata.setText(datas.getKata());
        vh.tvArti.setText(datas.getArti());

        vh.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view1 = inflater.inflate(R.layout.popup,null);
                builder.setView(view1);
                builder.setCancelable(true);
                TextView tvWord = view1.findViewById(R.id.tvKata);
                TextView tvResult = view1.findViewById(R.id.tvResult);
                tvWord.setText(datas.getKata());
                tvResult.setText(datas.getArti());
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    public void addItem(ArrayList<DataKamus> data){
        this.dataKamuses = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataKamuses.size();
    }

    public class VHolder extends RecyclerView.ViewHolder{

        private TextView tvkata;
        private TextView tvArti;
        private ConstraintLayout cl;

        public VHolder(View itemView) {
            super(itemView);
            tvkata = itemView.findViewById(R.id.tvHasilKata);
            tvArti = itemView.findViewById(R.id.tvHasilArti);
            cl = itemView.findViewById(R.id.clItem);
        }
    }
}
