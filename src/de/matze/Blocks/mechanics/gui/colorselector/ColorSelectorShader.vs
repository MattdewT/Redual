#version 130

attribute vec2 position;
attribute vec3 color;

varying vec3 v_color;

uniform mat4 pr_matrix;
uniform vec3 custom_color;
uniform mat4 ml_matrix;
uniform mat4 anim_matrix;

void main()
{
    if (color.x == -1) {
        v_color = custom_color;
    } else {
        v_color = color;
    }

    gl_Position =  pr_matrix * anim_matrix * ml_matrix * vec4(position, 1.0, 1);
}