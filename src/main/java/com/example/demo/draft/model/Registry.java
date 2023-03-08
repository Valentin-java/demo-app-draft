package com.example.demo.draft.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "pr30_registry", schema = "\"pr-registry\"")
public class Registry {

    public Registry() {
        this.logs = new HashSet<>();
        this.files = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "esb_message_id")
    private String esbMessageId;

    @Column(name = "esb_message_date")
    private LocalDateTime esbMessageDate;

    @Column(name = "bis_id")
    private String bisId;

    @Column(name = "bis_branch_id")
    private String bisBranchId;

    @Column(name = "icb_client_id")
    private Long icbClientId;

    @Column(name = "icb_client_code")
    private String icbClientCode;

    @Column(name = "icb_client_name")
    private String icbClientName;

    @Column(name = "salary_contract_id")
    private Long salaryContractId;

    @Column(name = "salary_contract_number")
    private String salaryContractNumber;

    @Column(name = "registry_number")
    private Long registryNumber;

    @Column(name = "registry_date")
    private LocalDateTime registryDate;

    @Column(name = "payment_order_number")
    private String paymentOrderNumber;

    @Column(name = "payment_order_date")
    private LocalDateTime paymentOrderDate;

    @Column(name = "payment_order_id")
    private String paymentOrderId;

    @Column(name = "registry_sum")
    private Double registrySum;

    @Column(name = "source_registry_id")
    private String sourceRegistryId;

    @Column(name = "source_system_id", nullable = false)
    private String sourceSystemId;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private RegistryStatus status;

    @JsonIgnore
    @OneToMany(mappedBy = "registry")
    @ToString.Exclude
    private Set<RegistryStatusLog> logs;

    @OneToMany(mappedBy = "registryForFile")
    @ToString.Exclude
    private Set<RegistryFile> files;

    @Column(name = "is_status_sent", nullable = false)
    private Boolean isStatusSent;

    @Column(name = "edit_date")
    private LocalDateTime editDate;

    @Column(name = "responsible_email")
    private String responsibleEmail;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "confirmation_free", nullable = false)
    private Boolean confirmationFree;

    @Column(name = "import_file")
    private String importFile;

    @Column(name = "user_name")
    private String userName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Registry registry = (Registry) o;
        return id != null && Objects.equals(id, registry.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
