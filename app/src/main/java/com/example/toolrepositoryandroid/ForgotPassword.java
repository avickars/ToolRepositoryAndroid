package com.example.toolrepositoryandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private TextView email;
    private Button resetPassword;

    private FirebaseAuth firebaseAuth;

    private Boolean status;

    AlertDialog.Builder a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = (TextView) findViewById(R.id.emailEditTextResetPassword);
        resetPassword = (Button) findViewById(R.id.resetPassword);

        email.addTextChangedListener(resetPasswordWatcher);

        firebaseAuth = FirebaseAuth.getInstance();

        a = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert);
    }

    public void resetPasswordClick(View view) {
        firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    status = true;
                    alert();
                } else {
                    status = false;
                    alert();
                }

            }
        });

    }

    private TextWatcher resetPasswordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String emailInput = email.getText().toString().trim();

            if (!emailInput.isEmpty()) {
                resetPassword.setAlpha(1);
                resetPassword.setEnabled(true);
            } else {
                resetPassword.setAlpha((float) 0.5);
                resetPassword.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void alert() {
        if (status) {
            a.setMessage("An email has been sent to: " + email.getText().toString())
                    .setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        } else {
            a.setMessage("Invalid Email Address.  Either edit the email address or create an account.")
                    .setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }


    }

}
