package h04n6.sqlite_example;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoang on 2/14/2018.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";

    //phiên bản
    private static final int DATABASE_VERSION = 2;

    //tên cơ sở dữ liệu
    private static final String DATABASE_NAME = "Note_Manager";

    //tên bảng
    private static final String TABLE_NOTE = "Note";

    //tên cột
    private static final String COLUMN_NOTE_ID = "Note_Id";
    private static final String COLUMN_NOTE_TITLE = "Note_Title";
    private static final String COLUMN_NOTE_CONTENT = "Note_Content";

    //khởi tạo
    public MyDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //tạo bảng gồm 3 cột: ID, TITLE và CONTENT
    @Override
    public void onCreate(SQLiteDatabase db){
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");

        //script tạo bảng
        String script = "CREATE TABLE " + TABLE_NOTE + "("
                + COLUMN_NOTE_ID + " INTEGER PRIMARY KEY," + COLUMN_NOTE_TITLE + " TEXT,"
                + COLUMN_NOTE_CONTENT + " TEXT" + ");";

        //chạy lệnh tạo bảng
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");

        //hủy bảng cũ nếu nó đã tồn tại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);

        //và tạo lại
        onCreate(db);
    }

    //nếu trong bảng Note chưa có dữ liệu
    //chèn vào mặc định 2 bản ghi
    public void createDefaultNoteIfNeed(){
        int count = this.getNotesCount();
        if(count == 0){
            Note note1 = new Note("Example", "Testbay");
            Note note2 = new Note("WhyICantDoLikeThat", "Testbay");
            this.addNote(note1);
            this.addNote(note2);
        }
    }

    public void addNote(Note note){
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + note.getNoteTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITLE, note.getNoteTitle());
        values.put(COLUMN_NOTE_CONTENT, note.getNoteContent());

        //chèn 1 dòng dữ liệu vào bảng
        db.insert(TABLE_NOTE, null, values);

        //đóng kết nối database
        db.close();
    }

    public Note getNote(int id){
        Log.i(TAG, "MyDatabaseHelper.getNote ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        //viết câu lệnh truy vấn ntn là an toàn
        Cursor cursor = db.query(TABLE_NOTE, new String[] {COLUMN_NOTE_ID, COLUMN_NOTE_TITLE, COLUMN_NOTE_CONTENT},
                COLUMN_NOTE_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }

        Note note = new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

        return note;
    }

    public List<Note> getAllNotes(){
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... ");

        List<Note> noteList = new ArrayList<Note>();

        String selectQuery = "SELECT * FROM " + TABLE_NOTE;

        SQLiteDatabase db = this.getWritableDatabase(); //? tại sao lại là Writable
        Cursor cursor = db.rawQuery(selectQuery, null);

        //cursor.moveToFirst là chuyển con trỏ đến đầu danh sách cursor
        if(cursor.moveToFirst()){
            do{
                Note note = new Note();
                note.setNoteId(Integer.parseInt(cursor.getString(0)));
                note.setNoteTitle(cursor.getString(1));
                note.setNoteContent(cursor.getString(2));
                noteList.add(note);
            }while(cursor.moveToNext());
        }

        return noteList;
    }

    public int getNotesCount(){
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... ");

        String countQuery = "Select * FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount(); //đếm từ câu lệnh select

        cursor.close();

        return count;
    }

    public int updateNote(Note note){
        Log.i(TAG, "MyDatabaseHelper.updateNote ... " + note.getNoteTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITLE, note.getNoteTitle());
        values.put(COLUMN_NOTE_CONTENT, note.getNoteContent());

        return db.update(TABLE_NOTE, values, COLUMN_NOTE_ID + " = ?",
                new String[] {String.valueOf(note.getNoteId())});
    }

    public void deleteNote(Note note){
        Log.i(TAG, "MyDatabaseHelper.deleteNote ... " + note.getNoteTitle());

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, COLUMN_NOTE_ID + "=?",
                new String[] {String.valueOf(note.getNoteId())});
        db.close();
    }
}






























