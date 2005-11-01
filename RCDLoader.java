import java.net.*;
import java.io.*;
import java.util.*;

public class RCDLoader
{
	private String err;
	public String getError()
	{
		return err;
	}

	public RigidChipCore loadFromURL(URL url)
	{
		InputStream in = null;
		try
		{
			in = url.openStream();
			return load(in);
		}
		catch (Exception e)
		{
			err = e.toString();
			e.printStackTrace();
		}
		finally
		{
			try {
				if (in != null)
					in.close();
			} catch (Exception e) { }
		}
		return null;
	}

	protected RigidChipCore load(InputStream in) throws Exception
	{
		in = new BufferedInputStream(in);
	
		err = null;

		RigidChipCore core = new RigidChipCore();
		
		String key;
		while ((key = readKey(in).toLowerCase()) != null)
		{
			if ("val".equals(key))
				procVal(in, core);
			else if ("key".equals(key))
				procKey(in, core);
			else if ("body".equals(key))
			{
				procBody(in, core);
				break;
			}
			else
				throw new Exception("Unknown block type (" + key + ")");
		}
		
		return core;
	}
	
	protected void procVal(InputStream in, RigidChipCore core) throws Exception
	{
		if (in.read() != '{')
			throw new Exception("open bracket must be after val");
		
		String key;
		while ((key = readKey(in)) != null)
		{
			if (in.read() != '(')
				throw new Exception("open square bracket must be after variable name (" + key + ")");

			key = key.toUpperCase();
			RCVariable var = new RCVariable(core, key, readTo(in, ')'));
			core.variables.put(key, var);
		}

		if (in.read() != '}')
			throw new Exception("close bracket of val was not found");
	}

	protected void procKey(InputStream in, RigidChipCore core) throws Exception
	{
		if (in.read() != '{')
			throw new Exception("open bracket must be after key");
		
		String keyn;
		while ((keyn = readKey(in)) != null)
		{
			if (in.read() != ':')
				throw new Exception(": must be after key number");

			String var;
			while ((var = readKey(in)) != "")
			{
				if (in.read() != '(')
					throw new Exception("open square bracket must be after variable name");
				
				int n = 0;
				try {
					n = Integer.parseInt(keyn);
				} catch (Exception e) { }
				
				Properties opts = new Properties();
				RigidChip.setOptions(opts, readTo(in, ')'));
				Float step = null;
				try {
					step = new Float(opts.getProperty("step"));
				} catch (Exception e) { }
				
				if (step != null && n < core.keys.length)
				{
					RCVariable v = (RCVariable)core.variables.get(var.toUpperCase());
					if (v != null)
						core.keys[n].steps.put(v, step);
				}
				
				while (readComment(in) != null);
				if (markedRead(in) != ',')
				{
					in.reset();
					break;
				}
			}
		}

		if (in.read() != '}')
			throw new Exception("close bracket of key was not found");
	}

	protected void procBody(InputStream in, RigidChipCore core) throws Exception
	{
		if (in.read() != '{')
			throw new Exception("open bracket must be after body");
		
		String key = readKey(in);
		if (key == null)
			throw new Exception("can't read first element of body");

		key = key.toLowerCase();
		if ("n".equals(key) || "s".equals(key)
		 || "e".equals(key) || "w".equals(key)
		)
		{
			if (in.read() != ':')
				throw new Exception(": must be after direction");
			key = readKey(in);
			if (key == null)
				throw new Exception("chip type must be after direction:");
			key = key.toLowerCase();
		}
		if (!"core".equals(key))
			throw new Exception("first chip is not core (" + key + ")");

		if (in.read() != '(')
			throw new Exception("open square bracket must be after core");

		core.setOptions(readTo(in, ')'));

		while (readComment(in) != null);

		if (in.read() != '{')
			throw new Exception("open bracket must be after core()");

		readChips(in, core);

		// Coreの閉じ
		if (in.read() != '}')
			;//throw new Exception("close bracket of core was not found");

		while (readComment(in) != null);

		// Bodyの閉じ
		if (in.read() != '}')
			;//throw new Exception("close bracket of body was not found");
	}
	
