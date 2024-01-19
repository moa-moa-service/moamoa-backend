package site.moamoa.backend.domain.embedded;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {
    private String name;
    private double latitude;
    private double longitude;
}