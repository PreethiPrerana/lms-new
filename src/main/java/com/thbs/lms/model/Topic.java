package com.thbs.lms.model;

import lombok.AllArgsConstructor;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
