package com.example.notemaster.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.notemaster.Model.Ghichu;
import com.example.notemaster.Model.Ghichu_Thumuc;
import com.example.notemaster.Model.HinhanhGhichu;
import com.example.notemaster.Model.Nhacnho;
import com.example.notemaster.Model.Thumuc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DatabaseNoteMaster.db";
    private static final int DATABASE_VERSION = 1;
    private final Context context;
    private SQLiteDatabase Database;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Không cần thực hiện bất kỳ thao tác tạo bảng nào ở đây vì chúng ta đã có tệp .db sẵn
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý khi có sự thay đổi phiên bản cơ sở dữ liệu (nếu cần)
    }

    public void initDatabase() {
        try {
            this.copyDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Database = this.getWritableDatabase();
    }

    public void copyDatabase() throws IOException {
        InputStream inputStream = context.getAssets().open(DATABASE_NAME);
        String outFileName = context.getDatabasePath(DATABASE_NAME).getPath();
        OutputStream outputStream = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public ArrayList<Thumuc> GetDataFolder() {
        ArrayList<Thumuc> arrFolder = new ArrayList<>();
        Database = this.getReadableDatabase();
        String query = "SELECT * FROM Thumuc WHERE ID_Thumuc > 1";
        Cursor cursor = Database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int IDThumuc = cursor.getInt(0);
                String Tenthumuc = cursor.getString(1);
                int IDNguoidung = cursor.getInt(2);

                Thumuc pl = new Thumuc(IDThumuc, Tenthumuc, IDNguoidung);
                arrFolder.add(pl);
            } while (cursor.moveToNext());
        }
        return arrFolder;
    }

    public String GetNameFolderByID(int id) {
        String nameFolder = null;
        Database = this.getReadableDatabase();
        String query = "SELECT Tenthumuc FROM Thumuc WHERE ID_Thumuc = " + id;
        Cursor cursor = Database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                nameFolder = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        return nameFolder;
    }

    public void InsertTableFolder(String nameFolder, Integer idNguoidung) {
        ContentValues values = new ContentValues();
        values.put("TenThumuc", nameFolder);
        values.put("ID_Nguoidung", (Integer) idNguoidung);
        Database.insert("Thumuc", null, values);
        Database.close();
    }

    public void DeleteTableFolder(int id) {
        Database = this.getWritableDatabase();
        String deleteGhichuQuery = "DELETE FROM Ghichu WHERE ID_Thumuc = " + id;
        String deleteThumucQuery = "DELETE FROM Thumuc WHERE ID_Thumuc = " + id;
        Database.execSQL(deleteGhichuQuery);
        Database.execSQL(deleteThumucQuery);
        Database.close();
    }

    public void RenameTableFolder(int id, String nameFolderNew) {
        Database = this.getWritableDatabase();
        String query = "UPDATE Thumuc SET Tenthumuc = ? WHERE ID_Thumuc = ?";
        Database.execSQL(query, new String[]{nameFolderNew, String.valueOf(id)});
        Database.close();
    }

    public void InsertTableNote(String tieuDe, String noiDung, Integer idThumuc, String timeCreate) {
        Database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Tieude", tieuDe);
        values.put("Noidung", noiDung);
        values.put("ID_Thumuc", idThumuc);
        values.put("Timecreate", timeCreate);
        Database.insert("Ghichu", null, values);
        Database.close();
    }

    public void InsertTemporaryTableNote(Integer idGhichu) {
        Database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID_Ghichu", idGhichu);
        Database.insert("Ghichu", null, values);
        Database.close();
    }

    public ArrayList<Ghichu> GetDataGhichu() {
        ArrayList<Ghichu> arrGhichu = new ArrayList<>();
        Database = this.getReadableDatabase();
        String query = "SELECT * FROM Ghichu";
        Cursor cursor = Database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int IDGhichu = cursor.getInt(0);
                String Tieude = cursor.getString(1);
                String Noidung = cursor.getString(2);
                int IDThumuc = cursor.getInt(3);
                String Timecreate = cursor.getString(4);
                String Matkhau = cursor.getString(5);

                Ghichu gc = new Ghichu(IDGhichu, Tieude, Noidung, IDThumuc, Timecreate, Matkhau);
                arrGhichu.add(gc);
            } while (cursor.moveToNext());
        }
        return arrGhichu;
    }

    public ArrayList<Ghichu> GetDataNoteByID(int id) {
        ArrayList<Ghichu> arrNote = new ArrayList<>();
        Database = this.getReadableDatabase();
        String query = "SELECT * FROM Ghichu WHERE ID_Thumuc = " + id;
        Cursor cursor = Database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int IDGhichu = cursor.getInt(0);
                String Tieude = cursor.getString(1);
                String Noidung = cursor.getString(2);
                int IDThumuc = cursor.getInt(3);
                String Tgian = cursor.getString(4);
                String Matkhau = cursor.getString(5);

                Ghichu gc = new Ghichu(IDGhichu, Tieude, Noidung, IDThumuc, Tgian, Matkhau);
                arrNote.add(gc);
            } while (cursor.moveToNext());
        }
        return arrNote;
    }

    public ArrayList<Ghichu_Thumuc> GetDataNoteByFolder() {
        ArrayList<Ghichu_Thumuc> arr = new ArrayList<>();
        Database = this.getReadableDatabase();
        String query = "SELECT Ghichu.ID_Ghichu, Ghichu.Tieude, Ghichu.Noidung, Thumuc.ID_Thumuc, Thumuc.Tenthumuc, Ghichu.Timecreate, Ghichu.MkKhoa FROM Thumuc, Ghichu WHERE Ghichu.ID_Thumuc = Thumuc.ID_Thumuc ORDER BY Thumuc.ID_Thumuc ASC;";
        Cursor cursor = Database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int IDGhichu = cursor.getInt(0);
                String Tieude = cursor.getString(1);
                String Noidung = cursor.getString(2);
                int IDThumuc = cursor.getInt(3);
                String TenThumuc = cursor.getString(4);
                String Timecreate = cursor.getString(5);
                String Matkhau = cursor.getString(6);

                Ghichu_Thumuc gc = new Ghichu_Thumuc(IDGhichu, Tieude, Noidung, IDThumuc, TenThumuc, Timecreate, Matkhau);
                arr.add(gc);
            } while (cursor.moveToNext());
        }
        return arr;
    }

    public void DeleteTableNote(int id) {
        Database = this.getWritableDatabase();
        String deleteNoteQuery = "DELETE FROM Ghichu WHERE ID_Ghichu = " + id;
        Database.execSQL(deleteNoteQuery);
        String deleteImageQuery = "DELETE FROM HinhanhGhichu WHERE ID_Ghichu = " + id;
        Database.execSQL(deleteImageQuery);
        Database.close();
    }

    public void UpdateContentNote(int id, String ndgc) {
        Database = this.getWritableDatabase();
        String query = "UPDATE Ghichu SET Noidung = ? WHERE ID_Ghichu = ?";
        Database.execSQL(query, new String[]{ndgc, String.valueOf(id)});
        Database.close();
    }

    public ArrayList<HinhanhGhichu> GetDataHinhanh(int id) {
        ArrayList<HinhanhGhichu> arrHinhanh = new ArrayList<>();
        Database = this.getReadableDatabase();
        String query = "SELECT * FROM HinhanhGhichu WHERE ID_Ghichu = " + id;
        Cursor cursor = Database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int IDHinhanh = cursor.getInt(0);
                byte[] Hinhanh = cursor.getBlob(1);
                int IDGhichu = cursor.getInt(2);

                HinhanhGhichu ha = new HinhanhGhichu(IDHinhanh, Hinhanh, IDGhichu);
                arrHinhanh.add(ha);
            } while (cursor.moveToNext());
        }
        return arrHinhanh;
    }

    public void DeleteHinhanhByID(int id) {
        Database = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM HinhanhGhichu WHERE ID_Hinhanh = " + id;
        Database.execSQL(deleteQuery);
        Database.close();
    }

    public void InsertTableHinhanhChichu(byte[] hinhanh, int id) {
        Database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Hinhanh", hinhanh);
        values.put("ID_Ghichu", id);
        Database.insert("HinhanhGhichu", null, values);
        Database.close();
    }

    public void UpdateTableImageByIDNote(int id) {
        Database = this.getWritableDatabase();
        String query = "UPDATE HinhanhGhichu SET ID_Ghichu = ? WHERE ID_Ghichu = 0";
        Database.execSQL(query, new String[]{String.valueOf(id)});
        Database.close();
    }

    public ArrayList<Ghichu> SearchNote(String key) {
        ArrayList<Ghichu> arr = new ArrayList<>();
        Database = this.getReadableDatabase();
        String query = "SELECT * FROM Ghichu WHERE LOWER(Tieude) LIKE ?";
        String[] selectionArgs = new String[]{"%" + key.toLowerCase() + "%"};
        Cursor cursor = Database.rawQuery(query, selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                int IDGhichu = cursor.getInt(0);
                String Tieude = cursor.getString(1);
                String Noidung = cursor.getString(2);
                int IDThumuc = cursor.getInt(3);
                String Tgian = cursor.getString(4);
                String Matkhau = cursor.getString(5);

                Ghichu gc = new Ghichu(IDGhichu, Tieude, Noidung, IDThumuc, Tgian, Matkhau);
                arr.add(gc);
            } while (cursor.moveToNext());
        }
        return arr;
    }

    public void UpdateKeyNote(int id, String key) {
        Database = this.getReadableDatabase();
        String query = "UPDATE Ghichu SET MkKhoa = ? WHERE ID_Ghichu = ?";
        Database.execSQL(query, new String[]{key, String.valueOf(id)});
        Database.close();
    }

    public ArrayList<Ghichu_Thumuc> GetNoteByDate(String date) {
        ArrayList<Ghichu_Thumuc> arr = GetDataNoteByFolder();
        ArrayList<Ghichu_Thumuc> arrResult = new ArrayList<>();

        for (int i = 0; i < arr.size(); i++) {
            Ghichu_Thumuc ghichuThumuc = arr.get(i);
            String time = ghichuThumuc.getTimecreate();
            String[] timetoArr = time.split(" ");
            String datePart = timetoArr[0];
            if (datePart.equals(date)) {
                arrResult.add(ghichuThumuc);
            }
        }
        return arrResult;
    }


    public ArrayList<Nhacnho> GetDataAlarm() {
        ArrayList<Nhacnho> arr = new ArrayList<>();
        Database = this.getReadableDatabase();
        String query = "SELECT * FROM Alarm";
        Cursor cursor = Database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int ID_Alarm = cursor.getInt(0);
                String TimeAlarm = cursor.getString(1);
                String TitleAlarm = cursor.getString(2);
                int ID_Nguoidung = cursor.getInt(3);

                Nhacnho nn = new Nhacnho(ID_Alarm, TimeAlarm, TitleAlarm, ID_Nguoidung);
                arr.add(nn);
            } while (cursor.moveToNext());
        }
        return arr;
    }

    public void InsertTableAlarm(String TimeAlarm, String TitleAlarm) {
        Database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TimeAlarm", TimeAlarm);
        values.put("TitleAlarm", TitleAlarm);
        Database.insert("Alarm", null, values);
        Database.close();
    }

    public void DeleteTableAlarm(int id) {
        Database = this.getWritableDatabase();
        String deleteAlarmQuery = "DELETE FROM Alarm WHERE ID_Alarm = " + id;
        Database.execSQL(deleteAlarmQuery);
        Database.close();
    }

}
