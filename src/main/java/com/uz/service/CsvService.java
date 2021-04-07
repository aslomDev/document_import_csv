package com.uz.service;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.uz.entity.CsvEntity;
import com.uz.repository.CsvRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Service
public class CsvService {

    @Autowired private CsvRepository csvRepository;



    public List<CsvEntity> makeDb(MultipartFile file){

        List<CsvEntity> entityList = new LinkedList<>();
        try {
            String line = "";
            String splitBy = ",";
            String zero = ".";
            InputStreamReader red = new InputStreamReader(file.getInputStream());
            BufferedReader reader = new BufferedReader(red);
            while ((line = reader.readLine()) != null){
                CsvEntity entity = new CsvEntity();
                String[] csv = line.split(splitBy);
                String[] id = getColumn(csv[0]);
                if (id.length > 0){
                    for (int i = 0; i < id.length; i++) {
                        CsvEntity multIdentity = new CsvEntity();
                        multIdentity.setId(id[i]);
                        multIdentity.setName(csv[1]);
                        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm:ssX");
                        Date date = df.parse(csv[2]);
                        multIdentity.setDoc_date(date);
                        entityList.add(multIdentity);
                        csvRepository.save(multIdentity);
                    }
                }else {
                    entity.setId(csv[0]);
                    System.out.println("id: " + csv[0] + " name: " + csv[1] + " date: " + csv[2]);
                    entity.setName(csv[1]);
                    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm:ssX");
                    Date date = df.parse(csv[2]);
                    entity.setDoc_date(date);
                    entityList.add(entity);
                    csvRepository.save(entity);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return entityList;
    }

    private String[] getColumn(String strings){
        String zero = ",";
        String column = strings.replace(".", ",");
        String[] id = column.split(zero);
        return id;
    }


    public List<CsvEntity> getForm() {
        return csvRepository.findAll();
    }

    public List<CsvEntity> orderById() {
        return csvRepository.findByOrderById();
    }


    public void delete() {
        csvRepository.deleteAll();
    }
}
