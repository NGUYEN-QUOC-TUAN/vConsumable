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
import moonpo.consumable.model.mapid.CategoryID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "cat_mstr")
@IdClass(CategoryID.class)
public class Category {

    @Id
    @Column(name = "cat_code", length = 3)
    private String code;

    @Column(name = "cat_desc", length = 30)
    private String desc;

    @Id
    @Column(name = "cat_group", length = 20)
    private String group;
}