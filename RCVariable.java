import java.util.*;

public class RCVariable
{
	public RigidChipCore core;
	public String name;
	public float value, def, min, max, step;
	public boolean fcolor, fmax, fstep, disp;
	public boolean modified;

	private Float strToFloat(String s)
	{
		try {
			return new Float(s);
		} catch (Exception e) { }
		return null;
	}
	public RCVariable(RigidChipCore core, String name, String opt)
	{
		this.core = core;
		this.name = name;
		
		Properties opts = new Properties();
		RigidChip.setOptions(opts, opt);
		
		Float f;
		def = min = max = step = 0.0f;
		if (opts.getProperty("default") != null)
			fcolor = opts.getProperty("default").charAt(0) == '#';
		if (fcolor)
			def = Integer.decode(opts.getProperty("default")).intValue();
		else if ((f = strToFloat(opts.getProperty("default"))) != null)
			def = f.floatValue();
		if ((f = strToFloat(opts.getProperty("min"))) != null)
			min = f.floatValue();
		fmax = (f = strToFloat(opts.getProperty("max"))) != null;
		if (fmax)
			max = f.floatValue();
		fstep = (f = strToFloat(opts.getProperty("step"))) != null;
		if (fstep)
			step = f.floatValue();
		disp = !"0".equals(opts.getProperty("disp"));
		
		value = def;
	}
	
	public void setValue(float v)
	{
		if (value == v) return;
		modified = true;
		if (v < min)
			value = min;
		else if (fmax && v > max)
			value = max;
		else
			value = v;
	}

	public void reset()
	{
		modified = false;
	}
	public void act()
	{
		if (modified || !fstep) return;
		if (value < def)
		{
			modified = true;
			if (def - value < step)
				value = def;
			else
				value += step;
		}
		else if (value > def)
		{
			modified = true;
			if (value - def < step)
				value = def;
			else
				value -= step;
		}
	}
}
