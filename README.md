## KAMBA-ANDROID-SDK

[![](https://jitpack.io/v/usekamba/kamba-android-sdk.svg)](https://jitpack.io/#usekamba/kamba-android-sdk)

Kamba-android-sdk makes it easy for developers and merchants to receive payments via Kamba Payment Systems.

## Current Payment Methods 
As a merchant or developer you can currently use this sdk to receive payments via 
1. QR-code (Useful for developers who desire in-store payments, or in-locale payments like ticket sales etc) 
2. App2App in which your app redirects payments to the users Kamba Wallet installed on the customers device to complete transactions. 

## Initial Setup
Create a Kamba Merchant account by contacting our support team. You will be given an apiKey and a merchant id for testing this library in SANDBOX mode. 
> NOTE: The KAMBA-ANDROID-SDK is in beta and still under developement. If you find any error create an issue so it can be seen to as soon as possible.

## Installation

### Android Studio (or Gradle)

No need to clone the repository or download any files -- just add this line to your app's `build.gradle` inside the `dependencies` section:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```
    allprojects {
            repositories {
                ...
                maven { url 'https://jitpack.io' }
            }
    }
```

Step 2. Add the dependency

```
dependencies
    {
	        implementation 'com.github.usekamba:kamba-android-sdk:v1.0-beta'
    }
```


#### Set up Credentials
It is important to set up your credentials so that our systems can authenticate your payment requests.
Usually you will do this in the Activity that will display the payment method. This code will run after your user selects the Kamba Payment option. 

```java
ClientConfig.getInstance().configure("YOUR_API_KEY","YOUR_MERCHANT_ID",ClientConfig.Environment.SANDBOX);
```
## Usage
### App2App + Checkout
The current tools allow you to use our CheckoutWidget ui component to display purchase information and a QR Code that represents the actual checkout.

![Screenshot](screenshots/app2app_.gif)

```xml
<com.usekamba.kambapaysdk.ui.CheckoutWidget xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/checkout"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

</com.usekamba.kambapaysdk.ui.CheckoutWidget>

```

We have also created a KambaButton that your users will click to accept app2app payments. Clicking on this button will redirect the user to his/her Kamba digital wallet to complete transaction.

```xml
<com.usekamba.kambapaysdk.ui.KambaButton
        android:id="@+id/pay"
        android:layout_width="0dp"
        android:layout_height="52dp"
        android:layout_marginBottom="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginStart="8dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent">

    </com.usekamba.kambapaysdk.ui.KambaButton>
```
You can add both of them in the same layout to show the CheckoutWidget containing the checkout details in the same screen as well as provide the KambaButton for App2App payments.

Create an instance of the CheckoutRequest object that represents the item your user has selected to purchase. Add the price and description as necessary. The CheckoutTransaction object will asynchronously make a call to our systems and trigger a callback and return a CheckoutResponse object that your CheckoutWidget requires to populate itself. In the onSuccess callback start the activity that will show the CheckoutWidget and the KambaButton.
```java
public class MerchantActivity extends AppCompatActivity {
    ...
    CheckoutRequest checkoutRequest = new CheckoutRequest();
    checkoutRequest.setInitialAmount(36000.00);
    checkoutRequest.setNotes("Curso Android Para Visionários");
            CheckoutTransaction checkoutTransaction = new CheckoutTransactionBuilder().addCheckoutRequest(checkoutRequest)
                    .addClientConfig(ClientConfig.getInstance()).build();
            checkoutTransaction.enqueue(new TransactionCallback() {
                @Override
                public void onSuccess(CheckoutResponse checkoutResponse) {
                    runOnUiThread(() -> {
                        startActivity(new Intent(context, CheckoutActivity.class).putExtra("checkout", checkoutResponse));
                    });
                }

                @Override
                public void onFailure(String message) {

                }
            });

    ...
}
            
```

In the Activity that will host the CheckoutWidget do the following:

```java 
public class CheckoutActivity extends AppCompatActivity {
    CheckoutWidget checkoutWidget;
    ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        checkoutWidget = findViewById(R.id.checkout);
        payButton = findViewById(R.id.pay);

        response = (CheckoutResponse) getIntent().getSerializableExtra("checkout");
        checkoutWidget.setAmount(Double.parseDouble(checkoutResponse.getTotalAmount()));
        checkoutWidget.setExpirationDate("22/09/2018 13:58");
        checkoutWidget.setTotalCheckoutAmount(Double.parseDouble(checkoutResponse.getTotalAmount()));
        checkoutWidget.setItemDescription(checkoutResponse.getNotes());
        checkoutWidget.setItemAmount(Double.parseDouble(checkoutResponse.getTotalAmount()));
        checkoutWidget.setQrCode(checkoutResponse.getMerchant().getId());
    
        payButton.setOnClickListener(v -> payButton.payWithWallet(checkoutResponse, context));
    
    }
}
```