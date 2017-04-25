package com.fausgoal.coffee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

public class MainActivity extends AppCompatActivity {

    private ObjectGraph mActivityScopeGraph;

    @Inject
    CoffeeMaker mCoffeeMaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GLCoffeeApplication application = (GLCoffeeApplication) getApplication();
        List<Object> modules = new LinkedList<>();
        modules.add(new DripCoffeeModule());
//        modules.add(new PumpModule());
        mActivityScopeGraph = application.plus(modules);
        mActivityScopeGraph.inject(this);

        setContentView(R.layout.activity_main);

        TextView tvHello = (TextView) findViewById(R.id.tvHello);
        tvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCoffeeMaker.brew();
            }
        });
    }
}
