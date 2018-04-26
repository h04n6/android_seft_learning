package h04n6.fragmentexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showText(String topImageText, String bottomImageText){
        BottomFragment bottomFragment =
                (BottomFragment)this.getSupportFragmentManager()
                        .findFragmentById(R.id.bottom_fragment);
        bottomFragment.showText(topImageText, bottomImageText);
    }
}
