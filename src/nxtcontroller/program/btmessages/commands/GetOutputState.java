package nxtcontroller.program.btmessages.commands;

import nxtcontroller.enums.nxtbuiltin.CommandType;
import nxtcontroller.program.utils.Converter;

/**
 * generate a byte array command for NXT
 * Command name:  GET_OUTPUT_STATE 
 * Byte [0-1] @see DirectCommand
 * Byte [2] - Output port
 * @author Lukas Dilik
 *
 */
public class GetOutputState extends DirectCommand{

	private static final byte COMMAND_LENGTH = 3;
	
	/**
	 * port where motor is connected
	 * @param portNumber <0,2>, on NXT: A:0,B:1,C:1
	 */
	public void setOutputPort(byte portNumber){
		super.command[2] = portNumber;
		super.refreshCommand();
	}
	
	public GetOutputState(byte portNumber) {	
		super(COMMAND_LENGTH, CommandType.GET_OUTPUT_STATE);
		super.setRequireResponseToOn();
		setOutputPort(portNumber);
	}
	
	public String toString(){
		String temp = super.toString();
		temp += "GET_OUTPUT_STATE_START\n";
		temp += Converter.bytesToString(bytes)+"\n";
		temp += "GET_OUTPUT_STATE_END\n";
		return temp;
	}

}