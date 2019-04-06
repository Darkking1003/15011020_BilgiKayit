package yildiz.com.mobil2019;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.io.FileNotFoundException;

public class saved_activity extends AppCompatActivity {
    Button Courses;
    TextView Ad,Soyad,Dogumyeri,Dogumtarihi,telefon,Yaş;
    String deneme;
    ImageView Image;
    private static final int Request_Call=1;
    final Context context=this;
    final static String TAG="SavedAct";
    android.app.Application Application;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar*/
        setContentView(R.layout.saved_screen);
        if (savedInstanceState != null){
            Log.d("Deneme", "onCreate: buradayım "+savedInstanceState.getString("Ad"));
        }
        getSupportActionBar().setTitle("Kayıtlı Kişi"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Application=getApplication();

        Courses=(Button) findViewById(R.id.Ders);
        Ad=(TextView) findViewById(R.id.Ad_Saved);
        Soyad=(TextView) findViewById(R.id.Soyad_Saved);
        Dogumyeri=(TextView) findViewById(R.id.Dogum_yeri_Saved);
        Dogumtarihi=(TextView) findViewById(R.id.Dogum_tarihi_Saved);
        telefon=(TextView)findViewById(R.id.Telefon);
        Yaş=(TextView) findViewById(R.id.Yaş);
        Image=(ImageView) findViewById(R.id.Image);

        Intent pre_intent=getIntent();
        Log.d(TAG, "onCreate: "+pre_intent.getStringExtra("Age"));
        Ad.setText(pre_intent.getStringExtra("Ad"));
        Soyad.setText(pre_intent.getStringExtra("Soyad"));
        Dogumyeri.setText(pre_intent.getStringExtra("DogYer"));
        Dogumtarihi.setText(pre_intent.getStringExtra("DogTar"));
        telefon.setText(pre_intent.getStringExtra("Telefon"));
        Yaş.setText(pre_intent.getStringExtra("Age"));
        String ImagePath= pre_intent.getStringExtra("Image");
        Log.d(TAG, "onCreate: "+ImagePath);
        if(ImagePath.compareTo("Image Yok")==0){
            Image.setImageDrawable(getDrawable(R.drawable.unknown));
            Log.d(TAG, "onCreate: Image Yok");
        }else {
            Uri ImgP = Uri.parse(ImagePath);

            try {
                Bitmap btmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImgP);

                Image.setImageBitmap(btmap);
            } catch (java.io.IOException e1) {
                e1.printStackTrace();
            }
        }


        Log.d("Deneme", "onCreate: Burdayım "+deneme);



        Courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, yildiz.com.mobil2019.Courses.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("Deneme", "onRestoreInstanceState: Burdayım");
        deneme=savedInstanceState.getString("Ad");
        Ad.setText(savedInstanceState.getString("Ad"));
        Soyad.setText(savedInstanceState.getString("Soyad"));
        Dogumtarihi.setText(savedInstanceState.getString("DogTar"));
        Dogumyeri.setText(savedInstanceState.getString("DogYer"));
        telefon.setText(savedInstanceState.getString("Telefon"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Ad",Ad.getText().toString());
        outState.putString("Soyad",Soyad.getText().toString());
        outState.putString("DogTar",Dogumtarihi.getText().toString());
        outState.putString("DogYer",Dogumyeri.getText().toString());
        outState.putString("Telefon",telefon.getText().toString());
    }

    public void MakeACall(View arg0){

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage("Aramayı Direk mi yapmak istersiniz yoksa arama tuşlama penceresine gitmek mi");
        builder.setPositiveButton("Direk", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + telefon.getText().toString()));
                if(ContextCompat.checkSelfPermission(saved_activity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(saved_activity.this,new String[]{Manifest.permission.CALL_PHONE},Request_Call);
                }else {
                    Application.startActivity(intent);
            }
        }
        });
            builder.setNegativeButton("Tuşlama Penceresine", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    final Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + telefon.getText().toString()));
                    Application.startActivity(intent);
                }
            });
            builder.create().show();


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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case Request_Call:{
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + telefon.getText().toString()));
                    Application.startActivity(intent);
                }else
                    return;
            }

        }
    }
}
