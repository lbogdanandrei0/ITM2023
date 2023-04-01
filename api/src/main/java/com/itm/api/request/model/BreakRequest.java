package com.itm.api.request.model;

import com.itm.api.timeline.model.Timeline;
import com.itm.api.user.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
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

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "initiator", referencedColumnName = "id")
    private User initiator;

    @Column
    private UUID externalUuid;

    @ManyToMany(targetEntity = Timeline.class)
    @JoinTable(name = "break_request_timeline", joinColumns = @JoinColumn(name="break_request_id"), inverseJoinColumns = @JoinColumn(name="timeline_id"))
    private List<Timeline> timelines;
}
