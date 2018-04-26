package h04n6.game_2d;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

/**
 * Created by hoang on 2/13/2018.
 */

public class TopFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstancedState){
        View view = inflater.inflate(R.layout.activity_top, container, false);
        return view;
    }
}
