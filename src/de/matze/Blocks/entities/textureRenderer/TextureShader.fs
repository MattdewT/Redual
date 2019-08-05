varying vec2 TexCoords;

uniform sampler2D Sampler;

void main()
{
	gl_FragColor = texture(Sampler, TexCoords);
}