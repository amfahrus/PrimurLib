package net.primurlib;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
//import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

public class MyParser {
	public String[] xmlParsing(String fetchurl,String roottag,String parseelemnt,String parseelemntchild)
	{
	String[] temp=null;
	URL url=null;
	
	Log.d("I m Here","2");
	try {
	url = new URL(fetchurl);
	} catch (MalformedURLException e) {
	// TODO Auto-generated catch block
	Log.d("I got Exception","3");
	e.printStackTrace();
	}
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	DocumentBuilder db=null;
	try {
	db = dbf.newDocumentBuilder();
	}
	catch (ParserConfigurationException e1) {
	e1.printStackTrace();
	Log.d("I m Here","4");
	}
	Document doc=null;
	try {
	doc = db.parse(new InputSource(url.openStream()));
	} catch (SAXException e2) {
	// TODO Auto-generated catch block
	Log.d("I m Here","5");
	e2.printStackTrace();
	} catch (IOException e3) {
	// TODO Auto-generated catch block
	Log.d("I m Here","6");
	e3.printStackTrace();
	}
	org.w3c.dom.Element elt=doc.getDocumentElement();
	NodeList nodeList = elt.getElementsByTagName(roottag);
	temp=new String[nodeList.getLength()];
	Log.d("the length of nodelist",Integer.toString(nodeList.getLength()));


	for (int i = 0; i < nodeList.getLength(); i++)
	{
	
	Node node = nodeList.item(i);

	NodeList titleList = node.getChildNodes();
	Log.d("The length of titlelist",Integer.toString(titleList.getLength()));

		for(int j=0;j<titleList.getLength();j++)
		{
		Node node1= titleList.item(j);
		String name = node1.getNodeName();
	
			if (name.equalsIgnoreCase(parseelemnt)) {
		
			Log.d("J value"," "+j);
			temp[i]=node1.getFirstChild().getNodeValue();
			NodeList elmList = node1.getChildNodes();
			Log.d("temp value",temp[i]);
			
				for(int k=0;k<elmList.getLength();k++)
				{
				Node node2= elmList.item(k);
				String child = node2.getNodeName();
			
					if (child.equalsIgnoreCase(parseelemntchild)) {
				
					Log.d("k value"," "+k);
					temp[i]=node2.getFirstChild().getNodeValue();
				
					Log.d("temp value",temp[i]);
					}
				}
			}
		}
	}

	return(temp);


	}
}
