package site.moamoa.backend.web.dto.base;

import java.util.Date;
import java.util.List;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
import org.springframework.web.multipart.MultipartFile;

public class PostDTO {
    Long category_id;
    Integer personnel;
    Date deadline;
    String product_name;
    List<MultipartFile> image;
    Address deal_location;
    Integer price;
    String description;
}
