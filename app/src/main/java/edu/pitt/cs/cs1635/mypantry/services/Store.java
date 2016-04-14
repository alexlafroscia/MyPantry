package edu.pitt.cs.cs1635.mypantry.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import edu.pitt.cs.cs1635.mypantry.model.DaoMaster;
import edu.pitt.cs.cs1635.mypantry.model.DaoSession;
import edu.pitt.cs.cs1635.mypantry.model.Item;
import edu.pitt.cs.cs1635.mypantry.model.ItemDao;
import edu.pitt.cs.cs1635.mypantry.model.Recipe;
import edu.pitt.cs.cs1635.mypantry.model.RecipeDao;
import edu.pitt.cs.cs1635.mypantry.model.RecipeItem;
import edu.pitt.cs.cs1635.mypantry.model.RecipeItemDao;

/**
 * Created by alex on 4/13/16.
 */
public class Store {
    private boolean initialized;

    protected SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private ItemDao itemDao;
    private RecipeDao recipeDao;
    private RecipeItemDao recipeItemDao;

    private static Store ourInstance = new Store();

    public static Store getInstance() {
        return ourInstance;
    }

    private Store() {}

    /**
     * Set a context for the Store, and initialize greenDao once we have a context to use
     *
     * @param context the context to use for the service
     */
    public void initWithContext(Context context) {
        if (initialized)
                return;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "pantry-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        itemDao = daoSession.getItemDao();
        recipeDao = daoSession.getRecipeDao();
        recipeItemDao = daoSession.getRecipeItemDao();
        initialized = true;
    }

    /**
     * Get all of the Items in the DB
     * @return the list of items
     */
    public List<Item> getItems() {
        return this.itemDao.loadAll();
    }

    /**
     * Get all of the Items in the DB with a title that matches the given one
     * @param title the title to match
     * @return the list of matching items
     */
    public List<Item> getItems(String title) {
        return this.itemDao.queryBuilder()
                .where(ItemDao.Properties.Title.eq(title))
                .list();
    }

    /**
     * Get the first item in the DB with a title that matches the given one
     * @param title the title to match
     * @return the matching item, or `null` if there is no match
     */
    public Item getItem(String title) {
        List<Item> list = this.getItems(title);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public Item createItem(String title) {
        Item item = new Item();
        item.setTitle(title);
        item.setAmount(2);  // Default new items to "High"
        item.setOnGroceryList(false);
        this.itemDao.insertOrReplace(item);
        return item;
    }

    /**
     * Get all of the Recipes in the DB
     * @return the list of recipes
     */
    public List<Recipe> getRecipes() {
        return this.recipeDao.loadAll();
    }

    /**
     * Get all of the Recipes in the DB with a title that matches the given one
     * @param title the title to match
     * @return the list of matching recipes
     */
    public List<Recipe> getRecipes(String title) {
        return this.recipeDao.queryBuilder()
                .where(RecipeDao.Properties.Title.eq(title))
                .list();
    }

    /**
     * Get the first Recipe in the DB with a title that matches the given one
     * @param title the title to match
     * @return the matching Recipe, or `null` if there is no match
     */
    public Recipe getRecipe(String title) {
        List<Recipe> list = this.getRecipes(title);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public Recipe getRecipe(Long id) {
        // Guard against invalid ID
        if (id == 0)
            return null;

        List<Recipe> list = this.recipeDao.queryBuilder()
                .where(RecipeDao.Properties.Id.eq(id))
                .list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * Get the list of items for a given recipe
     *
     * Uses the join table to look up all of the items for a given recipe.  Had to be implemented
     * using a join table due to the fact that recipes can have many items, and items can belong to
     * multiple recipes.
     *
     * Note: While one could iterate over each of the elements in the list representing each join
     *       item and fetch the items directly, this would create an (N + 1) query situation.  By
     *       instead collecting the IDs and then querying for all of them at the same time, we can
     *       simply make two queries instead.
     *
     * @param recipe the recipe to look up the Items for
     * @return the list of Items needed by that recipe
     */
    public List<Item> getItemsForRecipe(Recipe recipe) {
        List<RecipeItem> recipeItems = this.recipeItemDao.queryBuilder()
                .where(RecipeItemDao.Properties.RecipeId.eq(recipe.getId()))
                .list();
        List<Long> ids = new ArrayList<>();
        for ( RecipeItem recipeItem : recipeItems) {
            ids.add(recipeItem.getItemId());
        }
        return this.itemDao.queryBuilder()
                .where(ItemDao.Properties.Id.in(ids))
                .list();
    }

    /**
     * Add an item to a recipe
     *
     * Creates a new join element, if one cannot be found that already joins the two items together
     *
     * @param item the item to add
     * @param recipe the recipe to add it to
     */
    public void addItemToRecipe(Item item, Recipe recipe) {
        List<RecipeItem> recipeItems = this.recipeItemDao.queryBuilder()
                .where(
                        RecipeItemDao.Properties.RecipeId.eq(recipe.getId()),
                        RecipeItemDao.Properties.ItemId.eq(item.getId()))
                .list();
        if (recipeItems.size() == 0) {
            RecipeItem recipeItem = new RecipeItem(recipe.getId(), item.getId());
            this.recipeItemDao.insertOrReplace(recipeItem);
        }
    }

    public Recipe saveRecipe(Recipe recipe) {
        if (recipe.getId() == null) {
            this.recipeDao.insertOrReplace(recipe);
        } else {
            this.recipeDao.update(recipe);
        }
        return recipe;
    }
}
