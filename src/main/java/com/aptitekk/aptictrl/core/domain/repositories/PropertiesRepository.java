/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.domain.repositories;

import com.aptitekk.aptictrl.core.domain.entities.Property;
import com.aptitekk.aptictrl.core.domain.repositories.annotations.EntityRepository;

import javax.persistence.PersistenceException;
import java.util.List;

@EntityRepository
public class PropertiesRepository extends EntityRepositoryAbstract<Property> {

    /**
     * Gets the Property Entity that matches the Property Key.
     *
     * @param propertyKey The Property Key.
     * @return the Property Entity if found, null otherwise.
     */
    public Property findPropertyByKey(Property.Key propertyKey) {
        if (propertyKey == null)
            return null;

        try {
            return entityManager
                    .createQuery("SELECT p FROM Property p WHERE p.propertyKey = :propertyKey", Property.class)
                    .setParameter("propertyKey", propertyKey)
                    .getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }

    /**
     * Gets the Property Entities whose Keys are assigned to the specified Property Group, within the specified Tenant.
     *
     * @param propertyGroup The Property Group to filter by.
     * @return A list containing all Property Entities that are within the Group.
     */
    public List<Property> findAllPropertiesByGroup(Property.Group propertyGroup) {
        if (propertyGroup == null)
            return null;

        try {
            return entityManager.createQuery("SELECT p from Property p WHERE p.propertyKey IN :propertyKeys", Property.class)
                    .setParameter("propertyKeys", propertyGroup.getKeys())
                    .getResultList();
        } catch (PersistenceException e) {
            return null;
        }
    }
}
