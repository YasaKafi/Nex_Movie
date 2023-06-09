package com.example.nexmovie.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.nexmovie.NavigationBar;
import com.example.nexmovie.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPage extends AppCompatActivity {
    private static final String TAG = "LoginPage";
    private static final int RC_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    EditText username, password;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login_page);
        ImageView imageView = findViewById(R.id.ilustration);
        LinearLayout linearLayout = findViewById(R.id.linearLayoutLogin);
        ProgressBar progressBar = findViewById(R.id.progesBar);

        imageView.bringToFront();
        linearLayout.requestLayout();

        username = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        buttonLogin = findViewById(R.id.btnLogin);

        // Mendapatkan instance Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("google_email", "");
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");


        // Jika email tersimpan di Shared Preferences, langsung navigasikan ke NavigationBar
        if (!TextUtils.isEmpty(savedEmail)) {
            Intent intent = new Intent(LoginPage.this, NavigationBar.class);
            startActivity(intent);
            finish();
        }

        if (!TextUtils.isEmpty(savedUsername) && !TextUtils.isEmpty(savedPassword)) {
            Intent intent = new Intent(LoginPage.this, NavigationBar.class);
            startActivity(intent);
            finish();
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                String usernameKu = username.getText().toString();
                String passwordKu = password.getText().toString();
                AndroidNetworking.post("https://mediadwi.com/api/latihan/login")
                        .addBodyParameter("username", usernameKu)
                        .addBodyParameter("password", passwordKu)
                        .setTag("login")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    boolean status = response.getBoolean("status");
                                    String message = response.getString("message");
                                    if (status){
                                        // Simpan usernameKu dan passwordKu ke Shared Preference
                                        saveCredentialsToSharedPreferences(usernameKu, passwordKu);

                                        if (TextUtils.isEmpty(usernameKu) || TextUtils.isEmpty(usernameKu)) {
                                            progressBar.setVisibility(View.GONE);

                                            // menampilkan Toast error
                                            Toast.makeText(getApplicationContext(), "Username atau password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                                            return;
                                        }else{

                                            Toast.makeText(LoginPage.this, message, Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(LoginPage.this, NavigationBar.class);
                                            startActivity(intent);
                                            finish();
                                        }


                                    }else{
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(LoginPage.this, "Login Gagal", Toast.LENGTH_LONG).show();
//
                                    }
                                } catch (JSONException e) {

                                    throw new RuntimeException(e);
                                }

                            }

                            @Override
                            public void onError(ANError error) {
                                progressBar.setVisibility(View.GONE);
                                // Handle error
                                Toast.makeText(LoginPage.this, "Login gagal. Error: " + error.getErrorBody(), Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign-In options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("458933570288-9rq6uftt1blfkmbtc3ua7avr4qg6qjr9.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        // Launch the Google Sign-In flow when the "Sign in with Google" button is clicked
        Button googleSignInButton = findViewById(R.id.btnGoogle);
        googleSignInButton.setOnClickListener(v-> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign-In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign-In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        // Mengirim data akun Google ke MainActivity
                        updateUI(user);

                    }
                });

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // User is signed in, save email to Shared Preferences
            String email = user.getEmail();
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("google_email", email);
            editor.apply();

            // User is signed in, launch the MainActivity
            Intent intent = new Intent(this, NavigationBar.class);
            startActivity(intent);
            finish();
        } else {
            // User is signed out, stay on the LoginActivity
            Toast.makeText(LoginPage.this, "Sign in failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveCredentialsToSharedPreferences(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }

}

