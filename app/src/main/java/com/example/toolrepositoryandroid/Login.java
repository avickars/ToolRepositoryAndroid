package com.example.toolrepositoryandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private FirebaseAuth mAuth;
    private TextView email;
    private TextView password;
    private Button loginButton;
    private Button signInButton;

    private TextView forgotPassword;

    private ProgressDialog progressDialog;

    // Alert
    AlertDialog.Builder a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        email = (TextView) findViewById(R.id.emailEditText);
        password = (TextView) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginPageButton);
        signInButton = (Button) findViewById(R.id.signUpButtonLogin);

        email.addTextChangedListener(loginTextWatcher);
        password.addTextChangedListener(loginTextWatcher);

        forgotPassword = (TextView) findViewById(R.id.forgotPasswordTextView);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPasswordClick();
            }
        });

        // Initialize Alert
        a = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert);


    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String emailInput = email.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();

            if (!emailInput.isEmpty() && !passwordInput.isEmpty()){
                loginButton.setEnabled(true);
                loginButton.setAlpha(1);
            } else {
                loginButton.setEnabled(false);
                loginButton.setAlpha((float) 0.5);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Dismiss Progress Dialog
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
//                    updateUI(user);
                    if (user.isEmailVerified()){
                        progressDialog.dismiss();
                        // Going to ToolRepo
                        toToolRepo();
                    } else {
                        user.sendEmailVerification();
                        FirebaseAuth.getInstance().signOut();
                        progressDialog.dismiss();
                        alert();
                    }

                } else {
                    // Dismiss Progress Dialog
                    progressDialog.dismiss();
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(getApplicationContext(), "Login Failed. Check Email and Password or Create an Account", Toast.LENGTH_SHORT).show();
//                    updateUI(null);

                }
            }
        });
    }

    private void toToolRepo() {
        Intent intent = new Intent(getApplicationContext(),ToolRepo.class);
        startActivity(intent);
        finish();
    }

    public void signUpClick(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
        finish();
    }

    public void loginClick(View view) {
        // Initializing Progress Dialog
        progressDialog = new ProgressDialog(Login.this);
        // Show Dialog
        progressDialog.show();
        //Set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        // Set Transparent Background
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        login(email.getText().toString(),password.getText().toString());
    }

    private void alert() {
        a.setMessage("The Email: " + email.getText().toString() + " has not been verified.  " +
                "A verification email has been sent to this address please verify the email and try again.")
                .setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    // To Forgot Password Page
    public void forgotPasswordClick() {
        Intent intent1 = new Intent(getApplicationContext(), ForgotPassword.class);
        startActivity(intent1);
    }
}
