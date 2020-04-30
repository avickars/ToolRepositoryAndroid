package com.example.toolrepositoryandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;


public class AddTool extends AppCompatActivity {

    ImageView image;

    TextView addImage;

    Intent getImageIntent;

    TextView toolName;
    TextView toolType;
    TextView toolLocation;
    TextView toolDescription;

    Button addTool;

    // To Upload Image
    String imageName = UUID.randomUUID().toString() + ".jpg";

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tool);

        image = (ImageView) findViewById(R.id.toolPicture);
        addImage = (TextView) findViewById(R.id.insertImageTextView);

        getImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        toolName = (TextView) findViewById(R.id.toolName);
        toolType = (TextView) findViewById(R.id.toolType);
        toolLocation = (TextView) findViewById(R.id.toolLocation);
        toolDescription = (TextView) findViewById(R.id.toolDescription);

        addTool = (Button) findViewById(R.id.addToolButton);

        toolType.addTextChangedListener(newToolWatcher);
        toolLocation.addTextChangedListener(newToolWatcher);
        toolDescription.addTextChangedListener(newToolWatcher);
    }

    private TextWatcher newToolWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String toolNameInput = toolName.getText().toString().trim();
            String toolTypeInput = toolType.getText().toString().trim();
            String toolLocationInput = toolLocation.getText().toString().trim();



            boolean val = !toolNameInput.isEmpty() &&
                    !toolTypeInput.isEmpty() &&
                    !toolLocationInput.isEmpty();

            if (val) {
                addTool.setEnabled(true);
                addTool.setAlpha(1);
            } else {
                addTool.setEnabled(false);
                addTool.setAlpha((float) 0.5);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri selectedImage = data.getData();

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                image.setImageBitmap(bitmap);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addImageClick(View view) {
        startActivityForResult(getImageIntent, 1);
    }

    public void toToolRepo(View view) {
        Intent intent = new Intent(getApplicationContext(), ToolRepo.class);
        startActivity(intent);
        finish();
    }



    public void createTool(View view) {
        // Initializing Progress Dialog
        progressDialog = new ProgressDialog(AddTool.this);
        // Show Dialog
        progressDialog.show();
        //Set Content View
        progressDialog.setContentView(R.layout.progress_dialog);
        // Set Transparent Background
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        image.setDrawingCacheEnabled(true);
        image.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();



        UploadTask uploadTask = FirebaseStorage.getInstance().getReference().child("images").child(imageName).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "UploadFailed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Tool Successfully Created", Toast.LENGTH_SHORT).show();
                toToolRepo();
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
            }
        });
    }

    private void toToolRepo() {
        Intent intent = new Intent(getApplicationContext(),ToolRepo.class);

        startActivity(intent);
        finish();
    }

}
