package com.example.classroom.entities;


import com.example.classroom.enums.ERole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ERole name;

}
