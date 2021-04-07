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

        String csf = "C:\\Users\\Аслом\\Desktop\\csv\\test.csv";
        try {
            String line = "";
            String splitBy = ",";
            InputStreamReader red = new InputStreamReader(file.getInputStream());
            BufferedReader reader = new BufferedReader(red);
            while ((line = reader.readLine()) != null){
                CsvEntity entity = new CsvEntity();
                String[] csv = line.split(splitBy);
                entity.setId(csv[0]);
                entity.setName(csv[1]);
                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm:ssX");
                Date date = df.parse(csv[2]);
                entity.setDoc_date(date);
                System.out.println("id: "+ csv[0] + " name: "+ csv[1] + " date: " + csv[2]);
                entityList.add(entity);
                csvRepository.save(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return entityList;

//
//        CsvEntity csvEntity = null;
//
//        try {
//            InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream());
//            CSVParser csvParser = CSVFormat.EXCEL.withAllowMissingColumnNames().parse(inputStreamReader);
//
//
//            for (CSVRecord record : csvParser){
//                csvEntity = new CsvEntity();
//                if (record.getRecordNumber() == 1){
//                    csvEntity.setId(record.get(0));
//                }else if (record.getRecordNumber() == 2){
//                    csvEntity.setName(record.get((int) record.getRecordNumber()-1));
//                }else if (record.getRecordNumber() == 3){
//                    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm:ssX");
//                    Date date = df.parse(record.get((int) record.getRecordNumber()-1));
//                    csvEntity.setDoc_date(date);
//                }
//                csvRepository.save(csvEntity);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return csvEntity;

    }


    public List<CsvEntity> getForm() {
        return csvRepository.findAll();
    }
}
