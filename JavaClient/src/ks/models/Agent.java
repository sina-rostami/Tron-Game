package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class Agent extends KSObject
{
	protected Integer health;
	protected Position position;
	protected EDirection direction;
	protected Integer wallBreakerCooldown;
	protected Integer wallBreakerRemTime;
	
	// getters
	
	public Integer getHealth()
	{
		return this.health;
	}
	
	public Position getPosition()
	{
		return this.position;
	}
	
	public EDirection getDirection()
	{
		return this.direction;
	}
	
	public Integer getWallBreakerCooldown()
	{
		return this.wallBreakerCooldown;
	}
	
	public Integer getWallBreakerRemTime()
	{
		return this.wallBreakerRemTime;
	}
	
	
	// setters
	
	public void setHealth(Integer health)
	{
		this.health = health;
	}
	
	public void setPosition(Position position)
	{
		this.position = position;
	}
	
	public void setDirection(EDirection direction)
	{
		this.direction = direction;
	}
	
	public void setWallBreakerCooldown(Integer wallBreakerCooldown)
	{
		this.wallBreakerCooldown = wallBreakerCooldown;
	}
	
	public void setWallBreakerRemTime(Integer wallBreakerRemTime)
	{
		this.wallBreakerRemTime = wallBreakerRemTime;
	}
	
	
	public Agent()
	{
	}
	
	public static final String nameStatic = "Agent";
	
	@Override
	public String name() { return "Agent"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize health
		s.add((byte) ((health == null) ? 0 : 1));
		if (health != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(health).array()));
		}
		
		// serialize position
		s.add((byte) ((position == null) ? 0 : 1));
		if (position != null)
		{
			s.addAll(b2B(position.serialize()));
		}
		
		// serialize direction
		s.add((byte) ((direction == null) ? 0 : 1));
		if (direction != null)
		{
			s.add((byte) (direction.getValue()));
		}
		
		// serialize wallBreakerCooldown
		s.add((byte) ((wallBreakerCooldown == null) ? 0 : 1));
		if (wallBreakerCooldown != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(wallBreakerCooldown).array()));
		}
		
		// serialize wallBreakerRemTime
		s.add((byte) ((wallBreakerRemTime == null) ? 0 : 1));
		if (wallBreakerRemTime != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(wallBreakerRemTime).array()));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize health
		byte tmp0;
		tmp0 = s[offset];
		offset += Byte.BYTES;
		if (tmp0 == 1)
		{
			health = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			health = null;
		
		// deserialize position
		byte tmp1;
		tmp1 = s[offset];
		offset += Byte.BYTES;
		if (tmp1 == 1)
		{
			position = new Position();
			offset = position.deserialize(s, offset);
		}
		else
			position = null;
		
		// deserialize direction
		byte tmp2;
		tmp2 = s[offset];
		offset += Byte.BYTES;
		if (tmp2 == 1)
		{
			byte tmp3;
			tmp3 = s[offset];
			offset += Byte.BYTES;
			direction = EDirection.of(tmp3);
		}
		else
			direction = null;
		
		// deserialize wallBreakerCooldown
		byte tmp4;
		tmp4 = s[offset];
		offset += Byte.BYTES;
		if (tmp4 == 1)
		{
			wallBreakerCooldown = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			wallBreakerCooldown = null;
		
		// deserialize wallBreakerRemTime
		byte tmp5;
		tmp5 = s[offset];
		offset += Byte.BYTES;
		if (tmp5 == 1)
		{
			wallBreakerRemTime = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			wallBreakerRemTime = null;
		
		return offset;
	}
}
