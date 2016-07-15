package com.example.freecalling.voicecall;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.freecalling.Accuracycheak;
import com.example.freecalling.R;

public class Logadapter extends BaseAdapter{

	Context ctx;
	ArrayList<Logbin>list;
	LayoutInflater inflater;
	public Logadapter(Context ctx,ArrayList<Logbin>list){
		this.list=list;
		this.ctx=ctx;
		inflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		
		
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		System.out.println(position+"  "+list.get(position).getImage());
		
		if(convertView==null){
			convertView=inflater.inflate(R.layout.logdesign, null);
			ImageView contactimage=(ImageView) convertView.findViewById(R.id._defaultcontactimage);
			TextView contactname=(TextView) convertView.findViewById(R.id._contactname);
			ImageView inorout=(ImageView) convertView.findViewById(R.id._typeofcall);
			TextView contactno=(TextView) convertView.findViewById(R.id._contactno);
			ImageView msgorcall=(ImageView) convertView.findViewById(R.id._type);
			TextView datee=(TextView) convertView.findViewById(R.id._date);
			
			
			Accuracycheak ac=new Accuracycheak();
			if(list.get(position).getImage()==null)
			{
				contactimage.setImageResource(R.drawable.defaultcontactimage);
			}else{
				Bitmap b=ac.StringToBitMap(list.get(position).getImage());
				contactimage.setImageBitmap(b);
			}
			
			contactimage.setImageResource(R.drawable.defaultcontactimage);
			if(list.get(position).getContactno().equals(list.get(position).getContactname())){
				contactname.setText("Unknown");
				contactno.setText(list.get(position).getContactno());
			}
			else{
			contactname.setText(list.get(position).getContactname());
			contactno.setText(list.get(position).getContactno());
			}
			
			if(list.get(position).getTypeofcall().equals("smssent")){
				msgorcall.setImageResource(R.drawable.msg);
				inorout.setImageResource(R.drawable.dialedcall);
			}
			else if(list.get(position).getTypeofcall().equals("smsrecived")){
				msgorcall.setImageResource(R.drawable.msg);
				inorout.setImageResource(R.drawable.recivedcall);
			}
			else if(list.get(position).getTypeofcall().equals("calldialed")){
				msgorcall.setImageResource(R.drawable.phoneicon);
				inorout.setImageResource(R.drawable.dialedcall);
			}
			else if(list.get(position).getTypeofcall().equals("callmissed")){
				msgorcall.setImageResource(R.drawable.phoneicon);
				inorout.setImageResource(R.drawable.missedcall);
			}
			else if(list.get(position).getTypeofcall().equals("callrecived")){
				msgorcall.setImageResource(R.drawable.phoneicon);
				inorout.setImageResource(R.drawable.recivedcall);
			}
			
			datee.setText(list.get(position).getDate());
		}
		else
		{
			
			ImageView contactimage=(ImageView) convertView.findViewById(R.id._defaultcontactimage);
			TextView contactname=(TextView) convertView.findViewById(R.id._contactname);
			ImageView inorout=(ImageView) convertView.findViewById(R.id._typeofcall);
			TextView contactno=(TextView) convertView.findViewById(R.id._contactno);
			ImageView msgorcall=(ImageView) convertView.findViewById(R.id._type);
			TextView datee=(TextView) convertView.findViewById(R.id._date);
			Accuracycheak ac=new Accuracycheak();
			
			
			if(list.get(position).getImage()==null)
			{
				contactimage.setImageResource(R.drawable.defaultcontactimage);
			}else{
				Bitmap b=ac.StringToBitMap(list.get(position).getImage());
				contactimage.setImageBitmap(b);
			}
			if(list.get(position).getContactno().equals(list.get(position).getContactname())){
				contactname.setText("Unknown");
				contactno.setText(list.get(position).getContactno());
			}
			else{
			contactname.setText(list.get(position).getContactname());
			contactno.setText(list.get(position).getContactno());
			}
			
			if(list.get(position).getTypeofcall().equals("smssent")){
				msgorcall.setImageResource(R.drawable.msg);
				inorout.setImageResource(R.drawable.dialedcall);
			}
			else if(list.get(position).getTypeofcall().equals("smsrecived")){
				msgorcall.setImageResource(R.drawable.msg);
				inorout.setImageResource(R.drawable.recivedcall);
			}
			else if(list.get(position).getTypeofcall().equals("dialed")){
				msgorcall.setImageResource(R.drawable.phoneicon);
				inorout.setImageResource(R.drawable.dialedcall);
			}
			else if(list.get(position).getTypeofcall().equals("missed")){
				msgorcall.setImageResource(R.drawable.missed);
				inorout.setImageResource(R.drawable.missedcall);
			}
			else if(list.get(position).getTypeofcall().equals("recived")){
				msgorcall.setImageResource(R.drawable.phoneicon);
				inorout.setImageResource(R.drawable.recivedcall);
			}
			else if(list.get(position).getTypeofcall().equals("rejected")){
				msgorcall.setImageResource(R.drawable.phoneicon);
				inorout.setImageResource(R.drawable.recivedcall);
			}
			else if(list.get(position).getTypeofcall().equals("blocked")){
				msgorcall.setImageResource(R.drawable.blockcaller);
				inorout.setImageResource(R.drawable.recivedcall);
			}
			
			datee.setText(list.get(position).getDate());
			
			
		}
		return convertView;
	}

}
