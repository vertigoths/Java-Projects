package com.patikadev.patikaspringframework.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Building
{
    private int price;

    private int squareMeters;

    private int roomCount;

    private int hallCount;
}
