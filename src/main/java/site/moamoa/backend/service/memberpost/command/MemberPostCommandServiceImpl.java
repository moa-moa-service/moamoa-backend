package site.moamoa.backend.service.memberpost.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.moamoa.backend.domain.mapping.MemberPost;
import site.moamoa.backend.repository.mapping.MemberPostRepository;

@AllArgsConstructor
@Service
public class MemberPostCommandServiceImpl implements MemberPostCommandService {

  private final MemberPostRepository memberPostRepository;
  @Override
  public void save(MemberPost memberPost) {
    memberPostRepository.save(memberPost);
  }
}
