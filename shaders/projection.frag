
vec3 getInversed(vec3 origin) {
    vec3 inversed = 1.0 - origin;
    return mix(origin, inversed, clamp(postProcessingMetadata.inversionIntensity, 0.0, 1.0));
}

void main() {
    vec2 uv = gl_FragCoord.xy / metadata.resolution;

    vec3 diffuse = texture(projectionTextures.diffuse, uv).rgb;
    vec3 depth = texture(projectionTextures.depth, uv).rgb;

    vec3 color = diffuse;

    FragColor = vec4(getInversed(color), 1.0);
}