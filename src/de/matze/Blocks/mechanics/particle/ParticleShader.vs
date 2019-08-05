in vec2 position;
in vec4 a;
in vec4 b;
in vec4 c;
in vec4 d;

uniform mat4 pr_matrix;

void main()
{
	gl_Position = pr_matrix * mat4(a,b,c,d) * vec4(position, -1.0, 1.0);
}

