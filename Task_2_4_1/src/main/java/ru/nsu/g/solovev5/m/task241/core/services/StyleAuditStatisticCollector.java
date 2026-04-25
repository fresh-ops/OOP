package ru.nsu.g.solovev5.m.task241.core.services;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import ru.nsu.g.solovev5.m.task241.core.models.StyleAuditResults;

/**
 * An audit statistics collector.
 */
public class StyleAuditStatisticCollector implements AuditListener {
    private int warningsCounter = 0;
    private int errorsCounter = 0;

    /**
     * Freezes collected statistics.
     *
     * @return frozen statistics
     */
    public StyleAuditResults freeze() {
        return new StyleAuditResults(warningsCounter, errorsCounter);
    }

    @Override
    public void auditStarted(AuditEvent event) {
        warningsCounter = 0;
        errorsCounter = 0;
    }

    @Override
    public void auditFinished(AuditEvent event) {
    }

    @Override
    public void fileStarted(AuditEvent event) {
    }

    @Override
    public void fileFinished(AuditEvent event) {
    }

    @Override
    public void addError(AuditEvent event) {
        switch (event.getSeverityLevel()) {
            case ERROR -> errorsCounter++;
            case WARNING -> warningsCounter++;
            default -> {
                break;
            }
        }
    }

    @Override
    public void addException(AuditEvent event, Throwable throwable) {
    }
}
