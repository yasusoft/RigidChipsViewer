import javavl.consts.*;
import javavl.lower.*;

public class RigidChipCowl extends RigidChip
{
	public int getType(){ return TYPE_COWL; }
	public String getTypeString(){ return "Cowl"; }
	public static jVLTexture texture0, texture2, texture34, texture5;
	public jVLTexture getTexture()
	{
		int opt = 0;
		Float f;
		if ((f = core.strToFloat(option.getProperty("option"))) != null)
			opt = f.intValue();
		
		switch (opt)
		{
			default: return texture0;
			case 2:  return texture2;
			case 3:
			case 4:  return texture34;
			case 5:  return texture5;
		}
	}

	protected jVLVertex[][] vertices = new jVLVertex[4][];
	protected int[][] indices = new int[4][];
	protected void drawMain(jVLDevice device)
	{
		int opt = 0;
		Float f;
		if ((f = core.strToFloat(option.getProperty("option"))) != null)
			opt = f.intValue();
		
		switch (opt)
		{
			default:
				super.drawMain(device);
				break;
			case 1: // Frame
				if (vertices[0] == null || indices[0] == null)
				{	// top
					makeRect(-0.30f, -0.30f,  0.30f, -0.27f, 0.00f, 0.00f, 1.00f, 0.05f);
					vertices[0] = super.vertices;
					indices[0]  = super.indices;
				}
				device.drawIndexedPrimitive(jVLPMType.TRIANGLE, vertices[0], indices[0]);
				if (vertices[1] == null || indices[1] == null)
				{	// bottom
					makeRect(-0.30f,  0.27f,  0.30f,  0.30f, 0.00f, 0.95f, 1.00f, 1.00f);
					vertices[1] = super.vertices;
					indices[1]  = super.indices;
				}
				device.drawIndexedPrimitive(jVLPMType.TRIANGLE, vertices[1], indices[1]);
				if (vertices[2] == null || indices[2] == null)
				{	// left
					makeRect(-0.30f, -0.27f, -0.27f,  0.27f, 0.00f, 0.05f, 0.05f, 0.95f);
					vertices[2] = super.vertices;
					indices[2]  = super.indices;
				}
				device.drawIndexedPrimitive(jVLPMType.TRIANGLE, vertices[2], indices[2]);
				if (vertices[3] == null || indices[3] == null)
				{	// right
					makeRect( 0.27f, -0.27f,  0.30f,  0.27f, 0.95f, 0.05f, 1.00f, 0.95f);
					vertices[3] = super.vertices;
					indices[3]  = super.indices;
				}
				device.drawIndexedPrimitive(jVLPMType.TRIANGLE, vertices[3], indices[3]);
				break;
			case 2: // Circle
				if (texture2 != null)
				{
					device.setTexture(0, texture2);
					device.setTextureStageState(0, jVLTSS.COLOROP, jVLTOP.MODULATE);
				}
				if (super.vertices == null || super.indices == null)
					makeCircle();
				device.drawIndexedPrimitive(jVLPMType.TRIANGLE, super.vertices, super.indices);
				break;
			case 3: // âEè„Ç™ÇËéOäpå`
				if (texture34 != null)
				{
					device.setTexture(0, texture34);
					device.setTextureStageState(0, jVLTSS.COLOROP, jVLTOP.MODULATE);
				}
				if (super.vertices == null || super.indices == null)
					makeTriangle(new float[]{-0.3f, 0.3f, 0.3f}, new float[]{0.3f, -0.3f, 0.3f}, new float[]{0.0f, 1.0f, 1.0f}, new float[]{1.0f, 0.0f, 1.0f});
				device.drawIndexedPrimitive(jVLPMType.TRIANGLE, super.vertices, super.indices);
				break;
			case 4: // ç∂è„Ç™ÇËéOäpå`
				if (texture34 != null)
				{
					device.setTexture(0, texture34);
					device.setTextureStageState(0, jVLTSS.COLOROP, jVLTOP.MODULATE);
				}
				if (super.vertices == null || super.indices == null)
					makeTriangle(new float[]{-0.3f, 0.3f, -0.3f}, new float[]{-0.3f, 0.3f, 0.3f}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 0.0f, 0.0f});
				device.drawIndexedPrimitive(jVLPMType.TRIANGLE, super.vertices, super.indices);
				break;
			case 5: // è„ä€â∫äp
				if (texture5 != null)
				{
					device.setTexture(0, texture5);
					device.setTextureStageState(0, jVLTSS.COLOROP, jVLTOP.MODULATE);
				}
				if (vertices[0] == null || indices[0] == null)
				{
					makeCircle((float)Math.PI, 6);
					vertices[0] = super.vertices;
					indices[0]  = super.indices;
				}
				device.drawIndexedPrimitive(jVLPMType.TRIANGLE, vertices[0], indices[0]);
				if (vertices[1] == null || indices[1] == null)
				{
					makeRect(-0.3f, 0.0f, 0.3f, 0.3f, 0.0f, 0.5f, 1.0f, 1.0f);
					vertices[1] = super.vertices;
					indices[1]  = super.indices;
				}
				device.drawIndexedPrimitive(jVLPMType.TRIANGLE, vertices[1], indices[1]);
				break;
		}
	}
}
