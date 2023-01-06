#version 400 core

struct ObjectProperties {
    float texturesEnabled;

    vec3 tint;
    sampler2D diffuseSampler;
};

in vec4 fVertexPosition;
in vec2 fTexCoords;
in vec3 fNormal;

out vec4 FragColor;

uniform ObjectProperties properties;

void main() {
    vec3 color = vec3(1);
    color *= properties.tint;
    color *= texture(properties.diffuseSampler, fTexCoords).rgb;

    FragColor = vec4(color, 1.0);
}