package ks.models;

import java.lang.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;

import ks.KSObject;

public class World extends KSObject
{
	protected List<List<ECell>> board;
	protected Map<String, Agent> agents;
	protected Map<String, Integer> scores;
	protected Constants constants;
	
	// getters
	
	public List<List<ECell>> getBoard()
	{
		return this.board;
	}
	
	public Map<String, Agent> getAgents()
	{
		return this.agents;
	}
	
	public Map<String, Integer> getScores()
	{
		return this.scores;
	}
	
	public Constants getConstants()
	{
		return this.constants;
	}
	
	
	// setters
	
	public void setBoard(List<List<ECell>> board)
	{
		this.board = board;
	}
	
	public void setAgents(Map<String, Agent> agents)
	{
		this.agents = agents;
	}
	
	public void setScores(Map<String, Integer> scores)
	{
		this.scores = scores;
	}
	
	public void setConstants(Constants constants)
	{
		this.constants = constants;
	}
	
	
	public World()
	{
	}
	
	public static final String nameStatic = "World";
	
	@Override
	public String name() { return "World"; }
	
	@Override
	public byte[] serialize()
	{
		List<Byte> s = new ArrayList<>();
		
		// serialize board
		s.add((byte) ((board == null) ? 0 : 1));
		if (board != null)
		{
			List<Byte> tmp0 = new ArrayList<>();
			tmp0.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(board.size()).array()));
			while (tmp0.size() > 0 && tmp0.get(tmp0.size() - 1) == 0)
				tmp0.remove(tmp0.size() - 1);
			s.add((byte) tmp0.size());
			s.addAll(tmp0);
			
