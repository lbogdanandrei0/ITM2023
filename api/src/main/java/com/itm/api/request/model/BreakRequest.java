package com.itm.api.request.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "break_request")
@Getter
@Setter
public class BreakRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq_generator")
    @SequenceGenerator(name = "user_id_seq_generator", sequenceName = "user_id_sequence", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column
    private String place;

    @Column
    private String comment;

    @Column
    private UUID external_uuid;
}
