package nxtcontroller.activity;

import nxtcontroller.enums.ErrorCodes;
import nxtcontroller.enums.InfoCodes;
import nxtcontroller.enums.TypeOfMessage;
import nxtcontroller.program.R;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	/* declaration constant values */
	public static final String TAG = "nxtcontroller"; //debug tag for LogCat
    private static final int REQUEST_CONNECT_DEVICE = 2;
    private static final int REQUEST_ENABLE_BT = 1;
	
	/* private class properties declaration */
    private BluetoothAdapter bluetoothAdapter;
    private TextView statusLabel; 
    private Button connectButton;
    private String[] errors,infos; //message arrays
    
    
    // The Handler that gets information back from NXTCommunicator
    private final Handler messageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	try{
        		switch (msg.what) {
            	case(TypeOfMessage.TOAST_ERROR):
            		Toast.makeText(getApplicationContext(), (String) msg.obj,2).show();
            	break;
            	case(TypeOfMessage.TOAST_INFO):
            		Toast.makeText(getApplicationContext(), (String) msg.obj,Toast.LENGTH_LONG).show();
            	break;
            	case(TypeOfMessage.STATUS_LABEL):
            		statusLabel.setText((String)msg.obj);
            	break;
            }
        	}catch (Exception e){
        		Log.e(TAG,"meesage handling error",e);
        	}

        }
    };
	
    
    /* Methods and Constructors declaration */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        
    	//getting needed resources
    	Resources res = getResources();
    	errors = res.getStringArray(R.array.errorsMsg);
    	infos = res.getStringArray(R.array.infoMsg);
    	
    	bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    	if(bluetoothAdapter == null){
    		String msg = errors[ErrorCodes.BLUETOOTH_NOT_FOUND];
    		Toast.makeText(getApplicationContext(),msg,2).show();
    		finish();
    		return;
    	}
    	if(!bluetoothAdapter.isEnabled())
    		turnOnBluetooth();
    	setupComponents();
    }
    
    public void setupComponents(){
        statusLabel = (TextView) findViewById(R.id.statusLabel);
        connectButton = (Button) findViewById(R.id.connectButton);
        setUpListeners();
    }
    
    public void setUpListeners(){
    	try{
    		Resources res = this.getResources();
    		String [] temp = res.getStringArray(R.array.infoMsg);
    		statusLabel.setText(temp[InfoCodes.DISCONNECTED]);
            connectButton.setOnClickListener(new View.OnClickListener()  {
                public void onClick(View v) {
                	connectNXTDevice();
                }
            });
    	}catch(Exception e){
    		Log.e(MainActivity.TAG," setUpListeners error",e);
    	}
    }
    
    public void connectNXTDevice(){
        Intent intent = new Intent(this, BluetoothDeviceManagerActivity.class);
        startActivityForResult(intent, REQUEST_CONNECT_DEVICE);
    }
    
	public void turnOnBluetooth(){
    	if (! bluetoothAdapter.isEnabled()) {
    	    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    	    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    	}
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        	case REQUEST_CONNECT_DEVICE:
	            if (resultCode == Activity.RESULT_OK) {
	                String deviceName = data.getExtras().getString(BluetoothDeviceManagerActivity.NAME_OF_DEVICE);
	                String deviceAddress = data.getExtras().getString(BluetoothDeviceManagerActivity.ADDRESS_OF_DEVICE);
	                statusLabel.setText(deviceName+"\n"+deviceAddress);
	                //TODO 
	            }
        	case REQUEST_ENABLE_BT:
        		if(resultCode == Activity.RESULT_OK){
        			String msg = infos[InfoCodes.BLUETOOTH_ACTIVATED];
        			Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
        		}else if (resultCode == Activity.RESULT_CANCELED ){
        			//String msg = errors[ErrorCodes.BLUETOOTH_CANT_BE_ACTIVATED];
        			//TODO Toast.makeText(getApplicationContext(),msg,2).show();
        		}
            break;
        }
    }
    
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}