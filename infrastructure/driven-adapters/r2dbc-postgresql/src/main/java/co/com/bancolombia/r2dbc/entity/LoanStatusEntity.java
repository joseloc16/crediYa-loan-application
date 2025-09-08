package co.com.bancolombia.r2dbc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Table("estados")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanStatusEntity {
    @Id
    @Column(name = "id_estado")
    private String id;

    @Column(name = "nombre")
    private String name;

    @Column(name = "descripcion")
    private String description;
}
