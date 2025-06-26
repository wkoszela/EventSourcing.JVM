package io.eventdriven.eventdrivenarchitecture.e02_entities_definition;

import io.eventdriven.eventdrivenarchitecture.e02_entities_definition.core.Database;
import io.eventdriven.eventdrivenarchitecture.e02_entities_definition.core.EventBus;
import io.eventdriven.eventdrivenarchitecture.e02_entities_definition.gueststayaccount.GuestStatus;
import io.eventdriven.eventdrivenarchitecture.e02_entities_definition.gueststayaccount.GuestStayAccount;
import io.eventdriven.eventdrivenarchitecture.e02_entities_definition.gueststayaccount.GuestStayAccountEvent;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GuestStayFacade {
    private final Database database;

    private final EventBus eventBus;

    public GuestStayFacade(Database database, EventBus eventBus) {
        this.database = database;
        this.eventBus = eventBus;
    }

    public void checkInGuest(GuestStayAccountCommand.CheckInGuest command) {
        // TODO: Double checkin is possible!
        GuestStayAccount account =
            database.get(GuestStayAccount.class, command.guestStayId())
                .orElse(new GuestStayAccount(command.guestStayId, GuestStatus.CHECK_IN, command.now));
        database.store(account.getGuestId(), account);
        eventBus.publish(new Object[] {
            new GuestStayAccountEvent.GuestCheckedIn(account.getGuestId(), account.getCheckInTime()) });
    }

    public void recordCharge(GuestStayAccountCommand.RecordCharge command) {
        GuestStayAccount account = database.get(GuestStayAccount.class, command.guestStayId())
            .orElseThrow(() -> new IllegalStateException("Entity not found"));

        if (account.getStatus() == GuestStatus.CHECK_OUT) {
            throw new RuntimeException("Guest has been already checked out. Operation not permitted");
        }
        account.setBalance(account.getBalance() - command.amount);
        database.store(command.guestStayId(), account);
        eventBus.publish(new Object[] {
            new GuestStayAccountEvent.ChargeRecorded(account.getGuestId(), command.amount, command.now()) });
    }

    public void recordPayment(GuestStayAccountCommand.RecordPayment command) {
        var account = database.get(GuestStayAccount.class, command.guestStayId())
            .orElseThrow(() -> new IllegalStateException("Entity not found"));

        // TODO: Fill the implementation calling your entity/aggregate
        // account.doSomething;
        Object[] events = new Object[] {};

        database.store(command.guestStayId(), account);
        eventBus.publish(events);

        throw new RuntimeException("TODO: Fill the implementation calling your entity/aggregate");
    }

    public void checkOutGuest(GuestStayAccountCommand.CheckOutGuest command) {
        var account = database.get(GuestStayAccount.class, command.guestStayId())
            .orElseThrow(() -> new IllegalStateException("Entity not found"));

        // TODO: Fill the implementation calling your entity/aggregate
        // account.doSomething;
        Object[] events = new Object[] {};

        database.store(command.guestStayId(), account);
        eventBus.publish(events);

        throw new RuntimeException("TODO: Fill the implementation calling your entity/aggregate");
    }

    public void initiateGroupCheckout(GroupCheckoutCommand.InitiateGroupCheckout command) {
        // TODO: Fill the implementation publishing event
        throw new RuntimeException("TODO: Fill the implementation publishing event");
    }

    public sealed interface GuestStayAccountCommand {
        record CheckInGuest(
            UUID guestStayId,
            OffsetDateTime now) implements GuestStayAccountCommand {
        }

        record RecordCharge(
            UUID guestStayId,
            double amount,
            OffsetDateTime now) implements GuestStayAccountCommand {
        }

        record RecordPayment(
            UUID guestStayId,
            double amount,
            OffsetDateTime now) implements GuestStayAccountCommand {
        }

        record CheckOutGuest(
            UUID guestStayId,
            OffsetDateTime now,
            UUID groupCheckOutId) implements GuestStayAccountCommand {
            public CheckOutGuest(UUID guestStayId, OffsetDateTime now) {
                this(guestStayId, now, null);
            }
        }
    }

    public sealed interface GroupCheckoutCommand {
        record InitiateGroupCheckout(
            UUID groupCheckoutId,
            UUID clerkId,
            UUID[] guestStayIds,
            OffsetDateTime now) implements GroupCheckoutCommand {
        }
    }
}
