package site.moamoa.backend.web.dto.base;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
import org.springframework.web.multipart.MultipartFile;

public class MemberDTO {
    String nickname;
    MultipartFile profile_image;
    Address location;
}
