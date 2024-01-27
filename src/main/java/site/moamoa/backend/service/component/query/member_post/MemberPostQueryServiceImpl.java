package site.moamoa.backend.service.component.query.member_post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MemberPostQueryServiceImpl implements MemberPostQueryService {
}
