package co.ke.aeontech.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.Organisation_;
import co.ke.aeontech.models.Payment;
import co.ke.aeontech.models.Payment_;
import co.ke.aeontech.repository.custom.PaymentRepositoryCustom;

public class PaymentRepositoryImpl implements PaymentRepositoryCustom {

	@PersistenceUnit
	private EntityManagerFactory factory;

	@Override
	public List<Payment> SearchBtw(Date from, Date to, Long id) {
		final EntityManager manager = factory.createEntityManager();
		List<Payment> reports = new ArrayList<Payment>();
		try {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<Payment> query = builder.createQuery(Payment.class);
			
			Root<Payment> requestRoot = query.from(Payment.class);			
			Path<Date> date = requestRoot.get(Payment_.date);

			Join<Payment, Organisation> join = requestRoot.join(Payment_.organisation);
			
			query = query.select(requestRoot).distinct(true)
					.where(builder.and(builder.between(date, from, to), 
							builder.equal(join.get(Organisation_.id), id)));
			
			reports = manager.createQuery(query).getResultList();
		} catch(NoResultException nre){
			return null;
		}finally {
			manager.close();
		}
		return reports;
	}

}
