package ru.khomyakov.springlearning.dao;

import org.springframework.stereotype.Component;
import ru.khomyakov.springlearning.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private int PEOPLE_COUNT;

    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public List<Person> index() {
        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM person";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return people;
    }

    public Person show(int id) {
        Person person = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE id=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            person = new Person();

            resultSet.next();
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return person;
    }

    public void save(Person person) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO person VALUES(1, ?, ?, ?)");
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setString(3, person.getEmail());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(int id, Person newPerson) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE person SET name=?, age=?, email=? WHERE id=?");
            statement.setString(1, newPerson.getName());
            statement.setInt(2, newPerson.getAge());
            statement.setString(3, newPerson.getEmail());
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(int id) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE FROM person WHERE id=?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
