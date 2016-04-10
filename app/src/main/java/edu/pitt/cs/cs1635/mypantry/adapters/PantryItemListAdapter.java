package edu.pitt.cs.cs1635.mypantry.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.pitt.cs.cs1635.mypantry.R;
import edu.pitt.cs.cs1635.mypantry.model.Item;

/**
 * Created by alex on 4/9/16.
 */
public class PantryItemListAdapter extends RecyclerView.Adapter<PantryItemListAdapter.ViewHolder> {

    public List<Item> data;

    public PantryItemListAdapter(List<Item> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pantry_item, null);

        // Create view holder
        ViewHolder viewHolder = new PantryItemListAdapter.ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.pantryItemName.setText(this.data
                .get(position)
                .getTitle());
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView pantryItemName;

        public ViewHolder(View itemView) {
            super(itemView);
            pantryItemName = (TextView) itemView.findViewById(R.id.pantry_item_name);
        }
    }
}
