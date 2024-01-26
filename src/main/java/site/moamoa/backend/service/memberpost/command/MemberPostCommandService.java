package site.moamoa.backend.service.memberpost.command;

import site.moamoa.backend.domain.mapping.MemberPost;

public interface MemberPostCommandService {
  void save(MemberPost memberPost);

}
