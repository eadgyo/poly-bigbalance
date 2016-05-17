#version 330 core

layout(location = 0) in vec2 vertexPosition_screenspace;
layout(location = 1) in vec2 vertexUV;

out vec2 UV;

uniform mat4 MVP;

void main()
{
	//vec2 vertexPosition_homoneneousspace = vertexPosition_screenspace - vec2(300, 400);
	//vertexPosition_homoneneousspace /= vec2(300, 400);
	//gl_Position =  vec4(vertexPosition_homoneneousspace, 0, 1);
	gl_Position =  MVP * vec4(vertexPosition_screenspace, 0, 1);

	UV = vertexUV;
}