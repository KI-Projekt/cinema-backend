package de.cinetastisch.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import static jakarta.persistence.GenerationType.SEQUENCE;


@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Tag {

    @Schema(accessMode = READ_ONLY)
    @SequenceGenerator(name = "tag_sequence", sequenceName = "tag_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "tag_sequence")
    @Column(name = "id")
    private @Id Long id;
    private String tag;

    @OneToMany(mappedBy = "tag")
    @JsonIgnore
    private Set<ReviewTag> reviewTags;

    public Tag(String tag) {
        this.tag = tag;
    }
}
