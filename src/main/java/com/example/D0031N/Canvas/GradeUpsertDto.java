// GradeUpsertDto.java – request body vid rättning/uppdatering
package com.example.D0031N.Canvas;

public class GradeUpsertDto {
    private String grade;     // t.ex. A-F, G/U
    private String comment;   // valfri
    private String gradedAt;  // ISO8601, valfri; om null -> now()

    public GradeUpsertDto() {}
    public GradeUpsertDto(String grade, String comment, String gradedAt) {
        this.grade = grade; this.comment = comment; this.gradedAt = gradedAt;
    }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public String getGradedAt() { return gradedAt; }
    public void setGradedAt(String gradedAt) { this.gradedAt = gradedAt; }
}
