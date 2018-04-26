package h04n6.sqlite_example;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private static final int MENU_ITEM_VIEW = 111;
    private static final int MENU_ITEM_EDIT = 222;
    private static final int MENU_ITEM_CREATE = 333;
    private static final int MENU_ITEM_DELETE = 444;

    private static final int MY_REQUEST_CODE = 1000;

    private final List<Note> noteList = new ArrayList<Note>();
    private ArrayAdapter<Note> listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        MyDatabaseHelper db = new MyDatabaseHelper(this);
        db.createDefaultNoteIfNeed();

        List<Note> list = db.getAllNotes();
        this.noteList.addAll(list);

        //định nghĩa 1 Adapter
        //1 - Context
        //2 - Layout cho các dòng
        //3 - ID của TextView mà dữ liệu sẽ được ghi vào
        //4 - Danh sách dữ liệu

        this.listViewAdapter = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, this.noteList);

        //Adapter : bộ điều hợp
        //đăng ký Adapter cho ListView
        this.listView.setAdapter(listViewAdapter);

        //đăng ký Context menu cho ListView
        registerForContextMenu(this.listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, view, menuInfo);
        menu.setHeaderTitle("Select The Action");

        menu.add(0, MENU_ITEM_VIEW, 0, "View Note");
        menu.add(0, MENU_ITEM_CREATE, 1, "Create New Note");
        menu.add(0, MENU_ITEM_EDIT, 2, "Edit Note");
        menu.add(0, MENU_ITEM_DELETE, 4, "Delete Note");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Note selectedNote = (Note) this.listView.getItemAtPosition(info.position);

        if(item.getItemId() == MENU_ITEM_VIEW){
            Toast.makeText(getApplicationContext(), selectedNote.getNoteContent(), Toast.LENGTH_LONG)
                    .show();
        }
        else if (item.getItemId() == MENU_ITEM_CREATE){
            Intent intent = new Intent(this, AddEditNoteActivity.class);

            //start AddEditNoteActivity, có phản hồi
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        }
        else if (item.getItemId() == MENU_ITEM_EDIT){
            Intent intent = new Intent(this, AddEditNoteActivity.class);
            intent.putExtra("note", selectedNote);

            //start AddEditNoteActivity, có phản hồi
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        }
        else if (item.getItemId() == MENU_ITEM_DELETE){
            //hỏi trước khi xóa
            new AlertDialog.Builder(this)
                    .setMessage(selectedNote.getNoteTitle() + " ~ Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteNote(selectedNote);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else{
            return false;
        }
        return true;
    }

    //người dùng đồng ý xóa 1 note
    private void deleteNote(Note note){
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        db.deleteNote(note);
        this.noteList.remove(note);
        //Refresh ListView
        this.listViewAdapter.notifyDataSetChanged();
    }

    //khi AddEditNoteActivity hoàn thành, nó gửi phản hồi lại
    //(nếu bạn đã start nó bằng cách sử dụng startActivityForResult())
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        if(resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE){
            boolean needRefresh = data.getBooleanExtra("needRefresh", true);
            //refresh ListView bằng cách lấy ra toàn bộ note bằng getAllNotes() nếu 1 note nào đó
            //bị sửa chữa
            if(needRefresh){
                this.noteList.clear();
                MyDatabaseHelper db = new MyDatabaseHelper(this);
                List<Note> list = db.getAllNotes();
                this.noteList.addAll(list);
                //Thông báo dữ liệu thay đổi (Để refresh ListView)
                this.listViewAdapter.notifyDataSetChanged();
            }
        }
    }
}


































