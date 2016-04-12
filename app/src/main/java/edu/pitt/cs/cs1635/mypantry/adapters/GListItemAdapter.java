package edu.pitt.cs.cs1635.mypantry.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import edu.pitt.cs.cs1635.mypantry.R;
import edu.pitt.cs.cs1635.mypantry.model.Item;
import edu.pitt.cs.cs1635.mypantry.model.ItemDao;

/**
 * Created by Chris on 3/30/2016.
 */
public class GListItemAdapter extends RecyclerView.Adapter<GListItemAdapter.ViewHolder> {

    private List<Item> data;
    private ItemDao itemDao;

    public GListItemAdapter(ItemDao itemDao){
        this.itemDao = itemDao;
        loadData();

        this.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                loadData();
            }
        });
    }

    public void loadData() {
        this.data = this.itemDao.queryBuilder()
                .where(ItemDao.Properties.OnGroceryList.eq(true))
                .list();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View gListItemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.glist_item, null);

        ViewHolder viewHolder = new GListItemAdapter.ViewHolder(gListItemView, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setPantryItem(this.data.get(position));
    }

    public void removeItem(Item item) {
        item.setOnGroceryList(false);
        this.itemDao.update(item);
        this.notifyDataSetChanged();
    }

    public void buyItem(Item item) {
        item.setOnGroceryList(false);
        item.setAmount(2);
        this.itemDao.update(item);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Item pantryItem;
        private TextView pantryItemName;

        public ViewHolder(View itemView, final GListItemAdapter adapter) {
            super(itemView);

            this.pantryItemName = (TextView) itemView.findViewById(R.id.gList_item_name);

            Button removeButton = (Button) itemView.findViewById(R.id.gList_item_remove_item);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.removeItem(pantryItem);
                }
            });

            Button boughtButton = (Button) itemView.findViewById(R.id.gList_item_bought_item);
            boughtButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.buyItem(pantryItem);
                }
            });
        }

        protected void setPantryItem(Item pantryItem) {
            this.pantryItem = pantryItem;
            this.pantryItemName.setText(pantryItem.getTitle());
        }
    }
}
