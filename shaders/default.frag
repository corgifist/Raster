#version 400 core

struct ObjectProperties {
    float texturesEnabled;

    vec3 tint;
    sampler2D diffuseSampler;
};

out vec4 FragColor;

in VertexOutput {
    vec4 vertexPosition;
    vec2 texCoords;
    vec3 normal;
} vertexOutput;

uniform ObjectProperties properties;

void main() {
    vec3 color = vec3(1);
    color *= properties.tint;
    color *= texture(properties.diffuseSampler, vertexOutput.texCoords).rgb;

    FragColor = vec4(color, 1.0);
    FragColor = vec4(vertexOutput.normal, 1.0);
}