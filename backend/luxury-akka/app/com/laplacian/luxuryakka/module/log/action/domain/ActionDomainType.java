package com.laplacian.luxuryakka.module.log.action.domain;

public enum ActionDomainType
{
    USER("USER");

    private ActionDomainType(String displayName)
    {
        this.displayName = displayName;
    }
    private final String displayName;

    public String displayName()
    {
        return this.displayName;
    }
}
