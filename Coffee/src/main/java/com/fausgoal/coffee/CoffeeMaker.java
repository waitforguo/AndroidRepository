package com.fausgoal.coffee;

import javax.inject.Inject;

/**
 * Description：
 * <br/><br/>Created by Fausgoal on 3/28/17.
 * <br/><br/>
 */
public class CoffeeMaker {
    @Inject
//    Lazy<Heater> mHeater; // Don't want to create a possibly costly heater until we need it.
            Heater mHeater;
    @Inject
    Pump mPump;

    public void brew() {
//        mHeater.get().on();
        mHeater.on();
        mPump.pump();
        System.out.println(" [_]P coffee! [_]P ");
//        mHeater.get().off();
        mHeater.off();
    }
}
