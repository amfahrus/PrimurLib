package net.primurlib;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MyAdapter extends BaseAdapter{
	LayoutInflater inflation=null;
	Context mycontext=null;
	String urlvalue=null,roottag=null,parseelement=null,image=null;
	MyParser myparseobj=null;
	String[] title_array=null,subtitle_array=null,node_array=null,
			id_array=null,author_array=null,
			classification=null,gmd=null,language=null,
			publisher=null,dateissued=null,latitude=null,longitude=null;
	
	MyAdapter(Context c,String url)
	{
	Log.d("1","1");
	mycontext=c;
	inflation=LayoutInflater.from(mycontext);
	myparseobj=new MyParser();
	  title_array=myparseobj.xmlParsing(url,"mods","titleInfo","title");
	  subtitle_array=myparseobj.xmlParsing(url,"mods","titleInfo","subTitle");
	  node_array=myparseobj.xmlParsing(url,"mods","node","nodeName");
	  latitude=myparseobj.xmlParsing(url,"mods","node","nodeLat");
	  longitude=myparseobj.xmlParsing(url,"mods","node","nodeLong");
	  author_array=myparseobj.xmlParsing(url,"mods","name","namePart");
	  classification=myparseobj.xmlParsing(url,"mods","classification","");
	  gmd=myparseobj.xmlParsing(url,"mods","physicalDescription","form");
	  language=myparseobj.xmlParsing(url,"mods","language","languageTerm");
	  publisher=myparseobj.xmlParsing(url,"mods","originInfo","publisher");
	  dateissued=myparseobj.xmlParsing(url,"mods","originInfo","dateIssued");
	  id_array=myparseobj.xmlParsing(url,"mods","recordInfo","recordIdentifier");
	  
	}


	@Override
	public int getCount() {
	// TODO Auto-generated method stub
	Log.d("title_array",title_array.length+"");
	return title_array.length;
	}

	@Override
	public Object getItem(int position) {
	// TODO Auto-generated method stub
	return position;
	}

	@Override
	public long getItemId(int position) {
	// TODO Auto-generated method stub
	return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	// TODO Auto-generated method stub
	MyHolder holder=new MyHolder();;
	if(convertView == null)
	{
		if(getCount() > 1){
			convertView=inflation.inflate(R.layout.listitem, null);
			holder.tv=(TextView)convertView.findViewById(R.id.mytextview);
			holder.nv=(TextView)convertView.findViewById(R.id.mysubtextview);
			holder.id=(TextView)convertView.findViewById(R.id.hidden);
			holder.iv=(ImageView)convertView.findViewById(R.id.myimgview);
		} else {
			convertView=inflation.inflate(R.layout.detailitem, null);
			convertView.setClickable(true);
			holder.tv=(TextView)convertView.findViewById(R.id.title);
			holder.nv=(TextView)convertView.findViewById(R.id.nodename);
			holder.av=(TextView)convertView.findViewById(R.id.subtitle);
			holder.cl=(TextView)convertView.findViewById(R.id.classification);
			holder.gd=(TextView)convertView.findViewById(R.id.gmd);
			holder.lg=(TextView)convertView.findViewById(R.id.language);
			holder.pu=(TextView)convertView.findViewById(R.id.publisher);
			holder.dt=(TextView)convertView.findViewById(R.id.dateissued);
			holder.id=(TextView)convertView.findViewById(R.id.hidden);
			holder.iv=(ImageView)convertView.findViewById(R.id.img);
		}
	}
	else
	{
		if(getCount() > 1){
			
			holder.tv=(TextView)convertView.findViewById(R.id.mytextview);
			holder.nv=(TextView)convertView.findViewById(R.id.mysubtextview);
			holder.id=(TextView)convertView.findViewById(R.id.hidden);
			holder.iv=(ImageView)convertView.findViewById(R.id.myimgview);
		} else {
			
			holder.tv=(TextView)convertView.findViewById(R.id.title);
			holder.nv=(TextView)convertView.findViewById(R.id.nodename);
			holder.av=(TextView)convertView.findViewById(R.id.subtitle);
			holder.cl=(TextView)convertView.findViewById(R.id.classification);
			holder.gd=(TextView)convertView.findViewById(R.id.gmd);
			holder.lg=(TextView)convertView.findViewById(R.id.language);
			holder.pu=(TextView)convertView.findViewById(R.id.publisher);
			holder.dt=(TextView)convertView.findViewById(R.id.dateissued);
			holder.id=(TextView)convertView.findViewById(R.id.hidden);
			holder.iv=(ImageView)convertView.findViewById(R.id.img);
		}
	}
	if(getCount() > 1){
		holder.tv.setText(title_array[position]+subtitle_array[position]);
		holder.id.setText(id_array[position]);
		holder.nv.setText(node_array[position]);
	} else {
		holder.tv.setText(title_array[position]+subtitle_array[position]);
		holder.av.setText("Author : " + author_array[position]);
		holder.cl.setText("Classification : " + classification[position]);
		holder.gd.setText("GMD : " + gmd[position]);
		holder.lg.setText("Language : " + language[position]);
		holder.pu.setText("Publisher : " + publisher[position]);
		holder.dt.setText("Publishing Year : " + dateissued[position]);
		holder.id.setText(id_array[position]);
		holder.nv.setText("Location : " + node_array[position]);

	}
	
	try{
		InputStream is= new java.net.URL("http://primurlib.net/themes/default/images/image.png").openStream();
		Bitmap b=BitmapFactory.decodeStream(is);
		holder.iv.setImageBitmap(b);
		}
		catch(Exception e){}
	
	return convertView;
	}
	
	static class MyHolder
	{
	TextView tv=null;TextView nv=null;TextView id=null;TextView av=null;
	TextView cl=null;TextView lg=null;TextView pu=null;TextView dt=null;
	TextView gd=null;ImageView iv=null;
	}
}
