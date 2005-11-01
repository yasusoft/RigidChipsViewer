import javavl.consts.*;
import javavl.lower.*;

public class RigidChipArm extends RigidChip
{
	public int getType(){ return TYPE_ARM; }
	public String getTypeString(){ return "Arm"; }
	public static jVLTexture texture;
	public jVLTexture getTexture(){ return texture; }
	
	private boolean anime;
	private float energy;
	
	protected jVLVertex[][] vertices = new jVLVertex[3][];
	protected int[][] indices = new int[3][];
	protected void drawMain(jVLDevice device)
	{
		if (vertices[0] == null || indices[0] == null)
		{	// top-left
			makeRect(new float[]{-0.3f, -0.2f, -0.1f, -0.3f}, new float[]{-0.3f, -0.3f, 0.0f, 0.0f}, new float[]{0.00f, 0.17f, 0.33f, 0.00f}, new float[]{0.0f, 0.0f, 0.5f, 0.5f});
			vertices[0] = super.vertices;
			indices[0]  = super.indices;
		}
		device.drawIndexedPrimitive(jVLPMType.TRIANGLE, vertices[0], indices[0]);
		if (vertices[1] == null || indices[1] == null)
		{	// top-right
			makeRect(new float[]{0.2f, 0.3f, 0.3f, 0.1f}, new float[]{-0.3f, -0.3f, 0.0f, 0.0f}, new float[]{0.83f, 1.00f, 1.00f, 0.66f}, new float[]{0.0f, 0.0f, 0.5f, 0.5f});
			vertices[1] = super.vertices;
			indices[1]  = super.indices;
		}
		device.drawIndexedPrimitive(jVLPMType.TRIANGLE, vertices[1], indices[1]);
		if (vertices[2] == null || indices[2] == null)
		{	// bottom
			makeRect(-0.3f, 0.0f, 0.3f, 0.3f, 0.00f, 0.5f, 1.0f, 1.0f);
			vertices[2] = super.vertices;
			indices[2]  = super.indices;
		}
		device.drawIndexedPrimitive(jVLPMType.TRIANGLE, vertices[2], indices[2]);
	}

	protected void drawTransMain(jVLDevice device)
	{
		if (!anime) return;

		int opt = 0;
		Float f;
		if ((f = core.strToFloat(option.getProperty("option"))) != null)
			opt = f.intValue();
		if (opt <= 0) return;
		if (opt > 50000) opt = 50000;

		jVLVertex[] vertices = new jVLVertex[4];
		vertices[0] = new jVLVertex();
		vertices[0].x = -opt/400000f;
		vertices[0].y = 0f;
		vertices[0].z = -opt/80000f-0.3f;
		vertices[0].nx =  0f;
		vertices[0].ny = -1f;
		vertices[0].nz =  0f;
		vertices[0].color = 0x80000000;
		vertices[1] = new jVLVertex();
		vertices[1].x =  opt/400000f;
		vertices[1].y = 0f;
		vertices[1].z = -opt/80000f-0.3f;
		vertices[1].nx =  0f;
		vertices[1].ny = -1f;
		vertices[1].nz =  0f;
		vertices[1].color = 0x80000000;
		vertices[2] = new jVLVertex();
		vertices[2].x =  opt/400000f;
		vertices[2].y = 0f;
		vertices[2].z = 0f;
		vertices[2].nx =  0f;
		vertices[2].ny = -1f;
		vertices[2].nz =  0f;
		vertices[2].color = 0x80000000;
		vertices[3] = new jVLVertex();
		vertices[3].x = -opt/400000f;
		vertices[3].y = 0f;
		vertices[3].z = 0f;
		vertices[3].nx =  0f;
		vertices[3].ny = -1f;
		vertices[3].nz =  0f;
		vertices[3].color = 0x80000000;
		int[] indices = new int[6];
		indices[0] = 0;
		indices[1] = 1;
		indices[2] = 2;
		indices[3] = 2;
		indices[4] = 3;
		indices[5] = 0;
		device.setRenderState(jVLRS.ALPHABLENDENABLE, 1);
		device.getMaterial().ambient.set(1f, 1f, 0f);
		device.getMaterial().diffuse.set(0f, 0f, 0f);
		device.drawIndexedPrimitive(jVLPMType.TRIANGLE, vertices, indices);
		device.setRenderState(jVLRS.ALPHABLENDENABLE, 0);
	}
	
	public void act()
	{
		boolean a = anime;
		if (anime)
			anime = false;

		int opt = 0;
		Float f;
		if ((f = core.strToFloat(option.getProperty("option"))) != null)
			opt = f.intValue();
		if (opt < 0) opt *= -1;
		if (opt > 1)
		{
			energy += 5000;
			if (energy > opt)
			{
				energy = opt;

				if ((f = core.strToFloat(option.getProperty("power"))) != null)
				{
					int power = f.intValue();
					if (power >= opt)
					{
						energy -= opt;
						if (!a) anime = true;
					}
				}
			}
		}
		super.act();
	}
}
