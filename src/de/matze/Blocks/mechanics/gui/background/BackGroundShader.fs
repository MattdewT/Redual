varying vec2 TexCoords;

uniform sampler2D Sampler;
uniform float alpha;

void main()
{
	gl_FragColor = vec4(0.1, 0.1, 0.1, clamp(alpha, 0.5, 0.8));
}