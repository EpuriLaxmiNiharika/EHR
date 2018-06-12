package com.example.niharika.hospitalmanagementsystem;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

/*import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
*/

public class DisplayGraph extends AppCompatActivity {

    LineChart linechart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_patient_sugar_levels_graph);

    /*    GraphView graphView = (GraphView)findViewById(R.id.displaySugarlevelGraph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
           new DataPoint(0,1),
           new DataPoint(1,5)
        });
    graphView.addSeries(series); */

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        linechart = (LineChart) findViewById(R.id.line_chart_sugar_level);

        linechart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                System.out.println("Gesture Startx: " + me.getX());
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                System.out.println("Last performed Gesture: " + lastPerformedGesture);
                if(lastPerformedGesture!=ChartTouchListener.ChartGesture.SINGLE_TAP){
                    linechart.highlightValues(null);
                }

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {
                System.out.println("Chart long pressed ");
            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {
                System.out.println("Double tapped ");
            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {
                System.out.println("Single tapped ");
            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
                System.out.println("Fling "+velocityX + "--"+velocityY);
            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
                System.out.println("Scale zoon "+scaleX + "== "+scaleY);
            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {
                System.out.println("Translate / Move"+ "dX: " + dX + " + dY: " + dY);
            }
        });


        linechart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                System.out.println("Entry selected"+ e.toString());
                System.out.println("LOWHIGH"+ "low: " + linechart.getLowestVisibleXIndex()
                        + " high: " + linechart.getHighestVisibleXIndex());

                System.out.println("MIN MAX"+ "xmin: " + linechart.getXChartMin()
                        + " xmax: " + linechart.getXChartMax()
                        + " ymin: " + linechart.getYChartMin()
                        + " ymax: " + linechart.getYChartMax());
            }

            @Override
            public void onNothingSelected() {
                System.out.println("Nothing selected"+ "Nothing selected.");
            }
        });


        linechart.setDrawGridBackground(false);

        // add data
        setData();

        // get the legend (only possible after setting data)
        Legend l = linechart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        // no description text
        linechart.setDescription("Demo Line Chart");
        linechart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        linechart.setTouchEnabled(true);

        // enable scaling and dragging
        linechart.setDragEnabled(true);
        linechart.setScaleEnabled(true);
        // linechart.setScaleXEnabled(true);
        // linechart.setScaleYEnabled(true);

        LimitLine upper_limit = new LimitLine(130f, "Upper Limit");
        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(10f);

        LimitLine lower_limit = new LimitLine(-30f, "Lower Limit");
        lower_limit.setLineWidth(4f);
        lower_limit.enableDashedLine(10f, 10f, 0f);
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        lower_limit.setTextSize(10f);

        YAxis leftAxis = linechart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(upper_limit);
        leftAxis.addLimitLine(lower_limit);
        leftAxis.setAxisMaxValue(220f);
        leftAxis.setAxisMinValue(-50f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        linechart.getAxisRight().setEnabled(false);

        //linechart.getViewPortHandler().setMaximumScaleY(2f);
        //linechart.getViewPortHandler().setMaximumScaleX(2f);

        linechart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        //  dont forget to refresh the drawing
        linechart.invalidate();


    }

    private ArrayList<String> setXAxisValues(){
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("10");
        xVals.add("20");
        xVals.add("30");
        xVals.add("30.5");
        xVals.add("40");

        return xVals;
    }

    private ArrayList<Entry> setYAxisValues(){
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(60, 0));
        yVals.add(new Entry(48, 1));
        yVals.add(new Entry(70.5f, 2));
        yVals.add(new Entry(100, 3));
        yVals.add(new Entry(180.9f, 4));

        return yVals;
    }

    private void setData() {
        ArrayList<String> xVals = setXAxisValues();

        ArrayList<Entry> yVals = setYAxisValues();

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "DataSet 1");

        set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        //   set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        linechart.setData(data);
    }

}

