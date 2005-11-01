import javavl.consts.*;
import javavl.lower.*;

public class RigidChipWeight extends RigidChip
{
	public int getType(){ return TYPE_WEIGHT; }
	public String getTypeString(){ return "Weight"; }
	public static jVLTexture texture;
	public jVLTexture getTexture(){ return texture; }
}
