package com.example.demo.draft.model;

import com.example.demo.draft.model.enums.FileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pr30_registry_file", schema = "\"pr-registry\"")
public class RegistryFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "icb_file_id")
    private String icbFileId;

    @Column(name = "elib_file_id")
    private Long elibFileId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_ext", nullable = false)
    private String fileExt;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    private FileType fileType;

    @Column(name = "size")
    private Long size;

    @ManyToOne
    @JoinColumn(name = "registry_id", nullable = false)
    private Registry registryForFile;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;
}
