package com.example.huertapps;

import java.util.ArrayList;
import java.util.Set;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BluetoothActivity extends Activity {
	private BluetoothAdapter  BA;
	private ListView list_bt;
	private Set<BluetoothDevice>pairedDevices;
	private  ArrayAdapter<String> btArrayPaired;
	private BroadcastReceiver myReceiver= new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
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
                    btArrayPaired.add(device.getName());
                   btArrayPaired.notifyDataSetChanged();
                   list_bt.setAdapter(btArrayPaired);
                }
            
                   
                   /*else
                   {
                       boolean flag = true;    // flag to indicate that particular device is already in the arlist or not
                       for(int i = 0; i<pairedDevices.size();i++)
                       {
                           if(device.getAddress().equals(pairedDevices.get(i).getAddress()))
                           {
                               flag = false;
                           }
                       }
                       if(flag == true)
                       {
                           btArrayPaired.add(device.getName());
                           btArrayPaired.notifyDataSetChanged();
                }
            }
            list_bt.setAdapter(btArrayPaired);
            }*/
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
		  

		  }
		 
	

	 public void getPairedDevices() {
	      pairedDevices = BA.getBondedDevices();            
	        if(pairedDevices.size()>0)
	        {
	            for(BluetoothDevice device : pairedDevices)
	            {
	            	btArrayPaired.add(device.getName());
	                //arrayListPairedBluetoothDevices.add(device);
	            }
	        }
	        list_bt.setAdapter(btArrayPaired);
	        //BA.notifyDataSetChanged();
	    }
	}


	 
	
		
        
