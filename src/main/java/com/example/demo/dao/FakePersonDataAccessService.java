package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

    private static List<Person> DB = new ArrayList<>();


    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 0;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonByID(final UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePersonById(final UUID id) {
        final Optional<Person> maybePerson = selectPersonByID(id);
        if (maybePerson.isEmpty()) {
            return 0;
        }
        DB.remove(maybePerson.get());
        return 1;
    }

    @Override
    public int updatePersonById(final UUID id, final Person personToUpdate) {
        return selectPersonByID(id)
                .map(person -> {
                    int indexOfPersonToUpdate = DB.indexOf(person);
                    if (indexOfPersonToUpdate >= 0) {
                        DB.set(indexOfPersonToUpdate, new Person(id, personToUpdate.getName()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }
}
