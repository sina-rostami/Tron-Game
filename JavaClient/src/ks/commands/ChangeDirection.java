package ks.commands;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class ChangeDirection extends KSObject
{
	protected ECommandDirection direction;
	
	// getters
	
	public ECommandDirection getDirection()
	{
		return this.direction;
	}
	
	
	// setters
	
	public void setDirection(ECommandDirection direction)
	{
		this.direction = direction;
	}
	
	
	public ChangeDirection()
	{
	}
	
	public static final String nameStatic = "ChangeDirection";
	
	@Override
	public String name() { return "ChangeDirection"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize direction
		s.add((byte) ((direction == null) ? 0 : 1));
		if (direction != null)
		{
			s.add((byte) (direction.getValue()));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize direction
		byte tmp0;
		tmp0 = s[offset];
		offset += Byte.BYTES;
		if (tmp0 == 1)
		{
			byte tmp1;
			tmp1 = s[offset];
			offset += Byte.BYTES;
			direction = ECommandDirection.of(tmp1);
		}
		else
			direction = null;
		
		return offset;
	}
}
