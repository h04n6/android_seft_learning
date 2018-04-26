package h04n6.connect_php_mysql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button buttonGetImage;
    private Button buttonPostUser;
    private Button buttonPostFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)this.findViewById(R.id.buttonurl);
        buttonGetImage = findViewById(R.id.buttonGetImage);
        buttonPostUser = findViewById(R.id.buttonPostUser);
        buttonPostFile = findViewById(R.id.buttonPostFile);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetURL.class);
                startActivity(intent);
            }
        });

        buttonGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetImage.class);
                startActivity(intent);
            }
        });

        buttonPostUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostUser.class);
                startActivity(intent);
            }
        });

        buttonPostFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostFile.class);
                startActivity(intent);
            }
        });
    }
}
