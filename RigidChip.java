import java.util.*;
import javavl.consts.*;
import javavl.lower.*;

public class RigidChip
{
	public static final String Copyright = "Yasu software - http://www.yasu.nu/";
	public static final String Message = "DO NOT DISASSEMBLE !!";

	private static final String TEXTURE_EXT = ".jpg";

	public static final int DIR_CORE  = 0;
	public static final int DIR_NORTH = 1;
	public static final int DIR_EAST  = 2;
	public static final int DIR_SOUTH = 3;
	public static final int DIR_WEST  = 4;

	public static final int TYPE_NONE    = 0;
	public static final int TYPE_CORE    = 1;
	public static final int TYPE_CHIP    = 2;
	public static final int TYPE_FRAME   = 3;
	public static final int TYPE_WEIGHT  = 4;
	public static final int TYPE_WHEEL   = 5;
	public static final int TYPE_RLW     = 6;
	public static final int TYPE_JET     = 7;
	public static final int TYPE_RUDDER  = 8;
	public static final int TYPE_RUDDERF = 9;
	public static final int TYPE_TRIM    = 10;
	public static final int TYPE_TRIMF   = 11;
	public static final int TYPE_ARM     = 12;
	public static final int TYPE_COWL    = 13;
	public static RigidChip create(int type)
	{
		switch (type)
		{
			case TYPE_CORE:    return new RigidChipCore();
			case TYPE_CHIP:    return new RigidChipChip();
			case TYPE_FRAME:   return new RigidChipFrame();
			case TYPE_WEIGHT:  return new RigidChipWeight();
			case TYPE_WHEEL:   return new RigidChipWheel();
			case TYPE_RLW:     return new RigidChipRLW();
			case TYPE_JET:     return new RigidChipJet();
			case TYPE_RUDDER:  return new RigidChipRudder();
			case TYPE_RUDDERF: return new RigidChipRudderF();
			case TYPE_TRIM:    return new RigidChipTrim();
			case TYPE_TRIMF:   return new RigidChipTrimF();
			case TYPE_ARM:     return new RigidChipArm();
			case TYPE_COWL:    return new RigidChipCowl();
		}
		return null;
	}
	public static RigidChip create(String type)
	{
		type = type.toLowerCase();
		if ("core".equals(type))
			return new RigidChipCore();
		if ("chip".equals(type))
			return new RigidChipChip();
		if ("frame".equals(type))
			return new RigidChipFrame();
		if ("weight".equals(type))
			return new RigidChipWeight();
		if ("wheel".equals(type))
			return new RigidChipWheel();
		if ("rlw".equals(type))
			return new RigidChipRLW();
		if ("jet".equals(type))
			return new RigidChipJet();
		if ("rudder".equals(type))
			return new RigidChipRudder();
		if ("rudderf".equals(type))
			return new RigidChipRudderF();
		if ("trim".equals(type))
			return new RigidChipTrim();
		if ("trimf".equals(type))
			return new RigidChipTrimF();
		if ("arm".equals(type))
			return new RigidChipArm();
		if ("cowl".equals(type))
			return new RigidChipCowl();
		return null;
	}
	public static void loadTextures(java.applet.Applet applet)
	{ synchronized (RCView.SYNC) {
		jVLImage imgTex;
		if (RigidChipCore.texture == null && (imgTex = new jVLImage()).loadImage("res/core" + TEXTURE_EXT, applet))
			(RigidChipCore.texture = new jVLTexture()).setTexmap(0, imgTex);
		if (RigidChipChip.texture == null && (imgTex = new jVLImage()).loadImage("res/chip" + TEXTURE_EXT, applet))
			(RigidChipChip.texture = new jVLTexture()).setTexmap(0, imgTex);
		if (RigidChipWeight.texture == null && (imgTex = new jVLImage()).loadImage("res/weight" + TEXTURE_EXT, applet))
			(RigidChipWeight.texture = new jVLTexture()).setTexmap(0, imgTex);
		if (RigidChipWheel.texture == null && (imgTex = new jVLImage()).loadImage("res/wheel" + TEXTURE_EXT, applet))
			(RigidChipWheel.texture = new jVLTexture()).setTexmap(0, imgTex);
		if (RigidChipRLW.texture == null && (imgTex = new jVLImage()).loadImage("res/rlw" + TEXTURE_EXT, applet))
			(RigidChipRLW.texture = new jVLTexture()).setTexmap(0, imgTex);
		if (RigidChipJet.texture == null && (imgTex = new jVLImage()).loadImage("res/jet" + TEXTURE_EXT, applet))
			(RigidChipJet.texture = new jVLTexture()).setTexmap(0, imgTex);
		if (RigidChipJet.textureBall == null && (imgTex = new jVLImage()).loadImage("res/ball" + TEXTURE_EXT, applet))
			(RigidChipJet.textureBall = new jVLTexture()).setTexmap(0, imgTex);
		if (RigidChipRudder.texture == null && (imgTex = new jVLImage()).loadImage("res/rudder" + TEXTURE_EXT, applet))
			(RigidChipRudder.texture = new jVLTexture()).setTexmap(0, imgTex);
		if (RigidChipTrim.texture == null && (imgTex = new jVLImage()).loadImage("res/trim" + TEXTURE_EXT, applet))
			(RigidChipTrim.texture = new jVLTexture()).setTexmap(0, imgTex);
		if (RigidChipArm.texture == null && (imgTex = new jVLImage()).loadImage("res/arm" + TEXTURE_EXT, applet))
			(RigidChipArm.texture = new jVLTexture()).setTexmap(0, imgTex);
		if (RigidChipCowl.texture0 == null && (imgTex = new jVLImage()).loadImage("res/cowl0" + TEXTURE_EXT, applet))
			(RigidChipCowl.texture0 = new jVLTexture()).setTexmap(0, imgTex);
		if (RigidChipCowl.texture2 == null && (imgTex = new jVLImage()).loadImage("res/cowl2" + TEXTURE_EXT, applet))
			(RigidChipCowl.texture2 = new jVLTexture()).setTexmap(0, imgTex);
		if (RigidChipCowl.texture34 == null && (imgTex = new jVLImage()).loadImage("res/cowl34" + TEXTURE_EXT, applet))
			(RigidChipCowl.texture34 = new jVLTexture()).setTexmap(0, imgTex);
		if (RigidChipCowl.texture5 == null && (imgTex = new jVLImage()).loadImage("res/cowl5" + TEXTURE_EXT, applet))
			(RigidChipCowl.texture5 = new jVLTexture()).setTexmap(0, imgTex);
	} }

