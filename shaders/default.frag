vec3 getPhong(vec3 lightDir, vec3 lightColor, float specularDamper) {
        vec3 normal = normalize(vertexOutput.normal);
        vec3 viewDir = normalize(metadata.cameraPosition - vertexOutput.worldFragPos);
        vec3 reflectDir = reflect(-lightDir, normal);

        vec3 diffuse = vec3(max(dot(normal, lightDir), 0.0)) * lightColor;
        vec3 specular = vec3(pow(max(dot(viewDir, reflectDir), 0.0), specularDamper))
                * properties.specularIntensity
                * lightColor;

        return (diffuse + specular);
}

vec3 getPhongPoint(PointLight light) {
    vec3 phong = getPhong(normalize(light.position - vertexOutput.worldFragPos), light.color, light.specularDamper);
    float distance = length(light.position - vertexOutput.worldFragPos);
    float attenuation = 1.0 / (light.constant + light.linear * distance +
        		    light.quadratic * (distance * distance));

    return phong * attenuation;
}

vec3 getOptionalAmbient() {
    return vec3(ambient.intensity * ambient.color) * metadata.ambientLightExists;
}

vec3 getOptionalDirectional() {
    return getPhong(directional.direction, directional.color, directional.specularDamper);
}

void main() {
    float depth = apiLinearizeDepth(gl_FragCoord.z);
    vec3 color = properties.color;

    if (properties.texturesEnabled == 1.0) {
        vec4 sampled = texture(properties.diffuseSampler, vertexOutput.texCoords);
        if (sampled.a < 0.5) discard;
        color *= sampled.rgb;
    }

    vec3 phong = getOptionalAmbient();

    for (float lightIndex = 0; lightIndex < metadata.pointLightCount; lightIndex++) {
         PointLight point = points[int(lightIndex)];
         phong += getPhongPoint(point);
    }
    if (metadata.directionalLightExists == 1.0) phong += getOptionalDirectional();

    color *= phong;
    color *= properties.tint;
    FragColor = vec4(color, 1.0);
    FragDepth = vec4(vec3(depth), 1.0);
}