package site.moamoa.backend.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
  NEW_COMMENT("댓글"),
  NEW_PARTICIPATION("공동구매"),
  NEW_NOTICE("공지사항"),
  QUANTITY_FULFILL("공동구매");

  private final String belongingTo;
}