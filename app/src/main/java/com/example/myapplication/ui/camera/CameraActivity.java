package com.example.myapplication.ui.camera;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.example.myapplication.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class CameraActivity extends AppCompatActivity {

    private ImageButton snap ;
    private TextView input_btn;
    private ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture;
    private PreviewView previewView;
    private ImageCapture imageCapture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
        {
            String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission,1000);
        }
        snap = (ImageButton) findViewById(R.id.snapbutton);

        previewView = (PreviewView) findViewById(R.id.previewview);
        cameraProviderListenableFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderListenableFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderListenableFuture.get();
                startCameraX(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

        }, getExecutor());
        snap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                cacpturePhoto();


                //detect_moment();
            }
        });
    }

    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        Preview preview = new Preview.Builder().build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        cameraProvider.bindToLifecycle((LifecycleOwner) this,cameraSelector,preview, imageCapture);

    }
    private Executor getExecutor()
    {
        return ContextCompat.getMainExecutor(this);
    }

    private void cacpturePhoto() {
        long timestamp = System.currentTimeMillis();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "temp_pic");
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + File.separator + "Diary_oil";

        File outputDir = new File(path);


        Toast.makeText(CameraActivity.this, outputDir.exists() + "", Toast.LENGTH_SHORT).show();
        //create dir if not there
        if (!outputDir.exists()) {
            outputDir.mkdir();

        }

        File file = new File(path + File.separator + "/temp_pic.jpg");
        Uri outputdir = Uri.fromFile(outputDir);
        Toast.makeText(CameraActivity.this, outputdir.getPath() + "", Toast.LENGTH_SHORT).show();


        boolean bool = file.delete();
        //Toast.makeText(CameraVieActivity.this, file.getAbsolutePath() + file.exists(), Toast.LENGTH_SHORT).show();


        imageCapture.takePicture(
                new ImageCapture.OutputFileOptions.Builder(
                        getContentResolver(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                ).build(),
                getExecutor(),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Toast.makeText(CameraActivity.this, "Photo has been saved successfully ", Toast.LENGTH_SHORT).show();

                        //uri = outputFileResults.getSavedUri();
                        File saved_pic = file;

                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(CameraActivity.this, "Error saving photo: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        sourceBitmap = previewView.getBitmap();
        popup_dialog(sourceBitmap);
    }
    private Bitmap sourceBitmap;

    private void popup_dialog(Bitmap sourceBitmap) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.photo_taken);
        dialog.setCanceledOnTouchOutside(true);
        ImageView a =dialog.findViewById(R.id.imageView2);
        Button b = dialog.findViewById(R.id.post);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraActivity.super.onBackPressed();
            }
        });
        a.setImageBitmap(sourceBitmap);
        dialog.show();

    }
}


