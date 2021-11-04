package hanu.a2_1801040193;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hanu.a2_1801040193.adapters.ShopAdapter;
import hanu.a2_1801040193.models.Product;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvProduct;
    private List<Product> products;
    private EditText searchBox;
    CharSequence keyWord = "";
    private ShopAdapter shopAdapter;
    private String productsURL = "https://mpr-cart-api.herokuapp.com/products";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        searchBox = findViewById(R.id.search_box);
        rvProduct = findViewById(R.id.rvProduct);

        products = new ArrayList<>();

        ProductLoader productLoader = new ProductLoader();
        productLoader.execute(productsURL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.cart_btn){
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public class ProductLoader extends AsyncTask<String, Void, String>{
        URL url;
        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();

                Scanner sc = new Scanner(inputStream);
                StringBuilder result = new StringBuilder();
                String line;
                while(sc.hasNextLine()) {
                    line = sc.nextLine();
                    result.append(line);
                }

                return result.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result == null){
                Toast.makeText(MainActivity.this, "Error when fetching data", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i ++){
                    JSONObject jsonProduct = jsonArray.getJSONObject(i);

                    long productId = jsonProduct.getLong("id");
                    String productName = jsonProduct.getString("name");
                    String productThumbnail = jsonProduct.getString("thumbnail");
                    double productPrice = jsonProduct.getDouble("unitPrice");
                    Product product = new Product(productId, productName, productThumbnail,productPrice);

                    products.add(product);

                }

            } catch (JSONException e){
                e.printStackTrace();
            }

            rvProduct.setLayoutManager( new GridLayoutManager(MainActivity.this, 2, GridLayoutManager.VERTICAL, false));

            shopAdapter = new ShopAdapter(products);
            rvProduct.setAdapter(shopAdapter);

            searchBox.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    shopAdapter.getFilter().filter(charSequence);
                    keyWord = charSequence;
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }
    }
}