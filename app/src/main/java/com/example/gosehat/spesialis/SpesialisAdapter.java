package com.example.gosehat.spesialis;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.gosehat.R;

import java.util.ArrayList;

import db.DbHelper;
import model.Spesialis;

public class SpesialisAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Spesialis> spesialisList;
    private DbHelper dbHelper;

    public SpesialisAdapter(Context context, ArrayList<Spesialis> spesialisList, DbHelper dbHelper) {
        this.context = context;
        this.spesialisList = spesialisList;
        this.dbHelper = dbHelper;
    }

    @Override
    public int getCount() {
        return spesialisList.size();
    }

    @Override
    public Object getItem(int position) {
        return spesialisList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_spesialis, parent, false);
        }

        Spesialis spesialis = spesialisList.get(position);

        TextView listName = convertView.findViewById(R.id.listName);
        TextView listDescription = convertView.findViewById(R.id.listDescription);
        ImageButton updateButton = convertView.findViewById(R.id.updateButton);
        ImageButton deleteButton = convertView.findViewById(R.id.deleteButton);

        listName.setText(spesialis.getNama());
        listDescription.setText(spesialis.getDeskripsi());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateSpesialis.class);
                intent.putExtra("id_spesialis", spesialis.getId());
                intent.putExtra("nama_spesialis", spesialis.getNama());
                intent.putExtra("deskripsi_spesialis", spesialis.getDeskripsi());
                context.startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Konfirmasi Hapus");
                builder.setMessage("Anda yakin ingin menghapus spesialis ini?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteSpesialis(spesialis.getId());
                        spesialisList.remove(position);
                        notifyDataSetChanged();

                        Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing, dismiss the dialog
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        return convertView;
    }
}
