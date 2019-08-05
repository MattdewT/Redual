varying vec3 v_color;

uniform float mix_value;

void main()
{
	float Y = 0.2126 * v_color.x + 0.7152 * v_color.y + 0.0722 * v_color.z;
	gl_FragColor = vec4(mix(vec3(Y), v_color, mix_value), 1.0);
}