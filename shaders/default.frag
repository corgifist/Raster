
vec3 getPhongPoint(PointLight light) {
    vec3 normal = normalize(vertexOutput.normal);
    vec3 lightDir = normalize(light.position - vertexOutput.worldFragPos);

    vec3 diffuse = vec3(max(dot(normal, lightDir), 0.0)) * light.color;

    vec3 phong = diffuse;
    return max(phong, ambient.intensity * ambient.color);
}

void main() {
    vec3 color = vec3(1);

    color *= properties.tint;
    if (properties.texturesEnabled == 1.0) color *= texture(properties.diffuseSampler, vertexOutput.texCoords).rgb;
    color *= getPhongPoint(point);

    FragColor = vec4(color, 1.0);
}