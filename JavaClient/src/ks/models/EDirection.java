package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public enum EDirection
{
	Up((byte) 0),
	Right((byte) 1),
	Down((byte) 2),
	Left((byte) 3),
	;

	private final byte value;
	EDirection(byte value) { this.value = value; }
	public byte getValue() { return value; }
	
	private static Map<Byte, EDirection> reverseLookup;
	
	public static EDirection of(byte value)
	{
		if (reverseLookup == null)
		{
			reverseLookup = new HashMap<>();
			for (EDirection c : EDirection.values())
				reverseLookup.put(c.getValue(), c);
		}
		return reverseLookup.get(value);
	}
}
