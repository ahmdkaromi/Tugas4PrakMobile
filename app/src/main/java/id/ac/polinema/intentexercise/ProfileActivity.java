package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView avatar;
    private TextView fullname, email, homepage, about;
    private Button button_homepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        avatar = findViewById(R.id.image_profile);
        about = findViewById(R.id.label_about);
        fullname = findViewById(R.id.label_fullname);
        email = findViewById(R.id.label_email);
        homepage = findViewById(R.id.label_homepage);
        button_homepage = findViewById(R.id.button_homepage);

        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("image");

        String url = getIntent().getExtras().getString("homepage");

        avatar.setImageBitmap(bmp);
        about.setText(getIntent().getExtras().getString("about"));
        fullname.setText(getIntent().getExtras().getString("fullname"));
        email.setText(getIntent().getExtras().getString("email"));
        homepage.setText(url);

        button_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebURL(url);
            }
        });
    }

    public void openWebURL( String inURL ) {
        Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( inURL ) );

        startActivity( browse );
    }
}
