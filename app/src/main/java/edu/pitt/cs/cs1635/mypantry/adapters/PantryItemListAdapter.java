package edu.pitt.cs.cs1635.mypantry.adapters;

import android.graphics.Color;
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
 * Created by alex on 4/9/16.
 */
public class PantryItemListAdapter extends RecyclerView.Adapter<PantryItemListAdapter.ViewHolder> {

    public List<Item> data;
    private ItemDao itemDao;

    public PantryItemListAdapter(ItemDao itemDao) {
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
        this.data = this.itemDao.loadAll();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View itemLayoutView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.pantry_item, null);

        // Create view holder
        ViewHolder viewHolder = new PantryItemListAdapter.ViewHolder(itemLayoutView, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItemDao(this.itemDao);
        holder.setPantryItem(this.data.get(position));
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    /**
     * Remove item from the database and reload the view
     * @param item the item to remove
     */
    public void removeItem(Item item) {
        this.itemDao.delete(item);
        this.notifyDataSetChanged();
    }

    public void toggleItemOnGroceryList(Item item) {
        item.setOnGroceryList(!item.getOnGroceryList());
        this.itemDao.update(item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView pantryItemName;
        public Item pantryItem;

        private PantryItemListAdapter adapter;
        private View itemView;
        private ItemDao itemDao;

        public ViewHolder(View itemView, PantryItemListAdapter adapter) {
            super(itemView);

            this.adapter = adapter;

            // Save references to views for future manipulations
            this.itemView = itemView;
            this.pantryItemName = (TextView) itemView.findViewById(R.id.pantry_item_name);

            Button amountButton = (Button) itemView.findViewById(R.id.pantry_item_amount_button);
            amountButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;
                    if (b.getText().equals("High")) {
                        setItemAmount("Low");
                    } else if (b.getText().equals("Low")) {
                        setItemAmount("Out");
                    } else if (b.getText().equals("Out")) {
                        setItemAmount("High");
                    }
                }
            });

            Button toggleGroceryListButton = (Button) itemView.findViewById(R.id.pantry_item_toggle_grocery_list);
            toggleGroceryListButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleItemOnGroceryList();
                }
            });

            Button removeButton = (Button) itemView.findViewById(R.id.pantry_item_remove_button);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem();
                }
            });
        }

        public void setPantryItem(Item item) {
            this.pantryItem = item;
            this.pantryItemName.setText(item.getTitle());
            updateAmountInView();
        }

        private void setItemAmount(String amountText) {
            int amount;
            switch (amountText) {
                case "Out":
                    amount = 0;
                    break;
                case "Low":
                    amount = 1;
                    break;
                default:
                    amount = 2;
                    break;
            }
            this.pantryItem.setAmount(amount);
            this.itemDao.update(this.pantryItem);
            updateAmountInView();
        }

        private void setItemDao(ItemDao itemDao) {
            this.itemDao = itemDao;
        }

        private void updateAmountInView() {
            Button amountButton = (Button) this.itemView.findViewById(R.id.pantry_item_amount_button);
            int amount = this.pantryItem.getAmount();
            if (amount == 0) {
                amountButton.setText("Out");
                amountButton.setTextColor(Color.RED);
                this.pantryItemName.setTextColor(Color.RED);
            } else if (amount == 1) {
                amountButton.setText("Low");
                amountButton.setTextColor(Color.YELLOW);
                this.pantryItemName.setTextColor(Color.YELLOW);
            } else if (amount == 2) {
                amountButton.setText("High");
                amountButton.setTextColor(Color.BLACK);
                this.pantryItemName.setTextColor(Color.BLACK);
            }
        }

        private void removeItem() {
            this.adapter.removeItem(this.pantryItem);
        }

        private void toggleItemOnGroceryList() {
            this.adapter.toggleItemOnGroceryList(this.pantryItem);
        }
    }
}
