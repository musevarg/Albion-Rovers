package football.focus.footfragments.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import football.focus.footfragments.R;
import football.focus.footfragments.Util.NetworkUtil;

public class RegisterActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1234;
    String API_GET_TOKEN = "https://www.sedhna.com/rovers/braintree/php/main.php";
    String API_CHECKOUT = "https://www.sedhna.com/rovers/braintree/php/checkout.php";

    String token, amount;
    HashMap<String, String> paramsHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Albion Rovers F.C.");
        toolbar.setTitleTextColor(Color.rgb(155, 39, 0));
        setSupportActionBar(toolbar);

        Button backButton = findViewById(R.id.buttonRegBack);
        Button regButton = findViewById(R.id.buttonReg);
        final RadioButton sub1 = findViewById(R.id.radioPartial);
        final RadioButton sub2 = findViewById(R.id.radioFull);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        new getToken().execute();

        regButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (NetworkUtil.getConnectivityStatus(RegisterActivity.this) == 0)
                {
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("No internet")
                            .setMessage("An internet connection is required to register an account.")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                }
                else {
                    if (sub1.isChecked()) {
                        if (checkFields())
                            register(false);
                    } else if (sub2.isChecked()) {
                        if (checkFields())
                            submitPayment();
                    }
                }

            }
        });
    }

    //Checks for empty fields, for a valid email address and whether the passwords match
    private Boolean checkFields()
    {
        EditText email = findViewById(R.id.regEmail);
        EditText pass1 = findViewById(R.id.passView);
        EditText pass2 = findViewById(R.id.passView2);
        if(TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(pass1.getText()) || TextUtils.isEmpty(pass2.getText()))
        {
            Toast.makeText(RegisterActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches())
            {
                if (pass1.getText().toString().trim().equals(pass2.getText().toString().trim()))
                {
                    return true;
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            else
            {
                Toast.makeText(RegisterActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private void register(Boolean paid)
    {
        EditText email = findViewById(R.id.regEmail);
        EditText pass1 = findViewById(R.id.passView);
        EditText pass2 = findViewById(R.id.passView2);
        String sub;

        if (paid)
            sub = "Full";
        else
            sub = "Partial";

        String request = "https://sedhna.com/rovers/php/register.php?email=" + email.getText().toString().trim() + "&pass=" + pass1.getText().toString().trim() + "&subType=" + sub;
        new getData().execute(request);
    }

    /* BRAINTREE IMPLEMENTATION STARTS HERE */

    // Initiate Braintree Drop-In UI
    private void submitPayment()
    {
        String payValue = "5.99";
        if (!payValue.isEmpty()) {
            DropInRequest dropInRequest = new DropInRequest().clientToken(token);
            startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
        } else {
            Toast.makeText(this, "Enter a valid amount for payment", Toast.LENGTH_SHORT).show();
        }

    }

    //Posts data to checkout.php file on the server and returns the response from Braintree (Successful or Failure)
    private void sendPayments()
    {
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, API_CHECKOUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.toString().contains("Successful")) {
                            Toast.makeText(RegisterActivity.this, "Payment Success", Toast.LENGTH_SHORT).show();
                            register(true); // REGISTER USER
                        } else {
                            Toast.makeText(RegisterActivity.this, "Payment Failed", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("Response", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Err", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (paramsHash == null)
                    return null;
                Map<String, String> params = new HashMap<>();
                for (String key : paramsHash.keySet()) {
                    params.put(key, paramsHash.get(key));
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        RetryPolicy mRetryPolicy = new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(mRetryPolicy);
        queue.add(stringRequest);
    }

    // Request token from server, called during the OnCreate method
    private class getToken extends AsyncTask {
        //ProgressDialog mDialog;

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient client = new HttpClient();
            client.get(API_GET_TOKEN, new HttpResponseCallback() {
                @Override
                public void success(final String responseBody) {
                   // mDialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //group_payment.setVisibility(View.VISIBLE);
                            token = responseBody;
                        }
                    });
                }

                @Override
                public void failure(Exception exception) {
                    //mDialog.dismiss();
                    Log.d("Err", exception.toString());
                }
            });
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*mDialog = new ProgressDialog(RegisterActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
            mDialog.setCancelable(false);
            mDialog.setMessage("Loading Wallet, Please Wait");
            mDialog.show();*/
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }

    // Method called by the Drop-In UI and sets the transaction amount, also handles user cancellation from the drop-in UI
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String strNounce = nonce.getNonce();
                    amount = "5.99";
                    paramsHash = new HashMap<>();
                    paramsHash.put("amount", amount);
                    paramsHash.put("nonce", strNounce);
                    sendPayments();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "User canceled", Toast.LENGTH_SHORT).show();
            } else {
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("Err here", error.toString());
            }
        }
    }

    /* BRAINTREE IMPLEMENTATION FINISHES HERE */

    //Register user and update database
    private class getData extends AsyncTask<String, Void, JSONObject>
    {

        @Override
        protected JSONObject doInBackground(String... params)
        {
            String str = params[0];
            //String str="http://your.domain.here/yourSubMethod";
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try
            {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }

                //return new JSONObject(stringBuffer.toString());
                return new JSONObject(stringBuffer.toString());
            }
            catch(Exception ex)
            {
                Log.e("App", "yourDataTask", ex);
                return null;
            }
            finally
            {
                if(bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(JSONObject jsonobject)
        {
            if(jsonobject != null)
            {
                try {
                    String status = jsonobject.getString("status");

                    if (status.equals("OK"))
                    {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        String message = jsonobject.getString("message");
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException ex) {
                    Log.e("App", "Failure", ex);
                }
            }
        }
    }
}


