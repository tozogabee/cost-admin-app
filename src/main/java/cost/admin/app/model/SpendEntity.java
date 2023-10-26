package cost.admin.app.model;

import cost.admin.app.model.category.ProductCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "SPENDS")
@Getter
@Setter
@ToString
public class SpendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Column(name = "summary", nullable = false)
    private String summary;

    @Column(name = "paid", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyyTHH:mm")
    private LocalDateTime paid;

    @Column(name = "sum", nullable = false)
    private double sum;

    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

}
