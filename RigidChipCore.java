import java.util.*;

import javavl.consts.*;
import javavl.lower.*;

public class RigidChipCore extends RigidChip
{
	public int getType(){ return RigidChip.TYPE_CORE; }
	public String getTypeString(){ return "Core"; }
	public static jVLTexture texture = null;
	public jVLTexture getTexture(){ return texture; }

	public Hashtable variables;
	public RCKey[] keys;

	public RigidChipCore()
	{
		core = this;
		
		variables = new Hashtable();
		keys = new RCKey[17];
		for (int i = 0; i < keys.length; i ++)
			keys[i] = new RCKey(this, i);
	}
	
	public Float strToFloat(String str)
	{
		if (str == null || "".equals(str)) return null;
		try
		{
			if (str.charAt(0) == '#')
				return new Float(Integer.decode(str).floatValue());

			if (str.charAt(0) == '-')
			{
				RCVariable var = (RCVariable)variables.get(str.substring(1).toUpperCase());
				if (var != null)
					return new Float(-var.value);
			}

			RCVariable var = (RCVariable)variables.get(str.substring(0).toUpperCase());
			if (var != null)
				return new Float(var.value);

			return Float.valueOf(str);
		}
		catch (Exception e)
		{
		}
		return null;
	}
}
