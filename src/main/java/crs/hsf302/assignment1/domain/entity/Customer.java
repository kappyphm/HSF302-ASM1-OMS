package crs.hsf302.assignment1.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "first_name", columnDefinition = "NVARCHAR(100)")
    private String firstName;
    @Column(name = "last_name", columnDefinition = "NVARCHAR(100)")
    private String lastName;
    private String phone;
    @Column(unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Order> orders;
}
