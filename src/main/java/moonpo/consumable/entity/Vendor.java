package moonpo.consumable.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import moonpo.consumable.model.mapid.VendorID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "vd_mstr")
@IdClass(VendorID.class)
public class Vendor {
    @Id
    @Column(name = "vd_code", length = 5)
    private String code;
    @Column(name = "vd_name", length = 40)
    private String desc;
    @Column(name = "vd_group", length = 20)
    private String group;
}
