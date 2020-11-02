package edu.uic.cs478.ThreadingWithHandlers;

// import edu.uic.cs478.YourName.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

public class HandlerThreadActivity extends Activity  {

	private Handler mHandler = new Handler() ; // Handler is created by UI Thread and associated with it
	private Button mButton1 ;
	private Button mButton2 ;
	private ImageView mImageView ;
	private ProgressBar mProgressBar ;
	private Bitmap mBitmap ;
	private Boolean mBitmapLock = false ;
	private final String urlString = "https://pictures.topspeed.com/IMG/crop/200512/2003-ferrari-enzo-40_600x0w.jpg";

	public HandlerThreadActivity() {
	}

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mButton1 = (Button) findViewById(R.id.button1) ;
        mButton2 = (Button) findViewById(R.id.button2) ;
        mImageView = (ImageView) findViewById(R.id.imageView1) ;
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar) ;
        
        mButton1.setOnClickListener(new View.OnClickListener() {
        	       public void onClick(View v) {

        	    	        Thread t1 = new Thread(new ReadPageRunnable()) ;   	
        	    	        t1.start();
        	       }
        });
        
        mButton2.setOnClickListener(new View.OnClickListener() {
 	           public void onClick(View v) {
 	    	           Toast.makeText(HandlerThreadActivity.this, "Second toast! ", Toast.LENGTH_SHORT).show() ;
 	          }
        });       
    }

    // This code runs in background thread in parallel with UI thread
    public class ReadPageRunnable implements Runnable  {	

		public void run() {

			// Post runnable on mHandler (feeding UI thread's message queue)
			mHandler.post(new Runnable() {
				public void run() {
					mProgressBar.setVisibility(ProgressBar.VISIBLE);
					mProgressBar.setProgress(0) ; 
				}
			} ) ;
			
			try {  Thread.sleep(2000); }
			catch (InterruptedException e) { System.out.println("Thread interrupted!") ; } ;

			// again...
			mHandler.post(new Runnable() {
				public void run() {
					mProgressBar.setProgress(33) ;
				}
			} );

			// ... and again
			try {  Thread.sleep(2000); }
			catch (InterruptedException e) { System.out.println("Thread interrupted!") ; } ;

			// ... and again
			mHandler.post(new Runnable() {
				public void run() {
					mProgressBar.setProgress(66) ;
				}
			} );

			// Now get picture and bind it to mBitmap
            try {
				URL aUrl = new URL(urlString);   // This could raise malformed URL exception
				Bitmap b = BitmapFactory.decodeStream((InputStream) aUrl.getContent());
				synchronized (mBitmapLock) {
					mBitmap = b;
				}
			}
            catch (Exception e) {System.out.println("Could not read image from web!") ; }
			
			// Tell UI thread to display picture and hide progress bar
			mHandler.post(new Runnable() {
				public void run() {
					mProgressBar.setVisibility(ProgressBar.INVISIBLE) ;
					synchronized (mBitmapLock) {
						mImageView.setImageBitmap(mBitmap) ;
					}
				}
			} );
		}
	} ;
}

    

