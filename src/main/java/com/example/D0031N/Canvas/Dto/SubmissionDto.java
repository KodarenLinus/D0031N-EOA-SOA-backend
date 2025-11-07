package com.example.D0031N.Canvas.Dto;
/*
 * Data Transfer Object
 * For Submission
 * Submission (JSON):
 * Submission {
 *   submissionId: xxxxx,
 *   studentId: xxxxx,
 *   status: xxxxx,
 *   submittedAt: xxxxx,
 * }
 * */
public record SubmissionDto(
        Long submissionId,
        Long studentId,
        String status,
        String submittedAt
) {}