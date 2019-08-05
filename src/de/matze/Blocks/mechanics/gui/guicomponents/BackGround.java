package de.matze.Blocks.mechanics.gui.guicomponents;

import java.util.List;

import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.graphics.VertexArray;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.maths.Vector2f;
import de.matze.Blocks.mechanics.gui.GUIObject;

public class BackGround extends GUIComponent implements Animation_Matrix{

	private float margin = 0f;
	private VertexArray mesh;
	private int texture;
	private Matrix4f animation_matrix;

	private float alpha = 1;
	
	public BackGround(Loader loader, GUIObject p, int texture) {
		super(GUIComponentTypes.BackGround);
		List<GUIComponent> list = p.getComponentList(GUIComponentTypes.Label);
		Vector2f max = new Vector2f(-100000, -100000);
		Vector2f min = new Vector2f( 100000,  100000);
		for(GUIComponent p1 : list) {
			Vector2f current_min = ((Label) p1).getMin();
			Vector2f current_max = ((Label) p1).getMax();
			
			if(current_min.x < min.x) {
				min.x = current_min.x;
			}  
			if(current_max.x > max.x) {
				max.x = current_max.x;
			}
			if(current_min.y < min.y) {
				min.y = current_min.y;
			} 
			if(current_max.y > max.y) {
				max.y = current_max.y;
			}
			
		}
		mesh = loader.loadToVAO(new float[] {
				 max.x + margin, max.y + margin,
				 min.x - margin, max.y + margin,
				 max.x + margin, min.y - margin, 
				 min.x - margin, min.y - margin
		}, 2);
		
		this.texture = texture;
		this.animation_matrix = Matrix4f.identity();
	}

	public BackGround(Loader loader, int texture, Vector2f min, Vector2f max) {
		super(GUIComponentTypes.BackGround);
		mesh = loader.loadToVAO(new float[] {
				max.x + margin, max.y + margin,
				min.x - margin, max.y + margin,
				max.x + margin, min.y - margin,
				min.x - margin, min.y - margin
		}, 2);

		this.texture = texture;
		this.animation_matrix = Matrix4f.identity();
	}
	
	public VertexArray getMesh() {
		return mesh;
	}
	
	public int getTexture() {
		return texture;
	}

	public void setAnimationMatrix(Matrix4f animation_matrix) {
		this.animation_matrix = animation_matrix;
	}

	public Matrix4f getAnimationMatrix() {
		return this.animation_matrix;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public float getAlpha() {
		return alpha;
	}
}
