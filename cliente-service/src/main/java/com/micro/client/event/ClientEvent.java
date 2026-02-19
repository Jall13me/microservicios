package com.micro.client.event;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientEvent {

    private Long clienteId;
    private String name;
    private String email;

    private String accion;

    private LocalDateTime timestamp;
}