package yildiz.com.mobil2019;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class DetailScreen extends AppCompatActivity {
    TextView Ad,veren,derslik,saat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);

        getSupportActionBar().setTitle("Ders Listesi"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Map<String,Classes> Data=new HashMap<String,Classes>();
        Data.put("Bilgisayar Bilimlerine Giriş",new Classes("MAG","13:00","DB-11"));
        Data.put("Programlama Dilleri",new Classes("HİT","09:00","D-012"));
        Data.put("Algoritma Analizi",new Classes("MEK","13:00","D-111"));
        Intent pre_int=getIntent();
        String DersAdi=pre_int.getStringExtra("DersAdı");

        Ad=(TextView) findViewById(R.id.DersAdi);
        veren=(TextView) findViewById(R.id.DersiVeren);
        derslik=(TextView) findViewById(R.id.Derslik);
        saat=(TextView) findViewById(R.id.Saat);

        Ad.setText(DersAdi);
        veren.setText(Data.get(DersAdi).DersiVeren);
        derslik.setText(Data.get(DersAdi).Derslik);
        saat.setText(Data.get(DersAdi).Saat);


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
