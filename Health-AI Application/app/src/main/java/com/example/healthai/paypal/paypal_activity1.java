package com.example.healthai.paypal;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;

import com.example.healthai.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import org.json.JSONException;
import org.json.JSONObject;
import java.math.BigDecimal;
public class paypal_activity1 extends AppCompatActivity {

    private static final int PAYPAL_REQUEST_CODE = 123;
    private static final String TAG = "MainActivity";

    // PayPal configuration
    private static PayPalConfiguration paypalConfig = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // Use sandbox for testing
            .clientId("AbdXbn96l8Pi11oyqTv05s-OrBXgcFWM8PK3gZqeuRQvMK2q2oFrX1ciR_D7qfZxNqs3A1EELY_I4jmB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start PayPal service
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        startService(intent);

        // Perform payment
        makePayment();
    }

    private void makePayment() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal("10.0"), "USD", "Test payment/month",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    String paymentId = confirmation.getProofOfPayment().getPaymentId();
                    String state = confirmation.getProofOfPayment().getState();

                    // Handle successful payment
                    Toast.makeText(this, "Payment successful. Payment ID: " + paymentId, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                }
            } else if (resultCode == RESULT_CANCELED) {
                // Handle canceled payment
                Toast.makeText(this, "Payment canceled.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                // Invalid payment or configuration
                Toast.makeText(this, "Invalid payment or configuration.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
            }

            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Stop PayPal service
        stopService(new Intent(this, PayPalService.class));
    }
}