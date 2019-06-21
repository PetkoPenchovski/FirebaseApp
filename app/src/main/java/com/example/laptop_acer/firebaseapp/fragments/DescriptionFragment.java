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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.utils.MarshmallowPermissions;
import com.example.laptop_acer.firebaseapp.utils.Utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DescriptionFragment extends BaseFragment implements View.OnClickListener {

    private EditText inputTask;
    private EditText inputTaskDescription;
    private Button btnCamera;
    private ImageView imgView;
    private Button btnGallery;
    private Bitmap bitmap;
    private String cameraFilePath;
    private MarshmallowPermissions marshmallowPermissions;
    private Uri picUri;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private Utility utility;

    @Override
    protected void onViewCreated() {
        bindElements();
        checkPermissions();
    }

    private void bindElements() {
        inputTask = view.findViewById(R.id.edt_txt_task);
        inputTaskDescription = view.findViewById(R.id.edt_txt_task_description);
        btnCamera = view.findViewById(R.id.btn_camera);
        btnCamera.setOnClickListener(this);
        imgView = view.findViewById(R.id.img_pic);
        btnGallery = view.findViewById(R.id.btn_gallery);
        btnGallery.setOnClickListener(this);
        marshmallowPermissions = new MarshmallowPermissions(getActivity());
    }

//    private void openCamera() {
//        try {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getContext(),
//                    BuildConfig.APPLICATION_ID + ".provider", createImageFile()));
//            startActivityForResult(intent, CAMERA_REQUEST_CODE);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private void openGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        String[] mimeTypes = {"image/jpeg", "image/png"};
//        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//        startActivityForResult(intent, GALLERY_REQUEST_CODE);
//    }
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        //This is the directory in which the file will be created. This is the default location of Camera photos
//        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_DCIM), "Camera");
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//        // Save a file: path for using again
//        cameraFilePath = "file://" + image.getAbsolutePath();
//        return image;
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK)
//            switch (requestCode) {
//                case CAMERA_REQUEST_CODE:
//                    Uri selectedImage = data.getData();
//                    Bitmap bitmap = BitmapFactory.decodeFile(cameraFilePath);
//                    imgView.setImageURI(selectedImage);
//                    imgView.setImageBitmap(bitmap);
//                    break;
//            }
//        if (resultCode == Activity.RESULT_OK)
//            switch (requestCode) {
//                case CAMERA_REQUEST_CODE:
//                    Bitmap bitmap = BitmapFactory.decodeFile(cameraFilePath);
//                    imgView.setImageBitmap(bitmap);
//                    break;
//            }
//
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getActivity());

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
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

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        imgView.setImageBitmap(bm);
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_description;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera:
                checkPermissions();
//                openCamera();
                selectImage();
                break;
            case R.id.btn_gallery:
                checkPermissions();
//                openGallery();
                break;
        }
    }

    private void checkPermissions() {
        if (!marshmallowPermissions.checkPermissionForExternalStorage()) {
            marshmallowPermissions.requestPermissionForExternalStorage();
        } else if (!marshmallowPermissions.checkPermissionForCamera()) {
            marshmallowPermissions.requestPermissionForCamera();
        }
    }
}
