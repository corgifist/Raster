
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
    return phong;
}

vec3 getOptionalAmbient() {
    if (metadata.pointLightCount == 0.0) {
        if (metadata.ambientLightExists == 1.0) {
            return vec3(ambient.intensity * ambient.color);
        }
    }
    return vec3(1);
}

void main() {
    vec3 color = vec3(1);

    if (properties.texturesEnabled == 1.0) color *= texture(properties.diffuseSampler, vertexOutput.texCoords).rgb;

    color *= getOptionalAmbient();

    if (metadata.pointLightCount != 0.0) {
        vec3 phong = vec3(0);
        for (float lightIndex = 0; lightIndex < metadata.pointLightCount; lightIndex++) {
            PointLight point = points[int(lightIndex)];
            phong += getPhongPoint(point);
        }
        color *= phong;
    }

    color *= properties.tint;

    FragColor = vec4(color, 1.0);
}