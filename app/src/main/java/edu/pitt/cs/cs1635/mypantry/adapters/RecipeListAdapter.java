package edu.pitt.cs.cs1635.mypantry.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.pitt.cs.cs1635.mypantry.R;
import edu.pitt.cs.cs1635.mypantry.RecipeDetail;
import edu.pitt.cs.cs1635.mypantry.model.Recipe;
import edu.pitt.cs.cs1635.mypantry.services.Store;

/**
 * Created by alex on 4/12/16.
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

    public List<Recipe> data;
    private Context context;

    public RecipeListAdapter(Context context) {
        this.context = context;
        Store store = Store.getInstance();
        this.data = store.getRecipes();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view
        View itemLayoutView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recipe_list_item, null);

        // Create view holder
        return new RecipeListAdapter.ViewHolder(itemLayoutView, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setRecipe(this.data.get(position));
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView recipeItemName;

        private RecipeListAdapter adapter;
        private Recipe recipe;

        public ViewHolder(View itemView, final RecipeListAdapter adapter) {
            super(itemView);
            this.adapter = adapter;

            // Save references to views for future manipulations
            this.recipeItemName = (TextView) itemView.findViewById(R.id.recipe_list_item_name);

            final ViewHolder that = this;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(adapter.context, RecipeDetail.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("RECIPE_ID", that.recipe.getId());
                    adapter.context.startActivity(intent);
                }
            });
        }

        protected void setRecipe(Recipe recipe) {
            this.recipe = recipe;
            this.recipeItemName.setText(recipe.getTitle());
        }
    }
}
