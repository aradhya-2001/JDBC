package com.db.repositary;

import com.db.entity.JpaPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // can use this or can use the following on top of DbsApplication class --> @EnableJpaRepositories(basePackageClasses = {com.db.repositary.JpaRepo.class})
public interface JpaRepo extends JpaRepository<JpaPerson, Integer>{ // if made Jpa as class, then has to implement all methods of JpaRepository

}

/*
JpaRepository interface is implemented by SimpleJpaRepository class which has an instance variable of EntityManager interface which is implemented by Hibernate.
Which means, Hibernate, row mapper, etc. implements JPA via EntityManager.

On application startup, we get EntityManager from EntityManagerFactory which will be linked with one of the implementation's of JPA i.e. hibernate, row mapper, etc.

When we call JPA methods like save(), it will call the method implementation of Hibernate by creating a session from session factory/pool to link btw JPA and Hibernate.
After query is over, session will be dead/closed.
Hibernate will do then 1 unit of work from that created session and will connect to underlying DB (MySQL in our case) through connection pool (via Hikari) to save the data ultimately.
*/

