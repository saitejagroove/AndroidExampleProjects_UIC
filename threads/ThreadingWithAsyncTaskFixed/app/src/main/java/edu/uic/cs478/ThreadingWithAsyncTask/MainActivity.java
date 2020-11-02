package edu.uic.cs478.ThreadingWithAsyncTaskFixed;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;


public class MainActivity extends Activity {

	private Button mButton1;
	private Button mButton2;
	private ImageView mImageView;
	private ProgressBar mProgressBar;
	private Bitmap mBitmap;
	private String urlString = "https://pictures.topspeed.com/IMG/crop/200512/2003-ferrari-enzo-40_600x0w.jpg";

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mButton1 = (Button) findViewById(R.id.button1);
		mButton2 = (Button) findViewById(R.id.button2);
		mImageView = (ImageView) findViewById(R.id.imageView1);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

		mButton1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new ReadPageTask(mImageView, mProgressBar).execute(urlString);            // urlString passed to doInBackground()
			}
		});

		mButton2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Second toast! ", Toast.LENGTH_SHORT).show();
			}
		});
	}

	// Preventing memory leaks -
	//    Step 1:  Make the AsyncTask subclass static so that its instance no longer references
	//             the MainActivity instance
	public static class ReadPageTask extends AsyncTask<String, Integer, Bitmap> {

		private final WeakReference<ImageView> wImageView;
		private final WeakReference<ProgressBar> wProgressBar;

		// Preventing memory leaks:
		// Step 2: Pass references to MainActivity widgets and create weak references
		//         in static AsyncTask subclass
		ReadPageTask(ImageView aImageView, ProgressBar aProgressBar) {
			wImageView = new WeakReference<ImageView>(aImageView);
			wProgressBar = new WeakReference<ProgressBar>(aProgressBar);
		}

		protected void onPreExecute() {

			// Preventing memory leaks -
			//  Step 3:  Can no longer reference non-static field in enclosing class
			//           since ReadPageTask is now a static class.
			// mProgressBar.setVisibility(ProgressBar.VISIBLE);
			// mProgressBar.setProgress(0) ;
			ProgressBar p = wProgressBar.get();
			if (p != null) {
				p.setVisibility(ProgressBar.VISIBLE);
				p.setProgress(0);
			}
		}

		@Override
		protected Bitmap doInBackground(String... strings) {
			URL aUrl = null;
			Bitmap result = null;

			try {
				aUrl = new URL(strings[0]);   // This could raise malformed URL exception
				Thread.sleep(2000);
				publishProgress(25);      //  This method allows us to publish progress on UI thread
				Thread.sleep(2000);
				publishProgress(50);
				Thread.sleep(2000);
				publishProgress(75);
				result = BitmapFactory.decodeStream((InputStream) aUrl.getContent());
				publishProgress(99);
			} catch (Exception e) {
				System.out.println("Could not read web site: " + strings[0] + ".");
			}
			;

			return result;
		}

		// This method is executed in the UI thread to update the progress bar in the display 
		@Override
		protected void onProgressUpdate(Integer... values) {

			// Preventing memory leaks -
			//  Step 3:  Can no longer reference non-static field in enclosing class
			//           since ReadPageTask is now a static class.
			// mProgressBar.setProgress(values[0]);
			ProgressBar p = wProgressBar.get();

			// Preventing memory leaks -
			// Step 4:  Must check that non-static fields in enclosing class have
			//          not been garbage collected.
			if (p != null) {
				p.setProgress(values[0]);
			}
		}

		// This method is executed in the UI thread after doInBackground() has returned
		protected void onPostExecute(Bitmap bitmap) {
			// Preventing memory leaks -
			//  Step 3:  Can no longer reference non-static field in enclosing class
			//           since ReadPageTask is now a static class.
			// mImageView.setImageBitmap(bitmap) ;
			// mProgressBar.setVisibility(ProgressBar.INVISIBLE) ;
			ProgressBar p = wProgressBar.get();
			ImageView im = wImageView.get();

			// Preventing memory leaks -
			// Step 4:  Must check that non-static fields in enclosing class have
			//          not been garbage collected.
			if (p != null) {
				p.setVisibility(ProgressBar.INVISIBLE);
			}
			if (im != null) {
				im.setImageBitmap(bitmap);
			}
		}
	}
}


