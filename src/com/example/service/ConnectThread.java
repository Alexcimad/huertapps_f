package com.example.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

public class ConnectThread extends Thread{
	private static final String TAG = "MyActivity";
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private final BluetoothSocket mmSocket;
	private final BluetoothDevice mmDevice;
	public BluetoothAdapter BA;
	private Context context;
	
	  public ConnectThread(Context context,BluetoothDevice device) {
	       BluetoothSocket tmp = null;
	       mmDevice = device;
	       
	    
	       try {
	    	   Method m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
              tmp = (BluetoothSocket) m.invoke(device, Integer.valueOf(1));
              Log.v(TAG, "conected");
	       } catch (Exception e) { }
	       mmSocket = tmp;
	   }

	   public void run() {
		   BA=BluetoothAdapter.getDefaultAdapter();
		   BA.cancelDiscovery();
		  
		   
	       try {
	    	   
	           mmSocket.connect();
	           
	       
	       } catch (IOException connectException) {
	           try {
	               mmSocket.close();
	               
	           } catch (IOException closeException) { }
	           return;
	       }
	   }
	     
	   

	   public void cancel() {
	       try {
	           mmSocket.close();
	       } catch (IOException e) { }
	   }
	
}
