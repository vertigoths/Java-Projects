package com.patikadev.patikaspringframework.service;

import com.patikadev.patikaspringframework.data.Building;
import com.patikadev.patikaspringframework.data.House;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildingManager
{
    public int GetTotalPriceOf(List<Building> buildingList)
    {
        int totalPrice = 0;

        for (Building building : buildingList)
        {
            totalPrice += building.getPrice();
        }

        return totalPrice;
    }

    public int GetTotalPriceOf(List<Building> houseList, List<Building> villaList, List<Building> summerhouseList)
    {
        List<Building> combinedList = CombineLists(houseList, villaList, summerhouseList);
        return GetTotalPriceOf(combinedList);
    }

    public float GetAverageSquaresMeter(List<Building> buildingList)
    {
        int totalSquaresMeter = 0;

        for (Building building : buildingList)
        {
            totalSquaresMeter += building.getSquareMeters();
        }

        return totalSquaresMeter / buildingList.size();
    }

    public float GetAverageSquaresMeter(List<Building> houseList, List<Building> villaList, List<Building> summerhouseList)
    {
        List<Building> combinedList = CombineLists(houseList, villaList, summerhouseList);
        return GetAverageSquaresMeter(combinedList);
    }

    public String GetRoomAndHallData(List<Building> houseList, List<Building> villaList, List<Building> summerhouseList)
    {
        List<Building> combinedList = CombineLists(houseList, villaList, summerhouseList);
        return GetRoomAndHallData(combinedList);
    }

    private String GetRoomAndHallData(List<Building> buildingList)
    {
        int totalRooms = 0;
        int totalHalls = 0;

        for (Building building : buildingList)
        {
            totalRooms += building.getRoomCount();
            totalHalls += building.getHallCount();
        }

        return "Total Rooms: " + totalRooms + " | " + "Halls: " + totalHalls;
    }

    private List<Building> CombineLists(List<Building> houseList, List<Building> villaList, List<Building> summerhouseList)
    {
        List<Building> combinedList = new ArrayList<>(houseList);
        combinedList.addAll(villaList);
        combinedList.addAll(summerhouseList);

        return combinedList;
    }
}
