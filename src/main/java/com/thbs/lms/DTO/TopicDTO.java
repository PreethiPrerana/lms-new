package com.thbs.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The {@code TopicDTO} class represents a data transfer object (DTO) for a
 * topic.
 * It encapsulates information about a topic, including its ID and name.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicDTO {
    /**
     * The unique identifier for the topic.
     */
    private Long topicId;

    /**
     * The name of the topic.
     */
    private String topicName;
}
