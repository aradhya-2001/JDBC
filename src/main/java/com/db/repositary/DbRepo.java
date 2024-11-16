package com.db.repositary;

import com.db.entity.Person;


import java.util.List;

public interface DbRepo
{
    List<Person> getPersonData();

    int addPerson(Person person);

    int addPersonByPreparedStatement(Person person);
}
