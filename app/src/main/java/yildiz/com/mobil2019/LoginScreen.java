package yildiz.com.mobil2019;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity {
    TextView KullaniciA,Sifre;
    Button Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        final Context context=this;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        KullaniciA=(TextView) findViewById(R.id.KullanıcıAdı);
        Sifre=(TextView) findViewById(R.id.Sifre);
        Login=(Button) findViewById(R.id.Login);

        LinearLayout.LayoutParams param=(LinearLayout.LayoutParams) KullaniciA.getLayoutParams();
        param.topMargin=width/2-50;

        KullaniciA.setLayoutParams(param);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ad=KullaniciA.getText().toString();
                String Password=Sifre.getText().toString();
                Log.d("Deneme", "onClick: "+"admin".equals(ad));
                if("admin".equals(ad)&&"password".equals(Password)){
                    Intent intent=new Intent(context,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast toast=Toast.makeText(context,"Yanlis kullanici adı veya sifre",Toast.LENGTH_SHORT);
                    toast.show();
                }
                Log.d("Deneme", "onClick: "+ad+" "+Password);
            }
        });

    }

    public void CloseKeyboard(View arg0){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
