package com.example.D0031N.Canvas;

public record SubmissionDto(
        Long submissionId,
        Long studentId,
        String status,
        String submittedAt // formaterat ISO-datum ("YYYY-MM-DDTHH:mm:ss")
) {}