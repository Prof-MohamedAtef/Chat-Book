package mo.ed.prof.yusor.Activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.ed.prof.yusor.Fragments.DatePickerFragment;
import mo.ed.prof.yusor.Fragments.FragmentPriecsSuggestions;
import mo.ed.prof.yusor.R;
import mo.ed.prof.yusor.helpers.Config;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AddBookActivity extends AppCompatActivity {

    private Date Now;
    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    String imageFileName;
    String currentImagePAth;
    @BindView(R.id.camera)
    ImageView Camera;
    @BindView(R.id.BookImage)
    ImageView BookImage;
    @BindView(R.id.edit_book_name)
    EditText edit_book_name;
    @BindView(R.id.faculties_spinner)
    Spinner faculties_spinner;

    String BookName;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 55;
    private String IMAGE_TYPE="image/*";
    final static int SELECT_PICTURE=12;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    protected static final int GALLERY_PICTURE = 1;
    final static int RESULT_LOAD_IMAGE=11;
    String selectedImagePath;
    Uri selectedImage;
    Bitmap bitmap;
    Bitmap imageBitmap;
    BitmapFactory.Options imageBitmapOp;
    String [] filePathColumn;
    private boolean HasImage=false;
    private String UploadedImage1;
    private String DATA_KEY="data";
    private java.lang.String SampleDateFormat_KEY="yyyyMMdd_HHmmss";
    private String JPEG_KEY="JPEG_";
    private java.lang.String JPG_EXTENSION=".jpg";
    private String FILE_EXTENSION="file:";


    @BindView(R.id.edit_publish_year_picker)
    EditText EditPublishYearBicker;
    @BindView(R.id.edit_checkPrice)
    EditText EditCheckPrice;
    @BindView(R.id.fragment_prices_suggestions_container)
    LinearLayout fragment_prices_suggestions_container;
    private java.lang.String DatePicker="datePicker";
    private String Science="SCIENCE";
    private String LITERATURE="LITERATURE";
    private String AGRICULTURAL_SCIENCES="AGRICULTURAL_SCIENCES";
    private String EDUCATION="EDUCATION";
    FragmentPriecsSuggestions fragmentPriecsSuggestions;
    public static String BOOK_NAME="BOOK_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        ButterKnife.bind(this);
        fragmentPriecsSuggestions=new FragmentPriecsSuggestions();
        EditPublishYearBicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogue();
                String slash=getString(R.string.slash);
                EditPublishYearBicker.setText(Config.Day+slash+Config.Month+slash+Config.Year);
            }
        });

        EditCheckPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBooksPricesAvailable();
            }
        });

//        ArrayList<StudentsEntity> FacultiesList= new ArrayList<String>();
//        FacultiesList.add(Science);
//        FacultiesList.add(LITERATURE);
//        FacultiesList.add(AGRICULTURAL_SCIENCES);
//        FacultiesList.add(EDUCATION);
//        CustomSpinnerAdapter customSpinnerAdapterFaculties = new CustomSpinnerAdapter(getApplicationContext(), FacultiesList);
//        faculties_spinner.setAdapter(customSpinnerAdapterFaculties);

        calendar = new Calendar() {
            @Override
            protected void computeTime() {
            }

            @Override
            protected void computeFields() {

            }

            @Override
            public void add(int field, int amount) {

            }

            @Override
            public void roll(int field, boolean up) {

            }

            @Override
            public int getMinimum(int field) {
                return 0;
            }

            @Override
            public int getMaximum(int field) {
                return 0;
            }

            @Override
            public int getGreatestMinimum(int field) {
                return 0;
            }

            @Override
            public int getLeastMaximum(int field) {
                return 0;
            }
        };
        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialougeChooseCameraOrGallery();
            }
        });


    }

    private void getBooksPricesAvailable() {
        BookName= edit_book_name.getText().toString();
        /*
        connect 2 api using book name to retrieve similar books
        launch books fragment
        display books with prices
        enable user to enter suitable price
         */
        Bundle bundle=new Bundle();
        bundle.putString(BOOK_NAME,BOOK_NAME);
        fragmentPriecsSuggestions.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_prices_suggestions_container, fragmentPriecsSuggestions, "newsApi")
                .commit();
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


    private void showDatePickerDialogue() {
        DialogFragment newFragment = new DatePickerFragment();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.container_frame, booksGalleryFragment , "newsApi")
//                .commit();
        newFragment.show(getFragmentManager(), DatePicker);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_CANCELED&&data!=null){
            ActivityCompat.requestPermissions(AddBookActivity.this,
                    new String[]{WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get(DATA_KEY);
                setBitmapToImageView(imageBitmap);
                try{
                    createImageFile();
                    addPicToPhone();
                    BookImage.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            else if (requestCode == GALLERY_PICTURE && resultCode == RESULT_OK) {
                if (data != null) {
                    Bundle selectedImage = data.getExtras();
                    filePathColumn = new String[]{MediaStore.Images.Media.DATA};
                    String filePath =  MediaStore.Images.Media.DATA ;
                    Bitmap  imagebitmap=(Bitmap)selectedImage.get(DATA_KEY);
                    Config.imageBitmap=imagebitmap.toString();
                    Cursor c = getContentResolver().query(Uri.parse(filePath), filePathColumn, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumn[0]);
                    selectedImagePath = c.getString(columnIndex);
                    Config.image_name=selectedImagePath;
                    c.close();
                    if (selectedImagePath != null) {
                        bitmap = BitmapFactory.decodeFile(selectedImagePath);
                        bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);
                        BookImage.setVisibility(View.VISIBLE);
                        BookImage.setImageBitmap(bitmap);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.canceled),
                            Toast.LENGTH_SHORT).show();
                }
            }else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
                selectedImage = data.getData();
                filePathColumn = new String[]{MediaStore.Images.Media.DATA};
                imageBitmap= LoadThenDecodeBitmap();
                setBitmapToImageView(imageBitmap);
            }else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK){
                selectedImage = data.getData();
                filePathColumn = new String[]{MediaStore.Images.Media.DATA};
                if (selectedImage!=null){
                    imageBitmap= LoadThenDecodeBitmap();
                    setBitmapToImageView(imageBitmap);
                }else {
                    Bundle selectedImage = data.getExtras();
                    filePathColumn = new String[]{MediaStore.Images.Media.DATA};
                    imageBitmap=(Bitmap)selectedImage.get(DATA_KEY);
                    Config.imageBitmap=imageBitmap.toString();
                    if (imageBitmap!= null) {
                        bitmap = Bitmap.createScaledBitmap(imageBitmap, 400, 400, false);
                        setBitmapToImageView(bitmap);
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
        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        selectedImagePath= cursor.getString(columnIndex);
        imageBitmap= decodeSampledBitmapFromResource(selectedImagePath,100,100);
        Config.selectedImagePath=selectedImagePath;
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
        Config.currentImagePAth=currentImagePAth;
        return image;
    }

    private void  addPicToPhone(){
        Intent mediaScanIntent= new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f= new File(currentImagePAth);
        Uri contentUri= Uri.fromFile(f);
        selectedImage=contentUri;
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
}