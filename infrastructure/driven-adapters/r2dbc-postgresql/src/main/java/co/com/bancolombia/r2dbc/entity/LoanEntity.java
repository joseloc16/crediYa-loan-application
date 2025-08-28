package co.com.bancolombia.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("solicitud")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanEntity {
    @Id
    @Column("id_solicitud")
    private String id;

    @Column("monto")
    private BigDecimal amount;

    @Column("plazo")
    private Integer termMonths;

    @Column("email")
    private String email;

    @Column("id_tipo_prestamo")
    private Long loanTypeId;

    @Column("id_estado")
    private Long statusId;
}
