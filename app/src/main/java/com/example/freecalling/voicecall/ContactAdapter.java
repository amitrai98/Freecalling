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

public class ContactAdapter extends BaseAdapter{

	ArrayList<ContactBin> list;
	Context ctx;
	LayoutInflater inflater;
	public ContactAdapter(Context ctx,ArrayList<ContactBin>list){
		this.ctx=ctx;
		this.list=list;
		inflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		
		int i= list.size();
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
		if(convertView==null){
			convertView=inflater.inflate(R.layout.contactdesign, null);
			ImageView img=(ImageView)convertView.findViewById(R.id._img);
			TextView  nm=(TextView)convertView.findViewById(R.id._contactname);
			TextView  no=(TextView)convertView.findViewById(R.id._contactno);
			
			Accuracycheak ac=new Accuracycheak();
			if(list.get(position).getImage()==(null))
			{
			img.setImageResource(R.drawable.defaultcontactimage);
			}else{
				Bitmap b=ac.StringToBitMap(list.get(position).getImage());
				img.setImageBitmap(b);
			}
				
			
			String name=list.get(position).getName();
			no.setText(list.get(position).getNumber());
			nm.setText(list.get(position).getName());
			if(list.get(position).getName().equals(list.get(position).getNumber())){
				nm.setText("no name");
				
			}
			else
				nm.setText(list.get(position).getName());
			
		}
		else{
			
			ImageView img=(ImageView)convertView.findViewById(R.id._img);
			TextView  nm=(TextView)convertView.findViewById(R.id._contactname);
			TextView  no=(TextView)convertView.findViewById(R.id._contactno);
			
			if(list.get(position).getImage()==(null))
			{
			img.setImageResource(R.drawable.defaultcontactimage);
			}else{
				Accuracycheak ac=new Accuracycheak();
				Bitmap b=ac.StringToBitMap(list.get(position).getImage());
				img.setImageBitmap(b);
			}String name=list.get(position).getName();
			no.setText(list.get(position).getNumber());
			nm.setText(list.get(position).getName());
			if(list.get(position).getName().equals(list.get(position).getNumber())){
				nm.setText("no name");
				
			}
			else
				nm.setText(list.get(position).getName());
			
		}
		return convertView;
		
		
	}

}
