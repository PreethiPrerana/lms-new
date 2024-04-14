package com.thbs.lms.model;

import lombok.AllArgsConstructor;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Topic")
public class Topic {
    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(name = "sequence-generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @Parameter(name = "initial_value", value = "100"),
            @Parameter(name = "increment_size", value = "1")
    })
    private Long topicID;

    private String topicName;
    private String description;

    @ManyToOne
    @JoinColumn(name = "courseID")
    private Course course;
}
