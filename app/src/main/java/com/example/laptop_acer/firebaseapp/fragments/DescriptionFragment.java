package com.example.laptop_acer.firebaseapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.utils.Utility;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class DescriptionFragment extends BaseFragment implements View.OnClickListener {

    private static final String TEMP_FILE_NAME = "temp_file";
    private ProgressBar progressBar;
    private EditText edtPhotoName;
    private EditText edtTask;
    private EditText edtTaskDescription;
    private Button btnCamera;
    private Button btnUpload;
    private ImageView imgView;
    private Bitmap imgBitmap;
    private String cameraFilePath;
    private Uri picUri;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private Utility utility;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Override
    protected void onViewCreated() {
        bindElements();
    }

    private void bindElements() {
        progressBar = view.findViewById(R.id.progressbar_description_fragment);
        edtPhotoName = view.findViewById(R.id.edt_name_photo);
        edtTask = view.findViewById(R.id.edt_txt_task);
        edtTaskDescription = view.findViewById(R.id.edt_txt_task_description);
        btnCamera = view.findViewById(R.id.btn_camera);
        btnCamera.setOnClickListener(this);
        btnUpload = view.findViewById(R.id.btn_upload);
        btnUpload.setOnClickListener(this);
        imgView = view.findViewById(R.id.img_pic);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage
                .getReferenceFromUrl("gs://fir-app-57375.appspot.com")
                .child("ic_launcher.png");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Camera"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Gallery"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Camera", "Gallery",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getActivity());

                if (items[item].equals("Camera")) {
                    userChoosenTask = "Camera";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Gallery")) {
                    userChoosenTask = "Gallery";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imgView.setImageBitmap(thumbnail);
    }

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                imgBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imgView.setImageBitmap(imgBitmap);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_description;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera:
                selectImage();
                break;
            case R.id.btn_upload:
                uploadPictures(imgBitmap);
                break;
        }
    }

    private void uploadPictures(Bitmap imgBitmap) {
        progressBar.setVisibility(View.VISIBLE);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), imgBitmap,
                TEMP_FILE_NAME, null);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//        byte[] data = outputStream.toByteArray();

        UploadTask uploadTask = storageReference.putFile(Uri.parse(path));
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
        progressBar.setVisibility(View.GONE);
    }

    public void storeMultipleImages(List<Uri> imageUris) {
        for (Uri uri : imageUris) {
            storeImage(uri);
        }
    }

    private void storeImage(Uri uri) {
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}