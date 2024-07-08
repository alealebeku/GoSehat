package com.example.gosehat.dokter;

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
import model.Dokter;

public class DokterAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Dokter> DokterArrayList;
    private DbHelper dbHelper;
    public DokterAdapter(Context context, ArrayList<Dokter> DokterArrayList, DbHelper dbHelper) {
        this.context = context;
        this.DokterArrayList = DokterArrayList;
        this.dbHelper = dbHelper;
    }
    @Override
    public int getCount() {
        return DokterArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return DokterArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_dokter, parent, false);
        }

        Dokter dokter = DokterArrayList.get(position);

        TextView listName = convertView.findViewById(R.id.listnamadokter);
        TextView  sp = convertView.findViewById(R.id.listSpesialisDokter);
        TextView  klinik = convertView.findViewById(R.id.listKlnikDokter);


        ImageButton updateButton = convertView.findViewById(R.id.updateButton);
        ImageButton deleteButton = convertView.findViewById(R.id.deleteButton);


        listName.setText(dokter.getNama_dokter());

        String namaSpesialis = dbHelper.getNamaSpesialisById(dokter.getId_spesialis());
        sp.setText(namaSpesialis);

        String namaklnik = dbHelper.getNamaklinikById(dokter.getId_klinik());
        klinik.setText(namaklnik);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateDokter.class);
                intent.putExtra("id_dokter", dokter.getId_dokter());
                intent.putExtra("nama_dokter", dokter.getNama_dokter());
                intent.putExtra("alamat_dokter", dokter.getAlamat());
                intent.putExtra("jenis_kelamin", dokter.getJenis_kelamin());
                intent.putExtra("id_spessialis", dokter.getId_spesialis());
                intent.putExtra("id_klinik", dokter.getId_klinik());
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
                        dbHelper.deleteDokter(dokter.getId_dokter());
                        DokterArrayList.remove(position);
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
