package nxtcontroller.program.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * convert LITTLE-ENDIAN encoded variables
 * from bytes array to java int(32bits)
 * to bytes array from java int(32bits) or short(16bits)
 * @author Lukas Dilik
 */
public class Converter {
	
	/**
	 * decode byte array to integer value
	 * LITTLE-ENDIAN
	 * @param bytes
	 * @return integer value if byte-array not 2 or 4 bytes size return zero
	 */
	public static int fromBytes(byte[] bytes){
		if(bytes.length==2){
			ByteBuffer b = ByteBuffer.wrap(bytes);
			b.order(ByteOrder.LITTLE_ENDIAN);
			return b.getShort();
		}else if (bytes.length == 4){
			ByteBuffer b = ByteBuffer.wrap(bytes);
			b.order(ByteOrder.LITTLE_ENDIAN);
			return b.getInt();
		}else
			return 0;
	}
	
	/**
	 * encode uword(short) 2-bytes variable to byte array
	 * compatible with NXT LITTLE-ENDIAN
	 * @param var
	 * @return 2-elements byte array
	 */
	public static byte[] toUWORD(short var){
		ByteBuffer buffer = ByteBuffer.allocate(2);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort(var);
		return buffer.array();
	}
	
	/**
	 * encode ulong(int) 4-bytes variable to byte array
	 * compatible with LITTLE-ENDIAN
	 * @param var
	 * @return 4-elements byte array
	 */
	public static byte[] toULONG(int var){
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(var);
		return buffer.array();
	}
	
	/**
	 * if byte value is higher than max positive value of byte in java
	 * 127, then print byte value as unsigned ubyte from cpp
	 * @param bytes-array
	 * @return readable byte array values
	 */
	public static String bytesToString(byte[] bytes){
		String temp="[";
		int i = 0;
		for(byte b:bytes){
			int ubyte = (int)(b & 0xFF);
			temp+= "0x"+ Integer.toHexString(ubyte).toUpperCase();
			temp+= (i==bytes.length-1)? "" : ", ";
			i++;
		}
		return temp+"]";
	}

}
