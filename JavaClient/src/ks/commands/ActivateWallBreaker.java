package ks.commands;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class ActivateWallBreaker extends KSObject
{
	
	// getters
	
	
	// setters
	
	
	public ActivateWallBreaker()
	{
	}
	
	public static final String nameStatic = "ActivateWallBreaker";
	
	@Override
	public String name() { return "ActivateWallBreaker"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		return offset;
	}
}
