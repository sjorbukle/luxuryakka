package com.laplacian.luxuryakka.module.organizationstructure.domain;

import com.laplacian.luxuryakka.core.Asserts;

public enum OrganizationStructureType
{
    COUNTRY(1L),
    REGION(2L),
    RIVIERA(3L),
    CITY(4L);

    private OrganizationStructureType(Long id)
    {
        this.id = id;
    }
    private final Long id;

    public Long id()
    {
        return this.id;
    }

    public static OrganizationStructureType getById(Long id)
    {
        Asserts.argumentIsNotNull(id);
        Asserts.argumentIsTrue(id > 0L);

        for(OrganizationStructureType item: OrganizationStructureType.values())
        {
            if(item.id().equals(id)) { return item; }
        }

        throw new RuntimeException("OrganizationStructureType with id: " + id + " doesn't exists.");
    }
}
