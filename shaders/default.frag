#version 400 core

in vec4 fVertexPosition;
in vec2 fTexCoords;

out vec4 FragColor;

struct ObjectProperties {
    vec3 tint;
    sampler2D diffuseSampler;
};

uniform ObjectProperties properties;

void main() {
    FragColor = texture(properties.diffuseSampler, fTexCoords);
}