#version 130

attribute vec2 position;

varying vec2 TexCoords;

uniform mat4 pr_matrix;
uniform mat4 anim_matrix;

void main()
{
	vec4 p = pr_matrix * anim_matrix * vec4(position, 0, 1);
    gl_Position = p;
    TexCoords = p.xy * 0.5 + 0.5;
}