package in.therenter.therenterfirebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class ProductsActivity extends AppCompatActivity {

    private static final String[] day = {"/day", "/7 days", "/10 days", "/15 days", "/30 days"};
    private static final String[] month = {"/month", "/3 months", "/6 months", "/9 months", "/12 months"};
    private static int RESULT_LOAD_IMG = 1;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private AutoCompleteTextView auto;
    private MultiAutoCompleteTextView auto2;
    private MultiAutoCompleteTextView auto3;
    private boolean isDays = true;
    private String prods;
    private String tags;
    private String cats;
    private String imageString;

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        preferences = getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();

        auto = (AutoCompleteTextView) findViewById(R.id.autoCompleteProductType);
        auto2 = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTags);
        auto3 = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteCategories);

        ImageView img = (ImageView) findViewById(R.id.imageViewProd);
        assert img != null;
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });

        final TextView extra1 = (TextView) findViewById(R.id.textViewExtra1);
        TextView extra2 = (TextView) findViewById(R.id.textViewExtra2);
        TextView extra3 = (TextView) findViewById(R.id.textViewExtra3);
        TextView extra4 = (TextView) findViewById(R.id.textViewExtra4);
        TextView extra5 = (TextView) findViewById(R.id.textViewExtra5);

        final CheckBox autoRent = (CheckBox) findViewById(R.id.checkBoxAutoRent);

        final EditText text1 = (EditText) findViewById(R.id.editTextRent1);
        final EditText text2 = (EditText) findViewById(R.id.editTextRent2);
        final EditText text3 = (EditText) findViewById(R.id.editTextRent3);
        final EditText text4 = (EditText) findViewById(R.id.editTextRent4);
        final EditText text5 = (EditText) findViewById(R.id.editTextRent5);
        assert text1 != null;
        assert text2 != null;
        assert text3 != null;
        assert text4 != null;
        assert text5 != null;
        text1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                assert autoRent != null;
                if (autoRent.isChecked()) {

                    if (s.length() == 0) {
                        text2.setText("");
                        text3.setText("");
                        text4.setText("");
                        text5.setText("");
                    } else {

                        if (isDays) {
                            text2.setText(String.valueOf(Integer.parseInt(s.toString()) * 7));
                            text3.setText(String.valueOf(Integer.parseInt(s.toString()) * 10));
                            text4.setText(String.valueOf(Integer.parseInt(s.toString()) * 15));
                            text5.setText(String.valueOf(Integer.parseInt(s.toString()) * 30));
                        } else {
                            text2.setText(String.valueOf(Integer.parseInt(s.toString()) * 3));
                            text3.setText(String.valueOf(Integer.parseInt(s.toString()) * 6));
                            text4.setText(String.valueOf(Integer.parseInt(s.toString()) * 9));
                            text5.setText(String.valueOf(Integer.parseInt(s.toString()) * 12));
                        }
                    }
                }
            }
        });

        final TextView arr[] = new TextView[5];
        arr[0] = extra1;
        arr[1] = extra2;
        arr[2] = extra3;
        arr[3] = extra4;
        arr[4] = extra5;

        Spinner rentalPeriod = (Spinner) findViewById(R.id.spinnerRentalPeriod);

        assert rentalPeriod != null;
        rentalPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < 5; i++) {
                    if (position == 0) {
                        isDays = true;
                        arr[i].setText(day[i]);
                    } else {
                        isDays = false;
                        arr[i].setText(month[i]);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!preferences.contains("prods"))
            editor.putString("prods", "").apply();
        else {
            if (!(preferences.getString("prods", "").length() == 0)) {
                String[] abc = preferences.getString("prods", "").trim().split("\\s*,\\s*");
                assert auto != null;
                auto.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, abc));
            }
        }

        auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                prods = parent.getItemAtPosition(position).toString();
            }
        });

        if (!preferences.contains("tags"))
            editor.putString("tags", "").apply();
        else {
            if (!(preferences.getString("tags", "").length() == 0)) {
                String[] abc = preferences.getString("tags", "").trim().split("\\s*,\\s*");
                assert auto != null;
                auto2.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, abc));
            }
        }

        auto2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tags = parent.getItemAtPosition(position).toString();
            }
        });

        if (!preferences.contains("cats"))
            editor.putString("cats", "").apply();
        else {
            if (!(preferences.getString("cats", "").length() == 0)) {
                String[] abc = preferences.getString("cats", "").trim().split("\\s*,\\s*");
                assert auto != null;
                auto3.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, abc));
            }
        }

        auto3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cats = parent.getItemAtPosition(position).toString();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                File file = new File(imgDecodableString);
                TextView txt = (TextView) findViewById(R.id.textViewImageName);
                assert txt != null;
                txt.setText(file.getName());

//                ImageView imgView = (ImageView) findViewById(R.id.imageViewMain);
                // Set the Image in ImageView after decoding the String
                Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString);
//                imgView.setImageBitmap(bitmap);

                imageString = encodeToBase64(bitmap, Bitmap.CompressFormat.PNG, 100);

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void done(View view) {


    }
}