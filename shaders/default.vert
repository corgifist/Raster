#version 400 core

layout(location = 0) in vec3 vertexPosition;
layout(location = 1) in vec2 texCoords;
layout(location = 2) in vec3 normal;

struct Transformations {
    mat4 projection;
    mat4 view;
    mat4 transformation;
};

uniform Transformations transformations;

out VertexOutput {
    vec4 vertexPosition;
    vec2 texCoords;
    vec3 normal;
    vec3 worldFragPos;
} vertexOutput;

void main() {
    gl_Position = transformations.projection * transformations.view * transformations.transformation * vec4(vertexPosition, 1.0);

    vertexOutput.vertexPosition = vec4(vertexPosition, 1.0);
    vertexOutput.texCoords = texCoords;
    vertexOutput.normal = mat3(transpose(inverse(transformations.transformation))) * normal;
    vertexOutput.worldFragPos = mat3(transformations.transformation) * vertexPosition;
}