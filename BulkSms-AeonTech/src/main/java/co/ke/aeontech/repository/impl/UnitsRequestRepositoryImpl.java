package co.ke.aeontech.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.Organisation_;
import co.ke.aeontech.models.UnitsRequest;
import co.ke.aeontech.models.UnitsRequest_;
import co.ke.aeontech.pojos.Request;
import co.ke.aeontech.repository.custom.UnitsRequestRepositoryCustom;

public class UnitsRequestRepositoryImpl implements UnitsRequestRepositoryCustom {

	@PersistenceUnit
	private EntityManagerFactory factory;

	@Override
	@Transactional
	public List<UnitsRequest> findPendingByOrganisationId(Long id) {
		List<UnitsRequest> pendingRequests = new ArrayList<UnitsRequest>();
		final EntityManager manager = factory.createEntityManager();
		try {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<UnitsRequest> query = builder.createQuery(UnitsRequest.class);
			Root<UnitsRequest> requestRoot = query.from(UnitsRequest.class);
			Join<UnitsRequest, Organisation> join = requestRoot.join(UnitsRequest_.organisation);
			query = query.select(requestRoot).distinct(true)
					.where(builder.and(builder.equal(requestRoot.get(UnitsRequest_.requestStatus), Request.PENDING),
							builder.equal(join.get(Organisation_.id), id)));
			pendingRequests = manager.createQuery(query).getResultList();
		} 
		finally {
			manager.close();
		}

		return pendingRequests;
	}
}
