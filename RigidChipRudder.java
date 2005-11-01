import javavl.consts.*;
import javavl.lower.*;

public class RigidChipRudder extends RigidChip
{
	public int getType(){ return TYPE_RUDDER; }
	public String getTypeString(){ return "Rudder"; }
	public static jVLTexture texture;
	public jVLTexture getTexture(){ return texture; }

	protected jVLMatrix rotate(jVLMatrix matrix)
	{
		Float angle;
		if ((angle = core.strToFloat(option.getProperty("angle"))) == null)
			return matrix;

		jVLMatrix mat = new jVLMatrix();
		mat.rotationY(-angle.floatValue() * (float)Math.PI / 180);
		return mat.multiply(matrix);
	}
	protected jVLMatrix rotateR(jVLMatrix matrix)
	{
		Float angle;
		if ((angle = core.strToFloat(option.getProperty("angle"))) == null)
			return matrix;

		jVLMatrix mat = new jVLMatrix();
		mat.rotationY( angle.floatValue() * (float)Math.PI / 180);
		return mat.multiply(matrix);
	}
}
