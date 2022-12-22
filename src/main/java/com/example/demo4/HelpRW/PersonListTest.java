package com.example.demo4.HelpRW;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class PersonListTest implements Cloneable{
    public ArrayList<PersonTest> personTests;
    public PersonListTest() {
        personTests = new ArrayList<>();
    }


    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public void WriteToJsonFile(String jsonFileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = Files.newBufferedWriter(Paths.get(jsonFileName));
        gson.toJson(this, writer);
        writer.close();
    }

    public void readJson(String jsonFileName) {
        Gson gson = new Gson();

        try(FileReader reader = new FileReader(jsonFileName)) {

            PersonListTest root = gson.fromJson(reader, PersonListTest.class);
            personTests = root.personTests;

        } catch (Exception e) {
            System.out.println("Parsing error " + e.toString());
        }
    }
}
