package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;

import model.Dokter;
import model.JadwalDokter;
import model.Klinik;
import model.RawatJalan;
import model.Spesialis;
import model.User;

public class DbHelper extends SQLiteOpenHelper {

    // Nama dan versi database
    public static final String DATABASE_NAME = "GoSehat.db";
    private static final int DATABASE_VERSION = 2;

    // Nama tabel USER
    public static final String TABLE_USER = "tb_user";
    public static final String id_user = "id";
    public static final String nama_lengkap = "nama_lengkap";
    public static final String email = "email";
    public static final String role = "role";
    public static final String username = "username";
    public static final String password = "password";


    // TABEL SPESIALIS
    public static final String TABLE_SPESIALIS = "tb_spesialis";
    public static final String id_spesialis = "id";
    public static final String nama_spesilis = "nama_spesilis";
    public static final String deskripsi_spesialis = "deskripsi_spesialis";

    // TABEL KLINIK
    public static final String TABLE_KLINIK = "tb_klinik";
    public static final String id_klinik = "id";
    public static final String nama_klinik = "nama_klinik";
    public static final String alamat_klinik = "alamat_klinik";

    // TABEL DOKTER
    public static final String TABLE_DOKTER = "tb_dokter";
    public static final String id_dokter = "id_dokter";
    public static final String nama_dokter = "nama_dokter";
    public static final String umur = "umur";
    public static final String alamat = "alamat";
    public static final String jenis_kelamin = "jenis_kelamin";
    public static final String id_sps = "id_sps";
    public static final String id_klinikk = "id_klinikk";

    // TABEL JADWAL
    public static final String TABLE_JADWAL = "tb_jadwal";
    public static final String id_jadwal = "id_jadwal";
    public static final String PK_id_dokter = "id_dokter";
    public static final String hari = "hari";
    public static final String waktu_mulai = "waktu_mulai";
    public static final String waktu_berakhir = "waktu_berakhir";
    public static final String status_jadwal = "status";

    // TABEL RAWAT JALAN
    public static final String TABLE_RAWAT_JALAN = "tb_rawat_jalan";
    public static final String id_rawat_jalan = "id_rawat_jalan";
    public static final String fk_rawat_jalan_pasien = "id_pasien";
    public static final String fk_rawat_jalan_klinik = "id_klinik";
    public static final String fk_rawat_jalan_dokter = "id_dokter";
    public static final String fk_rawat_jalan_jadwal_dokter = "id_jadwal";
    public static final String tanggal = "tanggal";
    public static final String nomor_antrian = "nomor_antrian";
    public static final String status_rawat_jalan = "status";

    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " ("
            + id_user + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + nama_lengkap + " TEXT, "
            + role + " TEXT, "
            + email + " TEXT, "
            + username + " TEXT, "
            + password + " TEXT);";
    private static final String CREATE_TABLE_SPESIALIS = "CREATE TABLE " + TABLE_SPESIALIS + " ("
            + id_spesialis + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + nama_spesilis + " TEXT, "
            + deskripsi_spesialis + " TEXT);";

    private static final String CREATE_TABLE_KLINIK = "CREATE TABLE " + TABLE_KLINIK + " ("
            + id_klinik + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + nama_klinik + " TEXT, "
            + alamat_klinik + " TEXT);";
    private static final String CREATE_TABLE_DOKTER = "CREATE TABLE " + TABLE_DOKTER + " ("
            + id_dokter + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + nama_dokter + " TEXT, "
            + umur + " INTEGER, "
            + alamat + " TEXT, "
            + jenis_kelamin + " TEXT, "
            + id_sps + " INTEGER, "
            + id_klinikk + " INTEGER);";

    private static final String CREATE_TABLE_JADWAL = "CREATE TABLE " + TABLE_JADWAL + " ("
            + id_jadwal + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + tanggal + " TEXT, "
            + waktu_mulai + " TEXT, "
            + waktu_berakhir + " TEXT, "
            + status_jadwal + " TEXT);";

