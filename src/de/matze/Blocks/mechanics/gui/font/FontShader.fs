varying vec2 OutCoord;

uniform sampler2D Sampler;

void main()
{
	gl_FragColor = texture(Sampler, OutCoord);
}
