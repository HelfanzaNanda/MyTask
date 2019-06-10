package com.elfan.mytask.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elfan.mytask.DetailNoteActivity;
import com.elfan.mytask.R;
import com.elfan.mytask.model.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    private Context context;
    private List<NoteModel> data = new ArrayList<>();

    public NoteAdapter(Context context, List<NoteModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvJudul.setText(data.get(i).getJudul());
        myViewHolder.tvJumlah.setText("Rp."+data.get(i).getJumlahhutang());
        myViewHolder.tvTanggal.setText(data.get(i).getTanggal());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailNoteActivity.class);
                intent.putExtra(DetailNoteActivity.KEY_ID, data.get(i).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvJumlah, tvTanggal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tvJudul);
            tvJumlah = itemView.findViewById(R.id.tvJumlah);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
        }
    }
}
