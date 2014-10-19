package com.laplacian.luxuryakka.module.log.action.domain;

public enum DomainType
{
    USER("USER");

    private DomainType(String displayName)
    {
        this.displayName = displayName;
    }
    private final String displayName;

    public String displayName()
    {
        return this.displayName;
    }
}
