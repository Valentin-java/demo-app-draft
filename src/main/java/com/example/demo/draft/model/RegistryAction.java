package com.example.demo.draft.model;

import com.example.demo.draft.model.enums.Action;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pr30_action", schema = "\"pr-registry\"")
public class RegistryAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false)
    private Action code;

    @Column(name = "name", nullable = false)
    private String name;

    private Long priority;

    @ManyToMany(mappedBy = "actions")
    private List<RegistryStatus> statuses;
}
