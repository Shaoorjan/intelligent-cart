package com.company;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.List;

public class DataPlot extends ApplicationFrame {


    public DataPlot(String applicationTitle , String chartTitle, List<List<Double>> dataPoints) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Cycles","Speed(yds/s)",
                createDataset(dataPoints),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane(chartPanel);
    }


    private DefaultCategoryDataset createDataset(List<List<Double>> dataPoints) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (int i =0; i < dataPoints.get(0).size() ; i = i + 1 ){
                dataset.addValue(dataPoints.get(0).get(i), "Speed", i + "");
            }

        return dataset;
    }
}
