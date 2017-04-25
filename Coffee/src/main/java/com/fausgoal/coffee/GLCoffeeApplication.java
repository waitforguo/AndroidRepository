package com.fausgoal.coffee;

import android.app.Application;

import java.util.List;

import dagger.ObjectGraph;

/**
 * Description：
 * <br/><br/>Created by Fausgoal on 3/28/17.
 * <br/><br/>
 */
public class GLCoffeeApplication extends Application {
    private ObjectGraph mObjectGraph = null;

    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();
    }

    public void inject(Object object) {
        mObjectGraph.inject(object);
    }

    public ObjectGraph plus(List<Object> modules) {
        if (null == modules) {
            throw new IllegalArgumentException("You can't plus a null module");
        }
        return mObjectGraph.plus(modules.toArray());
    }

    private void initInjector() {
        mObjectGraph = ObjectGraph.create(new RootModule(this));
        mObjectGraph.inject(this);
        mObjectGraph.injectStatics();
    }
}
