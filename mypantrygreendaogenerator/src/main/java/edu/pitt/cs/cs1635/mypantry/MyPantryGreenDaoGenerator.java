package edu.pitt.cs.cs1635.mypantry;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * greenDao Generator for our Data Model
 *
 * Run as part of the build steps to generate the models for our application
 *
 * For more information on setting this up, the tutorial found at:
 *      http://www.devteam83.com/en/tutorial-greendao-from-scratch-part-1/
 * was used to set this up.
 */
public class MyPantryGreenDaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "edu.pitt.cs.cs1635.mypantry.model");

        // Recipe Model
        Entity recipe = schema.addEntity("Recipe");
        recipe.addIdProperty();
        recipe.addStringProperty("title");
        recipe.addStringProperty("directions");

        // Grocery Item Model
        Entity item = schema.addEntity("Item");
        item.addIdProperty();
        item.addStringProperty("title");
        item.addIntProperty("amount"); // Will be used as an enum for "enough, low, out"
        item.addBooleanProperty("onGroceryList");

        Entity recipeItem = schema.addEntity("RecipeItem");
        // Add relationship to a recipe
        Property recipeRelationshipProperty = recipeItem.addLongProperty("recipeId").getProperty();
        recipeItem.addToOne(recipe, recipeRelationshipProperty);
        // Add relationship to an item
        Property itemRelationshipProperty = recipeItem.addLongProperty("itemId").getProperty();
        recipeItem.addToOne(item, itemRelationshipProperty);

        new DaoGenerator().generateAll(schema, "../app/src/main/java");
    }
}
