package com.fausgoal.coffee;

import dagger.Module;
import dagger.Provides;

/**
 * Description：
 * <br/><br/>Created by Fausgoal on 3/28/17.
 * <br/><br/>
 */
@Module(complete = false, library = true)
public class PumpModule {
    @Provides
    Pump providePump(ThermosiphonPump pump) {
        return pump;
    }
}
