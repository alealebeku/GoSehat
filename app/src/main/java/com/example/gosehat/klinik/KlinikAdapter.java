package com.example.gosehat.klinik;

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
import model.Klinik;

public class KlinikAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Klinik> klinikArrayList;
    private DbHelper dbHelper;
    public KlinikAdapter(Context context, ArrayList<Klinik> klinikArrayList, DbHelper dbHelper) {
        this.context = context;
        this.klinikArrayList = klinikArrayList;
        this.dbHelper = dbHelper;
    }
    @Override
    public int getCount() {
        return klinikArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return klinikArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listklinik, parent, false);
        }

        Klinik klinik = klinikArrayList.get(position);

        TextView listName = convertView.findViewById(R.id.listNameKlinik);
        TextView listAlamat = convertView.findViewById(R.id.listAlamat);

        ImageButton updateButton = convertView.findViewById(R.id.updateButton);
        ImageButton deleteButton = convertView.findViewById(R.id.deleteButton);

        listName.setText(klinik.getNamaklinik());
        listAlamat.setText(klinik.getAlamat());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateKlinik.class);
                intent.putExtra("id_klinik", klinik.getId());
                intent.putExtra("nama_klinik", klinik.getNamaklinik());
                intent.putExtra("alamat_klinik", klinik.getAlamat());
                context.startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Konfirmasi Hapus");
                builder.setMessage("Anda yakin ingin menghapus data ini?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteKlinik(klinik.getId());
                        klinikArrayList.remove(position);
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
