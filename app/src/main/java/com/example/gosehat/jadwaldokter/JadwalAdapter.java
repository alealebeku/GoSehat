package com.example.gosehat.jadwaldokter;

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
import com.example.gosehat.dokter.UpdateDokter;

import java.util.ArrayList;

import db.DbHelper;
import model.Dokter;
import model.JadwalDokter;

public class JadwalAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<JadwalDokter> jadwalDokterArrayList;
    private DbHelper dbHelper;

    public JadwalAdapter(Context context, ArrayList<JadwalDokter> jadwalDokterArrayList, DbHelper dbHelper) {
        this.context = context;
        this.jadwalDokterArrayList = jadwalDokterArrayList;
        this.dbHelper = dbHelper;
    }

    @Override
    public int getCount() {
        return jadwalDokterArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return jadwalDokterArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_jadwal, parent, false);
        }

        JadwalDokter jadwalDokter = jadwalDokterArrayList.get(position);

        TextView listdokter = convertView.findViewById(R.id.listdokter);
        TextView listhari = convertView.findViewById(R.id.listhari);
        TextView listwaktu = convertView.findViewById(R.id.listwaktu);

        Dokter dokter = dbHelper.getDokterById(jadwalDokter.getPK_id_dokter());
        listdokter.setText(dokter.getNama_dokter());

        String hari = transformasiHari(jadwalDokter.getHari());

        listhari.setText(hari);

        String formattedwaktu = jadwalDokter.getWaktu_mulai() + " - " + jadwalDokter.getWaktu_berakhir();
        listwaktu.setText(formattedwaktu);

        ImageButton updateButton = convertView.findViewById(R.id.updateButton);
        ImageButton deleteButton = convertView.findViewById(R.id.deleteButton);
        ImageButton detailButton = convertView.findViewById(R.id.detailButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateJadwal.class);
                intent.putExtra("id_jadwal", jadwalDokter.getId_jadwal());
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
                        dbHelper.deleteJadwal(jadwalDokter.getId_jadwal());
                        jadwalDokterArrayList.remove(position);
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

        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "detail", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    private String transformasiHari(String hari) {
        if ("senin,selasa,rabu,kamis,jumat,sabtu,minggu".equals(hari)) {
            return "setiap hari";
        }
        // Tambahkan spasi setelah setiap koma
        hari = hari.replaceAll(",", ", ");

        // Ubah tanda koma terakhir menjadi ' & '
        int lastCommaIndex = hari.lastIndexOf(",");
        if (lastCommaIndex != -1) {
            hari = new StringBuilder(hari).replace(lastCommaIndex, lastCommaIndex + 1, " &").toString();
        }
        return hari;
    }
}
