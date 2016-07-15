package com.example.freecalling.voicecall;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.freecalling.Accuracycheak;
import com.example.freecalling.R;
import com.example.freecalling.db.Db;

public class Loglist extends Activity{

	ListView log;
	final ArrayList<Logbin> list=new ArrayList<Logbin>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loglist);
		
		
		
		log=(ListView) findViewById(R.id._listoflog);
		
		log.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i=new Intent(Loglist.this,Logdetail.class);
				i.putExtra("contactno",list.get(arg2).getContactno());
				startActivity(i);
				
				
			}
		});
		log.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				return false;
			}
		});
		
		
		log.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				AlertDialog.Builder builder=new AlertDialog.Builder(Loglist.this);
				builder.setTitle("select");
				final ArrayAdapter<String>choice=new ArrayAdapter<String>(Loglist.this, android.R.layout.select_dialog_item);
				choice.add("delete");
				final Db link=new Db(Loglist.this);
				final Accuracycheak ac=new Accuracycheak();
				link.open();
				if(list.get(arg2).getContactno().equals(link.getName(list.get(arg2).getContactno()))){
					choice.add("add to contact");
					
				}
				else
				{
					choice.add("update contact");
				}
				String blockstate=link.cheakblockbyno(list.get(arg2).getContactno());
				if(blockstate!=null && blockstate.equals("blocked"))
				{
					choice.add("unblock it");
				}
				else{
					choice.add("block it");
				}
				
				link.close();
				builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.setAdapter(choice, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(choice.getItem(which).equals("delete")){
							Db db=new Db(Loglist.this);
							
							db.open();
							Toast.makeText(Loglist.this, db.deletelog(list.get(arg2).getContactno()), Toast.LENGTH_SHORT).show();
							db.close();
							list.clear();
							updatelist();
						}
						else if(choice.getItem(which).equals("update contact")|| choice.getItem(which).equals("add to contact")){
							Intent i=new Intent(Loglist.this,AddNewContact.class);
							i.putExtra("contactno", list.get(arg2).getContactno());
							startActivity(i);
							
						}
						else if(choice.getItem(which).equals("block it")|| choice.getItem(which).equals("add to contact")){
						try {
								link.open();
								String blockstate=link.blocker(list.get(arg2).getContactno());
								Log.e("loglist", blockstate);
							} catch (Exception e) {
								e.printStackTrace();
							}finally{link.close();}
							
							
						}
						else if(choice.getItem(which).equals("unblock it")|| choice.getItem(which).equals("add to contact")){
							try {
								
								link.open();
								String blockstate=link.unblocker(list.get(arg2).getContactno(), ac.date());
								Log.e("loglist", blockstate);
							} catch (Exception e) {
								e.printStackTrace();
							}finally{link.close();}
							
						}
						
					}
				});
				AlertDialog alert=builder.create();
				alert.show();
				
				return true;
			}
		});
		
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.loglist, menu);
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.loglist, menu);
		return super.onCreateOptionsMenu(menu);
		
		
	}
	public void updatelist()
	{
		Db link=new Db(Loglist.this);
		link.open();
		try{
				Cursor cc=link.distinctlog();
					
					if(cc.moveToFirst())
					{
						while(!cc.isAfterLast())
						{	
							String contactno=cc.getString(0);
							Cursor c=link.logdata(contactno);
							if(c.moveToFirst())
							{
								while(!c.isAfterLast())
								{
									String cno=c.getString(0);
									String contactname=link.getName(contactno);
									String typeofcall=c.getString(1);
									String date=c.getString(2);
									String image=link.image(contactno);
									
									
								
									Logbin lbin=new Logbin(cno, contactname, typeofcall, date,image);
									list.add(lbin);
									c.moveToLast();
									c.moveToNext();
								}
							}c.close();
							cc.moveToNext();
						}cc.close();
						
					}
					link.close();			
		}
			catch(Exception e){
			e.printStackTrace();
		}
		
		Logadapter la=new Logadapter(Loglist.this, list);
		if(la!=null){
			log.setAdapter(la);
		}
	}
	@Override
	protected void onResume() {
		
		super.onResume();
		list.clear();
		updatelist();
	}

}
