package com.fausgoal.coffee;

/**
 * Description：
 * <br/><br/>Created by Fausgoal on 3/28/17.
 * <br/><br/>
 */

public class ElectricHeater implements Heater {
    boolean heating;

    @Override
    public void on() {
        System.out.println("~ ~ ~ heating ~ ~ ~");
        this.heating = true;
    }

    @Override
    public void off() {
        this.heating = false;
    }

    @Override
    public boolean isHot() {
        return heating;
    }
}
