// com/example/D0031N/Canvas/CanvasRosterItemDto.java
package com.example.D0031N.Canvas;

public class CanvasRosterItemDto {
    private String studentId;
    private String name;
    private String email;
    private String canvasGrade;   // ev. betyg för vald assignment
    private String gradedAt;      // ISO8601 sträng

    public CanvasRosterItemDto() {}
    public CanvasRosterItemDto(String studentId, String name, String email, String canvasGrade, String gradedAt) {
        this.studentId = studentId; this.name = name; this.email = email;
        this.canvasGrade = canvasGrade; this.gradedAt = gradedAt;
    }
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCanvasGrade() {
        return canvasGrade;
    }

    public void setCanvasGrade(String canvasGrade) {
        this.canvasGrade = canvasGrade;
    }

    public String getGradedAt() {
        return gradedAt;
    }

    public void setGradedAt(String gradedAt) {
        this.gradedAt = gradedAt;
    }
}
