import javavl.consts.*;
import javavl.lower.*;

public class RigidChipWheel extends RigidChip
{
	public int getType(){ return TYPE_WHEEL; }
	public String getTypeString(){ return "Wheel"; }
	public static jVLTexture texture;
	public jVLTexture getTexture(){ return texture; }

	private int angle;

	protected jVLVertex[] vertices2;
	protected int[] indices2;

	protected jVLMatrix rotate2(jVLMatrix matrix)
	{
		jVLMatrix mat = new jVLMatrix();
		mat.rotationY(angle * (float)Math.PI / 180);
		return mat.multiply(matrix);
	}
	protected jVLMatrix rotateR2(jVLMatrix matrix)
	{
		jVLMatrix mat = new jVLMatrix();
		mat.rotationY(-angle * (float)Math.PI / 180);
		return mat.multiply(matrix);
	}

	protected void drawMain(jVLDevice device)
	{
		if (vertices == null || indices == null)
			makeCircle();
		device.drawIndexedPrimitive(jVLPMType.TRIANGLE, vertices, indices);

		Float f;
		int opt = 0;
		if ((f = core.strToFloat(option.getProperty("option"))) != null)
			opt = f.intValue();
		int effect = 0;
		if ((f = core.strToFloat(option.getProperty("effect"))) != null)
			effect = f.intValue();
		if (opt == 1 || opt == 2 || effect > 1)
		{ // Žü‚è‚Ì—Ö‚ð•`‰æ
			float r = 0.30f;
			switch (opt)
			{
				case 1: r = 0.45f; break;
				case 2: r = 0.60f; break;
			}

			float w = 0.05f;
			switch (effect)
			{
				case 2: w = 0.10f; break;
				case 3: w = 0.15f; break;
				case 4: w = 0.20f; break;
				case 5: w = 0.25f; break;
				case 6: w = 0.30f; break;
				case 7: w = 0.35f; break;
				case 8: w = 0.40f; break;
				case 9: w = 0.45f; break;
				case 10: w = 0.5f; break;
			}

			if (vertices2 == null || indices2 == null)
			{
				final int N = 12;
				vertices2 = new jVLVertex[N*2];
				indices2 = new int[N*2*3];
				double a = 0;
				for (int i = 0, j = 0, k = 0; i < N; i ++, a += 2*Math.PI/N)
				{
					indices2[k++] =  j;
					indices2[k++] =  j+1;
					indices2[k++] = (j+2)%(N*2);
					indices2[k++] =  j+1;
					indices2[k++] = (j+3)%(N*2);
					indices2[k++] = (j+2)%(N*2);
					vertices2[j] = new jVLVertex();
					vertices2[j].x =  r * (float)Math.cos(a);
					vertices2[j].y =  w;
					vertices2[j].z = -r * (float)Math.sin(a);
					vertices2[j].nx =  (float)Math.cos(a);
					vertices2[j].ny =  0f;
					vertices2[j].nz = -(float)Math.sin(a);
					j++;
					vertices2[j] = new jVLVertex();
					vertices2[j].x =  r * (float)Math.cos(a);
					vertices2[j].y = -w;
					vertices2[j].z = -r * (float)Math.sin(a);
					vertices2[j].nx =  (float)Math.cos(a);
					vertices2[j].ny =  0f;
					vertices2[j].nz = -(float)Math.sin(a);
					j++;
				}
				/*
				glMaterialColor3f(0.5, 0.5, 0.5);
				glNormal3f(0, 0, 1);
				 \Žš–_c
				glVertex3f(-0.05, -r, 0);
				glVertex3f(-0.05,  r, 0);
				glVertex3f( 0.05,  r, 0);
				glVertex3f( 0.05, -r, 0);
				 \Žš–_‰¡
				glVertex3f(-r, -0.05, 0);
				glVertex3f(-r,  0.05, 0);
				glVertex3f( r,  0.05, 0);
				glVertex3f( r, -0.05, 0);
				*/
			}
			device.getMaterial().ambient.set(0.1f, 0.1f, 0.1f);
			device.getMaterial().diffuse.set(0.2f, 0.2f, 0.2f);
			device.drawIndexedPrimitive(jVLPMType.TRIANGLE, vertices2, indices2);
		}
	}
	
	public void act()
	{
		Float f;
		if ((f = core.strToFloat(option.getProperty("power"))) != null)
		{
			int pow = f.intValue();
			if ((f = core.strToFloat(option.getProperty("brake"))) != null)
			{
				int brake = f.intValue() / 10 + 1;
				if (pow > 0)
					pow = pow / brake + 50;
				else if (pow < 0)
					pow = pow / brake - 50;
			}
			if (pow >  1000) pow =  1000;
			if (pow < -1000) pow = -1000;
			angle = (int)(angle - pow / 50) % 360;
		}
		super.act();
	}
}
