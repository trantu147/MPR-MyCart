package hanu.a2_1801040193;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import hanu.a2_1801040193.adapters.CartAdapter;
import hanu.a2_1801040193.db.ProductManager;
import hanu.a2_1801040193.models.Product;

public class CartActivity extends AppCompatActivity {

    RecyclerView rvCart;
    public TextView totalPrice;
    List<Product> cartList;
    CartAdapter cartAdapter;
    ProductManager productManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        productManager = ProductManager.getInstance(this);

        totalPrice = findViewById(R.id.total_val);
        totalPrice.setText(productManager.getTotalPrice()+ " VND");

        rvCart = findViewById(R.id.rv_order);

        productManager = ProductManager.getInstance(this);
        cartList = productManager.getAllProducts();

        rvCart.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(cartList, this);
        rvCart.setAdapter(cartAdapter);

    }
}