	public int getType(){ return RigidChip.TYPE_NONE; }
	public String getTypeString(){ return null; }
	public jVLTexture getTexture(){ return null; }

	public RigidChipCore core;
	public RigidChip parent;
	
	public int direction;

	public Properties option;
	public void setOptions(String str)
	{
		RigidChip.setOptions(option, str);
	}
	public static void setOptions(Properties option, String str)
	{
		int len = str.length();
		int i, p = 0;
		while (p < len)
		{
			i = str.indexOf(",", p);
			if (i == -1) i = len;
			String s = str.substring(p, i);
			p = i + 1;
			
			i = s.indexOf("=");
			if (i != -1)
			{
				String name = s.substring(0, i).trim().toLowerCase();
				String value = s.substring(i+1).trim();
				if (name.length() != 0 && value.length() != 0)
					option.put(name, value);
			}
		}
	}

	private Vector sub;
	public int numSub()
	{
		return sub.size();
	}
	public void addSub(RigidChip chip)
	{
		chip.core = core;
		chip.parent = this;
		sub.addElement(chip);
	}
	public RigidChip getSub(int i)
	{
		return (RigidChip)sub.elementAt(i);
	}
	public void delSub(int i)
	{
		sub.removeElementAt(i);
	}
	public void delSub(RigidChip chip)
	{
		for (int i = sub.size()-1; i >= 0; i --)
			if (sub.elementAt(i) == chip)
			{
				sub.removeElementAt(i);
				break;
			}
	}
	
	protected RigidChip()
	{
		option = new Properties();
		sub = new Vector();
	}

