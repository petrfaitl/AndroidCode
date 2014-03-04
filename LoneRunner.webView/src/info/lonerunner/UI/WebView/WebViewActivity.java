package info.lonerunner.UI.WebView;

import android.app.Activity;
import android.app.Fragment.SavedState;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class WebViewActivity extends Activity
{

	protected WebView mWebView;
	protected FrameLayout webviewPlaceholder;
	private static final String TAG = "WebViewActivity";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webviewframe);
		//Log.d(TAG, "Entered onCreate()");

		initUI();

	}

	protected void initUI()
	{
		webviewPlaceholder = ((FrameLayout) findViewById(R.id.webviewPlaceholder));

		if (mWebView == null)
		{
			Log.d(TAG, "mWebView is null");
			mWebView = new WebView(this);
			mWebView.setLayoutParams(new ViewGroup.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			mWebView.setWebViewClient(new WebViewClient());
			mWebView.getSettings().setJavaScriptEnabled(true);
			mWebView.loadUrl("http://www.lonerunner.info/pace-calculator");

		}
		webviewPlaceholder.addView(mWebView);

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		if (mWebView != null)
		{
			//Log.d(TAG, "mWebView is NOT null");
			webviewPlaceholder.removeView(mWebView);
		}

		super.onConfigurationChanged(newConfig);
		setContentView(R.layout.webviewframe);
		initUI();
	}

//	@Override
//	protected void onSaveInstanceState(Bundle outState)
//	{
//		super.onSaveInstanceState(outState);
//		mWebView.saveState(outState);
//	}
//
//	@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState)
//	{
//		super.onRestoreInstanceState(savedInstanceState);
//		mWebView.restoreState(savedInstanceState);
//	}

	@Override
	public void onRestart()
	{
		super.onRestart();
		//Log.d(TAG, "Entered onRestart()");
	}

	@Override
	public void onStart()
	{
		super.onStart();
		//Log.d(TAG, "Entered onStart()");
	}

	@Override
	public void onPause()
	{
		super.onPause();
		//Log.d(TAG, "Entered onPause()");

	}

}