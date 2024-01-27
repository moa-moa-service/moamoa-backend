package site.moamoa.backend.service.component.query.post_image;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PostImageQueryServiceImpl implements PostImageQueryService {

}
