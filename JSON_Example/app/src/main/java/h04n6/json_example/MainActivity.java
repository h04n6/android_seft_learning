package h04n6.json_example;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText outputText;
    private RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void runExample(View view){
        /*try{
            //đọc file: res/raw/company.json và trả về đối tượng Company
            Company[] company = ReadJSON.readCompanyJSONFile(this);

            outputText.setText(company[1].toString());
        }
        catch (Exception e){
            outputText.setText(e.getMessage());
            e.printStackTrace();
        }*/

        /*FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Part1Fragment part1Fragment = new Part1Fragment();
        Part3Fragment part3Fragment = new Part3Fragment();
        Part2Fragment part2Fragment = new Part2Fragment();
        fragmentTransaction.add(R.id.fragment_container, part1Fragment, "Part3");
        fragmentTransaction.commit();*/

        mainLayout = (RelativeLayout) this.findViewById(R.id.mainLayout);
        //mainLayout.addView(createButton());
        getLayoutInflater().inflate(R.layout.select_question, mainLayout, true );
        createButton();
    }

    private void createButton(){
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.select_question);
        Button[] buttons = new Button[10];
        int m = 0, n =0;
        for(int i = 0; i < 10; i++){
            buttons[i] = new Button(this);
            buttons[i].setHeight(100);
            buttons[i].setWidth(50);
            buttons[i].setX(300*m);
            buttons[i].setY(150*n);
            buttons[i].setText(String.valueOf(i));
            relativeLayout.addView(buttons[i]);

            m++;
            if(m == 3){m = 0; n++;}
        }
    }
}
