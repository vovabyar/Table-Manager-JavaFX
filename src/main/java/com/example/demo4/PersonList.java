package com.example.demo4;

import com.google.gson.Gson;
import java.io.FileReader;
import java.util.*;


public class PersonList implements Cloneable{
    public ArrayList<Person> persons;
    public PersonList() {
        persons = new ArrayList<>();
    }
    public void readJson(String jsonFileName) {
        Gson gson = new Gson();
        try(FileReader reader = new FileReader(jsonFileName)) {
            PersonList root = gson.fromJson(reader, PersonList.class);
            persons = root.persons;

        } catch (Exception e) {
            System.out.println("Parsing error " + e.toString());
        }
    }

    public void sortName() {
        Collections.sort(persons);
    }

    public int BinarySearchByName(String name) {
        sortName();
        return Collections.binarySearch(persons, new Person(name, null), Person::compareTo);
    }

}
