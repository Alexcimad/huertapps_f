package com.example.huertapps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ConexionActivity extends Activity {
	private Button btn_bluetooth;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion); 
        
        btn_bluetooth = (Button) findViewById( R.id.btn_connect );
    
    
    btn_bluetooth.setOnClickListener(new View.OnClickListener() {

    	
    	  public void onClick(View v) {
    	    
    		  			Intent register_intent = new Intent( getApplicationContext(), BluetoothActivity.class );
                        startActivity( register_intent );
                        finish();
                       
    	  }
     
    	});
}
}
    
       

           
    

