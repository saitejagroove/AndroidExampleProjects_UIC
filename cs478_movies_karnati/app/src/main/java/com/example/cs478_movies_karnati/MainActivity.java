package com.example.cs478_movies_karnati;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE =1 ;
    ListView listView;
    String mTitle[] = {"Parasite","Avengers:EndGame","Joker","The IrishMan","Ford vs Ferrari","Black Panther","Ninnu Kori"};
    String mDescription[] = {"2019","2019","2019","2019","2019","2018","2017"};
    private int images[]= {R.drawable.parasitethumb,R.drawable.avengersthumb,R.drawable.jokerthumb,R.drawable.theirishmanthumb,R.drawable.fordvsferrarithumb,R.drawable.blackpantherthumb,R.drawable.ninnukorithumb};
    String mTitleWebsites[]= {"https://www.imdb.com/title/tt6751668/","https://www.imdb.com/title/tt4154796/","https://www.imdb.com/title/tt7286456/","https://www.imdb.com/title/tt1302006/","https://www.imdb.com/title/tt1950186/","https://www.imdb.com/title/tt1825683/","https://www.imdb.com/title/tt6996016/"};
    String mDirectorWebsites[]= {"https://en.wikipedia.org/wiki/Bong_Joon-ho","https://en.wikipedia.org/wiki/Russo_brothers","https://en.wikipedia.org/wiki/Todd_Phillips","https://en.wikipedia.org/wiki/Martin_Scorsese","https://en.wikipedia.org/wiki/James_Mangold","https://en.wikipedia.org/wiki/Ryan_Coogler","https://www.imdb.com/name/nm8526249/"};
    String mTrailerWebsites[]= {"https://www.youtube.com/watch?v=5xH0HfJHsaY","https://www.youtube.com/watch?v=TcMBFSGVi1c","https://www.youtube.com/watch?v=zAGVQLHvwOY","https://www.youtube.com/watch?v=RS3aHkkfuEI","https://www.youtube.com/watch?v=I3h9Z89U9ZA","https://www.youtube.com/watch?v=xjDjIWPwcPU","https://www.youtube.com/watch?v=Ia6EXfqKiV4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);

        MyAdapter adapter = new MyAdapter(this, mTitle,mDescription,images);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0) {
                    openActivity2(position);
                }
                else if (position==1) {
                    openActivity2(position);
                }
                else if (position==2) {
                    openActivity2(position);
                }
                else if (position==3) {
                    openActivity2(position);
                }
                else if (position==4) {
                    openActivity2(position);
                }
                else if (position==5) {
                    openActivity2(position);
                }else if (position==6) {
                    openActivity2(position);
                }
            }
        });


    }

    private void openActivity2(int pos) {
        Intent intent = new Intent(this,mm2Activity.class);
        intent.putExtra("position",pos);
        Log.i("errorint", "openActivity2: "+pos);
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "More Info");
        menu.add(0, v.getId(), 0, "Trailer");
        menu.add(0, v.getId(), 0, "Director Info");
        menu.add(0, v.getId(), 0, "Imdb page");
    }

    @Override
    public boolean onContextItemSelected( MenuItem  item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getTitle() == "More Info") {
            Intent infointent = new Intent(this,infoactivity.class);
            infointent.putExtra("position",info.position);
            startActivity(infointent);
        }else if(item.getTitle() == "Trailer"){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mTrailerWebsites[info.position]));
            startActivity(browserIntent);
        }else if(item.getTitle() == "Director Info"){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mDirectorWebsites[info.position]));
            startActivity(browserIntent);
        }else if(item.getTitle() == "Imdb page"){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mTitleWebsites[info.position]));
            startActivity(browserIntent);
        }
        else {
            return  false;
        }
        return true;
    }

    class MyAdapter extends ArrayAdapter<String>{

        Context context;
        String rTitle[];
        String rDescrition[];
        int rImgs[];

        MyAdapter(Context c, String title[],String description[],int imgs[]) {
            super(c, R.layout.row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rDescrition = description;
            this.rImgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row,parent,false );
            ImageView images = row.findViewById(R.id.image1);
            TextView myTitle = row.findViewById((R.id.textView1));
            TextView myDescription = row.findViewById((R.id.textView2));

            images.setImageResource((rImgs[position]));
            myTitle.setText((rTitle[position]));
            myDescription.setText((rDescrition[position]));



            return row;
        }
    }
}

