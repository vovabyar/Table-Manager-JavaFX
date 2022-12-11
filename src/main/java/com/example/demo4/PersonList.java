package com.example.demo4;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            reader.close();

        } catch (Exception e) {
            System.out.println("Parsing error " + e.toString());
        }
    }

    public void sortDescPrice() {
        Collections.sort(persons, new Person.CmpByFirstName());
    }
    public void sortName() {
        Collections.sort(persons);
    }

    public int BinarySearchByName(String name) {
        sortName();
        return Collections.binarySearch(persons, new Person(name, null), Person::compareTo);
    }



    public void addToList(Person b) {
        persons.add(b);
    }

    public Person GetByIndex(int index) {
        return persons.get(index);
    }

//    public MedicineList filtByFirm(String firm) {
//        var stream = persons.stream();
//        var res = stream.filter(book -> book.Firm.equals(firm));
//        var filteredBooks = new MedicineList();
//        Collections.addAll(filteredBooks.persons, res.toArray(Person[]::new));
//        return filteredBooks;
//    }
//    public Map<String, List<Person>> GroupByAuthorName(String name) {
//        //stream:
//        var stream = persons.stream();
//        //лямбда:
//        var res = stream.collect(Collectors.groupingBy(b -> b.Firm));
//        return res;
//    }

    public void print() {
        var stream = persons.stream();
        stream.forEach(System.out::println);
    }

}
