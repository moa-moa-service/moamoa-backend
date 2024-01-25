package site.moamoa.backend.service;

import site.moamoa.backend.domain.mapping.MemberPost;

public interface MemberPostCommandService {
  void save(MemberPost memberPost);

}
