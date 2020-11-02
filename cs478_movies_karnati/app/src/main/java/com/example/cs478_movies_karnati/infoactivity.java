package com.example.cs478_movies_karnati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class infoactivity extends AppCompatActivity {
    String mTitle[] = {"Parasite","Avengers:EndGame","Joker","The IrishMan","Ford vs Ferrari","Black Panther","Ninnu Kori"};
    String mYear[] = {"2019","2019","2019","2019","2019","2018","2017"};
    String mDuration[] = {"132","182 mins","122 mins","210 mins"," 152 mins","135 mins","137 mins"};
    String mDirector[] = {"Bong Joon-ho","Russo brothers","Todd Phillips","Martin Scorsese","James Mangold","Ryan Coogler","Shiva Nirvana"};
    String mCast[] = {"Song Kang-ho, Choi Woo-shik","Scarlett Johansson, Robert Downey Jr.","Joaquin Phoenix,  Arthur Fleck","Robert De Niro, Al Pacino","Christian Bale, Matt Demon","Chadwick Boseman and Martin Freeman ","Nani"};
    String mimdb[] = {"8.6/10","8.5/10","8.6/10","8.0/10","8.2/10","7.3/10","7.6/10"};
    String mrotten[] = {"96%","78%","68%","96%","92%","97%","92%"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoactivity);
        TextView tvtitle = (TextView) findViewById(R.id.tvtitle);
        TextView tvyear = (TextView) findViewById(R.id.tvyear);
        TextView tvduration = (TextView) findViewById(R.id.tvduration);
        TextView tvdirector = (TextView) findViewById(R.id.tvdirector);
        TextView tvcast = (TextView) findViewById(R.id.tvcast);
        TextView tvimdbrating = (TextView) findViewById(R.id.tvimdbrating);
        TextView tvrottenrating = (TextView) findViewById(R.id.tvrottenrating);
        Intent getI= getIntent();
        Bundle extra = getI.getExtras();
        final int pos = extra.getInt("position");
        tvtitle.setText(mTitle[pos]);
        tvyear.setText(" Year: "+ mYear[pos]);
        tvduration.setText(" Duration: "+ mDuration[pos]);
        tvdirector.setText(" Director: "+ mDirector[pos]);
        tvcast.setText(" Main Cast: "+ mCast[pos]);
        tvimdbrating.setText(" Imdb Rating: "+ mimdb[pos]);
        tvrottenrating.setText(" Rotten Tomatoes Rating: "+ mrotten[pos]);



    }
}
