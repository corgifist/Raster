
vec3 getPhongPoint(PointLight light) {
    vec3 normal = normalize(vertexOutput.normal);
    vec3 lightDir = normalize(light.position - vertexOutput.worldFragPos);
    vec3 viewDir = normalize(metadata.cameraPosition - vertexOutput.worldFragPos);
    vec3 reflectDir = reflect(-lightDir, normal);

    vec3 diffuse = vec3(max(dot(normal, lightDir), 0.0)) * light.color;
    vec3 specular = vec3(pow(max(dot(viewDir, reflectDir), 0.0), light.specularDamper))
            * properties.specularIntensity
            * light.color;

    vec3 phong = diffuse + specular;
    return max(phong, ambient.intensity * ambient.color);
}

void main() {
    vec3 color = vec3(1);

    if (properties.texturesEnabled == 1.0) color *= texture(properties.diffuseSampler, vertexOutput.texCoords).rgb;
    color *= getPhongPoint(point);
    color *= properties.tint;

    FragColor = vec4(color, 1.0);
}