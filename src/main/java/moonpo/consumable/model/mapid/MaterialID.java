package moonpo.consumable.model.mapid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialID implements Serializable {

    private String num;
    private String vendor;
    private String group;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MaterialID materialID = (MaterialID) obj;
        return Objects.equals(num, materialID.getNum()) && Objects.equals(vendor, materialID.getVendor()) && Objects.equals(group, materialID.getGroup());
    }
}
