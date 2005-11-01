import javavl.consts.*;
import javavl.lower.*;

public class RigidChipRLW extends RigidChipWheel
{
	public int getType(){ return TYPE_RLW; }
	public String getTypeString(){ return "RLW"; }
	public static jVLTexture texture;
	public jVLTexture getTexture(){ return texture; }
}
