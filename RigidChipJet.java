import javavl.consts.*;
import javavl.lower.*;

public class RigidChipJet extends RigidChip
{
	public int getType(){ return TYPE_JET; }
	public String getTypeString(){ return "Jet"; }
	public static jVLTexture texture, textureBall;
	public jVLTexture getTexture(){ return texture; }

	private int anime;

	protected void drawMain(jVLDevice device)
	{
		int opt = 0;
		Float f;
		if ((f = core.strToFloat(option.getProperty("option"))) != null)
			opt = f.intValue();
		
		if (opt == 1 || opt == 2)
		{
			float power = 0;
			if ((f = core.strToFloat(option.getProperty("power"))) != null)
				power = f.floatValue();
			if (power < 0) power *= -1;
			makeSphere((float)Math.pow((power+15)*3/(30*4*Math.PI), 1 / 3.0) * 0.3f, 12, 6);
			if (textureBall != null)
			{
				device.setTexture(0, textureBall);
				device.setTextureStageState(0, jVLTSS.COLOROP, jVLTOP.MODULATE);
			}
			else
				device.setTextureStageState(0, jVLTSS.COLOROP, jVLTOP.DISABLE);
			device.drawIndexedPrimitive(jVLPMType.TRIANGLE, vertices, indices);
		}
		else
		{
			if (vertices == null || indices == null)
				makeCircle();
			device.drawIndexedPrimitive(jVLPMType.TRIANGLE, vertices, indices);
		}
	}

	protected void drawTransMain(jVLDevice device)
	{
		int opt = 0;
		Float f;
		if ((f = core.strToFloat(option.getProperty("option"))) != null)
			opt = f.intValue();

		if (opt == 1 || opt == 2)
		{
		}
		else
		{
			float power = 0;
			if ((f = core.strToFloat(option.getProperty("power"))) != null)
				power = f.floatValue();
			if (power > 0)
			{
				jVLMatrix matrix = device.getTransform(jVLTransformType.WORLD, 0);
				jVLMatrix mat = new jVLMatrix();
				mat.rotationX((float)Math.PI);
				matrix = mat.multiply(matrix);
				device.setTransform(jVLTransformType.WORLD, matrix, 0);
			}
			else
				power *= -1;
			if (power > 1000)
				power = 1000;

			if (power > 1)
			{
				jVLVertex[] vertices = new jVLVertex[5];
				vertices[0] = new jVLVertex();
				vertices[0].x = 0f;
				vertices[0].y = 1f;
				vertices[0].z = 0f;
				vertices[0].nx = 0f;
				vertices[0].ny = 1f;
				vertices[0].nz = 0f;
				vertices[0].color = 0x80000000;
				vertices[1] = new jVLVertex();
				vertices[1].x = 0.15f;
				vertices[1].y = 0f;
				vertices[1].z = 0f;
				vertices[1].nx = 0f;
				vertices[1].ny = 0f;
				vertices[1].nz = 1f;
				vertices[1].color = 0x80000000;
				vertices[2] = new jVLVertex();
				vertices[2].x = -0.15f;
				vertices[2].y = 0f;
				vertices[2].z = 0f;
				vertices[2].nx = 0f;
				vertices[2].ny = 0f;
				vertices[2].nz = 1f;
				vertices[2].color = 0x80000000;
				vertices[3] = new jVLVertex();
				vertices[3].x = 0f;
				vertices[3].y = 0f;
				vertices[3].z = 0.15f;
				vertices[3].nx = 1f;
				vertices[3].ny = 0f;
				vertices[3].nz = 0f;
				vertices[3].color = 0x80000000;
				vertices[4] = new jVLVertex();
				vertices[4].x = 0f;
				vertices[4].y = 0f;
				vertices[4].z = -0.15f;
				vertices[4].nx = 1f;
				vertices[4].ny = 0f;
				vertices[4].nz = 0f;
				vertices[4].color = 0x80000000;
				int[] indices = new int[6];
				indices[0] = 0;
				indices[1] = 1;
				indices[2] = 2;
				indices[3] = 0;
				indices[4] = 3;
				indices[5] = 4;
				device.setRenderState(jVLRS.ALPHABLENDENABLE, 1);
				device.getMaterial().diffuse.set(0f, 0f, 0f);
				// è¨Ç≥Ç¢âä
				vertices[0].y = power / 1500f + 0.1f*(float)Math.sin(anime * (float)Math.PI / 180f);
				device.getMaterial().ambient.set(0f, 1f, 1f);
				device.drawIndexedPrimitive(jVLPMType.TRIANGLE, vertices, indices);
				// ëÂÇ´Ç¢âä
				vertices[0].y = power / 1000f + 0.1f*(float)Math.sin(anime * (float)Math.PI / 180f);
				device.getMaterial().ambient.set(0f, 0f, 1f);
				device.drawIndexedPrimitive(jVLPMType.TRIANGLE, vertices, indices);
				device.setRenderState(jVLRS.ALPHABLENDENABLE, 0);
			}
		}
	}
	
	public void act()
	{
		anime = (anime + 30) % 360;
		super.act();
	}
}
