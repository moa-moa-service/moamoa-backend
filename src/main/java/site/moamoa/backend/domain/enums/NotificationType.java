package site.moamoa.backend.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
  NEW_COMMENT("notices"),
  NEW_PARTICIPATION("posts"),
  NEW_NOTICE("notices"),
  QUANTITY_FULLFIL("posts");

  private final String belongingTo;
}