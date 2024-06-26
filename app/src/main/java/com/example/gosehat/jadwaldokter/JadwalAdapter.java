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

        TextView listtanggal = convertView.findViewById(R.id.listtanggal);
        TextView  listmulai = convertView.findViewById(R.id.listwaktumulai);
        TextView  listakhir = convertView.findViewById(R.id.listwaktuberakhir);

        ImageButton updateButton = convertView.findViewById(R.id.updateButton);
        ImageButton deleteButton = convertView.findViewById(R.id.deleteButton);
        ImageButton detailButton = convertView.findViewById(R.id.detailButton);

        String formattedTanggal = jadwalDokter.getTanggal().toString();
        String formattedWaktuMulai = jadwalDokter.getWaktu_mulai().toString();
        String formattedWaktuBerakhir = jadwalDokter.getWaktu_berakhir().toString();

        listtanggal.setText(formattedTanggal);
        listmulai.setText(formattedWaktuMulai);
        listakhir.setText(formattedWaktuBerakhir);


//        updateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, UpdateDokter.class);
//                intent.putExtra("id_dokter", dokter.getId_dokter());
//                intent.putExtra("nama_dokter", dokter.getNama_dokter());
//                intent.putExtra("alamat_dokter", dokter.getAlamat());
//                intent.putExtra("jenis_kelamin", dokter.getJenis_kelamin());
//                intent.putExtra("id_sps", dokter.getId_spesialis());
//                intent.putExtra("id_klinikk", dokter.getId_klinik());
//                context.startActivity(intent);
//            }
//        });
//
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Konfirmasi Hapus");
//                builder.setMessage("Anda yakin ingin menghapus data ini?");
//                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dbHelper.deleteDokter(dokter.getId_dokter());
//                        DokterArrayList.remove(position);
//                        notifyDataSetChanged();
//
//                        Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Do nothing, dismiss the dialog
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });

        return convertView;
    }
}
