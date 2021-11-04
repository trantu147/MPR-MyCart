package hanu.a2_1801040193.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.List;

import hanu.a2_1801040193.models.Product;

public class ProductCursorWrapper extends CursorWrapper {

    public ProductCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<>();

        moveToFirst();
        while (!isAfterLast()){
            Product product = getProduct();
            products.add(product);

            moveToNext();
        }

        return products;
    }

    public Product getProduct(){
        long id = getLong(getColumnIndex(DbSchema.ProductsTable.Cols.PRODUCT_ID));
        String name = getString(getColumnIndex(DbSchema.ProductsTable.Cols.PRODUCT_NAME));
        String thumbnail = getString(getColumnIndex(DbSchema.ProductsTable.Cols.THUMBNAIL));
        double unitPrice = getDouble(getColumnIndex(DbSchema.ProductsTable.Cols.UNIT_PRICE));
        int quantity = getInt(getColumnIndex(DbSchema.ProductsTable.Cols.QUANTITY));

        Product product = new Product(id, name, thumbnail, unitPrice, quantity);
        return product;
    }

    public Product getProductByID(){
        Product product = null;
        moveToFirst();
        if(!isAfterLast()){
            product = getProduct();
        }

        return product;
    }
}
