package com.example.cs478_movies_karnati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class mm2Activity extends AppCompatActivity {

    int imageshd[]= {R.drawable.parasitehd,R.drawable.avengershd,R.drawable.jokerhd,R.drawable.theirishmanhd,R.drawable.fordvsferarihd,R.drawable.blackpantherhd,R.drawable.ninnukorihd};
    String mTitleWebsites[]= {"https://www.imdb.com/title/tt6751668/","https://www.imdb.com/title/tt4154796/","https://www.imdb.com/title/tt7286456/","https://www.imdb.com/title/tt1302006/","https://www.imdb.com/title/tt1950186/","https://www.imdb.com/title/tt1825683/","https://www.imdb.com/title/tt6996016/"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm2);
        Intent getI= getIntent();
        Bundle extra = getI.getExtras();
        final int pos = extra.getInt("position");

        ImageView imageview = (ImageView)findViewById(R.id.imageView);
        imageview.setImageResource((imageshd[pos]));

        imageview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mTitleWebsites[pos]));
                startActivity(browserIntent);
            }
        });
    }
}
