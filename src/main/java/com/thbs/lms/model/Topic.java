package com.thbs.lms.model;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "Topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topicID;

    private String topicName;
    private String description;

    @ManyToOne
    @JoinColumn(name = "courseID")
    private Course course;
}
