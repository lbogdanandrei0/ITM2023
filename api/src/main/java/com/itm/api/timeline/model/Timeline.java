package com.itm.api.timeline.model;

import com.itm.api.user.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity(name = "user_timeline")
@Getter
@Setter
public class Timeline {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timeline_id_seq_generator")
    @SequenceGenerator(name = "timeline_id_seq_generator", sequenceName = "user_timeline_id_sequence", allocationSize = 1, initialValue = 1)
    @Column(name = "id")
    private Long id;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private UUID externalUuid;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
