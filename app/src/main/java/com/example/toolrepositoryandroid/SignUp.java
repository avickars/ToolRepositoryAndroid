package com.example.toolrepositoryandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    // Fields in the Activity
    TextView firstName;
    TextView lastName;
    TextView email;
    TextView password;
    TextView passwordConfirm;
    Button signUpButton;

    // String Fields
    String firstNameInput;
    String lastNameInput;
    String emailInput;
    String passwordInput;
    String passwordConfirmInput;

    private FirebaseAuth mAuth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Defining the Fields
        firstName = (TextView) findViewById(R.id.firstName);
        lastName = (TextView) findViewById(R.id.lastName);
        email = (TextView) findViewById(R.id.emailSignUp);
        password = (TextView) findViewById(R.id.passwordSignUp);
        passwordConfirm = (TextView) findViewById(R.id.passwordConfirmSignUp);
        signUpButton = (Button) findViewById(R.id.signUpButtonSignUp);

        // Defining Strings to be used
        String emailInput;
        String firstNameInput;
        String lastNameInput;
        String passwordInput;
        String passwordConfirmInput;

        // Adding in a text Change Listener
        firstName.addTextChangedListener(signUpTextWatcher);
        lastName.addTextChangedListener(signUpTextWatcher);
        email.addTextChangedListener(signUpTextWatcher);
        password.addTextChangedListener(signUpTextWatcher);
        passwordConfirm.addTextChangedListener(signUpTextWatcher);

    }

    private TextWatcher signUpTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            firstNameInput = firstName.getText().toString().trim();
            lastNameInput = lastName.getText().toString().trim();
            emailInput = email.getText().toString().trim();
            passwordInput = password.getText().toString().trim();
            passwordConfirmInput = passwordConfirm.getText().toString().trim();




            boolean val = !firstNameInput.isEmpty() &&
                    !lastNameInput.isEmpty() &&
                    !emailInput.isEmpty() &&
                    !passwordInput.isEmpty() &&
                    !passwordConfirmInput.isEmpty();

            if (val) {
                signUpButton.setEnabled(true);
                signUpButton.setAlpha(1);
            } else {
                signUpButton.setEnabled(false);
                signUpButton.setAlpha((float) 0.5);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void signUp(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // DataBase, Storing User Information
                            FirebaseUser user = mAuth.getCurrentUser();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference().child("users").child(user.getUid());
                            myRef.child("userID").setValue(user.getUid());
                            myRef.child("firstName").setValue(firstNameInput);
                            myRef.child("lastName").setValue(lastNameInput);
                            myRef.child("email").setValue(emailInput);

//                            FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("email").setValue(emailInput);

                            // Dismiss Progress Dialog
                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");


                            // Going to ToolRepo Now
                            toToolRepo();

                        } else {
                            // Dismiss Progress Dialog
                            progressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Sign Up Failed.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void toMainActivity(View view) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void toToolRepo() {
        Intent intent = new Intent(getApplicationContext(),ToolRepo.class);
        startActivity(intent);
        finish();
    }

    public void signUpClick(View view) {
        // Testing if the Passwords Match
        if (password.getText().toString().equals(passwordConfirm.getText().toString())) {
            if (password.getText().toString().length() >= 6) {

                // Initializing Progress Dialog
                progressDialog = new ProgressDialog(SignUp.this);
                // Show Dialog
                progressDialog.show();
                //Set Content View
                progressDialog.setContentView(R.layout.progress_dialog);
                // Set Transparent Background
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                signUp(email.getText().toString(), password.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Password must be at least 6 characters.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Passwords Don't Match.", Toast.LENGTH_SHORT).show();
        }
    }


}
