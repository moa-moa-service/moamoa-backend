package site.moamoa.backend.service.component.query.member_post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.api_payload.code.status.ErrorStatus;
import site.moamoa.backend.api_payload.exception.handler.MemberHandler;
import site.moamoa.backend.api_payload.exception.handler.MemberPostHandler;
import site.moamoa.backend.domain.enums.IsAuthorStatus;
import site.moamoa.backend.domain.mapping.MemberPost;
import site.moamoa.backend.repository.mapping.MemberPostRepository;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MemberPostQueryServiceImpl implements MemberPostQueryService {
}
