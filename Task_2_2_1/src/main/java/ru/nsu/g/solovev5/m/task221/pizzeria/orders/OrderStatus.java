package ru.nsu.g.solovev5.m.task221.pizzeria.orders;

/**
 * Describes different order statuses.
 */
public enum OrderStatus {
    NEW, PENDING, COOKING, IN_WAREHOUSE, DELIVERY, DONE;

    /**
     * Provides a new sequential status.
     *
     * @return a next order status
     * @throws IllegalStateException if this status is {@link #DONE}
     */
    public OrderStatus next() {
        return switch (this) {
            case NEW -> PENDING;
            case PENDING -> COOKING;
            case COOKING -> IN_WAREHOUSE;
            case IN_WAREHOUSE -> DELIVERY;
            case DELIVERY -> DONE;
            case DONE -> throw new IllegalStateException("No next status for the DONE status");
        };
    }
}
