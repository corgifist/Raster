#version 400 core

layout(location = 0) in vec3 vertexPosition;
layout(location = 1) in vec2 texCoords;

struct Transformations {
    mat4 projection;
    mat4 view;
    mat4 transformation;
};

uniform Transformations transformations;

out vec4 fVertexPosition;
out vec2 fTexCoords;

void main() {
    gl_Position = transformations.projection * transformations.view * transformations.transformation * vec4(vertexPosition, 1.0);
    fVertexPosition = transformations.view * transformations.transformation * vec4(vertexPosition, 1.0);
    fTexCoords = texCoords;
}