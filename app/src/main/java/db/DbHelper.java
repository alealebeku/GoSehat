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
import model.RiwayatMedis;
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
    public static final String jenis_kelamin_user = "jenis_kelamin";
    public static final String tanggal_lahir = "tanggal_lahir";
    public static final String email = "email";
    public static final String alamat_user = "alamat";
    public static final String role = "role";
    public static final String password = "password";


    // TABEL SPESIALIS
    public static final String TABLE_SPESIALIS = "tb_spesialis";
    public static final String id_spesialis = "id";
    public static final String nama_spesilis = "nama_spesilis";
    public static final String deskripsi_spesialis = "deskripsi_spesialis";
    public static final String status_spesialis = "status";

    // TABEL KLINIK
    public static final String TABLE_KLINIK = "tb_klinik";
    public static final String id_klinik = "id";
    public static final String nama_klinik = "nama_klinik";
    public static final String alamat_klinik = "alamat_klinik";
    public static final String status_klinik = "status";

    // TABEL DOKTER
    public static final String TABLE_DOKTER = "tb_dokter";
    public static final String id_dokter = "id_dokter";
    public static final String nama_dokter = "nama_dokter";
    public static final String umur = "umur";
    public static final String alamat_dokter = "alamat";
    public static final String jenis_kelamin_dokter = "jenis_kelamin";
    public static final String fk_dokter_spesialis = "id_spesialis";
    public static final String fk_dokter_klinik = "id_klinik";
    public static final String status_dokter = "status";

    // TABEL JADWAL
    public static final String TABLE_JADWAL = "tb_jadwal";
    public static final String id_jadwal = "id_jadwal";
    public static final String fk_jadwal_dokter = "id_dokter";
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

    // TABEL RAWAT JALAN
    public static final String TABLE_RIWAYAT_MEDIS = "tb_riwayat_medis";
    public static final String id_riwayat_medis = "id_rriwayat_medis";
    public static final String fk_riwayat_medis_rawat_jalan = "id_rawat_jalan";
    public static final String fk_riwayat_medis_pasien = "id_pasien";
    public static final String diagnosa = "diagnosa";
    public static final String pengobatan = "pengobatan";
    public static final String perkembangan = "perkembangan";

    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " ("
            + id_user + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + nama_lengkap + " TEXT, "
            + jenis_kelamin_user + " TEXT, "
            + tanggal_lahir + " TEXT, "
            + role + " TEXT, "
            + email + " TEXT, "
            + alamat_user + " TEXT, "
            + password + " TEXT);";
    private static final String CREATE_TABLE_SPESIALIS = "CREATE TABLE " + TABLE_SPESIALIS + " ("
            + id_spesialis + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + nama_spesilis + " TEXT, "
            + deskripsi_spesialis + " TEXT, "
            + status_spesialis + " INTEGER);";

    private static final String CREATE_TABLE_KLINIK = "CREATE TABLE " + TABLE_KLINIK + " ("
            + id_klinik + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + nama_klinik + " TEXT, "
            + alamat_klinik + " TEXT, "
            + status_klinik + " INTEGER);";
    private static final String CREATE_TABLE_DOKTER = "CREATE TABLE " + TABLE_DOKTER + " ("
            + id_dokter + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + nama_dokter + " TEXT, "
            + umur + " INTEGER, "
            + alamat_dokter + " TEXT, "
            + jenis_kelamin_dokter + " TEXT, "
            + fk_dokter_spesialis + " INTEGER, "
            + fk_dokter_klinik + " INTEGER, "
            + status_dokter + " INTEGER);";

    private static final String CREATE_TABLE_JADWAL = "CREATE TABLE " + TABLE_JADWAL + " ("
            + id_jadwal + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + fk_jadwal_dokter + " INTEGER, "
            + hari + " TEXT, "
            + waktu_mulai + " TEXT, "
            + waktu_berakhir + " TEXT, "
            + status_jadwal + " INTEGER);";

    private static final String CREATE_TABLE_RAWAT_JALAN = "CREATE TABLE " + TABLE_RAWAT_JALAN + " ("
            + id_rawat_jalan + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + fk_rawat_jalan_pasien + " INTEGER, "
            + fk_rawat_jalan_klinik + " INTEGER, "
            + fk_rawat_jalan_dokter + " INTEGER, "
            + fk_rawat_jalan_jadwal_dokter + " INTEGER, "
            + tanggal + " TEXT, "
            + nomor_antrian + " TEXT, "
            + status_rawat_jalan + " INTEGER);";

    private static final String CREATE_TABLE_RIWAYAT_MEDIS = "CREATE TABLE " + TABLE_RIWAYAT_MEDIS + " ("
            + id_riwayat_medis + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + fk_riwayat_medis_rawat_jalan + " INTEGER, "
            + fk_riwayat_medis_pasien + " INTEGER, "
            + diagnosa + " TEXT, "
            + pengobatan + " TEXT, "
            + perkembangan + " TEXT);";

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
        db.execSQL(CREATE_TABLE_RIWAYAT_MEDIS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPESIALIS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_KLINIK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOKTER);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_JADWAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RAWAT_JALAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RIWAYAT_MEDIS);
        onCreate(db);
    }

    //------------------------------------------USER-----------------------------------------------
    public boolean insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nama_lengkap, user.getNama());
        values.put(role, user.getRole());
        values.put(email, user.getEmail());
        values.put(password, user.getPassword());

        long result = db.insert(TABLE_USER, null, values);
        db.close();

        return result != -1;
    }

    public User getUserByEmail(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + email + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userEmail)});
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(id_user)));
            user.setNama(cursor.getString(cursor.getColumnIndexOrThrow(nama_lengkap)));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(email)));
            user.setRole(cursor.getString(cursor.getColumnIndexOrThrow(role)));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(password)));
        }
        cursor.close();
        return user;
    }

    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + id_user + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(id_user)));
            user.setNama(cursor.getString(cursor.getColumnIndexOrThrow(nama_lengkap)));
            user.setJenisKelamin(cursor.getString(cursor.getColumnIndexOrThrow(jenis_kelamin_user)));
            user.setTanggalLahir(cursor.getString(cursor.getColumnIndexOrThrow(tanggal_lahir)));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(email)));
            user.setAlamat(cursor.getString(cursor.getColumnIndexOrThrow(alamat_user)));
            user.setRole(cursor.getString(cursor.getColumnIndexOrThrow(role)));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(password)));
        }
        cursor.close();
        return user;
    }

    public boolean updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nama_lengkap, user.getNama());
        values.put(jenis_kelamin_user, user.getJenisKelamin());
        values.put(tanggal_lahir, user.getTanggalLahir());
        values.put(alamat_user, user.getAlamat());

        int result = db.update(TABLE_USER, values, id_user + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();

        return result > 0;
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
        values.put("status", spesialis.getStatus());

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
    public boolean deleteSpesialis(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(status_spesialis, 0);

        int result = db.update(TABLE_SPESIALIS, values, id_spesialis + " = ?", new String[]{String.valueOf(id)});
        db.close();

        return result > 0;
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
        values.put("status", klinik.getStatus());

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
    public Klinik getKlinikById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Klinik klinik = null;
        String query = "SELECT * FROM " + TABLE_KLINIK + " WHERE " + id_klinik + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            klinik = new Klinik();
            klinik.setId(cursor.getInt(cursor.getColumnIndexOrThrow(id_klinik)));
            klinik.setNamaklinik(cursor.getString(cursor.getColumnIndexOrThrow(nama_klinik)));
            klinik.setAlamat(cursor.getString(cursor.getColumnIndexOrThrow(alamat_klinik)));
            klinik.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(status_klinik)));
        }
        cursor.close();
        return klinik;
    }
    public boolean deleteKlinik(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(status_klinik, 0);

        int result = db.update(TABLE_KLINIK, values, id_klinik + " = ?", new String[]{String.valueOf(id)});
        db.close();

        return result > 0;
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
        values.put(alamat_dokter, dokter.getAlamat());
        values.put(jenis_kelamin_dokter, dokter.getJenis_kelamin());
        values.put(fk_dokter_spesialis, dokter.getId_spesialis());
        values.put(fk_dokter_klinik, dokter.getId_klinik());
        values.put(status_dokter, dokter.getStatus());

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
                dokter.setAlamat(cursor.getString(cursor.getColumnIndexOrThrow(alamat_dokter)));
                dokter.setJenis_kelamin(cursor.getString(cursor.getColumnIndexOrThrow(jenis_kelamin_dokter)));
                dokter.setId_spesialis(cursor.getInt(cursor.getColumnIndexOrThrow(fk_dokter_spesialis)));
                dokter.setId_klinik(cursor.getInt(cursor.getColumnIndexOrThrow(fk_dokter_klinik)));

                dokters.add(dokter);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dokters;
    }
    public boolean deleteDokter(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(status_dokter, 0);

        int result = db.update(TABLE_DOKTER, values, id_dokter + " = ?", new String[]{String.valueOf(id)});
        db.close();

        return result > 0;
    }
    public boolean updateDokter(Dokter dokter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nama_dokter, dokter.getNama_dokter());
        values.put(umur, dokter.getUmur());
        values.put(alamat_dokter, dokter.getAlamat());
        values.put(jenis_kelamin_dokter, dokter.getJenis_kelamin());
        values.put(fk_dokter_spesialis, dokter.getId_spesialis());
        values.put(fk_dokter_klinik, dokter.getId_klinik());

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
            dokter.setAlamat(cursor.getString(cursor.getColumnIndexOrThrow(alamat_dokter)));
            dokter.setJenis_kelamin(cursor.getString(cursor.getColumnIndexOrThrow(jenis_kelamin_dokter)));
            dokter.setId_spesialis(cursor.getInt(cursor.getColumnIndexOrThrow(fk_dokter_spesialis)));
        }
        cursor.close();
        return dokter;
    }

    public ArrayList<Dokter> getDokterByKlinik(int id) {
        ArrayList<Dokter> dokters = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_DOKTER + " WHERE " + fk_dokter_klinik + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            do {
                Dokter dokter = new Dokter();
                dokter.setId_dokter(cursor.getInt(cursor.getColumnIndexOrThrow(id_dokter)));
                dokter.setNama_dokter(cursor.getString(cursor.getColumnIndexOrThrow(nama_dokter)));
                dokter.setUmur(cursor.getInt(cursor.getColumnIndexOrThrow(umur)));
                dokter.setAlamat(cursor.getString(cursor.getColumnIndexOrThrow(alamat_dokter)));
                dokter.setJenis_kelamin(cursor.getString(cursor.getColumnIndexOrThrow(jenis_kelamin_dokter)));
                dokter.setId_spesialis(cursor.getInt(cursor.getColumnIndexOrThrow(fk_dokter_spesialis)));
                dokter.setId_klinik(cursor.getInt(cursor.getColumnIndexOrThrow(fk_dokter_klinik)));
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
    public boolean insertJadwal(JadwalDokter jadwalDokter) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(hari, jadwalDokter.getHari());
        values.put(fk_jadwal_dokter, jadwalDokter.getPK_id_dokter());
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
                jadwalDokter.setPK_id_dokter(cursor.getInt(cursor.getColumnIndexOrThrow(fk_jadwal_dokter)));
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
            jadwalDokter.setPK_id_dokter(cursor.getInt(cursor.getColumnIndexOrThrow(fk_jadwal_dokter)));
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
        String query = "SELECT * FROM " + TABLE_JADWAL + " WHERE " + fk_jadwal_dokter + " = ?" + " AND " + status_jadwal  + " = 1";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            do {
                JadwalDokter jadwalDokter = new JadwalDokter();
                jadwalDokter.setId_jadwal(cursor.getInt(cursor.getColumnIndexOrThrow(id_jadwal)));
                jadwalDokter.setPK_id_dokter(cursor.getInt(cursor.getColumnIndexOrThrow(fk_jadwal_dokter)));
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
        values.put(fk_jadwal_dokter, jadwalDokter.getPK_id_dokter());
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

    public RawatJalan getRawatJalanById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        RawatJalan rawatJalan = null;
        String query = "SELECT * FROM " + TABLE_RAWAT_JALAN + " WHERE " + id_rawat_jalan + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            rawatJalan = new RawatJalan();
            rawatJalan.setId(cursor.getInt(cursor.getColumnIndexOrThrow(id_rawat_jalan)));
            rawatJalan.setId_pasien(cursor.getInt(cursor.getColumnIndexOrThrow(fk_rawat_jalan_pasien)));
            rawatJalan.setId_klinik(cursor.getInt(cursor.getColumnIndexOrThrow(fk_rawat_jalan_klinik)));
            rawatJalan.setId_dokter(cursor.getInt(cursor.getColumnIndexOrThrow(fk_rawat_jalan_dokter)));
            rawatJalan.setId_jadwal(cursor.getInt(cursor.getColumnIndexOrThrow(fk_rawat_jalan_jadwal_dokter)));
            rawatJalan.setNomor_antrian(cursor.getString(cursor.getColumnIndexOrThrow(nomor_antrian)));
            rawatJalan.setTanggal(cursor.getString(cursor.getColumnIndexOrThrow(tanggal)));
            rawatJalan.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(status_rawat_jalan)));
        }
        cursor.close();
        return rawatJalan;
    }

    public RawatJalan getRawatJalanByIdPasien(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        RawatJalan rawatJalan = null;
        String query = "SELECT * FROM " + TABLE_RAWAT_JALAN + " WHERE " + fk_rawat_jalan_pasien + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            rawatJalan = new RawatJalan();
            rawatJalan.setId(cursor.getInt(cursor.getColumnIndexOrThrow(id_rawat_jalan)));
            rawatJalan.setId_pasien(cursor.getInt(cursor.getColumnIndexOrThrow(fk_rawat_jalan_pasien)));
            rawatJalan.setId_klinik(cursor.getInt(cursor.getColumnIndexOrThrow(fk_rawat_jalan_klinik)));
            rawatJalan.setId_dokter(cursor.getInt(cursor.getColumnIndexOrThrow(fk_rawat_jalan_dokter)));
            rawatJalan.setId_jadwal(cursor.getInt(cursor.getColumnIndexOrThrow(fk_rawat_jalan_jadwal_dokter)));
            rawatJalan.setNomor_antrian(cursor.getString(cursor.getColumnIndexOrThrow(nomor_antrian)));
            rawatJalan.setTanggal(cursor.getString(cursor.getColumnIndexOrThrow(tanggal)));
            rawatJalan.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(status_rawat_jalan)));
        }
        cursor.close();
        return rawatJalan;
    }

    public int getJumlahAntrianBelumDilayani(int idDokter, int idJadwalDokter, String tanggalRawatJalan) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_RAWAT_JALAN +
                " WHERE " + fk_rawat_jalan_dokter + " = ?" +
                " AND " + fk_rawat_jalan_jadwal_dokter + " = ?" +
                " AND " + tanggal + " = ?" +
                " AND " + status_rawat_jalan + " = 1";

        Cursor cursor = db.rawQuery(query, new String[]{
                String.valueOf(idDokter),
                String.valueOf(idJadwalDokter),
                tanggalRawatJalan
        });

        int jumlahAntrian = 0;
        if (cursor.moveToFirst()) {
            jumlahAntrian = cursor.getInt(0);
        }
        cursor.close();
        return jumlahAntrian;
    }

    public boolean updateStatusRawatJalan(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(status_rawat_jalan, 0);

        int result = db.update(TABLE_RAWAT_JALAN, values, id_rawat_jalan + " = ?", new String[]{String.valueOf(id)});
        db.close();

        return result > 0;
    }

    //----------------------------------------RIWAYAT MEDIS----------------------------------------------
    public boolean insertRiwayatMedis(RiwayatMedis riwayatMedis) {
        updateStatusRawatJalan(riwayatMedis.getId_rawat_jalan());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(fk_riwayat_medis_rawat_jalan, riwayatMedis.getId_rawat_jalan());
        values.put(fk_riwayat_medis_pasien, riwayatMedis.getId_pasien());
        values.put(diagnosa, riwayatMedis.getDiagnosa());
        values.put(pengobatan, riwayatMedis.getPengobatan());
        values.put(perkembangan, riwayatMedis.getPerkembangan());

        long result = db.insert(TABLE_RIWAYAT_MEDIS, null, values);
        db.close();

        return result != -1;
    }

    public ArrayList<RiwayatMedis> getRiwayatMedisByIdPasien(int id) {
        ArrayList<RiwayatMedis> riwayatMedis = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_RIWAYAT_MEDIS + " WHERE " + fk_riwayat_medis_pasien + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            do {
                RiwayatMedis rMedis = new RiwayatMedis();
                rMedis.setId(cursor.getInt(cursor.getColumnIndexOrThrow(id_riwayat_medis)));
                rMedis.setId_rawat_jalan(cursor.getInt(cursor.getColumnIndexOrThrow(fk_riwayat_medis_rawat_jalan)));
                rMedis.setId_pasien(cursor.getInt(cursor.getColumnIndexOrThrow(fk_riwayat_medis_pasien)));
                rMedis.setDiagnosa(cursor.getString(cursor.getColumnIndexOrThrow(diagnosa)));
                rMedis.setPengobatan(cursor.getString(cursor.getColumnIndexOrThrow(pengobatan)));
                rMedis.setPerkembangan(cursor.getString(cursor.getColumnIndexOrThrow(perkembangan)));

                RawatJalan rawatJalan = getRawatJalanByIdPasien(cursor.getInt(cursor.getColumnIndexOrThrow(fk_riwayat_medis_pasien)));
                Klinik klinik = getKlinikById(rawatJalan.getId_klinik());
                Dokter dokter = getDokterById(rawatJalan.getId_dokter());

                rMedis.setTanggal(rawatJalan.getTanggal());
                rMedis.setNama_klinik(klinik.getNamaklinik());
                rMedis.setNama_dokter(dokter.getNama_dokter());

                riwayatMedis.add(rMedis);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return riwayatMedis;
    }
}
