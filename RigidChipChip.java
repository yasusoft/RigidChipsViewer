import javavl.consts.*;
import javavl.lower.*;

public class RigidChipChip extends RigidChip
{
	public int getType(){ return TYPE_CHIP; }
	public String getTypeString(){ return "Chip"; }
	public static jVLTexture texture;
	public jVLTexture getTexture(){ return texture; }
}
