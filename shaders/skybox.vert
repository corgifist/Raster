void main() {
    vec4 calcPos = transformations.projection * transformations.view * vec4(vertexPosition, 1.0);
    gl_Position = calcPos.xyww;

    vertexOutput.vertexPosition = vec4(vertexPosition, 1.0);
}