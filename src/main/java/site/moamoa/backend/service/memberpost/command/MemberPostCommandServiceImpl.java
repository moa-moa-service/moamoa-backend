package site.moamoa.backend.service.memberpost.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.domain.mapping.MemberPost;
import site.moamoa.backend.repository.memberpost.MemberPostRepository;
import site.moamoa.backend.service.memberpost.command.MemberPostCommandService;

@AllArgsConstructor
@Service
public class MemberPostCommandServiceImpl implements MemberPostCommandService {

  private final MemberPostRepository memberPostRepository;
  @Override
  public void save(MemberPost memberPost) {
    memberPostRepository.save(memberPost);
  }
}
