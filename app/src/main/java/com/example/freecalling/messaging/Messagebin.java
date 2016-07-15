package com.example.freecalling.messaging;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.freecalling.R;

public class Messagebin {
	String date,message,messagetype;
	public Messagebin(String message,String date,String messagetype){
		this.date=date;
		this.message=message;
		this.messagetype=messagetype;
	}
	public String getDate(){
		return date;
	}

	public String messagetype(){
		return messagetype;
	}
	
	@Override
	public String toString() {
		return "Messagebin [date=" + date + ", message=" + message
				+ ", messagetype=" + messagetype + "]";
	}
	public String getMessage(){
		return message;
	}
}

    class Messageadapter extends BaseAdapter{
	ArrayList<Messagebin>list;
	Context ctx;
	LayoutInflater inflater;
	public void Messageadapters(Context ctx,ArrayList<Messagebin>list){
		this.ctx=ctx;
		this.list=list;
		inflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {


		if(list.get(position).messagetype().equals("smssent")){
			convertView=inflater.inflate(R.layout.sendsmsdesign, null);
		}
		else{
			convertView=inflater.inflate(R.layout.recivedmessagedesign, null);
		}
		TextView message=(TextView) convertView.findViewById(R.id._message);
		TextView date=(TextView) convertView.findViewById(R.id._date);
		
		
		message.setText(list.get(position).getMessage());
		
		date.setText(list.get(position).getDate());
		System.out.println(list.get(position).messagetype());
		
		return convertView;
	}
	
}
