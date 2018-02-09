package net.quantium.harvester.render;

public class Transform {
	public static final Transform IDENTITY = new Transform(0, 0, false, false, (short)999);
	
	public int x = 0;
	public int y = 0;
	public boolean flipX = false;
	public boolean flipY = false;
	public short colorTint = 999;
	
	public Transform(int x, int y, boolean flipX, boolean flipY, short colorTint) {
		this.x = x;
		this.y = y;
		this.flipX = flipX;
		this.flipY = flipY;
		this.colorTint = colorTint;
	}

	public Transform(Transform proto){
		this.x = proto.x;
		this.y = proto.y;
		this.flipX = proto.flipX;
		this.flipY = proto.flipY;
		this.colorTint = proto.colorTint;
	}
	
	public Transform combine(Transform transform){
		return new Transform(this.x + transform.x,
							 this.y + transform.y,
							 this.flipX ^ transform.flipX,
							 this.flipY ^ transform.flipY,
							 Color.multiply(this.colorTint, transform.colorTint));
	}
	
	public Transform translate(int x, int y){
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Transform tint(short color){
		this.colorTint = Color.multiply(this.colorTint, color);
		return this;
	}
	
	public Transform flip(boolean x, boolean y){
		this.flipX = this.flipX ^ x;
		this.flipY = this.flipY ^ y;
		return this;
	}
	
	public Transform flip(int mask){
		flip((mask & Layer.MIRRORFLAG_HORIZONTAL) != 0, (mask & Layer.MIRRORFLAG_VERTICAL) != 0);
		return this;
	}
}
