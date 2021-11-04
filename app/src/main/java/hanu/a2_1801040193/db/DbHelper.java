package hanu.a2_1801040193.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "cart.db";
    private static final int DB_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DbSchema.ProductsTable.NAME + "(" +
                DbSchema.ProductsTable.Cols.PRODUCT_ID +" INTEGER PRIMARY KEY, " +
                DbSchema.ProductsTable.Cols.PRODUCT_NAME + " TEXT, " +
                DbSchema.ProductsTable.Cols.THUMBNAIL + " TEXT," +
                DbSchema.ProductsTable.Cols.UNIT_PRICE + " DOUBLE, "+
                DbSchema.ProductsTable.Cols.QUANTITY + " INTEGER" + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("My Cart", "My Cart: upgrading DB; dropping/recreating tables.");
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.ProductsTable.NAME);

        onCreate(db);
    }
}
