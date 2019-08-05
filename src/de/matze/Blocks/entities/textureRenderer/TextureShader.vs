#version 130

attribute vec3 position;
attribute vec2 texture;

varying vec2 TexCoords;

uniform mat4 pr_matrix;
uniform mat4 ml_matrix;

void main()
{
	vec4 p = pr_matrix * ml_matrix * vec4(position.xz, -10, 1);
    gl_Position = p;
	TexCoords = texture;
}