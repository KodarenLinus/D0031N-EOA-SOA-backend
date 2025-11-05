// GradeDto.java – för läsning/listning av betyg
package com.example.D0031N.Canvas;

public class GradeDto {
    private String studentId;
    private String grade;
    private String comment;
    private String gradedAt;

    public GradeDto() {}
    public GradeDto(String studentId, String grade, String comment, String gradedAt) {
        this.studentId = studentId; this.grade = grade; this.comment = comment; this.gradedAt = gradedAt;
    }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public String getGradedAt() { return gradedAt; }
    public void setGradedAt(String gradedAt) { this.gradedAt = gradedAt; }
}
