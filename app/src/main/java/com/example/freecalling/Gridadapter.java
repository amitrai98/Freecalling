package com.example.freecalling;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Gridadapter extends BaseAdapter {
	private Context context;
	private final String[] mobileValues;
	public Gridadapter(Context context,String[] mobileValues)
	{
		this.context=context;
		this.mobileValues=mobileValues;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mobileValues.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View gridView;
		
		if(convertView==null){
//			gridView=new View(context);
			convertView=inflater.inflate(R.layout.menu, null);
			
			TextView textView=(TextView)convertView.findViewById(R.id._contacts_text);
			textView.setText(mobileValues[position]);
			
			ImageView imageView=(ImageView)convertView.findViewById(R.id._contacts);
			
			String mobile=mobileValues[position];
			
		if(mobile.equals("contacts")){
			imageView.setImageResource(R.drawable.contacts);	
			textView.setTextColor(Color.WHITE);
		}
		else if(mobile.equals("log")){
			imageView.setImageResource(R.drawable.log);
			textView.setTextColor(Color.WHITE);
		}
		else if(mobile.equals("sms")){
			imageView.setImageResource(R.drawable.sms);
			textView.setTextColor(Color.WHITE);
		}
		else if(mobile.equals("settings")){
			imageView.setImageResource(R.drawable.settings);
			textView.setTextColor(Color.WHITE);
		}
		else if(mobile.equals("status")){
			imageView.setImageResource(R.drawable.status);
			textView.setTextColor(Color.WHITE);
		}
		
		else if(mobile.equals("location")){
			imageView.setImageResource(R.drawable.location);
			textView.setTextColor(Color.WHITE);
		}
		if(mobile.equals("aboutus")){
			imageView.setImageResource(R.drawable.aboutus);
			textView.setTextColor(Color.WHITE);
		}
		else if(mobile.equals("help")){
			imageView.setImageResource(R.drawable.help);
			textView.setTextColor(Color.WHITE);
		}
		else if(mobile.equals("exit")){
			imageView.setImageResource(R.drawable.exit);
			textView.setTextColor(Color.WHITE);
			}
			
		}
		
		else{
			TextView textView=(TextView)convertView.findViewById(R.id._contacts_text);
			textView.setText(mobileValues[position]);
			
			ImageView imageView=(ImageView)convertView.findViewById(R.id._contacts);
			
			String mobile=mobileValues[position];
			
		if(mobile.equals("contacts")){
			imageView.setImageResource(R.drawable.contacts);	
		}
		else if(mobile.equals("log")){
			imageView.setImageResource(R.drawable.log);
		}
		else if(mobile.equals("sms")){
			imageView.setImageResource(R.drawable.sms);
		}
		
		else if(mobile.equals("location")){
			imageView.setImageResource(R.drawable.location);
		}
		if(mobile.equals("aboutus")){
			imageView.setImageResource(R.drawable.aboutus);
		}
		else if(mobile.equals("help")){
			imageView.setImageResource(R.drawable.help);
		}
		else if(mobile.equals("exit")){
			imageView.setImageResource(R.drawable.exit);
			}
			
		}
		

			
		
		return convertView;
	}

}
