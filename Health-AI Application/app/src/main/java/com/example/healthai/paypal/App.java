package com.example.healthai.paypal;

import android.app.Application;

import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.pyplcheckout.BuildConfig;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PayPalCheckout.setConfig(new CheckoutConfig(
                this,
                "AU6CwQzVKh8ng7kSCvlj7sMQ2caOsEBSWZBHcEtrSZ29kv_HXGqTTFWwmhi_BpKf-YaTBus94cP4N2zl",
                Environment.SANDBOX,
                CurrencyCode.USD,
                UserAction.PAY_NOW,
                "com.example.healthai://paypalpay"
        ));
    }
}
