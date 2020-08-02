package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public enum ECell
{
	Empty((byte) 0),
	AreaWall((byte) 1),
	BlueWall((byte) 2),
	YellowWall((byte) 3),
	;

	private final byte value;
	ECell(byte value) { this.value = value; }
	public byte getValue() { return value; }
	
	private static Map<Byte, ECell> reverseLookup;
	
	public static ECell of(byte value)
	{
		if (reverseLookup == null)
		{
			reverseLookup = new HashMap<>();
			for (ECell c : ECell.values())
				reverseLookup.put(c.getValue(), c);
		}
		return reverseLookup.get(value);
	}
}
