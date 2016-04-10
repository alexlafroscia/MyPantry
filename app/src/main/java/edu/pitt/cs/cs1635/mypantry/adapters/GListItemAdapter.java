package edu.pitt.cs.cs1635.mypantry.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.pitt.cs.cs1635.mypantry.R;
import edu.pitt.cs.cs1635.mypantry.model.GListItem;

/**
 * Created by Chris on 3/30/2016.
 */
public class GListItemAdapter extends ArrayAdapter<GListItem> {
    public GListItemAdapter(Context context, ArrayList<GListItem> items){
        super(context,0,items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GListItem it=getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.glist_item,parent,false);

        }

        TextView itemname=(TextView) convertView.findViewById(R.id.text_data);
        itemname.setText(it.item);

        if(it.amt.equals("LOW")){
            itemname.setTextColor(0xFF293E);
        }else if(it.amt.equals("MED")){
            itemname.setTextColor(0xFFA229);
        }else{
            //itemname.setTextColor(0x000000);
        }

        return convertView;
    }
}
