package com.example.D0031N.Canvas;

public class AssignmentDto {
    private Long id;
    private String name;
    private String scaleHint;
    private String type;

    public AssignmentDto() {}

    public AssignmentDto(Long id, String name, String scaleHint, String type) {
        this.id = id; this.name = name; this.scaleHint = scaleHint; this.type = type;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getScaleHint() { return scaleHint; }
    public void setScaleHint(String scaleHint) { this.scaleHint = scaleHint; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
