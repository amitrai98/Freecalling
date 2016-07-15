package com.example.freecalling.voicecall;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.freecalling.R;

public class logdetailbinadapter {
	String date,duration,type,contactno;
	public logdetailbinadapter(String contactno,String date,String duration,String type){
		this.date=date;
		this.duration=duration;
		this.type=type;
		this.contactno=contactno;
	}
	public String getContactno()
	{
		return contactno;
	}
	public String getDate(){
		return date;

		}
	public String getDuration(){
			return duration;

		}
	public String getType(){		
	return type;

		}
}
class LogdetailAdapter extends BaseAdapter{
	ArrayList<logdetailbinadapter>list;
	Context ctx;
	LayoutInflater inflater;
	
	public LogdetailAdapter(Context ctx,ArrayList<logdetailbinadapter>list){
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
		// TODO Auto-generated method stub
		convertView=inflater.inflate(R.layout.logdetaildesign, null);
		TextView date=(TextView)convertView.findViewById(R.id._contactname);
		TextView contactno=(TextView) convertView.findViewById(R.id._contactno);
		ImageView type=(ImageView) convertView.findViewById(R.id._typeofcall);
		ImageView msgorcallicon=(ImageView) convertView.findViewById(R.id._type);
		TextView duration=(TextView) convertView.findViewById(R.id._date);
		
		date.setText(list.get(position).getDate());
		contactno.setText(list.get(position).getContactno());
		
		if(list.get(position).getType().equals("smssent")){
			type.setImageResource(R.drawable.dialedcall);
			msgorcallicon.setImageResource(R.drawable.msg);
			duration.setText("");
		}if(list.get(position).getType().equals("smsrecived")){
			type.setImageResource(R.drawable.sms);
			msgorcallicon.setImageResource(R.drawable.msg);
			duration.setText("");
			
		}if(list.get(position).getType().equals("dialed")){
			type.setImageResource(R.drawable.dialedcall);
			msgorcallicon.setImageResource(R.drawable.phoneicon);
			duration.setText("");
		}if(list.get(position).getType().equals("missed")){
			type.setImageResource(R.drawable.missedcall);
			msgorcallicon.setImageResource(R.drawable.missed);
			duration.setText(list.get(position).getDuration());
			
		}if(list.get(position).getType().equals("recived")){
			type.setImageResource(R.drawable.recivedcall);
			msgorcallicon.setImageResource(R.drawable.recivedcall);
			duration.setText(list.get(position).getDuration());
		}
		if(list.get(position).getType().equals("blocked")){
			type.setImageResource(R.drawable.recivedcall);
			msgorcallicon.setImageResource(R.drawable.blockcaller);
			duration.setText(list.get(position).getDuration());
		}
		
		return convertView;
	}
	
}
