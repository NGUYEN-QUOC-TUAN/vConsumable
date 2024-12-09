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
import moonpo.consumable.model.mapid.MaterialID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "item_mstr")
@IdClass(MaterialID.class)
public class Material {
    @Id
    @Column(name = "item_num", length = 18)
    private String num;
    @Column(name = "item_desc", length = 40)
    private String desc;
    @Column(name = "item_std", length = 40)
    private String std;
    @Column(name = "item_cat", length = 3)
    private String cat;
    @Column(name = "item_curr", length = 3)
    private String curr;
    @Column(name = "item_cost")
    private Double cost;
    @Column(name = "item_um", length = 6)
    private String um;
    @Column(name = "item_vendor", length = 5)
    private String vendor;
    @Column(name = "item_ordlvl")
    private Double ordlvl;
    @Column(name = "item_moq")
    private Double moq;
    @Column(name = "item_group", length = 20)
    private String group;
}
