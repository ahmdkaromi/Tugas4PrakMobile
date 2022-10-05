package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private CircleImageView avatarImg;
    private ImageView inputImgBtn;
    private TextInputEditText et_fullname, et_email, et_password, et_confirm_password, et_homepage, et_about;
    private Button submitBtn;

    Bitmap bitmap;
    public static final String IMAGE_KEY = "image";
    public static final String FULLNAME_KEY = "fullname";
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";
    public static final String CONFIRM_PASSWORD_KEY = "confirm_password";
    public static final String HOMEPAGE_KEY = "homepage";
    public static final String ABOUT_KEY = "about";

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        avatarImg = findViewById(R.id.image_profile);
        inputImgBtn = findViewById(R.id.imageView);
        et_fullname = findViewById(R.id.text_fullname);
        et_email = findViewById(R.id.text_email);
        et_password = findViewById(R.id.text_password);
        et_confirm_password = findViewById(R.id.text_confirm_password);
        et_homepage = findViewById(R.id.text_homepage);
        et_about = findViewById(R.id.text_about);
        submitBtn = findViewById(R.id.button_ok);

        inputImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY_REQUEST_CODE);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarImg.buildDrawingCache();
                Bitmap img = avatarImg.getDrawingCache();

                Bundle extras = new Bundle();
                extras.putParcelable(IMAGE_KEY, img);

                String fullname = et_fullname.getText().toString();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                String confirmPassword = et_confirm_password.getText().toString();
                String homepage = et_homepage.getText().toString();
                String about = et_about.getText().toString();

                Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                intent.putExtras(extras);
                intent.putExtra(FULLNAME_KEY, fullname);
                intent.putExtra(EMAIL_KEY, email);
                intent.putExtra(PASSWORD_KEY, password);
                intent.putExtra(CONFIRM_PASSWORD_KEY, confirmPassword);
                intent.putExtra(HOMEPAGE_KEY, homepage);
                intent.putExtra(ABOUT_KEY, about);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_CANCELED){
            Toast.makeText(this, "Image input cancelled", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode == GALLERY_REQUEST_CODE){
            if(data != null){
                try {
                    Uri imageUri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    avatarImg.setImageBitmap(bitmap);
                }
                catch (IOException e){
                    Toast.makeText(this, "Cannot load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }
}
