// com/example/D0031N/Canvas/CanvasStudentDto.java
package com.example.D0031N.Canvas;

public class CanvasStudentDto {
    private String studentId;
    private String name;     // first + last
    private String email;    // om du har kolumnen, annars låt stå null

    public CanvasStudentDto() {}
    public CanvasStudentDto(String studentId, String name, String email) {
        this.studentId = studentId; this.name = name; this.email = email;
    }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
