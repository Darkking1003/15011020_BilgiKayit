package yildiz.com.mobil2019;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.BaseKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    Button Kaydet,Clear;
    EditText Ad_txt,Soyad_txt,DogYer_txt,DogTar_txt,telefon_txt;
    String Ad,Soyad,Dogum_yeri,Dogum_tarihi;

    ImageView Image;
    int Age;
    private static final String TAG="MainAct";
    static final int IMAGE_Galery_REQUEST=1;
    static final int Camera_Permission_Request=2;
    static final int Camera_Activity_Request=3;
    static final int EXTERNAL_STORAGE_PERMISSION=4;
    final Context context=this;
    String ImagePath="Image Yok";
    android.app.Application Application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = this;

        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION);
        }

        getSupportActionBar().setTitle("Kayıt Formu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImagePath="Image Yok";

        Kaydet=(Button) findViewById(R.id.Kaydet);
        Clear=(Button) findViewById(R.id.Clear);
        Image=(ImageView) findViewById(R.id.Image);
        Drawable drawable=getDrawable(R.drawable.unknown);
        Image.setImageDrawable(drawable);
        Ad_txt=(EditText) findViewById(R.id.Ad_Saved);
        Soyad_txt=(EditText) findViewById(R.id.Soyad_Saved);
        DogYer_txt=(EditText) findViewById(R.id.Doğum_yeri);
        DogTar_txt=(EditText) findViewById(R.id.Doğum_tarihi);
        telefon_txt=(EditText) findViewById(R.id.Telefon);



        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ad_txt.setText("");
                Soyad_txt.setText("");
                DogTar_txt.setText("");
                DogYer_txt.setText("");
                telefon_txt.setText("");
                Drawable drawable=getDrawable(R.drawable.unknown);
                Image.setImageDrawable(drawable);
            }
        });

        Kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("OnClick", "onClick: deneme");
                Intent intent=new Intent(context,saved_activity.class);
                Log.d("onClick", "onClick: "+Ad_txt.getText());
                if(CheckAllEmpty()){
                    if(CheckAllNumber()){
                        if(telefon_txt.getText().toString().length()==14) {
                            FindAge();
                            intent.putExtra("Ad", Ad_txt.getText().toString());
                            intent.putExtra("Soyad", Soyad_txt.getText().toString());
                            intent.putExtra("DogYer", DogYer_txt.getText().toString());
                            intent.putExtra("DogTar", DogTar_txt.getText().toString());
                            Log.d(TAG, "onClick: " + telefon_txt.getText().toString());
                            intent.putExtra("Telefon", telefon_txt.getText().toString());
                            Log.d(TAG, "onClick: "+Age);
                            intent.putExtra("Age",Integer.toString(Age));


                            ImagePath=SaveImage();
                            Log.d(TAG, "onClick: Image Varmı "+ImagePath);
                            intent.putExtra("Image",ImagePath);

                            startActivity(intent);
                        }else{
                            Toast hata=Toast.makeText(getApplicationContext(),"Lutfen Dogru Telefon Numarası Giriniz",Toast.LENGTH_SHORT);
                            hata.show();
                        }
                    }else{
                        Toast hata=Toast.makeText(getApplicationContext(),"Lutfen Sayı Girmeyiniz",Toast.LENGTH_SHORT);
                        hata.show();

                    }
                }else{
                    Toast hata=Toast.makeText(getApplicationContext(),"Lutfen Bütün alanları giriniz",Toast.LENGTH_SHORT);
                    hata.show();

                }

            }
        });
        DogTar_txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    final Calendar takvim = Calendar.getInstance();
                    int yil = takvim.get(Calendar.YEAR);
                    int ay = takvim.get(Calendar.MONTH);
                    int gun = takvim.get(Calendar.DAY_OF_MONTH);
                    CloseKeyboard(view);
                    DatePickerDialog dpd = new DatePickerDialog(context,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    month += 1;
                                    DogTar_txt.setText(dayOfMonth + "/" + month + "/" + year);
                                }
                            }, yil, ay, gun);

                    dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Seç", dpd);
                    dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", dpd);
                    dpd.show();
                }
            }
        });




        telefon_txt.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned spanned, int i2, int i3) {
                        if (source.length()>1){
                            return source;
                        }
                        for(int z=start;z<end;z++){
                            Log.d(TAG, "filter: "+source.charAt(z));
                            if(!Character.isDigit(source.charAt(z))||Character.isWhitespace(source.charAt(z))){
                                Log.d(TAG, "filter: Yanlış");
                                return "";
                            }
                        }

                            Log.d(TAG, "filter: "+spanned);
                        if(((spanned.length()==4)||(spanned.length()==8)||(spanned.length()==11))&&(source!="")){

                            return " "+source;
                        }
                        else{
                            return null;
                        }

                    }
                },
                new InputFilter.LengthFilter(14)
        });

    }


    public void Picture(View arg0){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage("Nasıl Fotograf Almak Istersiniz?");
        builder.setPositiveButton("Galeriden", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,IMAGE_Galery_REQUEST);
            }
        });
        builder.setNegativeButton("Kameradan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(checkSelfPermission(Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
                    Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camera,Camera_Activity_Request);
                }else{
                    requestPermissions(new String[]{Manifest.permission.CAMERA},Camera_Permission_Request);
                }
            }
        });
        builder.create().show();
    }

    protected String SaveImage(){
        Bitmap bitmap=((BitmapDrawable)Image.getDrawable()).getBitmap();

        if(bitmap==((BitmapDrawable)getDrawable(R.drawable.unknown)).getBitmap()){
            return "Image Yok";
        }

        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {
            ImagePath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Profile_Image", "Passing to another activity");
            Log.d(TAG, "onClick: Image " + ImagePath);
            return ImagePath;
        }else{
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION);
            return "Image Yok";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMAGE_Galery_REQUEST){
            if(resultCode== RESULT_OK){
                Uri TargetUri=data.getData();
                Bitmap bitmap;
                try{
                    bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(TargetUri));
                    Image.setImageBitmap(bitmap);
                    Image.setBackgroundColor(Color.rgb(255,255,255));
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }else{
                Drawable drawable=getDrawable(R.drawable.unknown);
                Image.setImageDrawable(drawable);
            }
        }else if(requestCode==Camera_Activity_Request){
            if(resultCode==RESULT_OK) {
                Bitmap img = (Bitmap) data.getExtras().get("data");
                Image.setImageBitmap(img);
            }else{
                Drawable drawable=getDrawable(R.drawable.unknown);
                Image.setImageDrawable(drawable);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==Camera_Permission_Request){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera,Camera_Activity_Request);
            }else{
                Drawable drawable=getDrawable(R.drawable.unknown);
                Image.setImageDrawable(drawable);
            }
        }else if(requestCode==EXTERNAL_STORAGE_PERMISSION){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED) {

            }else{

            }
        }
    }

    protected void FindAge(){
        String tarih=DogTar_txt.getText().toString();
        String Yıl[]=tarih.split("/",0);
        int yıl=Integer.parseInt(Yıl[2]);
        int thisyear= Calendar.getInstance().get(Calendar.YEAR);
        Age=thisyear-yıl;
        Log.d(TAG, "onFocusChange: "+Age);
    }
    protected Boolean CheckAllEmpty(){
        if(CheckifEmpty(Ad_txt)&&CheckifEmpty(Soyad_txt)&&CheckifEmpty(DogYer_txt)&&CheckifEmpty(DogTar_txt)){
            return true;
        }else{
            return false;
        }

    }

    protected Boolean CheckifEmpty(EditText textbox){
        String text=textbox.getText().toString();
        if(text.isEmpty()){
            return false;
        }else{
            return true;
        }

    }
    protected Boolean CheckAllNumber(){
        if(CheckifNumber(Ad_txt)&&CheckifNumber(Soyad_txt)&&CheckifNumber(DogYer_txt)){
            return true;
        }else{
            return false;
        }
    }
    protected Boolean CheckifNumber(EditText textbox){
        String text=textbox.getText().toString();
        if(text.matches(".*\\d.*")){
            return false;
        }else{
            return true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Deneme", "onSaveInstanceState: Burdayım");
        outState.putString("Ad",Ad_txt.getText().toString());
        outState.putString("Soyad",Soyad_txt.getText().toString());
        outState.putString("DogTar",DogTar_txt.getText().toString());
        outState.putString("DogYer",DogYer_txt.getText().toString());
        outState.putString("Telefon",telefon_txt.getText().toString());
        Log.d(TAG, "onSaveInstanceState: "+telefon_txt.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Ad_txt.setText(savedInstanceState.getString("Ad"));
        Soyad_txt.setText(savedInstanceState.getString("Soyad"));
        DogYer_txt.setText(savedInstanceState.getString("DogYer"));
        DogTar_txt.setText(savedInstanceState.getString("DogTar"));
        Log.d(TAG, "onRestoreInstanceState: "+savedInstanceState.getString("Telefon"));
        telefon_txt.setText(savedInstanceState.getString("Telefon"));
    }

    public void CloseKeyboard(View arg0){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
