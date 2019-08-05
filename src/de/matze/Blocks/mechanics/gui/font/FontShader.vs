#version 130

attribute vec2 position;
attribute vec2 textures;

varying vec2 OutCoord;

uniform mat4 pr_matrix;
uniform mat4 ml_matrix;
uniform mat4 anim_matrix;

void main()
{
	gl_Position = pr_matrix * anim_matrix * ml_matrix * vec4(position, 0.1, 1.0);
	OutCoord = textures;
}

