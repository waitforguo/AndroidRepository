package com.fausgoal.coffee;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 * Description：
 * <br/><br/>Created by Fausgoal on 3/28/17.
 * <br/><br/>
 */
public class CoffeeApp implements Runnable {
    @Inject
    CoffeeMaker mCoffeeMaker;

    @Override
    public void run() {
        mCoffeeMaker.brew();
    }

    public static void main(String[] args) {
        ObjectGraph objectGraph = ObjectGraph.create(new DripCoffeeModule());
        CoffeeApp coffeeApp = objectGraph.get(CoffeeApp.class);
        coffeeApp.run();
    }
}
