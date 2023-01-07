#version 400 core

struct ObjectProperties {
    float texturesEnabled;

    vec3 tint;
    sampler2D diffuseSampler;
};

struct Light {
    vec3 position;
    vec3 color;
};

in VertexOutput {
    vec4 vertexPosition;
    vec2 texCoords;
    vec3 normal;
    vec3 worldFragPos;
} vertexOutput;


out vec4 FragColor;

uniform Light light;
uniform ObjectProperties properties;

vec3 getDiffuse(vec3 normal) {
    vec3 diffuse = vec3(0);

    vec3 lightDir = normalize(light.position - vertexOutput.worldFragPos);

    return max(dot(normal, lightDir), 0.0) * light.color;
}

void main() {
    vec3 normal = normalize(vertexOutput.normal);
    vec3 color = vec3(1);

    color *= properties.tint;
    color *= texture(properties.diffuseSampler, vertexOutput.texCoords).rgb;
    color *= getDiffuse(normal);

    FragColor = vec4(color, 1.0);
}