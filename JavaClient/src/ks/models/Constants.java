package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class Constants extends KSObject
{
	protected Integer maxCycles;
	protected Integer initHealth;
	protected Integer wallBreakerCooldown;
	protected Integer wallBreakerDuration;
	protected Integer wallScoreCoefficient;
	protected Integer areaWallCrashScore;
	protected Integer myWallCrashScore;
	protected Integer enemyWallCrashScore;
	
	// getters
	
	public Integer getMaxCycles()
	{
		return this.maxCycles;
	}
	
	public Integer getInitHealth()
	{
		return this.initHealth;
	}
	
	public Integer getWallBreakerCooldown()
	{
		return this.wallBreakerCooldown;
	}
	
	public Integer getWallBreakerDuration()
	{
		return this.wallBreakerDuration;
	}
	
	public Integer getWallScoreCoefficient()
	{
		return this.wallScoreCoefficient;
	}
	
	public Integer getAreaWallCrashScore()
	{
		return this.areaWallCrashScore;
	}
	
	public Integer getMyWallCrashScore()
	{
		return this.myWallCrashScore;
	}
	
	public Integer getEnemyWallCrashScore()
	{
		return this.enemyWallCrashScore;
	}
	
	
	// setters
	
	public void setMaxCycles(Integer maxCycles)
	{
		this.maxCycles = maxCycles;
	}
	
	public void setInitHealth(Integer initHealth)
	{
		this.initHealth = initHealth;
	}
	
	public void setWallBreakerCooldown(Integer wallBreakerCooldown)
	{
		this.wallBreakerCooldown = wallBreakerCooldown;
	}
	
	public void setWallBreakerDuration(Integer wallBreakerDuration)
	{
		this.wallBreakerDuration = wallBreakerDuration;
	}
	
	public void setWallScoreCoefficient(Integer wallScoreCoefficient)
	{
		this.wallScoreCoefficient = wallScoreCoefficient;
	}
	
	public void setAreaWallCrashScore(Integer areaWallCrashScore)
	{
		this.areaWallCrashScore = areaWallCrashScore;
	}
	
	public void setMyWallCrashScore(Integer myWallCrashScore)
	{
		this.myWallCrashScore = myWallCrashScore;
	}
	
	public void setEnemyWallCrashScore(Integer enemyWallCrashScore)
	{
		this.enemyWallCrashScore = enemyWallCrashScore;
	}
	
	
	public Constants()
	{
	}
	
	public static final String nameStatic = "Constants";
	
	@Override
	public String name() { return "Constants"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize maxCycles
		s.add((byte) ((maxCycles == null) ? 0 : 1));
		if (maxCycles != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(maxCycles).array()));
		}
		
		// serialize initHealth
		s.add((byte) ((initHealth == null) ? 0 : 1));
		if (initHealth != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(initHealth).array()));
		}
		
		// serialize wallBreakerCooldown
		s.add((byte) ((wallBreakerCooldown == null) ? 0 : 1));
		if (wallBreakerCooldown != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(wallBreakerCooldown).array()));
		}
		
		// serialize wallBreakerDuration
		s.add((byte) ((wallBreakerDuration == null) ? 0 : 1));
		if (wallBreakerDuration != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(wallBreakerDuration).array()));
		}
		
		// serialize wallScoreCoefficient
		s.add((byte) ((wallScoreCoefficient == null) ? 0 : 1));
		if (wallScoreCoefficient != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(wallScoreCoefficient).array()));
		}
		
		// serialize areaWallCrashScore
		s.add((byte) ((areaWallCrashScore == null) ? 0 : 1));
		if (areaWallCrashScore != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(areaWallCrashScore).array()));
		}
		
		// serialize myWallCrashScore
		s.add((byte) ((myWallCrashScore == null) ? 0 : 1));
		if (myWallCrashScore != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(myWallCrashScore).array()));
		}
		
		// serialize enemyWallCrashScore
		s.add((byte) ((enemyWallCrashScore == null) ? 0 : 1));
		if (enemyWallCrashScore != null)
		{
			s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(enemyWallCrashScore).array()));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize maxCycles
		byte tmp0;
		tmp0 = s[offset];
		offset += Byte.BYTES;
		if (tmp0 == 1)
		{
			maxCycles = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			maxCycles = null;
		
		// deserialize initHealth
		byte tmp1;
		tmp1 = s[offset];
		offset += Byte.BYTES;
		if (tmp1 == 1)
		{
			initHealth = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			initHealth = null;
		
		// deserialize wallBreakerCooldown
		byte tmp2;
		tmp2 = s[offset];
		offset += Byte.BYTES;
		if (tmp2 == 1)
		{
			wallBreakerCooldown = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			wallBreakerCooldown = null;
		
		// deserialize wallBreakerDuration
		byte tmp3;
		tmp3 = s[offset];
		offset += Byte.BYTES;
		if (tmp3 == 1)
		{
			wallBreakerDuration = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			wallBreakerDuration = null;
		
		// deserialize wallScoreCoefficient
		byte tmp4;
		tmp4 = s[offset];
		offset += Byte.BYTES;
		if (tmp4 == 1)
		{
			wallScoreCoefficient = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			wallScoreCoefficient = null;
		
		// deserialize areaWallCrashScore
		byte tmp5;
		tmp5 = s[offset];
		offset += Byte.BYTES;
		if (tmp5 == 1)
		{
			areaWallCrashScore = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			areaWallCrashScore = null;
		
		// deserialize myWallCrashScore
		byte tmp6;
		tmp6 = s[offset];
		offset += Byte.BYTES;
		if (tmp6 == 1)
		{
			myWallCrashScore = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			myWallCrashScore = null;
		
		// deserialize enemyWallCrashScore
		byte tmp7;
		tmp7 = s[offset];
		offset += Byte.BYTES;
		if (tmp7 == 1)
		{
			enemyWallCrashScore = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			offset += Integer.BYTES;
		}
		else
			enemyWallCrashScore = null;
		
		return offset;
	}
}