	protected jVLMatrix translate(jVLMatrix matrix)
	{
		jVLMatrix mat = new jVLMatrix();
		switch (direction)
		{
			case DIR_NORTH: mat.translation( 0.0f, 0.0f, -0.3f); break;
			case DIR_SOUTH: mat.translation( 0.0f, 0.0f,  0.3f); break;
			case DIR_EAST:  mat.translation( 0.3f, 0.0f,  0.0f); break;
			case DIR_WEST:  mat.translation(-0.3f, 0.0f,  0.0f); break;
			default: mat.identity();
		}
		return mat.multiply(matrix);
	}
	protected jVLMatrix rotate(jVLMatrix matrix)
	{
		Float angle;
		if ((angle = core.strToFloat(option.getProperty("angle"))) == null)
			return matrix;

		jVLMatrix mat = new jVLMatrix();
		switch (direction)
		{
			case DIR_NORTH: mat.rotationX(-angle.floatValue() * (float)Math.PI / 180); break;
			case DIR_SOUTH: mat.rotationX( angle.floatValue() * (float)Math.PI / 180); break;
			case DIR_EAST:  mat.rotationZ(-angle.floatValue() * (float)Math.PI / 180); break;
			case DIR_WEST:  mat.rotationZ( angle.floatValue() * (float)Math.PI / 180); break;
			default: mat.identity();
		}
		return mat.multiply(matrix);
	}
	protected jVLMatrix rotate2(jVLMatrix matrix)
	{
		return matrix;
	}
	protected jVLMatrix translateR(jVLMatrix matrix)
	{
		jVLMatrix mat = new jVLMatrix();
		switch (direction)
		{
			case DIR_NORTH: mat.translation( 0.0f, 0.0f,  0.3f); break;
			case DIR_SOUTH: mat.translation( 0.0f, 0.0f, -0.3f); break;
			case DIR_EAST:  mat.translation(-0.3f, 0.0f,  0.0f); break;
			case DIR_WEST:  mat.translation( 0.3f, 0.0f,  0.0f); break;
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
			case DIR_NORTH: mat.rotationX( angle.floatValue() * (float)Math.PI / 180); break;
			case DIR_SOUTH: mat.rotationX(-angle.floatValue() * (float)Math.PI / 180); break;
			case DIR_EAST:  mat.rotationZ( angle.floatValue() * (float)Math.PI / 180); break;
			case DIR_WEST:  mat.rotationZ(-angle.floatValue() * (float)Math.PI / 180); break;
			default: mat.identity();
		}
		return mat.multiply(matrix);
	}
	protected jVLMatrix rotateR2(jVLMatrix matrix)
	{
		return matrix;
	}

