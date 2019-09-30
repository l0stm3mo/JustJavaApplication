/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantityOfCoffees = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if (quantityOfCoffees == 100) {
            Toast.makeText(this, "You can't have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantityOfCoffees = quantityOfCoffees + 1;
        display(quantityOfCoffees);
    }

    public void decrement(View view) {
        if (quantityOfCoffees == 1) {
            Toast.makeText(this, "You can't have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantityOfCoffees = quantityOfCoffees - 1;
        display(quantityOfCoffees);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        CheckBox checkCream = (CheckBox) findViewById(R.id.checkBox_Cream);
        CheckBox checkChoco = (CheckBox) findViewById(R.id.checkBox_Choco);
        boolean hasWhippedCream = checkCream.isChecked();
        boolean hasChoco= checkChoco.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChoco);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChoco);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order For " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
//        displayMessage(priceMessage);
    }
    /**
     * hitung harga
     */
    private int calculatePrice(boolean addCream, boolean addChoco){
        int basePrice = 5;

        if (addCream) {
            basePrice = basePrice + 1;
        }

        if (addChoco) {
            basePrice = basePrice + 2;
        }
        return quantityOfCoffees * basePrice;
    }

    /**
     * pesan order
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChoco){
        String priceMessage = getString(R.string.orderName, name);
        priceMessage += "\n" + getString(R.string.orderAddCream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.orderAddChoco, addChoco);
        priceMessage += "\n" + getString(R.string.orderQuantity, quantityOfCoffees);
        priceMessage += "\n" + getString(R.string.total, price);
        priceMessage += "\n" + getString(R.string.thankYou) ;
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */
//    private void displayPrice(int number) {
//        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }
    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(message);
//    }
}