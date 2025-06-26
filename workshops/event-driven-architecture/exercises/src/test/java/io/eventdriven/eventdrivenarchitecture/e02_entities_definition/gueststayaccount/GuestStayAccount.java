package io.eventdriven.eventdrivenarchitecture.e02_entities_definition.gueststayaccount;

import java.time.OffsetDateTime;
import java.util.UUID;

public class GuestStayAccount  {

  UUID guestId;
  GuestStatus status;
  OffsetDateTime checkInTime;
  double balance;

  public GuestStayAccount(UUID guestId, GuestStatus status, OffsetDateTime checkInTime) {
    this.guestId = guestId;
    this.status = status;
    this.checkInTime = checkInTime;
  }

  public GuestStayAccount() {}

  public UUID getGuestId() {
    return guestId;
  }

  public void setGuestId(UUID guestId) {
    this.guestId = guestId;
  }

  public GuestStatus getStatus() {
    return status;
  }

  public void setStatus(GuestStatus status) {
    this.status = status;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public OffsetDateTime getCheckInTime() {
    return checkInTime;
  }

  public void setCheckInTime(OffsetDateTime checkInTime) {
    this.checkInTime = checkInTime;
  }
}
