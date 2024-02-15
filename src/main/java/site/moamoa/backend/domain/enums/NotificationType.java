package site.moamoa.backend.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
  NEW_COMMENT("notice"),
  NEW_PARTICIPATION("post"),
  NEW_NOTICE("notice"),
  QUANTITY_FULLFIL("post");

  private final String belongingTo;
}