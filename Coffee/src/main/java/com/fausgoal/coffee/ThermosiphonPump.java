package com.fausgoal.coffee;

import javax.inject.Inject;

/**
 * Description：
 * <br/><br/>Created by Fausgoal on 3/28/17.
 * <br/><br/>
 */
public class ThermosiphonPump implements Pump {
    private final Heater mHeater;

    @Inject
    ThermosiphonPump(Heater heater) {
        this.mHeater = heater;
    }

    @Override
    public void pump() {
        if (mHeater.isHot()) {
            System.out.println("==> ==> pumping ==> ==>");
        }
    }
}