	protected jVLVertex[] vertices = null;
	protected int[] indices = null;
	protected void makeTriangle(float[] x, float[] z, float[] u, float[] v)
	{
		vertices = new jVLVertex[3];

		vertices[0] = new jVLVertex();
		vertices[0].x = x[0];
		vertices[0].y = 0f;
		vertices[0].z = z[0];
		vertices[0].nx =  0.0f;
		vertices[0].ny = -1.0f;
		vertices[0].nz =  0.0f;
		vertices[0].u = new float[]{u[0]};
		vertices[0].v = new float[]{v[0]};

		vertices[1] = new jVLVertex();
		vertices[1].x = x[1];
		vertices[1].y = 0f;
		vertices[1].z = z[1];
		vertices[1].nx =  0.0f;
		vertices[1].ny = -1.0f;
		vertices[1].nz =  0.0f;
		vertices[1].u = new float[]{u[1]};
		vertices[1].v = new float[]{v[1]};

		vertices[2] = new jVLVertex();
		vertices[2].x = x[2];
		vertices[2].y = 0f;
		vertices[2].z = z[2];
		vertices[2].nx =  0.0f;
		vertices[2].ny = -1.0f;
		vertices[2].nz =  0.0f;
		vertices[2].u = new float[]{u[2]};
		vertices[2].v = new float[]{v[2]};

		indices = new int[3];
		indices[0] = 0;
		indices[1] = 1;
		indices[2] = 2;
	}
	protected void makeRect(float x1, float z1, float x2, float z2, float u1, float v1, float u2, float v2)
	{
		makeRect(new float[]{x1, x2, x2, x1}, new float[]{z1, z1, z2, z2}, new float[]{u1, u2, u2, u1}, new float[]{v1, v1, v2, v2});
	}
	protected void makeRect(float[] x, float[] z, float[] u, float[] v)
	{
		vertices = new jVLVertex[4];

		vertices[0] = new jVLVertex();
		vertices[0].x = x[0];
		vertices[0].y = 0f;
		vertices[0].z = z[0];
		vertices[0].nx =  0.0f;
		vertices[0].ny = -1.0f;
		vertices[0].nz =  0.0f;
		vertices[0].u = new float[]{u[0]};
		vertices[0].v = new float[]{v[0]};

		vertices[1] = new jVLVertex();
		vertices[1].x = x[1];
		vertices[1].y = 0f;
		vertices[1].z = z[1];
		vertices[1].nx =  0.0f;
		vertices[1].ny = -1.0f;
		vertices[1].nz =  0.0f;
		vertices[1].u = new float[]{u[1]};
		vertices[1].v = new float[]{v[1]};

		vertices[2] = new jVLVertex();
		vertices[2].x = x[2];
		vertices[2].y = 0f;
		vertices[2].z = z[2];
		vertices[2].nx =  0.0f;
		vertices[2].ny = -1.0f;
		vertices[2].nz =  0.0f;
		vertices[2].u = new float[]{u[2]};
		vertices[2].v = new float[]{v[2]};

		vertices[3] = new jVLVertex();
		vertices[3].x = x[3];
		vertices[3].y = 0f;
		vertices[3].z = z[3];
		vertices[3].nx =  0.0f;
		vertices[3].ny = -1.0f;
		vertices[3].nz =  0.0f;
		vertices[3].u = new float[]{u[3]};
		vertices[3].v = new float[]{v[3]};

		indices = new int[6];
		indices[0] = 0;
		indices[1] = 1;
		indices[2] = 2;
		indices[3] = 2;
		indices[4] = 3;
		indices[5] = 0;
	}
	protected void makeCircle()
	{
		makeCircle(2.0f*(float)Math.PI, 12);
	}
	protected void makeCircle(float angle, int N)
	{
		vertices = new jVLVertex[N+1+1];
		vertices[0] = new jVLVertex();
		vertices[0].x = 0f;
		vertices[0].y = 0f;
		vertices[0].z = 0f;
		vertices[0].nx =  0.0f;
		vertices[0].ny = -1.0f;
		vertices[0].nz =  0.0f;
		vertices[0].u = new float[]{0.5f};
		vertices[0].v = new float[]{0.5f};

		indices = new int[(N+1)*3];

		float a = 0;
		for (int i = 1; i <= N+1; i ++, a += angle / N)
		{
			vertices[i] = new jVLVertex();
			vertices[i].x =  0.3f * (float)Math.cos(a);
			vertices[i].y =  0.0f;
			vertices[i].z = -0.3f * (float)Math.sin(a);
			vertices[i].nx =  0.0f;
			vertices[i].ny = -1.0f;
			vertices[i].nz =  0.0f;
			vertices[i].u = new float[]{0.5f + 0.5f * (float)Math.cos(a)};
			vertices[i].v = new float[]{0.5f - 0.5f * (float)Math.sin(a)};
		}
		for (int i = 0, j = 0; i <= N; i ++)
		{
			indices[j++] = 0;
			indices[j++] = i + 1;
			indices[j++] = i;
		}
	}
	protected void makeSphere(float radius, int numSlices, int numStacks)
	{
		if (radius <= 0.0f || numSlices < 3 || numStacks < 2)
			return;

		float t  = 0.0f;
		float p  = (float)Math.PI * 0.5f;
		float dt = (float)Math.PI * 2.0f / numSlices;
		float dp = (float)Math.PI / numStacks;
		
		//	頂点を生成する
		vertices = new jVLVertex[numSlices * (numStacks + 1)];
		
		for(int i = 0, k = 0; i <= numStacks; i++){
			t = 0;
			for(int j = 0; j < numSlices; j++, k++){
				vertices[k] = new jVLVertex();
				vertices[k].x  = radius * (float)Math.sin(t) * (float)Math.cos(p);
				vertices[k].y  = radius * (float)Math.sin(p);
				vertices[k].z  = radius * (float)Math.cos(t) * (float)Math.cos(p);
				vertices[k].nx = vertices[k].x / radius;
				vertices[k].ny = vertices[k].y / radius;
				vertices[k].nz = vertices[k].z / radius;
				vertices[k].u = new float[]{t / (float)(2 * Math.PI)};
				vertices[k].v = new float[]{0.5f - p / (float)Math.PI};
				t += dt;
			}
			p -= dp;
		}

		//	面を生成する
		indices = new int[numSlices * numStacks * 2 * 3];

		for(int i = 0, k = 0; i < numStacks; i++){
			for(int j = 0; j < numSlices; j++){
				int j1 = j;
				int j2 = j + 1;
				if(j + 1 == numSlices)j2 = 0;

				indices[k++] =       i * numSlices + j1;
				indices[k++] = (i + 1) * numSlices + j1;
				indices[k++] = (i + 1) * numSlices + j2;
				indices[k++] =       i * numSlices + j1;
				indices[k++] = (i + 1) * numSlices + j2;
				indices[k++] =       i * numSlices + j2;
			}
		}
	}
	protected void drawMain(jVLDevice device)
	{
		if (vertices == null || indices == null)
			makeRect(-0.3f, -0.3f, 0.3f, 0.3f, 0f, 0f, 1f, 1f);
		device.drawIndexedPrimitive(jVLPMType.TRIANGLE, vertices, indices);
	}
	protected void drawTransMain(jVLDevice device)
	{
	}

