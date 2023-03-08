package com.example.demo.draft.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "pr30_registry_status_log", schema = "\"pr-registry\"")
public class RegistryStatusLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "registry_id", nullable = false)
    private Registry registry;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private RegistryStatus status;

    @Column(name = "status_message")
    private String statusMessage;

    @Column(name = "status_date", nullable = false)
    private LocalDateTime statusDate;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private RegistryEvent event;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;
}
