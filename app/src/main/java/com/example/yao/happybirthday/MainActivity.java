package com.example.yao.happybirthday;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
        import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    int quantity = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(LOG_TAG, "Let's look at lifecycle: Call onCreate");
    }

    protected void onStart() {
        super.onStart();
        Log.v(LOG_TAG, "Let's look at lifecycle: Call onStart");
    }

    protected void onRestart() {
        super.onRestart();
        Log.v(LOG_TAG, "Let's look at lifecycle: Call onRestart");
    }

    protected void onResume() {
        super.onResume();
        Log.v(LOG_TAG, "Let's look at lifecycle: Call onResume");
    }

    protected void onPause() {
        super.onPause();
        Log.v(LOG_TAG, "Let's look at lifecycle: Call onPause");
    }

    protected void onStop() {
        super.onStop();
        Log.v(LOG_TAG, "Let's look at lifecycle: Call onStop");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG, "Let's look at lifecycle: Call onDestroy");
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameEditText = (EditText) findViewById(R.id.name_edittext);
        String name = nameEditText.getText().toString();

        CheckBox creamCheckBox = (CheckBox) findViewById(R.id.cream_checkbox);
        boolean hasWhippedCream = creamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
        Log.v("MainActivity", "Whipped cream: " + hasWhippedCream);
        displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + name);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;
        if (hasWhippedCream) basePrice++;
        if (hasChocolate) basePrice += 2;
        return quantity *basePrice;
    }

    /**
     * Create summary of the order.
     *
     * @param name of the user
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolate is whether or not the user wants whipped cream topping
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nAdd whipped cream? " + hasWhippedCream;
        priceMessage += "\nAdd chocolate? " + hasChocolate;
        priceMessage += "\nAmount due $" + price;
        priceMessage = priceMessage + "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
            display(quantity);
        }
        else {
            Toast.makeText(this, "You can not have more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
        }
    }

    public void decrement(View view) {
        if (quantity > 1) {
            quantity--;
            display(quantity);
        }
        else Toast.makeText(this, "You can not have less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
}
