package edu.uic.cs478.ThreadingRunUiThread;

// import edu.uic.cs478.YourName.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

// import edu.uic.cs478.ThreadingBad.R;

public class MainActivity extends Activity
{
	private Button mButton1 ;
	private Button mButton2 ;
	private ImageView mImageView ;
	private Bitmap mBitmap ;
    private Boolean mBitmapLock = false ;
    private static final String URL_STRING = "https://pictures.topspeed.com/IMG/crop/200512/2003-ferrari-enzo-40_600x0w.jpg" ;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mButton1 = (Button) findViewById(R.id.button1) ;
        mButton2 = (Button) findViewById(R.id.button2) ;
        mImageView = (ImageView) findViewById(R.id.imageView1) ;
        
        mButton1.setOnClickListener(new View.OnClickListener() {
        	       public void onClick(View v) {
        	    	       
        	    	       getPicture() ;
        	    	       // mImageView.setImageBitmap(mBitmap) ;
        	    	       
        	       }
        });
        
        
        mButton2.setOnClickListener(new View.OnClickListener() {
 	       public void onClick(View v) {
               Toast.makeText(MainActivity.this, "Second toast! ", Toast.LENGTH_SHORT).show() ;
 	       }   
     });
    }

    /** This method gets a picture from a web site and displays it in an image view 
     * 
     */
    public void getPicture() {
       
           Runnable aRunnable = new Runnable() {
        	       public void run() {
			       // Beware of possible race condition on mBitmap!
                       synchronized (mBitmapLock) {
                           mBitmap = loadImageFromNetwork(URL_STRING) ;
                       }

			       displayPicture();
                };
           } ;
	       
           Thread t1 = new Thread(aRunnable) ;
	       t1.start() ;
    }
    
    public void displayPicture() {

    	   //  The display of the picture must be executed on the UI thread
    	   //  One can use runOnUiThread() to make this happen
    	   runOnUiThread(new Runnable(){
    		   public void run() {
    			   // No data race with the UI thread on mImageView, but careful
    			   // with mBitmap!
    			   synchronized(mBitmapLock) {
    		          mImageView.setImageBitmap(mBitmap);
    			   }
    		   }
    	   });
    }

    private Bitmap loadImageFromNetwork(String url)  {
        try {
            Thread.sleep(3000);
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
            return bitmap;
        } catch (Exception e) {
            System.out.println("An exception occurred!!!");
            Log.i("Ugo ", e.toString()) ;
            return null ;
          }
        }
    	
}