    private static final String CREATE_TABLE_RAWAT_JALAN = "CREATE TABLE " + TABLE_RAWAT_JALAN + " ("
            + id_rawat_jalan + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + fk_rawat_jalan_pasien + " INTEGER, "
            + fk_rawat_jalan_klinik + " INTEGER, "
            + fk_rawat_jalan_dokter + " INTEGER, "
            + fk_rawat_jalan_jadwal_dokter + " INTEGER, "
            + tanggal + " TEXT, "
            + nomor_antrian + " TEXT, "
            + status_rawat_jalan + " INTEGER);";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        AndroidThreeTen.init(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_SPESIALIS);
        db.execSQL(CREATE_TABLE_KLINIK);
        db.execSQL(CREATE_TABLE_DOKTER);
        db.execSQL(CREATE_TABLE_JADWAL);
        db.execSQL(CREATE_TABLE_RAWAT_JALAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPESIALIS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_KLINIK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOKTER);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_JADWAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RAWAT_JALAN);
        onCreate(db);
    }

    //------------------------------------------USER-----------------------------------------------
    public boolean insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nama_lengkap, user.getNama());
        values.put(role, user.getRole());
        values.put(email, user.getEmail());
        values.put(username, user.getUsername());
        values.put(password, user.getPassword());

        long result = db.insert(TABLE_USER, null, values);
        db.close();

        return result != -1;
    }

    public ArrayList<User> getUsernameEmail() {
        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_USER, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(email)));
                    user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(password)));
                    user.setRole(cursor.getString(cursor.getColumnIndexOrThrow(role))); // Set role

                    users.add(user);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return users;
    }

    //------------------------------------------SPESIALIS-----------------------------------------------

    public boolean insertSpesialis(Spesialis spesialis) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nama_spesilis", spesialis.getNama());
        values.put("deskripsi_spesialis", spesialis.getDeskripsi());

        long result = -1;
        try {
            result = db.insert(TABLE_SPESIALIS, null, values);
        } catch (Exception e) {
            Log.e("DbHelper", "Error inserting spesialis", e);
        } finally {
            db.close();
        }

        Log.d("DbHelper", "Insert result: " + result);

        return result != -1;
    }

    public ArrayList<Spesialis> getAllSpesialis() {
        ArrayList<Spesialis> spesialis = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SPESIALIS, null);

        if (cursor.moveToFirst()) {
            do {
                Spesialis spesialis1 = new Spesialis();
                spesialis1.setId(cursor.getInt(cursor.getColumnIndexOrThrow(id_spesialis)));
                spesialis1.setNama(cursor.getString(cursor.getColumnIndexOrThrow(nama_spesilis)));
                spesialis1.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(deskripsi_spesialis)));

                spesialis.add(spesialis1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return spesialis;
    }
    public void deleteSpesialis(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SPESIALIS, id_spesialis + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
    public boolean updateSpesialis(Spesialis spesialis) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nama_spesilis, spesialis.getNama());
        values.put(deskripsi_spesialis, spesialis.getDeskripsi());

        int result = db.update(TABLE_SPESIALIS, values, id_spesialis + " = ?", new String[]{String.valueOf(spesialis.getId())});
        db.close();

        return result > 0;
    }

    //------------------------------------------KLINIK-----------------------------------------------
    public boolean insertKlinik(Klinik klinik) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nama_klinik", klinik.getNamaklinik());
        values.put("alamat_klinik", klinik.getAlamat());

        long result = -1;
        try {
            result = db.insert(TABLE_KLINIK, null, values);
        } catch (Exception e) {
            Log.e("DbHelper", "Error inserting klinik", e);
        } finally {
            db.close();
        }

        Log.d("DbHelper", "Insert result: " + result);

        return result != -1;
    }
    public ArrayList<Klinik> getAllKlinik() {
        ArrayList<Klinik> kliniks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_KLINIK, null);

        if (cursor.moveToFirst()) {
            do {
                Klinik klinik = new Klinik();
                klinik.setId(cursor.getInt(cursor.getColumnIndexOrThrow(id_klinik)));
                klinik.setNamaklinik(cursor.getString(cursor.getColumnIndexOrThrow(nama_klinik)));
                klinik.setAlamat(cursor.getString(cursor.getColumnIndexOrThrow(alamat_klinik)));

                kliniks.add(klinik);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return kliniks;
    }
    public void deleteKlinik(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_KLINIK, id_klinik + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
    public boolean updateKlinik(Klinik klinik) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nama_klinik, klinik.getNamaklinik());
        values.put(alamat_klinik, klinik.getAlamat());

        int result = db.update(TABLE_KLINIK, values, id_klinik + " = ?", new String[]{String.valueOf(klinik.getId())});
        db.close();

        return result > 0;
    }

    //----------------------------------------DOKTER----------------------------------------------
    public boolean insertDokter(Dokter dokter) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nama_dokter, dokter.getNama_dokter());
        values.put(umur, dokter.getUmur());
        values.put(alamat, dokter.getAlamat());
        values.put(jenis_kelamin, dokter.getJenis_kelamin());
        values.put(id_sps, dokter.getId_spesialis());
        values.put(id_klinikk, dokter.getId_klinik());

        long result = db.insert(TABLE_DOKTER, null, values);
        db.close();

        return result != -1;
    }
    public ArrayList<Dokter> getAllDokter() {
        ArrayList<Dokter> dokters = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DOKTER, null);

        if (cursor.moveToFirst()) {
            do {
                Dokter dokter = new Dokter();
                dokter.setId_dokter(cursor.getInt(cursor.getColumnIndexOrThrow(id_dokter)));
                dokter.setNama_dokter(cursor.getString(cursor.getColumnIndexOrThrow(nama_dokter)));
                dokter.setUmur(cursor.getInt(cursor.getColumnIndexOrThrow(umur)));
                dokter.setAlamat(cursor.getString(cursor.getColumnIndexOrThrow(alamat)));
                dokter.setJenis_kelamin(cursor.getString(cursor.getColumnIndexOrThrow(jenis_kelamin)));
                dokter.setId_spesialis(cursor.getInt(cursor.getColumnIndexOrThrow(id_sps)));
                dokter.setId_klinik(cursor.getInt(cursor.getColumnIndexOrThrow(id_klinikk)));

                dokters.add(dokter);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dokters;
    }
    public void deleteDokter(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DOKTER, id_dokter + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
    public boolean updateDokter(Dokter dokter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nama_dokter, dokter.getNama_dokter());
        values.put(umur, dokter.getUmur());
        values.put(alamat, dokter.getAlamat());
        values.put(jenis_kelamin, dokter.getJenis_kelamin());
        values.put(id_sps, dokter.getId_spesialis());
        values.put(id_klinikk, dokter.getId_klinik());

        int result = db.update(TABLE_DOKTER, values, id_dokter + " = ?", new String[]{String.valueOf(dokter.getId_dokter())});
        db.close();

        return result > 0;
    }

    public Dokter getDokterById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Dokter dokter = null;
        String query = "SELECT * FROM " + TABLE_DOKTER + " WHERE " + id_dokter + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            dokter = new Dokter();
            dokter.setId_dokter(cursor.getInt(cursor.getColumnIndexOrThrow(id_dokter)));
            dokter.setNama_dokter(cursor.getString(cursor.getColumnIndexOrThrow(nama_dokter)));
            dokter.setUmur(cursor.getInt(cursor.getColumnIndexOrThrow(umur)));
            dokter.setAlamat(cursor.getString(cursor.getColumnIndexOrThrow(alamat)));
            dokter.setJenis_kelamin(cursor.getString(cursor.getColumnIndexOrThrow(jenis_kelamin)));
            dokter.setId_spesialis(cursor.getInt(cursor.getColumnIndexOrThrow(id_sps)));
        }
        cursor.close();
        return dokter;
    }

    public ArrayList<Dokter> getDokterByKlinik(int id) {
        ArrayList<Dokter> dokters = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_DOKTER + " WHERE " + id_klinikk + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            do {
                Dokter dokter = new Dokter();
                dokter.setId_dokter(cursor.getInt(cursor.getColumnIndexOrThrow(id_dokter)));
                dokter.setNama_dokter(cursor.getString(cursor.getColumnIndexOrThrow(nama_dokter)));
                dokter.setUmur(cursor.getInt(cursor.getColumnIndexOrThrow(umur)));
                dokter.setAlamat(cursor.getString(cursor.getColumnIndexOrThrow(alamat)));
                dokter.setJenis_kelamin(cursor.getString(cursor.getColumnIndexOrThrow(jenis_kelamin)));
                dokter.setId_spesialis(cursor.getInt(cursor.getColumnIndexOrThrow(id_sps)));
                dokter.setId_klinik(cursor.getInt(cursor.getColumnIndexOrThrow(id_klinikk)));
                dokters.add(dokter);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dokters;
    }

    public String getNamaSpesialisById(int idSpesialis) {
        SQLiteDatabase db = this.getReadableDatabase();
        String namaSpesialis = null;
        Cursor cursor = db.rawQuery("SELECT nama_spesilis FROM tb_spesialis WHERE id = ?", new String[]{String.valueOf(idSpesialis)});

        if (cursor.moveToFirst()) {
            namaSpesialis = cursor.getString(cursor.getColumnIndexOrThrow("nama_spesilis"));
        }
        cursor.close();
        return namaSpesialis;
    }
    public String getNamaklinikById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String namaKlinik = null;
        Cursor cursor = db.rawQuery("SELECT nama_klinik FROM tb_klinik WHERE id = ?", new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            namaKlinik = cursor.getString(cursor.getColumnIndexOrThrow("nama_klinik"));
        }
        cursor.close();
        return namaKlinik;
    }

    //----------------------------------------JADWAL DOKTER----------------------------------------------
    public boolean insertjadwal(JadwalDokter jadwalDokter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(hari, jadwalDokter.getHari());
        values.put(PK_id_dokter, jadwalDokter.getPK_id_dokter());
        values.put(hari, jadwalDokter.getHari());
        values.put(waktu_mulai, jadwalDokter.getWaktu_mulai());
        values.put(waktu_berakhir, jadwalDokter.getWaktu_berakhir());
        values.put(status_jadwal, jadwalDokter.getStatus());

        long result = db.insert(TABLE_JADWAL, null, values);
        db.close();

        return result != -1;
    }

    public ArrayList<JadwalDokter> getAllJadwal() {
        ArrayList<JadwalDokter> jadwalDokters = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_JADWAL + " WHERE " + status_jadwal + " = 1", null);

        if (cursor.moveToFirst()) {
            do {
                JadwalDokter jadwalDokter = new JadwalDokter();
                jadwalDokter.setId_jadwal(cursor.getInt(cursor.getColumnIndexOrThrow(id_jadwal)));
                jadwalDokter.setPK_id_dokter(cursor.getInt(cursor.getColumnIndexOrThrow(PK_id_dokter)));
                jadwalDokter.setHari(cursor.getString(cursor.getColumnIndexOrThrow(hari)));
                jadwalDokter.setWaktu_mulai(cursor.getString(cursor.getColumnIndexOrThrow(waktu_mulai)));
                jadwalDokter.setWaktu_berakhir(cursor.getString(cursor.getColumnIndexOrThrow(waktu_berakhir)));
                jadwalDokter.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(status_jadwal)));

                jadwalDokters.add(jadwalDokter);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return jadwalDokters;
    }

    public JadwalDokter getJadwalById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        JadwalDokter jadwalDokter = null;
        String query = "SELECT * FROM " + TABLE_JADWAL + " WHERE " + id_jadwal + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            jadwalDokter = new JadwalDokter();
            jadwalDokter.setId_jadwal(cursor.getInt(cursor.getColumnIndexOrThrow(id_jadwal)));
            jadwalDokter.setPK_id_dokter(cursor.getInt(cursor.getColumnIndexOrThrow(PK_id_dokter)));
            jadwalDokter.setHari(cursor.getString(cursor.getColumnIndexOrThrow(hari)));
            jadwalDokter.setWaktu_mulai(cursor.getString(cursor.getColumnIndexOrThrow(waktu_mulai)));
            jadwalDokter.setWaktu_berakhir(cursor.getString(cursor.getColumnIndexOrThrow(waktu_berakhir)));
            jadwalDokter.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(status_jadwal)));
        }
        cursor.close();
        return jadwalDokter;
    }

    public ArrayList<JadwalDokter> getJadwalByDokter(int id) {
        ArrayList<JadwalDokter> jadwalDokters = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_JADWAL + " WHERE " + PK_id_dokter + " = ?" + " AND " + status_jadwal  + " = 1";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            do {
                JadwalDokter jadwalDokter = new JadwalDokter();
                jadwalDokter.setId_jadwal(cursor.getInt(cursor.getColumnIndexOrThrow(id_jadwal)));
                jadwalDokter.setPK_id_dokter(cursor.getInt(cursor.getColumnIndexOrThrow(PK_id_dokter)));
                jadwalDokter.setHari(cursor.getString(cursor.getColumnIndexOrThrow(hari)));
                jadwalDokter.setWaktu_mulai(cursor.getString(cursor.getColumnIndexOrThrow(waktu_mulai)));
                jadwalDokter.setWaktu_berakhir(cursor.getString(cursor.getColumnIndexOrThrow(waktu_berakhir)));
                jadwalDokter.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(status_jadwal)));

                jadwalDokters.add(jadwalDokter);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return jadwalDokters;
    }

    public boolean updateJadwal(JadwalDokter jadwalDokter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PK_id_dokter, jadwalDokter.getPK_id_dokter());
        values.put(hari, jadwalDokter.getHari());
        values.put(waktu_mulai, jadwalDokter.getWaktu_mulai());
        values.put(waktu_berakhir, jadwalDokter.getWaktu_berakhir());
        values.put(status_jadwal, jadwalDokter.getStatus());

        int result = db.update(TABLE_JADWAL, values, id_jadwal + " = ?", new String[]{String.valueOf(jadwalDokter.getId_jadwal())});
        db.close();

        return result > 0;
    }

    public boolean deleteJadwal(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(status_jadwal, 0);

        int result = db.update(TABLE_JADWAL, values, id_jadwal + " = ?", new String[]{String.valueOf(id)});
        db.close();

        return result > 0;
    }

    //----------------------------------------RAWAT JALAN----------------------------------------------
    public boolean insertRawatJalan(RawatJalan rawatJalan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(fk_rawat_jalan_pasien, rawatJalan.getId_pasien());
        values.put(fk_rawat_jalan_klinik, rawatJalan.getId_klinik());
        values.put(fk_rawat_jalan_dokter, rawatJalan.getId_dokter());
        values.put(fk_rawat_jalan_jadwal_dokter, rawatJalan.getId_jadwal());
        values.put(tanggal, rawatJalan.getTanggal());
        values.put(nomor_antrian, rawatJalan.getNomor_antrian());
        values.put(status_rawat_jalan, rawatJalan.getStatus());

        long result = db.insert(TABLE_RAWAT_JALAN, null, values);
        db.close();

        return result != -1;
    }

    public int getNomorUrut(int idDokter, int idJadwalDokter, String tanggalRawatJalan) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_RAWAT_JALAN + " WHERE " + fk_rawat_jalan_dokter + " = ? AND " + fk_rawat_jalan_jadwal_dokter + " = ? AND " + tanggal + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idDokter), String.valueOf(idJadwalDokter), tanggalRawatJalan});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }
}
