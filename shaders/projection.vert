void main() {
    gl_Position = transformations.projection * transformations.transformation * vec4(vertexPosition, 1.0);
}