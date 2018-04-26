package h04n6.stackview_example;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.StackView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private StackView stackView;
    private Button button;

    private final String[] BUTTON_TEXT = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.stackView = (StackView) findViewById(R.id.stackView);

        List<StackItem> items = new ArrayList<StackItem>();

        for(String buttonText : BUTTON_TEXT){
            items.add(new StackItem(buttonText));
        }

        StackAdapter adapter = new StackAdapter(this, R.layout.stack_item, items);
        stackView.setAdapter(adapter);
        stackView.setHorizontalScrollBarEnabled(true);
        stackView.setBackgroundColor(Color.rgb(230, 255, 255));
    }
}
