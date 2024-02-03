package site.moamoa.backend.service.component.query.commment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moamoa.backend.service.module.comment.CommentModuleService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentQueryServiceImpl implements CommentQueryService{

    private final CommentModuleService commentModuleService;
}
