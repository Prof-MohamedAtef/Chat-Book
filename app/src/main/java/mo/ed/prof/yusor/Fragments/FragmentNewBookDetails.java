package mo.ed.prof.yusor.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Adapter.CustomSpinnerAdapter;
import mo.ed.prof.yusor.GenericAsyncTasks.RetrieveAuthorsAsyncTask;
import mo.ed.prof.yusor.GenericAsyncTasks.RetrieveBooksAsyncTask;
import mo.ed.prof.yusor.Network.SnackBarClassLauncher;
import mo.ed.prof.yusor.Network.VerifyConnection;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Config;
import mo.ed.prof.yusor.helpers.Room.StudentsEntity;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static mo.ed.prof.yusor.Activities.AddBookActivity.BookID_KEY;
import static mo.ed.prof.yusor.Activities.AddBookActivity.BookTitle_KEY;
import static mo.ed.prof.yusor.helpers.Config.currentImagePAth;
import static mo.ed.prof.yusor.helpers.Config.selectedImagePath;

/**
 * Created by Prof-Mohamed Atef on 3/15/2019.
 */

public class FragmentNewBookDetails extends Fragment implements RetrieveAuthorsAsyncTask.OnAuthorsRetrievalTaskCompleted,
        RetrieveBooksAsyncTask.OnBooksRetrievalTaskCompleted{

    @BindView(R.id.camera)
    ImageView Camera;

    @BindView(R.id.BookImage)
    ImageView BookImage;

    @BindView(R.id.Auth_spinner)
    Spinner Auth_spinner;

    @BindView(R.id.CircleImageLinear)
    LinearLayout CircleImageLinear;

    @BindView(R.id.Faculty_spinner)
    Spinner Faculty_spinner;

    @BindView(R.id.Edit_addBook)
    EditText Edit_addBook;

    @BindView(R.id.EditAuthorName)
    EditText EditAuthorName;

    @BindView(R.id.Edit_isbnNum)
    EditText Edit_isbnNum;

    @BindView(R.id.Edit_PublishYear)
    EditText Edit_PublishYear;

    @BindView(R.id.Next_BTN)
    Button Next_BTN;

    private SnackBarClassLauncher snackBarLauncher;
    private java.lang.String KEY_AuthPosition="KEY_AuthPosition";
    private String KEY_AUTHList="KEY_AUTHList";
    private String KEY_BooksLIST="KEY_BooksLIST";
    private String KEY_POSITION="KEY_POSITION";
    private java.lang.String SampleDateFormat_KEY="yyyyMMdd_HHmmss";
    private String JPEG_KEY="JPEG_";
    private VerifyConnection verifyConnection;
    private String AuthURL ="http://fla4news.com/Yusor/api/v1/authers";
    private String BooksURL="http://fla4news.com/Yusor/api/v1/books";
    private String IMAGE_TYPE="image/*";
    final static int SELECT_PICTURE=12;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 55;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    protected static final int GALLERY_PICTURE = 1;
    final static int RESULT_LOAD_IMAGE=11;
    private String FILE_EXTENSION="file:";
    private String DATA_KEY="data";
    Bitmap imageBitmap;
    String [] filePathColumn;
    Uri selectedImage;
    File fileNaming;
    Bitmap bitmap;
    String imageFileName;
    private java.lang.String JPG_EXTENSION=".jpg";
    private String UploadedImage1;
    private Uri ImageFileUri;
    private String imageName;
    private String ImageURL_KEY="imageFileUri";
    private boolean HasImage=false;
    private String BookTitle;
    private String BookID;
    private String BookName;
    private String AuthorName;
    private String ISBN_Num;
    private String PublishYear;
    private Uri ImageUri;
    private String FacultyName;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Auth_spinner
        verifyConnection=new VerifyConnection(getActivity());
        Bundle bundle=getArguments();
        if (bundle!=null){
            BookID= bundle.getString(BookID_KEY);
            BookTitle=bundle.getString(BookTitle_KEY);
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_new_book_details, container, false);
        ButterKnife.bind(this,rootView);
        snackBarLauncher=new SnackBarClassLauncher();
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_AUTHList, Config.AuthList);
        outState.putInt(KEY_AuthPosition,Config.AuthPosition);
        outState.putSerializable(KEY_BooksLIST, Config.BooksList);
        outState.putInt(KEY_POSITION,Config.BookPosition);
        if (Config.ImageFileUri!=null){
            outState.putString(ImageURL_KEY, Config.ImageFileUri.toString());
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_CANCELED&&data!=null){
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                if (data != null) {

                    Bundle extras = data.getExtras();
                    imageFileName= data.getData().getPath();
                    fileNaming=new File(imageFileName);
                    imageName= fileNaming.getName();
                    ImageFileUri =data.getData();
                    Config.ImageFileUri=ImageFileUri;
                    imageBitmap = (Bitmap) extras.get(DATA_KEY);
                    setBitmapToImageView(imageBitmap);
                    try{
                        createImageFile();
                        addPicToPhone();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            else if (requestCode == GALLERY_PICTURE && resultCode == RESULT_OK) {
                if (data != null) {
                    Bundle selectedImage = data.getExtras();
                    ImageFileUri=data.getData();
                    Config.ImageFileUri=ImageFileUri;
                    imageFileName=data.getData().getPath();
                    fileNaming=new File(imageFileName);
                    imageName= fileNaming.getName();
                    filePathColumn = new String[]{MediaStore.Images.Media.DATA};
                    String filePath =  MediaStore.Images.Media.DATA ;
                    Bitmap  imagebitmap=(Bitmap)selectedImage.get(DATA_KEY);
                    Config.imageBitmap=imagebitmap.toString();
                    Cursor c = getActivity().getContentResolver().query(Uri.parse(filePath), filePathColumn, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumn[0]);
                    selectedImagePath = c.getString(columnIndex);
                    Config.image_name=selectedImagePath;
                    c.close();
                    if (selectedImagePath != null) {
                        bitmap = BitmapFactory.decodeFile(selectedImagePath);
                        bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);
                        setBitmapToImageView(bitmap);
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.canceled),
                            Toast.LENGTH_SHORT).show();
                }
            }else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
                if (data!=null){
                    selectedImage = data.getData();
//                AudioFilePath= selectedImage.getPath();
                    ImageFileUri =data.getData();
                    Config.ImageFileUri=ImageFileUri;
                    imageFileName=data.getData().getPath();
                    fileNaming=new File(imageFileName);
                    imageName= fileNaming.getName();
                    filePathColumn = new String[]{MediaStore.Images.Media.DATA};
                    imageBitmap= LoadThenDecodeBitmap();
                    setBitmapToImageView(imageBitmap);
                }
            }else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK){
                if (data!=null){
                    selectedImage = data.getData();
                    imageFileName=data.getData().getPath();
                    fileNaming=new File(imageFileName);
                    imageName= fileNaming.getName();
                    ImageFileUri =data.getData();
                    Config.ImageFileUri=ImageFileUri;
                    filePathColumn = new String[]{MediaStore.Images.Media.DATA};
                    if (selectedImage!=null){
                        imageBitmap= LoadThenDecodeBitmap();
                        setBitmapToImageView(imageBitmap);
                    }else {
                        Bundle selectedImage = data.getExtras();
                        imageFileName=data.getData().getPath();
                        fileNaming=new File(imageFileName);
                        imageName= fileNaming.getName();
                        ImageFileUri =data.getData();
                        Config.ImageFileUri=ImageFileUri;
                        filePathColumn = new String[]{MediaStore.Images.Media.DATA};
                        imageBitmap=(Bitmap)selectedImage.get(DATA_KEY);
                        Config.imageBitmap=imageBitmap.toString();
                        if (imageBitmap!= null) {
                            bitmap = Bitmap.createScaledBitmap(imageBitmap, 400, 400, false);
                            setBitmapToImageView(bitmap);
                        }
                    }
                }
            }
            if (Config.currentImagePAth!=null){
                UploadedImage1= Config.currentImagePAth;
            }else if (Config.imageBitmap!=null){
                UploadedImage1= Config.imageBitmap;
            }else if (Config.selectedImagePath!=null){
                UploadedImage1=Config.selectedImagePath;
            }
        }
    }

    private void setUriToImageView(Uri uriToImageView) {
        if (BookImage.getDrawable()==null){
            BookImage.setImageURI(uriToImageView);
        }else {
            BookImage.setImageURI(uriToImageView);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState!=null) {
            Config.AuthList = (ArrayList<StudentsEntity>) savedInstanceState.getSerializable(KEY_AUTHList);
            Config.AuthPosition = savedInstanceState.getInt(KEY_AuthPosition);
            if (Config.AuthList != null) {
                PopulateExistingAuthorsList(Config.AuthList, Config.AuthPosition);
            }
            Config.BooksList = (ArrayList<StudentsEntity>) savedInstanceState.getSerializable(KEY_BooksLIST);
            Config.BookPosition = savedInstanceState.getInt(KEY_POSITION);
            if (Config.BooksList != null) {
                PopulateExistingBooksList(Config.BooksList, Config.BookPosition);
            }
            ImageFileUri= Uri.parse(savedInstanceState.getString(ImageURL_KEY));
            if (ImageFileUri!=null){
                setUriToImageView(ImageFileUri);
            }
        }else {
            if (verifyConnection.isConnected()) {
                RetrieveAuthorsAsyncTask retrieveAuthorsAsyncTask = new RetrieveAuthorsAsyncTask((RetrieveAuthorsAsyncTask.OnAuthorsRetrievalTaskCompleted) FragmentNewBookDetails.this, getActivity());
                retrieveAuthorsAsyncTask.execute(AuthURL);
                RetrieveBooksAsyncTask retrieveBooksAsyncTask = new RetrieveBooksAsyncTask((RetrieveBooksAsyncTask.OnBooksRetrievalTaskCompleted) FragmentNewBookDetails.this, getActivity());
                retrieveBooksAsyncTask.execute(BooksURL);
            }
        }

        CircleImageLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditAuthorName.setVisibility(View.VISIBLE);
                Config.Author_Edit=true;
            }
        });

        Next_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookName= Edit_addBook.getText().toString();
                if (Config.Author_Edit){
                    AuthorName=EditAuthorName.getText().toString();
                }else {
                    AuthorName= Auth_spinner.getSelectedItem().toString();
                }
                ISBN_Num= Edit_isbnNum.getText().toString();
                FacultyName= Faculty_spinner.getSelectedItem().toString();
                PublishYear= Edit_PublishYear.getText().toString();
                ImageUri=Config.ImageFileUri;
                ((FragmentNewBookDetails.OnNextDetailsRequired) getActivity()).onNextNewBookNameDetailsNeeded(BookName,AuthorName,ISBN_Num,FacultyName,PublishYear, ImageUri.toString());
            }
        });

        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialougeChooseCameraOrGallery();
            }
        });

        Auth_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Config.AuthorTitle= Config.AuthList.get(position).getAuthorTitle();
                Config.AuthorID= Config.AuthList.get(position).getAuthorID();
                Config.AuthPosition= position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Faculty_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Config.FacultyName = Config.BooksList.get(position).getFacultyName();
                Config.FacultyID= Config.BooksList.get(position).getFacultyID();
                Config.FacultyPosition=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void PopulateExistingAuthorsList(ArrayList<StudentsEntity> AuthorssList, int auth_Position) {
        CustomSpinnerAdapter customSpinnerAdapterFaculties = new CustomSpinnerAdapter(getActivity(), AuthorssList);
        Auth_spinner.setAdapter(customSpinnerAdapterFaculties);
        Auth_spinner.setSelection(auth_Position);
    }

    @Override
    public void onAuthorsRetrievalApiTaskCompleted(ArrayList<StudentsEntity> result) {
        if (result.size() > 0) {
            Config.AuthList=result;
            PopulateExistingAuthorsList(result, 0);
        }
    }

    @Override
    public void onBooksRetrievalApiTaskCompleted(ArrayList<StudentsEntity> result) {
        if (result.size() > 0) {
            Config.BooksList=result;
            PopulateExistingBooksList(result, 0);
        }
    }

    private void PopulateExistingBooksList(ArrayList<StudentsEntity> result, int position) {
        CustomSpinnerAdapter customSpinnerAdapterFaculties = new CustomSpinnerAdapter(getActivity(), result);
        Faculty_spinner.setAdapter(customSpinnerAdapterFaculties);
        Faculty_spinner.setSelection(position);
    }

    private void DialougeChooseCameraOrGallery() {
        Intent pickIntent = new Intent();
        pickIntent.setType(IMAGE_TYPE);
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);
        Intent takePhotoIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        String pickTitle = this
                .getResources()
                .getString(R.string.chooser_Intent_select_or_take_picture); // Or
        // get
        // from
        // strings.xml
        Intent chooserIntent = Intent.createChooser(pickIntent,
                pickTitle);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                new Intent[] { takePhotoIntent });
        startActivityForResult(chooserIntent, SELECT_PICTURE);
    }

    private void setBitmapToImageView(Bitmap imageBitmap) {
        if (BookImage.getDrawable()==null){
            BookImage.setImageBitmap(imageBitmap);
            BookImage.setVisibility(View.VISIBLE);
            HasImage=true;
        }else {
            BookImage.setImageBitmap(imageBitmap);
            HasImage=true;
        }
    }

    private Bitmap LoadThenDecodeBitmap(){
        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        selectedImagePath= cursor.getString(columnIndex);
        imageBitmap= decodeSampledBitmapFromResource(selectedImagePath,100,100);
        selectedImagePath=selectedImagePath;
        Config.imageBitmap=imageBitmap.toString();
        Config.image_name=selectedImagePath;
        return imageBitmap;
    }


    public static Bitmap decodeSampledBitmapFromResource(String selectedImagePath, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath,options);
        Config.selectedImagePath=selectedImagePath;
        Config.image_name=selectedImagePath;
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeResource(res, resId, options);
        return BitmapFactory.decodeFile(selectedImagePath, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private File createImageFile() throws IOException {
        //create image name
        File image = null;
        CreateImageFileName();
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Config.StorageDir=storageDirectory;
        image = CreateTempFileMethod(storageDirectory);
        return image;
    }

    private void CreateImageFileName(){
        String timpstamp = new SimpleDateFormat(SampleDateFormat_KEY).format(new Date());
        imageFileName = timpstamp+"1" + JPEG_KEY ;
        Config.image_name=imageFileName;
    }

    @NonNull
    private File CreateTempFileMethod(File storageDirectory) throws IOException {
        File image;
        image = File.createTempFile(imageFileName, JPG_EXTENSION, storageDirectory);
        //save file name
        currentImagePAth = FILE_EXTENSION + image.getAbsolutePath();
        currentImagePAth=currentImagePAth;
        return image;
    }

    private void  addPicToPhone(){
        Intent mediaScanIntent= new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f= new File(currentImagePAth);
        Uri contentUri= Uri.fromFile(f);
        selectedImage=contentUri;
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    public interface OnNextDetailsRequired{
        void onNextNewBookNameDetailsNeeded(String BookName, String  AuthorName, String  ISBN_Num, String FacultyName, String PublishYear, String ImageUri);
    }
}