package site.moamoa.backend.domain.embedded;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String name;
    private Double latitude;
    private Double longitude;
}
