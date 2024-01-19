package site.moamoa.backend.web.dto.base;

import java.util.Date;
import java.util.List;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
import org.springframework.web.multipart.MultipartFile;

public class PostDTO {
    Long categoryId;
    Integer personnel;
    Date deadline;
    String productName;
    List<MultipartFile> image;
    Address dealLocation;
    Integer price;
    String description;
}
