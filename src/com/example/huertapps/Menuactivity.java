package com.example.huertapps;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Menuactivity extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		  // TODO Auto-generated method stub
		 
		 setContentView(R.layout.menu);
		  super.onCreate(savedInstanceState);
		  Toast.makeText(getApplicationContext(), "Aplicacion conectada", Toast.LENGTH_SHORT).show(); 			

		}

}
