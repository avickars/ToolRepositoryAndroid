package com.example.toolrepositoryandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private FirebaseAuth mAuth;

//    String email;
//    String password;
    // NEED TO DO FOR FIRST AND LAST NAME ***************

    // TextView Fields
    private TextView email;
    private TextView password;
    private TextView firstName;
    private TextView lastName;
    private TextView passwordConfirm;
    private Button updateProfileButton;

    String emailInput;
    String passwordInput;
    String firstNameInput;
    String lastNameInput;
    String passwordConfirmInput;

    // PlainText Fields
    TextView passwordPlainText;
    TextView confirmPasswordPlainText;

    Button logout;

    Boolean profileEditable = false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Finding Fields
        email = (TextView) findViewById(R.id.emailProfile);
        password = (TextView) findViewById(R.id.passwordProfile);
        firstName = (TextView) findViewById(R.id.firstNameProfile);
        lastName = (TextView) findViewById(R.id.lastNameProfile);
        passwordConfirm = (TextView) findViewById(R.id.confirmPassWordProfile);

        passwordPlainText = (TextView) findViewById(R.id.passwordTextViewProfile);
        confirmPasswordPlainText = (TextView) findViewById(R.id.confirmPasswordTextViewProfile);


        email.addTextChangedListener(updateProfileTextWatcher);
        password.addTextChangedListener(updateProfileTextWatcher);
        firstName.addTextChangedListener(updateProfileTextWatcher);
        lastName.addTextChangedListener(updateProfileTextWatcher);
        passwordConfirm.addTextChangedListener(updateProfileTextWatcher);


        updateProfileButton = (Button) findViewById(R.id.updateProfileButton);
        logout = (Button) findViewById(R.id.logoutProfileButton);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // Initializing Field Text
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (user != null) {
//            Log.d(TAG, "onCreate:" + user.getEmail());
//            email.setText(user.getEmail());
        }
    }

    public void updateProfileClick(View view) {
        if (!profileEditable) {
            profileEditable = true;
            password.setEnabled(true);
            password.setText("");

            passwordConfirm.setAlpha(1);
            passwordConfirm.setEnabled(true);
            passwordConfirm.setText("");

            confirmPasswordPlainText.setAlpha(1);

            email.setEnabled(true);
            firstName.setEnabled(true);
            lastName.setEnabled(true);

            updateProfileButton.setAlpha((float) 0.5);
            updateProfileButton.setEnabled(false);
            updateProfileButton.setText("Save");
            logout.setText("Cancel");

        } else {

        }
    }

    public void logoutClick(View view){
        if (profileEditable) {
            profileEditable = false;
            password.setEnabled(false);
            password.setText("fjksdlfajkldsa");

            passwordConfirm.setAlpha(0);
            passwordConfirm.setEnabled(false);
            passwordConfirm.setText("");

            confirmPasswordPlainText.setAlpha(0);

            email.setEnabled(false);
            firstName.setEnabled(false);
            lastName.setEnabled(false);

            updateProfileButton.setText("Update Profile");
            logout.setText("Logout");
            profileEditable = false;
        } else {
            mAuth.signOut();
            toMainActivity();
        }

    }

    private void toMainActivity() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private TextWatcher updateProfileTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            emailInput = email.getText().toString().trim();
            passwordInput = password.getText().toString().trim();
            firstNameInput = firstName.getText().toString().trim();
            lastNameInput = lastName.getText().toString().trim();
            passwordConfirmInput = passwordConfirm.getText().toString().trim();

            Boolean val = !emailInput.isEmpty() && !firstNameInput.isEmpty() && !lastNameInput.isEmpty();
            if (val) {
                updateProfileButton.setEnabled(true);
                updateProfileButton.setAlpha(1);
            } else {
                updateProfileButton.setEnabled(false);
                updateProfileButton.setAlpha((float) 0.5);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
