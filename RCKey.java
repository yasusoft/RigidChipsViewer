import java.util.*;

public class RCKey
{
	public RigidChipCore core;
	public int key;
	public Hashtable steps;
	
	public RCKey(RigidChipCore core, int key)
	{
		this.core = core;
		this.key = key;
		steps = new Hashtable();
	}
	
	public void act()
	{
		for (Enumeration e = steps.keys(); e.hasMoreElements();)
		{
			RCVariable var = (RCVariable)e.nextElement();
			Float step = (Float)steps.get(var);
			var.setValue(var.value + step.floatValue());
		}
	}
}
