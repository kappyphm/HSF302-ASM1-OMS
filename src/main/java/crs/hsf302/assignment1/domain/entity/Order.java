package crs.hsf302.assignment1.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email","program_id"})
})
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name", columnDefinition = "NVARCHAR(100)")
    private String firstName;
    @Column(name = "last_name", columnDefinition = "NVARCHAR(100)")
    private String lastName;
    private String phone;
    private String email;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String address1;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String address2;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String city;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String region;
    private String postal;

    @ManyToOne
    @JoinColumn(name = "country_code")
    private Country country;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @CreationTimestamp
    private LocalDateTime date;

    public String getCode(){
        return String.format("ORD%03d",id);
    }

}