			for (List<ECell> tmp1 : board)
			{
				s.add((byte) ((tmp1 == null) ? 0 : 1));
				if (tmp1 != null)
				{
					List<Byte> tmp2 = new ArrayList<>();
					tmp2.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp1.size()).array()));
					while (tmp2.size() > 0 && tmp2.get(tmp2.size() - 1) == 0)
						tmp2.remove(tmp2.size() - 1);
					s.add((byte) tmp2.size());
					s.addAll(tmp2);
					
					for (ECell tmp3 : tmp1)
					{
						s.add((byte) ((tmp3 == null) ? 0 : 1));
						if (tmp3 != null)
						{
							s.add((byte) (tmp3.getValue()));
						}
					}
				}
			}
		}
		
		// serialize agents
		s.add((byte) ((agents == null) ? 0 : 1));
		if (agents != null)
		{
			List<Byte> tmp4 = new ArrayList<>();
			tmp4.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(agents.size()).array()));
			while (tmp4.size() > 0 && tmp4.get(tmp4.size() - 1) == 0)
				tmp4.remove(tmp4.size() - 1);
			s.add((byte) tmp4.size());
			s.addAll(tmp4);
			
			for (Map.Entry<String, Agent> tmp5 : agents.entrySet())
			{
				s.add((byte) ((tmp5.getKey() == null) ? 0 : 1));
				if (tmp5.getKey() != null)
				{
					List<Byte> tmp6 = new ArrayList<>();
					tmp6.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp5.getKey().length()).array()));
					while (tmp6.size() > 0 && tmp6.get(tmp6.size() - 1) == 0)
						tmp6.remove(tmp6.size() - 1);
					s.add((byte) tmp6.size());
					s.addAll(tmp6);
					
					s.addAll(b2B(tmp5.getKey().getBytes(Charset.forName("ISO-8859-1"))));
				}
				
				s.add((byte) ((tmp5.getValue() == null) ? 0 : 1));
				if (tmp5.getValue() != null)
				{
					s.addAll(b2B(tmp5.getValue().serialize()));
				}
			}
		}
		
		// serialize scores
		s.add((byte) ((scores == null) ? 0 : 1));
		if (scores != null)
		{
			List<Byte> tmp7 = new ArrayList<>();
			tmp7.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(scores.size()).array()));
			while (tmp7.size() > 0 && tmp7.get(tmp7.size() - 1) == 0)
				tmp7.remove(tmp7.size() - 1);
			s.add((byte) tmp7.size());
			s.addAll(tmp7);
			
			for (Map.Entry<String, Integer> tmp8 : scores.entrySet())
			{
				s.add((byte) ((tmp8.getKey() == null) ? 0 : 1));
				if (tmp8.getKey() != null)
				{
					List<Byte> tmp9 = new ArrayList<>();
					tmp9.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp8.getKey().length()).array()));
					while (tmp9.size() > 0 && tmp9.get(tmp9.size() - 1) == 0)
						tmp9.remove(tmp9.size() - 1);
					s.add((byte) tmp9.size());
					s.addAll(tmp9);
					
					s.addAll(b2B(tmp8.getKey().getBytes(Charset.forName("ISO-8859-1"))));
				}
				
				s.add((byte) ((tmp8.getValue() == null) ? 0 : 1));
				if (tmp8.getValue() != null)
				{
					s.addAll(b2B(ByteBuffer.allocate(Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).putInt(tmp8.getValue()).array()));
				}
			}
		}
		
		// serialize constants
		s.add((byte) ((constants == null) ? 0 : 1));
		if (constants != null)
		{
			s.addAll(b2B(constants.serialize()));
		}
		
		return B2b(s);
	}
	
	@Override
	protected int deserialize(byte[] s, int offset)
	{
		// deserialize board
		byte tmp10;
		tmp10 = s[offset];
		offset += Byte.BYTES;
		if (tmp10 == 1)
		{
			byte tmp11;
			tmp11 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp12 = Arrays.copyOfRange(s, offset, offset + tmp11);
			offset += tmp11;
			int tmp13;
			tmp13 = ByteBuffer.wrap(Arrays.copyOfRange(tmp12, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			board = new ArrayList<>();
			for (int tmp14 = 0; tmp14 < tmp13; tmp14++)
			{
				List<ECell> tmp15;
				byte tmp16;
				tmp16 = s[offset];
				offset += Byte.BYTES;
				if (tmp16 == 1)
				{
					byte tmp17;
					tmp17 = s[offset];
					offset += Byte.BYTES;
					byte[] tmp18 = Arrays.copyOfRange(s, offset, offset + tmp17);
					offset += tmp17;
					int tmp19;
					tmp19 = ByteBuffer.wrap(Arrays.copyOfRange(tmp18, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
					
					tmp15 = new ArrayList<>();
					for (int tmp20 = 0; tmp20 < tmp19; tmp20++)
					{
						ECell tmp21;
						byte tmp22;
						tmp22 = s[offset];
						offset += Byte.BYTES;
						if (tmp22 == 1)
						{
							byte tmp23;
							tmp23 = s[offset];
							offset += Byte.BYTES;
							tmp21 = ECell.of(tmp23);
						}
						else
							tmp21 = null;
						tmp15.add(tmp21);
					}
				}
				else
					tmp15 = null;
				board.add(tmp15);
			}
		}
		else
			board = null;
		
		// deserialize agents
		byte tmp24;
		tmp24 = s[offset];
		offset += Byte.BYTES;
		if (tmp24 == 1)
		{
			byte tmp25;
			tmp25 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp26 = Arrays.copyOfRange(s, offset, offset + tmp25);
			offset += tmp25;
			int tmp27;
			tmp27 = ByteBuffer.wrap(Arrays.copyOfRange(tmp26, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			agents = new HashMap<>();
			for (int tmp28 = 0; tmp28 < tmp27; tmp28++)
			{
				String tmp29;
				byte tmp31;
				tmp31 = s[offset];
				offset += Byte.BYTES;
				if (tmp31 == 1)
				{
					byte tmp32;
					tmp32 = s[offset];
					offset += Byte.BYTES;
					byte[] tmp33 = Arrays.copyOfRange(s, offset, offset + tmp32);
					offset += tmp32;
					int tmp34;
					tmp34 = ByteBuffer.wrap(Arrays.copyOfRange(tmp33, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
					
					tmp29 = new String(s, offset, tmp34, Charset.forName("ISO-8859-1"));
					offset += tmp34;
				}
				else
					tmp29 = null;
				
				Agent tmp30;
				byte tmp35;
				tmp35 = s[offset];
				offset += Byte.BYTES;
				if (tmp35 == 1)
				{
					tmp30 = new Agent();
					offset = tmp30.deserialize(s, offset);
				}
				else
					tmp30 = null;
				
				agents.put(tmp29, tmp30);
			}
		}
		else
			agents = null;
		
		// deserialize scores
		byte tmp36;
		tmp36 = s[offset];
		offset += Byte.BYTES;
		if (tmp36 == 1)
		{
			byte tmp37;
			tmp37 = s[offset];
			offset += Byte.BYTES;
			byte[] tmp38 = Arrays.copyOfRange(s, offset, offset + tmp37);
			offset += tmp37;
			int tmp39;
			tmp39 = ByteBuffer.wrap(Arrays.copyOfRange(tmp38, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
			
			scores = new HashMap<>();
			for (int tmp40 = 0; tmp40 < tmp39; tmp40++)
			{
				String tmp41;
				byte tmp43;
				tmp43 = s[offset];
				offset += Byte.BYTES;
				if (tmp43 == 1)
				{
					byte tmp44;
					tmp44 = s[offset];
					offset += Byte.BYTES;
					byte[] tmp45 = Arrays.copyOfRange(s, offset, offset + tmp44);
					offset += tmp44;
					int tmp46;
					tmp46 = ByteBuffer.wrap(Arrays.copyOfRange(tmp45, 0, 0 + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
					
					tmp41 = new String(s, offset, tmp46, Charset.forName("ISO-8859-1"));
					offset += tmp46;
				}
				else
					tmp41 = null;
				
				Integer tmp42;
				byte tmp47;
				tmp47 = s[offset];
				offset += Byte.BYTES;
				if (tmp47 == 1)
				{
					tmp42 = ByteBuffer.wrap(Arrays.copyOfRange(s, offset, offset + Integer.BYTES)).order(ByteOrder.LITTLE_ENDIAN).getInt();
					offset += Integer.BYTES;
				}
				else
					tmp42 = null;
				
				scores.put(tmp41, tmp42);
			}
		}
		else
			scores = null;
		
		// deserialize constants
		byte tmp48;
		tmp48 = s[offset];
		offset += Byte.BYTES;
		if (tmp48 == 1)
		{
			constants = new Constants();
			offset = constants.deserialize(s, offset);
		}
		else
			constants = null;
		
		return offset;
	}
}
