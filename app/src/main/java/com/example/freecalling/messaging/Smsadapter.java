package com.example.freecalling.messaging;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.freecalling.Accuracycheak;
import com.example.freecalling.R;


public class Smsadapter extends BaseAdapter{

	ArrayList<Smsinitializer> data;
	Context c;
	LayoutInflater inflater;
	public Smsadapter(Context c,ArrayList<Smsinitializer>data)
	{
		this.c=c;
		this.data=data;
		
		
		inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=inflater.inflate(R.layout.inboxdesign, null);
			ImageView contactimg=(ImageView) convertView.findViewById(R.id._contactimage);
			TextView name=(TextView) convertView.findViewById(R.id._name);
			TextView status=(TextView) convertView.findViewById(R.id._status);
			ImageView type=(ImageView) convertView.findViewById(R.id._msgtype);
			final SimpleDateFormat parser = new SimpleDateFormat("dd-MM");
			
			TextView date=(TextView) convertView.findViewById(R.id._date);
			
			if(data.size()>0){

				contactimg.setImageResource(R.drawable.defaultcontactimage);
				
				Accuracycheak ac=new Accuracycheak();
				if(data.get(position).getImage()==null)
				{
					contactimg.setImageResource(R.drawable.defaultcontactimage);
				}else{
					Bitmap b=ac.StringToBitMap(data.get(position).getImage());
					contactimg.setImageBitmap(b);
				}
				name.setText(data.get(position).getName());
				if(data.get(position).getStatus().equals("pending")){
					status.setText(data.get(position).getStatus());
					status.setTextColor(Color.YELLOW);
				}else if(data.get(position).getStatus().equals("smssent")){
					status.setText(data.get(position).getStatus());
					status.setTextColor(Color.WHITE);
				}
				else if(data.get(position).getStatus().equals("smsrecived")){
					status.setText(data.get(position).getStatus());
					status.setTextColor(Color.GREEN);
				}
				else {
					status.setText(data.get(position).getStatus());
					status.setTextColor(Color.RED);
				}
				status.setText(data.get(position).getStatus());
				if(data.get(position).getType().equals("smssent"))
				type.setImageResource(R.drawable.dialedcall);
				else
					type.setImageResource(R.drawable.recivedcall);
					
				date.setText(data.get(position).getDate());
			
			
			}else{
				
				
			}
			
			}
			else
			{
				
			ImageView contactimg=(ImageView) convertView.findViewById(R.id._contactimage);
			TextView name=(TextView) convertView.findViewById(R.id._name);
			TextView status=(TextView) convertView.findViewById(R.id._status);
			ImageView type=(ImageView) convertView.findViewById(R.id._msgtype);
			final SimpleDateFormat parser = new SimpleDateFormat("dd-MM");
			
			TextView date=(TextView) convertView.findViewById(R.id._date);
			contactimg.setImageResource(R.drawable.defaultcontactimage);
			if(data.get(position).getImage()==null)
			{
				contactimg.setImageResource(R.drawable.defaultcontactimage);
			}else{
				Accuracycheak ac=new Accuracycheak();
				Bitmap b=ac.StringToBitMap(data.get(position).getImage());
				contactimg.setImageBitmap(b);
			}name.setText(data.get(position).getName());
			if(data.get(position).getStatus().equals("pending")){
				status.setText(data.get(position).getStatus());
				status.setTextColor(Color.YELLOW);
			}else if(data.get(position).getStatus().equals("sent")){
				status.setText(data.get(position).getStatus());
				status.setTextColor(Color.WHITE);
			}
			else if(data.get(position).getStatus().equals("delivered")){
				status.setText(data.get(position).getStatus());
				status.setTextColor(Color.GREEN);
			}
			else {
				status.setText(data.get(position).getStatus());
				status.setTextColor(Color.RED);
			}
			status.setText(data.get(position).getStatus());
			if(data.get(position).getType().equals("smssent"))
			type.setImageResource(R.drawable.dialedcall);
			else
				type.setImageResource(R.drawable.recivedcall);
				
			date.setText(data.get(position).getDate());
			
		}
		return convertView;
	}

}
