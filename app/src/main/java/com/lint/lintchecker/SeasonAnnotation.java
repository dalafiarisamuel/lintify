package com.lint.lintchecker;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;


public class SeasonAnnotation {

    public void setSeasonCase(@Season int season) {

        switch (season) {

            case Season.FALL:

                System.out.println("Fall");
                break;

            case Season.SPRING:

                System.out.println("Spring");
                break;

            case Season.SUMMER:

                System.out.println("Summer");
                break;

            case Season.WINTER:

                System.out.println("Winter");
                break;
        }
    }


    @IntDef({Season.WINTER, Season.SPRING, Season.SUMMER, Season.FALL})
    @Retention(SOURCE)
    private @interface Season {

        int WINTER = 0;
        int SPRING = 1;
        int SUMMER = 2;
        int FALL = 3;

    }


}
