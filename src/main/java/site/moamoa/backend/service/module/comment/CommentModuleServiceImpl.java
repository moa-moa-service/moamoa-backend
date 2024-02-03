package site.moamoa.backend.service.module.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.repository.comment.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentModuleServiceImpl implements CommentModuleService {

    private final CommentRepository commentRepository;
}
