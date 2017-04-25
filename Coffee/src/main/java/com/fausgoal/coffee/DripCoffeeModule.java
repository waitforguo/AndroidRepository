package com.fausgoal.coffee;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Description：
 * <br/><br/>Created by Fausgoal on 3/28/17.
 * <br/><br/>
 */
@Module(
        injects = {
                MainActivity.class
        },
        includes = {
                PumpModule.class
        },
        complete = false)
public class DripCoffeeModule {

    @Provides
    @Singleton
    Heater provideHeater() {
        return new ElectricHeater();
    }
}
