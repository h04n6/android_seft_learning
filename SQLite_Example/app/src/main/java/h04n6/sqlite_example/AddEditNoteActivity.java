package h04n6.sqlite_example;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {

    Note note;
    private static final int MODE_CREATE = 1;
    private static final int MODE_EDIT = 2;

    private int mode;
    private EditText textTitle;
    private EditText textContent;

    private boolean needRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        this.textTitle = (EditText) this.findViewById(R.id.text_note_title);
        this.textContent = (EditText) this.findViewById(R.id.text_note_content);

        Intent intent = this.getIntent();
        this.note = (Note) intent.getSerializableExtra("note");
        if(note == null){
            this.mode = MODE_CREATE;
        }
        else{
            this.mode = MODE_EDIT;
            this.textTitle.setText(note.getNoteTitle());
            this.textContent.setText(note.getNoteContent());
        }
    }

    //người dùng Click vào nút Save
    public void buttonSaveClicked(View view){
        MyDatabaseHelper db = new MyDatabaseHelper(this);

        String title = this.textTitle.getText().toString();
        String content = this.textContent.getText().toString();

        if(title.equals("") || content.equals("")){
            Toast.makeText(getApplicationContext(),
                    "Please enter title & content", Toast.LENGTH_LONG).show();
            return;
        }

        if(mode == MODE_CREATE){
            this.note = new Note(title, content);
            db.addNote(note);
        }
        else{
            this.note.setNoteTitle(title);
            this.note.setNoteContent(content);
            db.updateNote(note);
        }

        this.needRefresh = true;

        //trở lại MainActivity - cái này hình như nghĩa là trở lại thứ mà mình vừa bấm
        this.onBackPressed();
    }

    //khi người dùng click vào button Cancel
    public void buttonCancelClicked(View view){
        //không làm gì, trở về MainActivity
        this.onBackPressed();
    }

    //khi Activity này hoàn thành
    //có thể cần gửi phản hổi gì đó về cho Activity đã gọi nó
    @Override
    public void finish(){
        //chuẩn bị dữ liệu
        Intent data = new Intent();

        //yêu cầu MainActivity refresh lại ListView hoặc không
        data.putExtra("needRefresh", needRefresh);

        //Activity đã hoàn thành OK, trả về dữ liệu
        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }

}




































