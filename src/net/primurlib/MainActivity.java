package net.primurlib;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;


public class MainActivity extends Activity {

	ListView listcomp=null;
    Button btn_more,btn_search,btn_site,btn_exit;
    String uri_param = "", keywords = "", uri = "";
    boolean list;
	MyAdapter adapt_obj=null; 
	Context myref=null;
    int current_page = 1;
    static final int tampil_error=1;
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	if (cek_status(this)) 
	{
		list = true;
		moreListenerOnButton();
		searchListenerOnButton();
		websiteListenerOnButton();
		exitListenerOnButton();
		listcomp=(ListView)findViewById(R.id.mylistview); 
		uri = "http://primurlib.net/index.php?resultXML=true&page="+ current_page + uri_param;
		listcomp.setOnItemClickListener(new OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            //View current = listcomp.getChildAt(position);
	        	TextView strVal = (TextView)view.findViewById(R.id.hidden);
	        	String item = strVal.getText().toString();
	        	uri = "http://primurlib.net/index.php?p=show_detail&inXML=true&id=" + item;
	        	//Toast.makeText(MainActivity.this, "ID '" + uri + "' was clicked.", Toast.LENGTH_LONG).show(); 

	        	btn_more=(Button)findViewById(R.id.more);
	        	btn_more.setText("Back");
	        	list = false;
	        	loadProses();
	           
	        }
	    });
		loadProses();
	} 
	else 
	{
	//Toast.makeText(MainActivity.this, "No Connectivity", Toast.LENGTH_LONG).show();
		showDialog(tampil_error);
	}

	}
	
	public void loadProses(){
		myref=MainActivity.this;
		  new MyAsyncTask().execute(); 
	}
    
	private class MyAsyncTask extends AsyncTask<Void,Void,Void>{

	private final ProgressDialog dialog=new ProgressDialog(MainActivity.this);

	@Override
	protected Void doInBackground(Void... params) {
	// TODO Auto-generated method stub
	  try {
		  adapt_obj=new MyAdapter(myref,uri);
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	return null;
	}

	@Override
	protected void onPreExecute()
	{
	dialog.setMessage("Loading...");
	dialog.show();
	dialog.setCancelable(false);
	}

	@Override
	protected void onPostExecute(Void result)
	{
	if(dialog.isShowing() == true)
	{
		dialog.dismiss();
	}
	listcomp.setAdapter(adapt_obj);
	adapt_obj.notifyDataSetChanged();
	}
	}
	
	public void moreListenerOnButton() {
	    btn_more=(Button)findViewById(R.id.more);
	    btn_more.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {

				if(list){
				current_page += 1;
				}
				uri = "http://primurlib.net/index.php?resultXML=true&page="+ current_page + uri_param;
				loadProses();
				btn_more.setText("Reload");
	        	list = true;
			}
 
		});
 
	}
	
	public void searchListenerOnButton() {
		
		//keywords = txtsearch.getText().toString();
	    btn_search=(Button)findViewById(R.id.search);
	    btn_search.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				showdialogsearch();
 
			}
 
		});
 
	}
	
	public void websiteListenerOnButton() {
		
	    btn_site=(Button)findViewById(R.id.site);
	    btn_site.setOnClickListener(new OnClickListener() {
	    Intent intent;
			@Override
			public void onClick(View arg0) {
				String webAddress = "http://www.primurlib.net";
			    intent = new Intent(Intent.ACTION_VIEW);
			    intent.setData(Uri.parse(webAddress));
			    startActivity(intent);
			}
 
		});
 
	}
	
	public void exitListenerOnButton() {
		
	    btn_exit=(Button)findViewById(R.id.exit);
	    btn_exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
 
		});
 
	}
	
	public void showdialogsearch(){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Searching");
		alert.setMessage("Input keyword");

		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Search", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  String value = input.getText().toString();
		  	current_page = 1;
			uri_param = "&search=Search&keywords=" + value;
			uri = "http://primurlib.net/index.php?resultXML=true&page="+ current_page + uri_param;
			loadProses();
			btn_more.setText("Reload");
			list = true;
		  }
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
		
	}
	
    public boolean cek_status(Context cek) {
    	ConnectivityManager cm = (ConnectivityManager) cek.getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo info = cm.getActiveNetworkInfo();

		if (info != null && info.isConnected())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

    @Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case tampil_error:
			AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
			errorDialog.setTitle("Connection Error");
			errorDialog.setMessage("No Internet Connection");
			errorDialog.setNeutralButton("Exit",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
							Intent exit = new Intent(Intent.ACTION_MAIN);
							exit.addCategory(Intent.CATEGORY_HOME);
							exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							MainActivity.this.finish();
							startActivity(exit);
						}
					});

			AlertDialog errorAlert = errorDialog.create();
			return errorAlert;

		default:
			break;
		}
		return dialog;
	}
}
