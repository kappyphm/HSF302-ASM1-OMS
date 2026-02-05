package crs.hsf302.assignment1.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "countries")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@EqualsAndHashCode
public class Country {
    @Column(columnDefinition = "NVARCHAR(255)")
    private String name;
    @Id
    @Column(unique = true)
    private String code;
}
