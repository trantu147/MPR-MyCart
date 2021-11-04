package hanu.a2_1801040193.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

import hanu.a2_1801040193.models.Product;

public class ProductManager {
    private static ProductManager productManager;

    private DbHelper dbHelper;
    private SQLiteDatabase db;

    private static final String INSERT_STM = "INSERT INTO " +
            DbSchema.ProductsTable.NAME +"(" + DbSchema.ProductsTable.Cols.PRODUCT_ID +"," +
            DbSchema.ProductsTable.Cols.PRODUCT_NAME +","+
            DbSchema.ProductsTable.Cols.THUMBNAIL +","+
            DbSchema.ProductsTable.Cols.UNIT_PRICE +","+
            DbSchema.ProductsTable.Cols.QUANTITY +
            ") VALUES (?, ?, ?,  ?, ?)";

    public static ProductManager getInstance(Context context){
        if(productManager == null){
            productManager = new ProductManager(context);
        }
        return productManager;
    }

    public ProductManager(Context context){
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean addProduct(Product product){
        SQLiteStatement stm = db.compileStatement(INSERT_STM);
        stm.bindLong(1, product.getId());
        stm.bindString(2, product.getName());
        stm.bindString(3, product.getThumbnail());
        stm.bindDouble(4, product.getUnitPrice());
        stm.bindString(5, product.getQuantity() +"");

        long isAdded = stm.executeInsert();
        return isAdded > 0;
    }

    public boolean updateProduct(Product product){
        ContentValues contentValues = new ContentValues();

        contentValues.put(DbSchema.ProductsTable.Cols.PRODUCT_ID, product.getId());
        contentValues.put(DbSchema.ProductsTable.Cols.PRODUCT_NAME, product.getName());
        contentValues.put(DbSchema.ProductsTable.Cols.THUMBNAIL, product.getThumbnail());
        contentValues.put(DbSchema.ProductsTable.Cols.UNIT_PRICE, product.getUnitPrice());
        contentValues.put(DbSchema.ProductsTable.Cols.QUANTITY, product.getQuantity());

        int isUpdated = db.update(DbSchema.ProductsTable.NAME, contentValues,DbSchema.ProductsTable.Cols.PRODUCT_ID + "= ?", new String[] { product.getId() + "" });
        return isUpdated > 0;
    }

    public boolean deleteProduct(long productId){
        int isDeleted = db.delete(DbSchema.ProductsTable.NAME,
                DbSchema.ProductsTable.Cols.PRODUCT_ID + "= ?", new String[]{ productId + "" } );

        return isDeleted> 0;
    }

    public List<Product> getAllProducts(){
        String sql = "SELECT * FROM " + DbSchema.ProductsTable.NAME;
        Cursor cursor = db.rawQuery(sql, null);
        ProductCursorWrapper cursorWrapper = new ProductCursorWrapper(cursor);

        return cursorWrapper.getAllProducts();
    }

    public double getTotalPrice(){
        String sql = "SELECT SUM("+ DbSchema.ProductsTable.Cols.QUANTITY +" * "  + DbSchema.ProductsTable.Cols.UNIT_PRICE +  ") AS total FROM " + DbSchema.ProductsTable.NAME;
        Cursor cursor = db.rawQuery(sql, null);
        double totalPrice;
        cursor.moveToFirst();

        totalPrice = cursor.getDouble(0);
        return totalPrice;
    }


    public Product findProductById(long productId){
        String sql = "SELECT * FROM " + DbSchema.ProductsTable.NAME + " WHERE "+ DbSchema.ProductsTable.Cols.PRODUCT_ID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{productId+""});

        ProductCursorWrapper cursorWrapper = new ProductCursorWrapper(cursor);

        return cursorWrapper.getProductByID();
    }

}
