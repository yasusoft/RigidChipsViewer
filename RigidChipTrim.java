import javavl.consts.*;
import javavl.lower.*;

public class RigidChipTrim extends RigidChip
{
	public int getType(){ return TYPE_TRIM; }
	public String getTypeString(){ return "Trim"; }
	public static jVLTexture texture;
	public jVLTexture getTexture(){ return texture; }
	protected jVLMatrix rotate(jVLMatrix matrix)
	{
		Float angle;
		if ((angle = core.strToFloat(option.getProperty("angle"))) == null)
			return matrix;

		jVLMatrix mat = new jVLMatrix();
		switch (direction)
		{
			case DIR_NORTH: mat.rotationZ(-angle.floatValue() * (float)Math.PI / 180); break;
			case DIR_SOUTH: mat.rotationZ( angle.floatValue() * (float)Math.PI / 180); break;
			case DIR_EAST:  mat.rotationX( angle.floatValue() * (float)Math.PI / 180); break;
			case DIR_WEST:  mat.rotationX(-angle.floatValue() * (float)Math.PI / 180); break;
			default: mat.identity();
		}
		return mat.multiply(matrix);
	}
	protected jVLMatrix rotateR(jVLMatrix matrix)
	{
		Float angle;
		if ((angle = core.strToFloat(option.getProperty("angle"))) == null)
			return matrix;

		jVLMatrix mat = new jVLMatrix();
		switch (direction)
		{
			case DIR_NORTH: mat.rotationZ( angle.floatValue() * (float)Math.PI / 180); break;
			case DIR_SOUTH: mat.rotationZ(-angle.floatValue() * (float)Math.PI / 180); break;
			case DIR_EAST:  mat.rotationX(-angle.floatValue() * (float)Math.PI / 180); break;
			case DIR_WEST:  mat.rotationX( angle.floatValue() * (float)Math.PI / 180); break;
			default: mat.identity();
		}
		return mat.multiply(matrix);
	}
}
