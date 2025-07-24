package com.example.api.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvDataReader {

    public static Object[][] getCSVData(String filePath) {
        List<Object[]> data = new ArrayList<>();
        try (Reader in = new FileReader(filePath);
             CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim())) {

            for (CSVRecord record : parser) {
                // Assuming CSV headers are "title", "body", "userId"
                String title = record.get("title");
                String body = record.get("body");
                // userId is an integer, so parse it
                int userId = Integer.parseInt(record.get("userId"));
                data.add(new Object[]{title, body, userId});
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
            // Optionally, throw a runtime exception to fail the test if the data cannot be read
            throw new RuntimeException("Failed to read CSV data from " + filePath, e);
        }
        return data.toArray(new Object[0][0]);
    }
}