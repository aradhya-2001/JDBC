package com.db.service;

import com.db.config.DbConnection;
import com.db.entity.JpaPerson;
import com.db.entity.Person;
import com.db.repositary.DbRepo;
import com.db.repositary.JpaRepo;
import com.db.repositary.SpringJDBC;
import com.db.repositary.SqlConnector;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PersonService {

    @Autowired
    private SpringJDBC springJDBC;

    @Autowired
    private SqlConnector sqlConnector;

    @Autowired
    private JpaRepo jpaRepo;

    public List<Person> getPersonInfo() {
        return decideRepo("springJdbc").getPersonData();
    }

    public int addPerson(Person person){
        return decideRepo("sqlConnector").addPerson(person);
    }

    private DbRepo decideRepo(String repoType)
    {
        if(repoType.equalsIgnoreCase("springJdbc")){
            return springJDBC;
        } else {
            return sqlConnector;
        }
    }

    @Transactional
    public void addPersonByJpa(JpaPerson person){
        //String id = UUID.randomUUID().toString();
        JpaPerson obj = jpaRepo.save(new JpaPerson(person.getName(), person.getSerial_num(), person.getAge()));
        Log logger = LogFactory.getLog(PersonService.class);
        logger.info(obj);
        jpaRepo.getReferenceById(obj.getId());
    }
}

/*
At line 51, data will be stored in the Sessions Cache and the same data will be retrieved at line 54
(without reaching to DB layer).
Since the function is Transactional,
it means a transaction is started so all the queries will be atomic or all will be executed at once.
So,
without data being saved in db (at line 51 through save())
we can retrieve it (at line 54 through getReferenceById()) from cache only.
*/