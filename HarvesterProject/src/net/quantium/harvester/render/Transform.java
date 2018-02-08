package net.quantium.harvester.render;

public class Transform {
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
	}

	public Transform(Transform proto){
		this.x = proto.x;
		this.y = proto.y;
		this.flipX = proto.flipX;
		this.flipY = proto.flipY;
	}
	
	public Transform combine(Transform transform){
		return new Transform(this.x + transform.x,
							 this.y + transform.y,
							 this.flipX ^ transform.flipX,
							 this.flipY ^ transform.flipY,
							 Color.multiply(this.colorTint, transform.colorTint));
	}
	
	public void translate(int x, int y){
		this.x += x;
		this.y += y;
	}
}
