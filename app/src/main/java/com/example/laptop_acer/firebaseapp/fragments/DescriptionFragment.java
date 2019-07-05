package com.example.laptop_acer.firebaseapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.laptop_acer.firebaseapp.R;
import com.example.laptop_acer.firebaseapp.model.Task;
import com.example.laptop_acer.firebaseapp.remote.FirebaseAuthRepository;
import com.example.laptop_acer.firebaseapp.remote.FirebaseDataRepository;
import com.example.laptop_acer.firebaseapp.usecases.DescriptionUsecase;
import com.example.laptop_acer.firebaseapp.utils.PermissionUtilities;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DescriptionFragment extends BaseFragment implements View.OnClickListener,
        DescriptionUsecase.ViewListener {

    private static String CAMERA;
    private static String GALLERY;
    private static String CANCEL;
    private final static String CAMERA_KEY = "data";
    private final static String SELECT_IMAGE = "Select Image";
    private final static String IMAGE_FOLDER = "image/*";
    private final static int PICTURE_QUALITY = 90;

    private ProgressBar progressBar;
    private EditText edtPhotoName;
    private EditText edtTaskName;
    private EditText edtTaskDescription;
    private EditText edtLocation;
    private EditText edtTime;
    private Button btnTakePic;
    private Button btnUpload;
    private ImageView imgView;
    private Bitmap imgBitmap;
    private Uri imgUri;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private PermissionUtilities permissionUtilities;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private DescriptionUsecase descriptionUsecase;
    private FirebaseDataRepository firebaseDataRepository;
    private FirebaseAuthRepository firebaseAuthRepository;
    private Task task;

    public DescriptionFragment() {
        task = new Task();
        firebaseAuthRepository = FirebaseAuthRepository.getInstance();
        firebaseDataRepository = new FirebaseDataRepository();
        firebaseStorage = FirebaseStorage.getInstance();
    }

    @Override
    protected void onViewCreated() {
        bindElements();
    }

    private void bindElements() {
        progressBar = view.findViewById(R.id.progressbar_description_fragment);
        edtPhotoName = view.findViewById(R.id.edt_name_photo);
        edtTaskName = view.findViewById(R.id.edt_txt_task);
        edtTaskDescription = view.findViewById(R.id.edt_txt_task_description);
        edtLocation = view.findViewById(R.id.edt_location);
        edtTime = view.findViewById(R.id.edt_time);
        btnTakePic = view.findViewById(R.id.btn_take_pic);
        btnTakePic.setOnClickListener(this);
        btnUpload = view.findViewById(R.id.btn_upload);
        btnUpload.setOnClickListener(this);
        imgView = view.findViewById(R.id.img_pic);
        descriptionUsecase = new DescriptionUsecase();
        descriptionUsecase.setViewListener(this);
        CAMERA = getString(R.string.camera);
        GALLERY = getString(R.string.gallery);
        CANCEL = getString(R.string.cancel);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_description;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionUtilities.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals(CAMERA)) {
                        cameraIntent();
                    } else if (userChoosenTask.equals(GALLERY)) {
                        galleryIntent();
                    }
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {CAMERA, GALLERY, CANCEL};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {
                boolean result = PermissionUtilities.checkPermission(getActivity());

                if (items[index].equals(CAMERA)) {
                    userChoosenTask = CAMERA;
                    if (result)
                        cameraIntent();
                } else if (items[index].equals(GALLERY)) {
                    userChoosenTask = GALLERY;
                    if (result)
                        galleryIntent();
                } else if (items[index].equals(CANCEL)) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType(IMAGE_FOLDER);
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, SELECT_IMAGE), SELECT_FILE);
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
        Bitmap thumbnail = (Bitmap) data.getExtras().get(CAMERA_KEY);
        imgView.setImageBitmap(thumbnail);
        imgBitmap = thumbnail;
    }

    private void upload() {
        Bitmap thumbnail = imgBitmap;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, PICTURE_QUALITY, bytes);
        byte[] bytesData = bytes.toByteArray();
        uploadImage(bytesData);
    }

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                imgBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),
                        data.getData());
                imgUri = data.getData();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        imgView.setImageBitmap(imgBitmap);
    }

    private void onEditTaskInfo() {
        if(task == null) {
            task = new Task();
        }
        task.setUserId(firebaseAuthRepository.getUserId());
        task.setTime(edtTime.getText().toString().trim());
        task.setTaskLocation(edtLocation.getText().toString().trim());
        task.setTaskDescription(edtTaskDescription.getText().toString().trim());
        task.setTaskName(edtTaskName.getText().toString().trim());

        descriptionUsecase.validateNewTaskData(task);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_take_pic:
                selectImage();
                break;
            case R.id.btn_upload:
                upload();
                break;
        }
    }

    private void uploadImage(byte[] bytes) {
        descriptionUsecase.uploadImage(edtPhotoName.getText().toString(), bytes);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showInvalidTaskName() {
        Toast.makeText(getActivity(), (getString(R.string.error_task_name)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidTaskDescription() {
        Toast.makeText(getActivity(), (getString(R.string.error_task_description)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidTaskLocation() {
        Toast.makeText(getActivity(), (getString(R.string.error_task_location)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidTime() {
        Toast.makeText(getActivity(), (getString(R.string.error_task_time)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void uploadImageSuccess(String downloadLink) {
        task.setUrlImage(downloadLink);
        onEditTaskInfo();
    }

    @Override
    public void uploadImageError(String message) {
        Toast.makeText(getActivity(), (getString(R.string.error_upload)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTaskSavedSuccess(String taskId) {
        task.setTaskId(taskId);
        Toast.makeText(getActivity(), (getString(R.string.task_success)),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTaskSavedFailure() {
        Toast.makeText(getActivity(), (getString(R.string.error_task)),
                Toast.LENGTH_SHORT).show();
    }

}
