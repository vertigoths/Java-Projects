package com.patikadev.patikaspringframework;

import com.patikadev.patikaspringframework.data.Building;
import com.patikadev.patikaspringframework.data.House;
import com.patikadev.patikaspringframework.data.Summerhouse;
import com.patikadev.patikaspringframework.data.Villa;
import com.patikadev.patikaspringframework.service.BuildingManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@SpringBootApplication
public class PatikaSpringFrameworkApplication {

    public static void main(String[] args)
    {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext();
        context.scan("com.patikadev.patikaspringframework");

        context.refresh();

        BuildingManager buildingManager = context.getBean(BuildingManager.class);

        List<Building> houseList = List.of(new House[]{
                new House(500, 50, 3, 1),
                new House(300, 35, 1, 1),
                new House(800, 85, 4, 2),
        });

        List<Building> villaList = List.of(new Villa[]{
                new Villa(1500, 150, 13, 11),
                new Villa(1300, 135, 11, 11),
                new Villa(1800, 185, 14, 12),
        });

        List<Building> summerhouseList = List.of(new Summerhouse[]{
                new Summerhouse(2500, 250, 23, 21),
                new Summerhouse(2300, 235, 21, 21),
                new Summerhouse(2800, 285, 24, 22),
        });

        System.out.println("House prices : " + buildingManager.GetTotalPriceOf(houseList));
        System.out.println("Villa prices : " + buildingManager.GetTotalPriceOf(villaList));
        System.out.println("Summerhouse prices : " + buildingManager.GetTotalPriceOf(summerhouseList));
        System.out.println("Buildings prices : " +  buildingManager.GetTotalPriceOf(houseList, villaList, summerhouseList));

        System.out.println("Average squares meter for Houses : " + buildingManager.GetAverageSquaresMeter(houseList));
        System.out.println("Average squares meter for Villas : " + buildingManager.GetAverageSquaresMeter(villaList));
        System.out.println("Average squares meter for Summerhouses : " + buildingManager.GetAverageSquaresMeter(summerhouseList));
        System.out.println("Average squares meter for Buildings : " + buildingManager.GetAverageSquaresMeter(houseList, villaList, summerhouseList));

        System.out.println(buildingManager.GetRoomAndHallData(houseList, villaList, summerhouseList));

        // SpringApplication.run(PatikaSpringFrameworkApplication.class, args);
    }

}