	public void draw(jVLDevice device, RigidChip caller)
	{
		// 行列をバックアップ
		jVLMatrix mat_back = device.getTransform(jVLTransformType.WORLD, 0);

		jVLMatrix matrix = new jVLMatrix();
		matrix.set(mat_back);

		if (caller == parent)
		{
			matrix = translate(matrix);
			matrix = rotate(matrix);
			matrix = translate(matrix);
			matrix = rotate2(matrix);
		}

		// 色
		Float col;
		if ((col = core.strToFloat(option.getProperty("color"))) != null)
		{
			int c = col.intValue();
			float r = ((c >> 16) & 0xFF) / 255.0f;
			float g = ((c >>  8) & 0xFF) / 255.0f;
			float b = ((c >>  0) & 0xFF) / 255.0f;
			device.getMaterial().ambient.set(r*0.2f+0.1f, g*0.2f+0.1f, b*0.2f+0.1f);
			device.getMaterial().diffuse.set(r*0.7f, g*0.7f, b*0.7f);
		}
		else
		{
			device.getMaterial().ambient.set(0.3f, 0.3f, 0.3f);
			device.getMaterial().diffuse.set(0.7f, 0.7f, 0.7f);
		}

		// 自転
		jVLMatrix mat = new jVLMatrix();
		switch (direction)
		{
			default: mat.identity(); break;
			case DIR_EAST:  mat.rotationY(-(float)Math.PI / 2); break;
			case DIR_WEST:  mat.rotationY( (float)Math.PI / 2); break;
			case DIR_SOUTH: mat.rotationY( (float)Math.PI    ); break;
		}
		mat = mat.multiply(matrix);
		device.setTransform(jVLTransformType.WORLD, mat, 0);

		// テクスチャ
		jVLTexture tex = getTexture();
		if (tex != null)
		{
			device.setTexture(0, tex);
			device.setTextureStageState(0, jVLTSS.COLOROP, jVLTOP.MODULATE);
		}
		else
			device.setTextureStageState(0, jVLTSS.COLOROP, jVLTOP.DISABLE);

		// 描画
		drawMain(device);

		device.setTextureStageState(0, jVLTSS.COLOROP, jVLTOP.DISABLE);

		// 自転を元に戻す
		device.setTransform(jVLTransformType.WORLD, matrix, 0);

		// サブチップの描画
		for (int i = 0; i < sub.size(); i ++)
		{
			RigidChip c = (RigidChip)sub.elementAt(i);
			if (c != caller)
				c.draw(device, this);
		}

		if (caller != parent && parent != null)
		{
			matrix = rotateR2(matrix);
			matrix = translateR(matrix);
			matrix = rotateR(matrix);
			matrix = translateR(matrix);

			parent.draw(device, this);
		}

		// 行列を戻す
		device.setTransform(jVLTransformType.WORLD, mat_back, 0);
	}
	public void drawTrans(jVLDevice device, RigidChip caller)
	{
		// 行列をバックアップ
		jVLMatrix mat_back = device.getTransform(jVLTransformType.WORLD, 0);

		jVLMatrix matrix = new jVLMatrix();
		matrix.set(mat_back);

		if (caller == parent)
		{
			matrix = translate(matrix);
			matrix = rotate(matrix);
			matrix = translate(matrix);
			matrix = rotate2(matrix);
		}
		
		// 自転
		jVLMatrix mat = new jVLMatrix();
		switch (direction)
		{
			default: mat.identity(); break;
			case DIR_EAST:  mat.rotationY(-(float)Math.PI / 2); break;
			case DIR_WEST:  mat.rotationY( (float)Math.PI / 2); break;
			case DIR_SOUTH: mat.rotationY( (float)Math.PI    ); break;
		}
		mat = mat.multiply(matrix);
		device.setTransform(jVLTransformType.WORLD, mat, 0);

		// 描画
		drawTransMain(device);

		// 自転を元に戻す
		device.setTransform(jVLTransformType.WORLD, matrix, 0);

		// サブチップの描画
		for (int i = 0; i < sub.size(); i ++)
		{
			RigidChip c = (RigidChip)sub.elementAt(i);
			if (c != caller)
				c.drawTrans(device, this);
		}

		if (caller != parent && parent != null)
		{
			matrix = rotateR2(matrix);
			matrix = translateR(matrix);
			matrix = rotateR(matrix);
			matrix = translateR(matrix);

			parent.drawTrans(device, this);
		}

		// 行列を戻す
		device.setTransform(jVLTransformType.WORLD, mat_back, 0);
	}
	
	public void act()
	{
		for (int i = 0; i < sub.size(); i ++)
			((RigidChip)sub.elementAt(i)).act();
	}
}
