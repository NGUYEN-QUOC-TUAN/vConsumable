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
public class CategoryID implements Serializable {
    private String code;
    private String group;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CategoryID categoryID = (CategoryID) obj;
        return Objects.equals(code, categoryID.getCode()) && Objects.equals(group, categoryID.getGroup());
    }
}
