package hanu.a2_1801040193.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import hanu.a2_1801040193.CartActivity;
import hanu.a2_1801040193.R;
import hanu.a2_1801040193.db.ProductManager;
import hanu.a2_1801040193.models.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    List<Product> cartList;
    private CartActivity cartActivity;

    public CartAdapter(List<Product> cartList, CartActivity cartActivity){
        this.cartList = cartList;
        this.cartActivity = cartActivity;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.product_in_cart, parent, false);


        return new CartHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        Product product = cartList.get(position);

        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }


    public class CartHolder extends RecyclerView.ViewHolder {
        private TextView cartLineProductName, cartLineUnitPrice, cartLineQuantity, cartLinePrice;
        private ImageView thumbnail;
        private ImageButton btnIncrease, btnDecrease;
        private Context context;

        public CartHolder(@NonNull View itemView, Context context) {
            super(itemView);

            cartLineProductName = itemView.findViewById(R.id.cart_line_product_name);
            cartLineUnitPrice = itemView.findViewById(R.id.cart_line_unit_price);
            cartLineQuantity = itemView.findViewById(R.id.cart_line_quantity);
            cartLinePrice = itemView.findViewById(R.id.cart_line_sum_price);
            thumbnail = itemView.findViewById(R.id.cart_line_thumnail);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);

            this.context = context;
        }

        public void bind(Product product){
            cartLineProductName.setText(product.getName());
            cartLineQuantity.setText(product.getQuantity() + "");
            cartLineUnitPrice.setText(product.getUnitPrice() + " VND");
            cartLinePrice.setText(product.getPrice() + " VND");

            btnIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductManager productManager = ProductManager.getInstance(context);

                    boolean isUpdated = false;
                    product.increaseQuantity();
                    isUpdated = productManager.updateProduct(product);


                    if( isUpdated){
                        cartActivity.totalPrice.setText(productManager.getTotalPrice()+" VND");
                    }else{
                        Toast.makeText(context, "Add fail", Toast.LENGTH_SHORT).show();
                    }
                    notifyDataSetChanged();
                }

            });

            btnDecrease.setOnClickListener(new View.OnClickListener() {
                ProductManager productManager = ProductManager.getInstance(context);

                @Override
                public void onClick(View v) {
                    if(product.getQuantity() ==  1){
                        new AlertDialog.Builder(context)
                                .setTitle("Delete product")
                                .setMessage("Do you want to remove this product from cart?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        boolean isDeleted = productManager.deleteProduct(product.getId());
                                        if(isDeleted){
                                            cartList.remove(product);
                                            cartActivity.totalPrice.setText(productManager.getTotalPrice()+" VND");
                                            Toast.makeText(context, "Product has been removed", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(context, "Fail to remove product", Toast.LENGTH_SHORT).show();
                                        }
                                        notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();


                    }else{
                        product.decreaseQuantity();
                        productManager.updateProduct(product);
                        cartActivity.totalPrice.setText(productManager.getTotalPrice()+ " VND");
                        notifyDataSetChanged();
                    }


                }
            });

            ThumbnailLoader thumbnailLoader = new ThumbnailLoader();
            thumbnailLoader.execute(product.getThumbnail());

        }

        public class ThumbnailLoader extends AsyncTask<String, Void, Bitmap> {
            URL imgURL;
            HttpURLConnection urlConnection;

            @Override
            protected Bitmap doInBackground(String... strings) {
                try {
                    imgURL = new URL(strings[0]);
                    urlConnection = (HttpURLConnection) imgURL.openConnection();
                    urlConnection.connect();

                    InputStream is = urlConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    return bitmap;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                thumbnail.setImageBitmap(bitmap);
            }
        }

    }

}
