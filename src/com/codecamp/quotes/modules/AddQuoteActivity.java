package com.codecamp.quotes.modules;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.codecamp.quotes.R;
import com.parse.*;

import java.io.*;

/**
 * Created by david on 4/25/14.
 */
public class AddQuoteActivity extends Activity {
    private static final int PICK_IMAGE = 1;
    private String imageFilePath="";
    private byte[] imageBytes;
    private Context mContext;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_quote);
        mContext=this;
    }

    public void saveQuote(View view) {
        if(!imageFilePath.isEmpty()){
            File file = new File(imageFilePath);
            int size = (int) file.length();
            imageBytes = new byte[size];
            try {
                BufferedInputStream imageBuffer = new BufferedInputStream(new FileInputStream(file));
                imageBuffer.read(imageBytes, 0, imageBytes.length);
                imageBuffer.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Object drawable = getResources().getDrawable(R.drawable.ic_launcher);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            imageBytes = stream.toByteArray();
        }
        if(imageBytes!=null){
            final ParseFile emotiFile = new ParseFile("emoti.jpg",imageBytes);
            emotiFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                     if(e==null){
                         ParseObject quote = new ParseObject("Quote");
                         quote.put("content", ((EditText)findViewById(R.id.etAddQuote)).getText().toString());
                         quote.put("emoti", emotiFile);
                         quote.put("user", ParseUser.getCurrentUser());
                         quote.saveInBackground(new SaveCallback() {
                             @Override
                             public void done(ParseException e) {
                                 if(e==null){
                                     ((Activity)mContext).finish();
                                 }
                                 else {
                                     Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_LONG).show();
                                 }
                             }
                         });
                     }
                }
            });
        }
    }

    public void selectImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && data != null && data.getData() != null) {
            Uri _uri = data.getData();
            Cursor cursor = getContentResolver().query(_uri, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
            cursor.moveToFirst();
            imageFilePath = cursor.getString(0);
            File imgFile = new  File(imageFilePath);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ImageView myImage = (ImageView) findViewById(R.id.ivSelectedImage);
                myImage.setImageBitmap(myBitmap);
            }
            cursor.close();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}