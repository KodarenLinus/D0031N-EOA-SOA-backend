package com.example.D0031N.Canvas;

public class GradeDto {
    private String username;
    private String label;
    private Double points;
    private String gradedAt;

    public GradeDto() {}

    public GradeDto(String username, String label, Double points, String gradedAt) {
        this.username = username; this.label = label; this.points = points; this.gradedAt = gradedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public String getGradedAt() {
        return gradedAt;
    }

    public void setGradedAt(String gradedAt) {
        this.gradedAt = gradedAt;
    }
}
