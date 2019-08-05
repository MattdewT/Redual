#version 130

attribute vec2 position;

varying vec3 color;

uniform mat4 pr_matrix;
uniform mat4 ml_matrix;
uniform vec3 u_color;
uniform mat4 anim_matrix;

void main()
{
    gl_Position = pr_matrix * anim_matrix * ml_matrix * vec4(position, 0, 1);
    color = u_color;
}