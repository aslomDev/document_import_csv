package com.uz.service;

import com.uz.entity.CsvEntity;
import com.uz.repository.CsvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class CsvService {

    @Autowired private CsvRepository csvRepository;



    public List<CsvEntity> makeDb(MultipartFile file){

        List<CsvEntity> entityList = new LinkedList<>();
        Set<CsvEntity> entitySet = new HashSet<>();
        Set<CsvEntity> setE = new HashSet<>();
        try {
            String line = "";
            String splitBy = ",";
            InputStreamReader red = new InputStreamReader(file.getInputStream());
            BufferedReader reader = new BufferedReader(red);
            while ((line = reader.readLine()) != null){
                CsvEntity entity = new CsvEntity();
                String[] csv = line.split(splitBy);
                String[] id = parent(csv[0]);
                    entity.setId(csv[0]);
                    System.out.println("id: " + csv[0] + " name: " + csv[1] + " date: " + csv[2]);
                    entity.setName(csv[1]);
                    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm:ssX");
                    Date date = df.parse(csv[2]);
                    entity.setDoc_date(date);
                    entityList.add(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < entityList.size(); i++) {
            int finalI = i;
            entityList.forEach(item -> {
                String child = entityList.get(finalI).getId();
                String[] childId = parent(item.getId());
                String[] parentId = parent(child);
                if (parentId.length >= childId.length){
                    int count = 1;
                    for (int j = 0; j < childId.length; j++) {
                        if (parentId[j].equals(childId[j])){
                            if (childId.length == count){
                                entitySet.add(item);
                            }
                            count++;
                        }else {
                            ///  если таковой имеется отдельний записывается
                            setE.add(item);
                        }
                    }
                }
            });
        }

        entitySet.addAll(setE);

        entitySet.stream()
                .sorted((is1, is2) -> is1.getId().compareTo(is2.getId())).forEach(item -> csvRepository.save(item));

        setE.stream()
                .sorted((is1, is2) -> is1.getId().compareTo(is2.getId())).forEach(System.out::println);

        return getForm();
    }


    private String[] parent(String strings){
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
