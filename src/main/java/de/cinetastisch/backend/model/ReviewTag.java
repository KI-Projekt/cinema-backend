package de.cinetastisch.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewTag {

    @Schema(accessMode = READ_ONLY)
    @SequenceGenerator(name = "reviewTag_sequence", sequenceName = "reviewTag_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "reviewTag_sequence")
    @Column(name = "id")
    private @Id Long id;

    @ManyToOne
    @JoinColumn(name = "review_tag_id", nullable = false)
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    public ReviewTag(Tag tag, Review review) {
        this.tag = tag;
        this.review = review;
    }

}
