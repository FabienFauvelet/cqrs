package org.acme.domain.exception;

public class DomainRuleViolationException extends Throwable{
    public DomainRuleViolationException(String message) {
        super(message);
    }
}
