package edu.pitt.cs.cs1635.mypantry.model;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;

import edu.pitt.cs.cs1635.mypantry.model.RecipeItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "RECIPE_ITEM".
*/
public class RecipeItemDao extends AbstractDao<RecipeItem, Void> {

    public static final String TABLENAME = "RECIPE_ITEM";

    /**
     * Properties of entity RecipeItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property RecipeId = new Property(0, Long.class, "recipeId", false, "RECIPE_ID");
        public final static Property ItemId = new Property(1, Long.class, "itemId", false, "ITEM_ID");
    };

    private DaoSession daoSession;


    public RecipeItemDao(DaoConfig config) {
        super(config);
    }
    
    public RecipeItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RECIPE_ITEM\" (" + //
                "\"RECIPE_ID\" INTEGER," + // 0: recipeId
                "\"ITEM_ID\" INTEGER);"); // 1: itemId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RECIPE_ITEM\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, RecipeItem entity) {
        stmt.clearBindings();
 
        Long recipeId = entity.getRecipeId();
        if (recipeId != null) {
            stmt.bindLong(1, recipeId);
        }
 
        Long itemId = entity.getItemId();
        if (itemId != null) {
            stmt.bindLong(2, itemId);
        }
    }

    @Override
    protected void attachEntity(RecipeItem entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public RecipeItem readEntity(Cursor cursor, int offset) {
        RecipeItem entity = new RecipeItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // recipeId
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1) // itemId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, RecipeItem entity, int offset) {
        entity.setRecipeId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setItemId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(RecipeItem entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(RecipeItem entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getRecipeDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getItemDao().getAllColumns());
            builder.append(" FROM RECIPE_ITEM T");
            builder.append(" LEFT JOIN RECIPE T0 ON T.\"RECIPE_ID\"=T0.\"_id\"");
            builder.append(" LEFT JOIN ITEM T1 ON T.\"ITEM_ID\"=T1.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected RecipeItem loadCurrentDeep(Cursor cursor, boolean lock) {
        RecipeItem entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Recipe recipe = loadCurrentOther(daoSession.getRecipeDao(), cursor, offset);
        entity.setRecipe(recipe);
        offset += daoSession.getRecipeDao().getAllColumns().length;

        Item item = loadCurrentOther(daoSession.getItemDao(), cursor, offset);
        entity.setItem(item);

        return entity;    
    }

    public RecipeItem loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<RecipeItem> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<RecipeItem> list = new ArrayList<RecipeItem>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<RecipeItem> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<RecipeItem> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}