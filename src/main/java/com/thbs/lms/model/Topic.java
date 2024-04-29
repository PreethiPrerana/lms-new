package com.thbs.lms.model;

import lombok.AllArgsConstructor;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * The {@code Topic} class represents a topic within a course in the learning
 * management system.
 * It contains information such as the topic ID, name, description, and
 * associated course.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Topic")
public class Topic {
    /**
     * The unique identifier for the topic.
     */
    @Id
    @GeneratedValue(generator = "sequence-generator-topic")
    @GenericGenerator(name = "sequence-generator-topic", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @Parameter(name = "initial_value", value = "1000"),
            @Parameter(name = "increment_size", value = "1")
    })
    private Long topicID;

    /**
     * The name of the topic.
     */
    private String topicName;

    /**
     * The description of the topic, stored as TEXT.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * The course associated with the topic.
     */
    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;
}
