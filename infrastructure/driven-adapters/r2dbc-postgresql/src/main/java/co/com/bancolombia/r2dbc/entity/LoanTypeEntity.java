package co.com.bancolombia.r2dbc.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;

@Table("tipo_prestamo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanTypeEntity {
    @Id
    @Column("id_tipo_prestamo")
    private String id;

    @Column("nombre")
    private String name;

    @Column("monto_minimo")
    private BigDecimal minAmount;

    @Column("monto_maximo")
    private BigDecimal maxAmount;

    @Column("tasa_interes")
    private BigDecimal interestRate;

    @Column("validacion_automatica")
    private Boolean autoValidation;
}
