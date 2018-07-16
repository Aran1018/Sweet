package com.lucky.sweet.entity;


import com.lucky.sweet.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CirclePicData {

    private  List<Integer> pictureList;

    public static List<CirclePicData> get() {
        List<CirclePicData> circlePicDataList = new ArrayList<>();

        CirclePicData circlePicData1 = new CirclePicData();

        circlePicData1.pictureList = Arrays.asList(
                R.mipmap.test_second,
                R.mipmap.test_one,
                R.mipmap.test_second,
                R.mipmap.test_one

        );

        circlePicDataList.add(circlePicData1);

        return circlePicDataList;
    }

    public  List<Integer> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<Integer> pictureList) {
        this.pictureList = pictureList;
    }

}
