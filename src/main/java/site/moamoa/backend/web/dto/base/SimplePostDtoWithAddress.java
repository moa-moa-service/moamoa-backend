package site.moamoa.backend.web.dto.base;

import lombok.Builder;
import site.moamoa.backend.domain.embedded.Address;

@Builder
public record SimplePostDtoWithAddress(
    SimplePostDTO simplePostDTO,
    Address address
) {

}
