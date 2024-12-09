package moonpo.consumable.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "curr_mstr")
public class Currency {
    @Id
    @Column(name = "curr_code", length = 3)
    private String code;
    @Column(name = "curr_desc", length = 3)
    private String desc;
}
