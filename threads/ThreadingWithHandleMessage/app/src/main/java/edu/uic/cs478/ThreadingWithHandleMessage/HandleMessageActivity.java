package edu.uic.cs478.ThreadingWithHandleMessage;

// import edu.uic.cs478.YourName.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

public class HandleMessageActivity extends Activity  {

	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
    	      int what = msg.what ;
    	      switch (what) {
    	        case SET_PROGRESS_VISIBLE:
					mProgressBar.setVisibility(ProgressBar.VISIBLE);
    	    	      break; 
    	        case POST_PROGRESS: 
    	    	      mProgressBar.setProgress(msg.arg1);
 	    	      break; 
    	        case SET_PROGRESS_INVISIBLE:  
 	    	      mProgressBar.setVisibility(ProgressBar.INVISIBLE);
 	    	      break; 
    	        case UPDATE_IMAGE_VIEW: 
    	    	      mImageView.setImageBitmap((Bitmap) msg.obj); 
 	    	      break; 
    	      }
    	
        }
	}	; // Handler is associated with UI Thread
	
	private Button mButton1 ;
	private Button mButton2 ;
	private ImageView mImageView ;
	private ProgressBar mProgressBar ;
	private Bitmap mBitmap ;
	private Boolean mBitmapLock = false ;
	private final String urlString = "https://pictures.topspeed.com/IMG/crop/200512/2003-ferrari-enzo-40_600x0w.jpg" ;

	// "What" Values to be used by handleMessage()
	public static final int SET_PROGRESS_VISIBLE = 0 ;
	public static final int SET_PROGRESS_INVISIBLE = 1 ;
	public static final int POST_PROGRESS = 2 ;
	public static final int UPDATE_IMAGE_VIEW = 3 ;
	
	
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
 	    	           Toast.makeText(HandleMessageActivity.this, "Second toast! ", Toast.LENGTH_SHORT).show() ;
 	          }
        });       
    }

    
    public class ReadPageRunnable implements Runnable  {	

		public void run() {

			// Get a message instance with target set to UI thread's message queue
			Message msg = mHandler.obtainMessage(HandleMessageActivity.SET_PROGRESS_VISIBLE) ;
			mHandler.sendMessage(msg) ;

			// again, arg1 shows current progress
			msg = mHandler.obtainMessage(POST_PROGRESS) ;
			msg.arg1 = 0 ;
			mHandler.sendMessage(msg) ;

			try { Thread.sleep(1000); }
			   catch (InterruptedException e) { System.out.println("Thread interrupted!") ; } ;

			// and again, arg1 shows current progress
			msg = mHandler.obtainMessage(POST_PROGRESS) ;
			msg.arg1 = 33 ;
			mHandler.sendMessage(msg) ;
					
			try { Thread.sleep(1000); }
			   catch (InterruptedException e) { System.out.println("Thread interrupted!") ; } ;

			// and again, arg1 shows current progress
			msg = mHandler.obtainMessage(POST_PROGRESS) ;
			msg.arg1 = 66 ;
			mHandler.sendMessage(msg) ;

			// download bitmap
			Bitmap b = null ;
            try {
            	    URL aUrl = new URL(urlString) ;   // This could raise malformed URL exception
    			    b = BitmapFactory.decodeStream((InputStream) aUrl.getContent()) ; 
            }
            catch (Exception e) {System.out.println("Could not read image from web!") ; }

            // Get message to UI's queue, send bitmap along with message
			msg = mHandler.obtainMessage(UPDATE_IMAGE_VIEW) ;
			msg.obj = b;
			mHandler.sendMessage(msg) ;
			
			// This message will be queued after previous message
			msg = mHandler.obtainMessage(SET_PROGRESS_INVISIBLE) ;
			mHandler.sendMessage(msg) ;
		}
    }
}


    

