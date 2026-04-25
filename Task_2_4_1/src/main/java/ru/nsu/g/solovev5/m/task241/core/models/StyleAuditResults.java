package ru.nsu.g.solovev5.m.task241.core.models;

/**
 * Represents the style audit results.
 *
 * @param warnings the number of warnings
 * @param errors   the number of errors
 */
public record StyleAuditResults(int warnings, int errors) {
    /**
     * The total number of events equals to sum of warnings and errors.
     *
     * @return the total number of events
     */
    public int totalEvents() {
        return warnings + errors;
    }

    @Override
    public String toString() {
        return "[" + warnings + "/" + errors + "/" + totalEvents() + "]";
    }
}
