/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shuntingyard;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author thegoodhen
 */
public class HelpByteMethods {

	public static byte getLowerByte(int value) {
		return (byte) value;
	}

	public static byte getUpperByte(int value) {
		return (byte) (value >> 8);
	}

	public static int constructInt(byte msb, byte lsb) {
		return (((msb) << 8) | lsb & 0xFF);
	}

	public static float constructFloat(byte[] arr) {

		return ByteBuffer.wrap(arr).order(ByteOrder.LITTLE_ENDIAN).getFloat();
	}

	public static byte[] getFloatBytes(float val) {

		int bits = Float.floatToIntBits(val);
		byte[] bytes = new byte[4];
		for (int i = 0; i < 4; i++) {

			bytes[i] = (byte) ((bits >> i * 8) & 0xff);
		}
		return bytes;
	}

}
