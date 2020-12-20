package com.company;


import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String path = System.getProperty("user.dir");

//      Load from 'FCL' file
        String fileName = path + "\\src\\com\\company\\rules\\rules.fcl";
        FIS fis = FIS.load(fileName,true);

//      Error while loading?
        if( fis == null ) {
            System.err.println("Can't load file: '" + fileName + "'");
            return;
        }


//      Show
        FunctionBlock fb = fis.getFunctionBlock(null);
        JFuzzyChart.get().chart(fb);



        Variable acceleration = fb.getVariable("acceleration");
        Variable steeringAngle = fb.getVariable("steering_angle");

        dataPoint(fis);


//      initial values
        double accel = 0;
        double steering_angle = 0;
        double ball_dist = 250;
        double error_angle = -45;
        double speed = 0.005;
        double tree_angle = 10;
        double tree_dist = 1000;
        DecimalFormat df = new DecimalFormat("#.####");

        List<Double> speedPoints = new ArrayList<>();
        List<Double> ball_dist_points = new ArrayList<>();
        List<Double> error_angle_points = new ArrayList<>();
        List<Double> accel_points = new ArrayList<>();
        List<Double> steering_angle_points = new ArrayList<>();
        List<Double> tree_dist_points = new ArrayList<>();
        List<Double> tree_angle_points = new ArrayList<>();

        List<List<Double>> dataPoints = new ArrayList<>();

        int i = 0;


        while( i < 100){

//          Set New Inputs
            fb.setVariable("ball_dist", ball_dist);
            fb.setVariable("error_angle", error_angle);
            fb.setVariable("speed", speed);
            fb.setVariable("tree_angle",tree_angle);
            fb.setVariable("tree_dist", tree_dist);

//          Evaluate
            fis.evaluate();

//          get acceleration
            acceleration.defuzzify();
            accel = fb.getVariable("acceleration").getValue();
            accel_points.add(Double.parseDouble(df.format(accel)));

//          get steering angle
            steeringAngle.defuzzify();
            steering_angle = Double.parseDouble(df.format(fb.getVariable("steering_angle").getValue()));
            steering_angle_points.add(steering_angle);

            speed = Double.parseDouble(df.format(((speed + (accel * speed)) < 5)? speed + (accel * speed):5));
//            speed = (speed <= 0)? speed :  Math.abs(speed+accel);
            speedPoints.add(speed);

            ball_dist = Double.parseDouble(df.format(ball_dist - speed));
            ball_dist_points.add(ball_dist);

            error_angle =  Double.parseDouble(df.format(error_angle + steering_angle));
            error_angle_points.add(error_angle);

            tree_angle =  Double.parseDouble(df.format(tree_angle + steering_angle));
            tree_angle_points.add(tree_angle);

            tree_dist =  Double.parseDouble(df.format(tree_dist - speed));
            tree_dist_points.add(tree_dist);

            dataPoints.add(speedPoints);
            dataPoints.add(ball_dist_points);
            dataPoints.add(error_angle_points);
            dataPoints.add(accel_points);
            dataPoints.add(steering_angle_points);
            dataPoints.add(tree_angle_points);
            dataPoints.add(tree_dist_points);

            i++;
        }

        printValues(dataPoints);

        DataPlot chart = new DataPlot("Speed" , "", dataPoints);
        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible(true);


    }

    public static void dataPoint(FIS fis ){

        FunctionBlock fb = fis.getFunctionBlock(null);

        fb.setVariable("ball_dist", 28.5);
        fb.setVariable("error_angle", -45);
        fb.setVariable("speed",4.5);
        fb.setVariable("tree_angle",0);
        fb.setVariable("tree_dist", 500);

        fis.evaluate();

        Variable acceleration = fb.getVariable("acceleration");
        Variable steeringAngle = fb.getVariable("steering_angle");

        JFuzzyChart.get().chart(acceleration, acceleration.getDefuzzifier(), true);

    }


    public static void printValues(List<List<Double>> dataPoints){
        System.out.println("---- Input Values ---- "  );
        for (int i = 0 ; i < dataPoints.get(0).size() ; i++){
            System.out.print("  Speed: "  + dataPoints.get(0).get(i));
            System.out.print("  Ball Distance: " + dataPoints.get(1).get(i));
            System.out.print("  Error Angle: " + dataPoints.get(2).get(i));
            System.out.print("  Acceleration: " +  dataPoints.get(3).get(i));
            System.out.print("  Steering Angle: " + dataPoints.get(4).get(i));
            System.out.print("  Tree Angle: " +  dataPoints.get(5).get(i));
            System.out.print("  Tree Dist: " + dataPoints.get(6).get(i));
            System.out.print("\n" );

        }
    }

}
