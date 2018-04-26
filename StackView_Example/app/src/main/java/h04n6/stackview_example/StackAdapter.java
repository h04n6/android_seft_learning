package h04n6.stackview_example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

/**
 * Created by hoang on 3/3/2018.
 */

public class StackAdapter extends ArrayAdapter<StackItem> {

    private List<StackItem> items;
    private Context context;

    public StackAdapter(Context context, int textViewResourceId,
                        List<StackItem> objects){
        super(context, textViewResourceId, objects);
        this.items = objects;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View itemView = convertView;
        if(itemView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = layoutInflater.inflate(R.layout.stack_item, null);
        }
        StackItem stackItem = items.get(position);
        if(stackItem != null){
            //button được định nghĩa trên stack_item.xml
            Button button = (Button) itemView.findViewById(R.id.button);

            if(button != null){
                button.setText(stackItem.getItemText());
                //có thể set onClick ở đây
            }
        }
        return itemView;
    }
}


































