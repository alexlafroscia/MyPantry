package edu.pitt.cs.cs1635.mypantry.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.pitt.cs.cs1635.mypantry.R;
import edu.pitt.cs.cs1635.mypantry.model.Item;

/**
 * Simple adapter to use with a ListView that displays a list of items in an Recipe
 * Created by alex on 4/14/16.
 */
public class RecipeItemListAdapter extends ArrayAdapter<Item> {

    public RecipeItemListAdapter(Context context, List<Item> items) {
        super(context, 0, items) ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.recipe_list_item, parent, false);
        }

        TextView titleView = (TextView) convertView.findViewById(R.id.recipe_list_item_name);
        titleView.setText(item.getTitle());

        return convertView;
    }
}
