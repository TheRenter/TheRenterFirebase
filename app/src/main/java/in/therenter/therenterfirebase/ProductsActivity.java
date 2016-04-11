package in.therenter.therenterfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class ProductsActivity extends AppCompatActivity {

    private static final String[] day = {"/day", "/7 days", "/10 days", "/15 days", "/30 days"};
    private static final String[] month = {"/month", "/3 months", "/6 months", "/9 months", "/12 months"};
    private static int RESULT_LOAD_IMG = 1;
    private boolean isDays = true;
    private String imageString = "";

    private String ProductType;
    private String Name;
    private String Brand;
    private String Model;
    private String DeliveryType = "Home Delivery";
    private String ShippingType = "Paid";
    private String Tags;
    private String Categories;
    private String RentalPeriod;
    private String Color;
    private String ShortDescription;
    private String LongDescription;
    private String ShippingCharge;
    private String Deposit;
    private String StockCount;
    private int Rent1;
    private int Rent2;
    private int Rent3;
    private int Rent4;
    private int Rent5;

    private EditText txtName;
    private EditText txtBrand;
    private EditText txtModel;
    private EditText txtColor;
    private EditText txtShortDesc;
    private EditText txtLongDesc;
    private EditText txtDeposit;
    private EditText txtRent1;
    private EditText txtRent2;
    private EditText txtRent3;
    private EditText txtRent4;
    private EditText txtRent5;
    private EditText txtStockCount;
    private EditText txtShippingCharge;
    private EditText txtProductType;
    private EditText txtTags;
    private EditText txtCategories;

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

        Firebase.setAndroidContext(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initialize();

        final ImageView img = (ImageView) findViewById(R.id.imageViewProd);
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

        Spinner deliveryType = (Spinner) findViewById(R.id.spinnerDeliveryType);
        assert deliveryType != null;
        deliveryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    DeliveryType = "Home Delivery";
                else
                    DeliveryType = "Takeaway";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner shippingType = (Spinner) findViewById(R.id.spinnerShippingType);
        assert shippingType != null;
        shippingType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    ShippingType = "Paid";
                else if (position == 1)
                    ShippingType = "Free";
                else
                    ShippingType = "NA";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button done = (Button) findViewById(R.id.buttonDone);
        assert done != null;
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog pd = new ProgressDialog(ProductsActivity.this);
                pd.setMessage("Loading");
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setIndeterminate(true);
                pd.show();

                try {

                    ProductType = txtProductType.getText().toString();
                    Tags = txtTags.getText().toString();
                    Categories = txtCategories.getText().toString();
                    Name = txtName.getText().toString();
                    Brand = txtBrand.getText().toString();
                    Model = txtModel.getText().toString();
                    Deposit = txtDeposit.getText().toString();
                    Rent1 = Integer.parseInt(txtRent1.getText().toString());
                    Rent2 = Integer.parseInt(txtRent2.getText().toString());
                    Rent3 = Integer.parseInt(txtRent3.getText().toString());
                    Rent4 = Integer.parseInt(txtRent4.getText().toString());
                    Rent5 = Integer.parseInt(txtRent5.getText().toString());
                    StockCount = txtStockCount.getText().toString();
                    Color = txtColor.getText().toString();
                    ShortDescription = txtShortDesc.getText().toString();
                    LongDescription = txtLongDesc.getText().toString();
                    ShippingCharge = txtShippingCharge.getText().toString();

                    if (isDays)
                        RentalPeriod = "Days";
                    else
                        RentalPeriod = "Months";

                    Firebase firebase = new Firebase("https://the-renter-test.firebaseio.com/");

                    Product product = new Product(imageString, Name, Brand, Model, ProductType, DeliveryType, ShippingType, Tags, Categories, RentalPeriod, Color,
                            ShortDescription, LongDescription, Deposit, ShippingCharge, StockCount, Rent1, Rent2, Rent3, Rent4, Rent5);

                    Firebase newProduct = firebase.child("products");
                    newProduct.push().setValue(product, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            pd.dismiss();

                            if (firebaseError != null) {
                                Toast.makeText(ProductsActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                                System.out.println("Data could not be saved. " + firebaseError.getMessage());
                            } else {
                                Toast.makeText(ProductsActivity.this, "Product uploaded", Toast.LENGTH_SHORT).show();

                                txtName.setText("");
                                txtBrand.setText("");
                                txtModel.setText("");
                                txtColor.setText("");
                                txtShortDesc.setText("");
                                txtLongDesc.setText("");
                                txtDeposit.setText("");
                                txtRent1.setText("");
                                txtRent2.setText("");
                                txtRent3.setText("");
                                txtRent4.setText("");
                                txtRent5.setText("");
                                txtStockCount.setText("");
                                txtShippingCharge.setText("");
                                txtProductType.setText("");
                                txtTags.setText("");
                                txtCategories.setText("");
                                img.requestFocus();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialize() {

        txtProductType = (EditText) findViewById(R.id.editTextProductType);
        txtTags = (EditText) findViewById(R.id.editTextTags);
        txtCategories = (EditText) findViewById(R.id.editTextCategories);

        txtBrand = (EditText) findViewById(R.id.editTextBrand);
        txtColor = (EditText) findViewById(R.id.editTextColor);
        txtDeposit = (EditText) findViewById(R.id.editTextDeposit);
        txtLongDesc = (EditText) findViewById(R.id.editTextLongDesc);
        txtModel = (EditText) findViewById(R.id.editTextModel);
        txtName = (EditText) findViewById(R.id.editTextName);
        txtRent1 = (EditText) findViewById(R.id.editTextRent1);
        txtRent2 = (EditText) findViewById(R.id.editTextRent2);
        txtRent3 = (EditText) findViewById(R.id.editTextRent3);
        txtRent4 = (EditText) findViewById(R.id.editTextRent4);
        txtRent5 = (EditText) findViewById(R.id.editTextRent5);
        txtShortDesc = (EditText) findViewById(R.id.editTextShortDesc);
        txtStockCount = (EditText) findViewById(R.id.editTextStockCount);
        txtShippingCharge = (EditText) findViewById(R.id.editTextShippingCharge);
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

                imageString = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 75);

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }
}