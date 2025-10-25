package com.recruitment.controller;

import com.recruitment.entity.Job;
import com.recruitment.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @PostMapping("/job")
    public ResponseEntity<?> createJob(@RequestBody Job job) {
        job.setPostedOn(LocalDateTime.now());
        Job saved = jobRepository.save(job);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<?> getJob(@PathVariable Long jobId) {
        return jobRepository.findById(jobId)
                .map(j -> ResponseEntity.ok(j))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> allJobs() {
        return ResponseEntity.ok(jobRepository.findAll());
    }
}
