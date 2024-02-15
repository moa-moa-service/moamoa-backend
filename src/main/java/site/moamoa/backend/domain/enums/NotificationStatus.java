package site.moamoa.backend.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationStatus {
  READ,
  UNREAD
}