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

    private CircleImageView image_profile;
    private ImageView input_img;
    private TextInputEditText input_fullname, input_email, input_password, input_confirm_password, input_homepage, input_about;
    private Button button_OK;

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

        image_profile = findViewById(R.id.image_profile);
        input_img = findViewById(R.id.imageView);
        input_fullname = findViewById(R.id.text_fullname);
        input_email = findViewById(R.id.text_email);
        input_password = findViewById(R.id.text_password);
        input_confirm_password = findViewById(R.id.text_confirm_password);
        input_homepage = findViewById(R.id.text_homepage);
        input_about = findViewById(R.id.text_about);
        button_OK = findViewById(R.id.button_ok);

        input_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY_REQUEST_CODE);
            }
        });

        button_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_profile.buildDrawingCache();
                Bitmap img = image_profile.getDrawingCache();

                Bundle extras = new Bundle();
                extras.putParcelable(IMAGE_KEY, img);

                String fullname = input_fullname.getText().toString();
                String email = input_email.getText().toString();
                String password = input_password.getText().toString();
                String confirmPassword = input_confirm_password.getText().toString();
                String homepage = input_homepage.getText().toString();
                String about = input_about.getText().toString();

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
                    image_profile.setImageBitmap(bitmap);
                }
                catch (IOException e){
                    Toast.makeText(this, "Cannot load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }
}
