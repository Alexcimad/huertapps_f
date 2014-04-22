package com.example.huertapps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import com.example.service.ConnectThread;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BluetoothActivity extends Activity {
	private BluetoothAdapter  BA;
	private ListView list_bt;
	private Set<BluetoothDevice>pairedDevices;
	private  ArrayAdapter<String> btArrayPaired;
	private  ConnectThread thread;
	private BroadcastReceiver myReceiver= new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
        	Iterator<BluetoothDevice> it=pairedDevices.iterator();
        	Message msg = Message.obtain();
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                Toast.makeText(context, "ACTION_FOUND", Toast.LENGTH_SHORT).show();

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                try
                {
                    //device.getClass().getMethod("setPairingConfirmation", boolean.class).invoke(device, true);
                    //device.getClass().getMethod("cancelPairingUserInput", boolean.class).invoke(device);
                }
                catch (Exception e) {
                    Log.i("Log", "Inside the exception: ");
                    e.printStackTrace();
                }

                if(pairedDevices.size()<1) // this checks if the size of bluetooth device is 0,then add the
                {                                           // device to the arraylist.
                    btArrayPaired.add(device.getName()+"\n" + device.getAddress());
                   btArrayPaired.notifyDataSetChanged();
                   list_bt.setAdapter(btArrayPaired);
                }
            
                   
                   else
                   {
                       boolean flag = true;    // flag to indicate that particular device is already in the arlist or not
                       while(it.hasNext())
                      {
                           if(device.getAddress().equals(it.next().getAddress()))
                           {
                               flag = false;
                           }
                       
                       if(flag == true)
                       {
                           btArrayPaired.add(device.getName()+ "\n" + device.getAddress());
                           btArrayPaired.notifyDataSetChanged();
                }
                      }
            }
            list_bt.setAdapter(btArrayPaired);
            }
        }
        };
                
                        
        
            
            
          
	
	
	
	
	protected void onStart() {
	    // TODO Auto-generated method stub
		
	    super.onStart();
	    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	     startActivityForResult(turnOn, 0);
	    getPairedDevices();
	    Log.i("Log", "in the start searching method");
	    IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
	    BluetoothActivity.this.registerReceiver(myReceiver, intentFilter);
	    BA.startDiscovery();
	    
	}	
  
        
protected void onCreate(Bundle savedInstanceState) {
		  // TODO Auto-generated method stub
		 
		 setContentView(R.layout.activity_list_bluetooth);
		  super.onCreate(savedInstanceState);
		  BA= BluetoothAdapter.getDefaultAdapter();
			 list_bt= (ListView)findViewById(R.id.ListBT);

		 /* ArrayAdapter<String> */
		  btArrayPaired = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
		  
		  
		  Set<BluetoothDevice> pairedDevices = BA.getBondedDevices();
		  list_bt.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> av, View v, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					BA.cancelDiscovery();

		            // Get the device MAC address, which is the last 17 chars in the View
		            String info = ((TextView) v).getText().toString();
		            String address = info.substring(info.length() - 17);

					BluetoothDevice device= BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address);
					
					thread=new ConnectThread(getApplicationContext(),device); 
					thread.start();
					
					Intent register_intent = new Intent( getApplicationContext(), Menuactivity.class );
		            startActivity( register_intent );
		            finish();		 
					
				}

				
				
			
				
		});

		  }
		 
	

	 public void getPairedDevices() {
	      pairedDevices = BA.getBondedDevices();            
	        if(pairedDevices.size()>0)
	        {
	            for(BluetoothDevice device : pairedDevices)
	            {
	            	btArrayPaired.add(device.toString()+ "\n" + device.getAddress());
	                //arrayListPairedBluetoothDevices.add(device);
	            }
	        }
	        list_bt.setAdapter(btArrayPaired);
	        //BA.notifyDataSetChanged();
	    }
	

	 @Override
	 protected void onStop()
	 {
	     unregisterReceiver(myReceiver);
	     super.onStop();
	 }
	 
	
}	 
	
        
