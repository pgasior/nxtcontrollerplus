package com.nxtcontrollerplus.program.sensors;

import com.nxtcontrollerplus.activity.MainActivity;
import com.nxtcontrollerplus.program.NXTCommunicator;
import com.nxtcontrollerplus.program.btmessages.commands.SetInputMode;
import com.nxtcontrollerplus.program.btmessages.returnpackages.GetInputValuesReturnPackage;

import android.util.Log;

public abstract class Sensor {
	protected byte type;
	protected byte port;
	protected byte mode;
	protected NXTCommunicator nxtCommunicator = NXTCommunicator.getInstance();
	protected GetInputValuesReturnPackage data;
	
	public abstract int getId();
	
	public byte getType() {
		return type;
	}

	public byte getPort() {
		return port;
	}

	public byte getMode() {
		return mode;
	}

	public void setType(byte type) {
		this.type = type;
		initialize();
	}

	public void setMode(byte mode) {
		this.mode = mode;
		initialize();
	}

	public void setPort(byte port) {
		this.port = port;
		initialize();
	}

	public void refreshSensorData(GetInputValuesReturnPackage data){
		this.data = data;
	}
	
	public void initialize(){
		SetInputMode sim = new SetInputMode(port, type, mode);
		if(nxtCommunicator != null){
			nxtCommunicator.write(sim.getBytes());
			Log.d(MainActivity.TAG,sim.toString());
		}
	}
	
	/**
	 * MUST call refreshData before
	 * @return actual scaled values from NXT 
	 */
	public int getMeasuredData(){
		try {
			return data.getScaledValue();
		} catch (Exception e) {
			Log.e(MainActivity.TAG,"sensor has no data",e);
		}
		return -1;
	}
	
	protected Sensor(byte port) throws UnsupportedOperationException{
		if(port < 0 || port > 3)
			throw new UnsupportedOperationException("Port: "+Byte.toString(port)+", only <0-3> ports are legal.");
		this.port = port;
	}
	
	public abstract String toString();

}
