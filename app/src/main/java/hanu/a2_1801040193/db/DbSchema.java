package hanu.a2_1801040193.db;

public class DbSchema {

    public final class ProductsTable{
        public static final String NAME = "products";

        public final class Cols {
            public static final String PRODUCT_ID = "product_id";
            public static final String PRODUCT_NAME = "product_name";
            public static final String THUMBNAIL = "product_thumbnail";
            public static final String UNIT_PRICE = "unit_price";
            public static final String QUANTITY = "quantity";
        }
    }
}
