package com.micro.client.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cliente")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "activo")
    private boolean active = true;

    @PrePersist
    protected void onCreate() {
        if (this.fechaRegistro == null) {
            fechaRegistro = LocalDateTime.now();
        }
        if (!active) {
            active = true;
        }
    }
}
