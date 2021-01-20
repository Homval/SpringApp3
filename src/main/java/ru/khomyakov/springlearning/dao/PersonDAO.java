package ru.khomyakov.springlearning.dao;

import org.springframework.stereotype.Component;
import ru.khomyakov.springlearning.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private int PEOPLE_COUNT;
    private final List<Person> people;

    {
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "Tom", 24, "tom@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Jerry", 37, "jerry@google.com"));
        people.add(new Person(++PEOPLE_COUNT, "Micky", 65, "mickey@yahoo.com"));
        people.add(new Person(++PEOPLE_COUNT, "Bob", 29, "123bob@yandex.ru"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person newPerson) {
        Person oldPerson = show(id);
        oldPerson.setName(newPerson.getName());
        oldPerson.setAge(newPerson.getAge());
        oldPerson.setEmail(newPerson.getEmail());

    }

    public void delete(int id) {
        people.removeIf(p -> p.getId() == id);
    }
}
