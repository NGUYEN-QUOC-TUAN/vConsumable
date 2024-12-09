package moonpo.consumable.model.mapid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class VendorID implements Serializable {

    private String code;
    private String group;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this != obj) return false;
        if (obj == null || getClass() != obj.getClass()) return false;
        VendorID vendorID = (VendorID) obj;
        return Objects.equals(code, vendorID.getCode()) && Objects.equals(group, vendorID.getGroup());
    }
}
