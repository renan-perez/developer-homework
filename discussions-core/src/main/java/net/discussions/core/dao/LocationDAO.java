package net.discussions.core.dao;

import org.springframework.stereotype.Repository;

import net.discussions.core.exception.SystemException;
import net.discussions.core.model.Location;

@Repository
public class LocationDAO extends GenericDAO<Location, Long> {
	
	public Location saveOrUpdate(final Location location) throws SystemException {
		return location.getId() == null ? super.save(location) : update(location);
	}	
}
