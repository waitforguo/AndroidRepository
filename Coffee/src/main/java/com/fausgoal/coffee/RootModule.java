package com.fausgoal.coffee;

import android.content.Context;
import android.view.LayoutInflater;

import dagger.Module;
import dagger.Provides;

/**
 * Description：
 * <br/><br/>Created by Fausgoal on 3/28/17.
 * <br/><br/>
 */
@Module(
        injects = {
                GLCoffeeApplication.class
        },
        library = true)
public final class RootModule {
    private final Context mContext;

    public RootModule(Context context) {
        this.mContext = context;
    }

    @Provides
    Context provideApplicationContext() {
        return this.mContext;
    }

    @Provides
    LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(this.mContext);
    }
}
