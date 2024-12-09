package moonpo.consumable.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import moonpo.consumable.model.mapid.DepartmentID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "dept_mstr")
@IdClass(DepartmentID.class)
public class Department {

    @Id
    @Column(name = "dept_code", length = 6)
    private String code;
    @Column(name = "dept_desc", length = 20)
    private String desc;
    @Column(name = "dept_group", length = 20)
    private String group;
}
