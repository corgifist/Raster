void main() {
    gl_Position = apiDefaultPositionTransform();

    vertexOutput.vertexPosition = vec4(vertexPosition, 1.0);
    vertexOutput.texCoords = texCoords;
    vertexOutput.normal = mat3(transpose(inverse(transformations.transformation))) * normal;
    vertexOutput.worldFragPos = vec3(transformations.transformation * vec4(vertexPosition, 1.0));
}