import javavl.consts.*;
import javavl.lower.*;

public class RigidChipFrame extends RigidChipChip
{
	public int getType(){ return TYPE_FRAME; }
	public String getTypeString(){ return "Frame"; }

	protected jVLVertex[][] vertices = new jVLVertex[4][];
	protected int[][] indices = new int[4][];
	protected void drawMain(jVLDevice device)
	{
		int opt = 0;
		Float f;
		if ((f = core.strToFloat(option.getProperty("option"))) != null)
			opt = f.intValue();
		if (opt == 1) return;

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
	}
}
