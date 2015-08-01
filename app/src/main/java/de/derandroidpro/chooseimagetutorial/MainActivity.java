package de.derandroidpro.chooseimagetutorial;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    ImageView iv;
    Button btn, btnshare;
    Intent intent1;
    final int requcode = 3;
    Uri bilduri;
    Bitmap bm;
    InputStream is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.imageView);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("image/*");
                startActivityForResult(intent1,requcode );



            }
        });

        btnshare = (Button) findViewById(R.id.button2);
        btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareintent = new Intent();
                shareintent.setAction(Intent.ACTION_SEND);
                shareintent.setType("image/*");
                shareintent.putExtra(Intent.EXTRA_STREAM, bilduri);
                startActivity(Intent.createChooser(shareintent, "Bild senden..."));


            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){

            if(requestCode == requcode){

                bilduri = data.getData();
                try {
                    is = getContentResolver().openInputStream(bilduri);

                    BitmapFactory.Options bmoption = new BitmapFactory.Options();
                    bmoption.inSampleSize = 4;

                    bm = BitmapFactory.decodeStream(is, null, bmoption);
                    iv.setImageBitmap(bm);
                    btnshare.setEnabled(true);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }


        }



        super.onActivityResult(requestCode, resultCode, data);
    }
}