	protected void readChips(InputStream in, RigidChip addto) throws Exception
	{
		Vector adds = new Vector();

		while (readComment(in) != null);

		Stack stack = new Stack();
		RigidChip parent = addto;

		try
		{
			int c;
			while ((c = markedRead(in)) != -1)
			{
				if (c == '}')
				{
					if (stack.empty())
					{
						in.reset();
						break;
					}
					while (readComment(in) != null);
					parent = (RigidChip)stack.pop();
					continue;
				}
				in.reset();
				
				String key = readKey(in);
				if (key == null)
					throw new Exception("can't read direction or chip type");
				key = key.toLowerCase();
				
				int dir = RigidChip.DIR_CORE;
				if ("n".equals(key))
					dir = RigidChip.DIR_NORTH;
				else if ("e".equals(key))
					dir = RigidChip.DIR_EAST;
				else if ("w".equals(key))
					dir = RigidChip.DIR_WEST;
				else if ("s".equals(key))
					dir = RigidChip.DIR_SOUTH;
				
				if (dir != RigidChip.DIR_CORE)
				{
					if (in.read() != ':')
						throw new Exception(": must be after direction " + key);
					key = readKey(in);
					if (key == null)
						throw new Exception("chip type must be after direction:");
					key = key.toLowerCase();
				}
				else
					dir = RigidChip.DIR_NORTH;

				if (in.read() != '(')
					throw new Exception("open square bracket must be after " + key);
				
				RigidChip chip;
				if (parent != null && parent.getType() == RigidChip.TYPE_COWL && !"cowl".equals(key))
					throw new Exception("can't join " + key + " to cowl");
				if ("core".equals(key))
					throw new Exception("can't join core");
				chip = RigidChip.create(key);
				if (chip == null)
					throw new Exception("unknown chip type (" + key + ")");

				chip.direction = dir;
				chip.setOptions(readTo(in, ')'));

				if (parent == addto)
					adds.addElement(chip);
				parent.addSub(chip);
				stack.push(parent);
				parent = chip;
				
				while (readComment(in) != null);
				
				if (in.read() != '{')
					throw new Exception("open bracket must be after chip definition");

				while (readComment(in) != null);
			}
		}
		catch (Exception e)
		{
			for (int i = 0; i < adds.size(); i ++)
				addto.delSub((RigidChip)adds.elementAt(i));
			throw e;
		}
	}
	
	protected static int markedRead(InputStream in) throws IOException
	{
		in.mark(1);
		return in.read();
	}
	// スペースを飛ばす
	protected static void skipSpace(InputStream in) throws IOException
	{
		int c;
		while ((c = markedRead(in)) != -1)
			if (c > 0x20)
			{
				in.reset();
				break;
			}
	}
	// 指定文字まで読む
	protected static String readTo(InputStream in, int to) throws IOException
	{
		int c;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while ((c = in.read()) != -1 && c != to)
			out.write(c);
		return out.toString();
	}
	// コメントだったら改行まで読み込む
	protected static String readComment(InputStream in) throws IOException
	{
		skipSpace(in);

		int c;
		// 最初が"//"かチェック
		in.mark(2);
		if ((c = in.read()) != -1)
		{
			if (c != '/')
			{
				in.reset();
				return null;
			}
		}
		else
			return null;
		if ((c = in.read()) != -1)
		{
			if (c != '/')
			{
				in.reset();
				return null;
			}
		}
		else
			return null;

		// 最初のスペース１個は飛ばす
		if ((c = markedRead(in)) != -1 && c != 0x20)
			in.reset();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while ((c = in.read()) != -1)
		{
			out.write(c);
			if (c == '\n')
				break;
		}
		return out.toString();
	}
	// 英数字を読み込む
	protected static String readKey(InputStream in) throws IOException
	{
		while (readComment(in) != null);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int c;
		while ((c = markedRead(in)) != -1)
		{
			if ('A' <= c && c <= 'Z' || 'a' <= c && c <= 'z'
			 || '0' <= c && c <= '9' || c == '_' || c == '-'
			)
				out.write(c);
			else
			{
				in.reset();
				break;
			}
		}

		while (readComment(in) != null);

		if (out.size() == 0)
			return null;
		return out.toString();
	}
}
