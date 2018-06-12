package com.example.niharika.hospitalmanagementsystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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
import com.loopj.android.http.HttpGet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/*import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
*/

public class Display_patient_SugarLevels_Graph extends AppCompatActivity {

    LineChart linechart;
    String pat_id ="";

    ArrayList<Double> sugar_level = new ArrayList<Double>();
    ArrayList<String>app_date = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_patient_sugar_levels_graph);
        pat_id = getIntent().getStringExtra("id");
    /*    GraphView graphView = (GraphView)findViewById(R.id.displaySugarlevelGraph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
           new DataPoint(0,1),
           new DataPoint(1,5)
        });
    graphView.addSeries(series); */

        try {
            GetSugarLevelAsyncTask asyncTask =  new GetSugarLevelAsyncTask();

            if(Build.VERSION.SDK_INT>=11){
                BlockingQueue<Runnable> runnables = new LinkedBlockingDeque<>(100);
                Executor executor= new ThreadPoolExecutor(60,100,10, TimeUnit.SECONDS,runnables);
                asyncTask.executeOnExecutor(executor);

            }
            else{
                asyncTask.execute();
            }

            Log.e("LATER","LATER");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     private ArrayList<String> setXAxisValues(){
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("5_02_18");
        xVals.add("20_02_18");
        xVals.add("5_03_18");
        xVals.add("15_03_18");
        xVals.add("25_03_18");
         xVals.add("01_04_18");
         xVals.add("11_04_18");
         xVals.add("15_04_18");
         xVals.add("20_04_18");
         xVals.add("25_04_18");



        return xVals;
    }

    private ArrayList<String> setXAxisValues(ArrayList<String> dates){

        ArrayList<String> xVals = new ArrayList<String>();
        for(int i = 0; i<dates.size();i++){
            xVals.add(dates.get(i));
        }


        return xVals;
    }

  private ArrayList<Entry> setYAxisValues(){
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(330, 0));
        yVals.add(new Entry(310, 1));
        yVals.add(new Entry(270, 2));
        yVals.add(new Entry(290, 3));
        yVals.add(new Entry(220, 4));
        yVals.add(new Entry(280, 5));
        yVals.add(new Entry(280, 6));
          yVals.add(new Entry(240, 7));
          yVals.add(new Entry(260, 8));
          yVals.add(new Entry(270, 9));


        return yVals;
    }

    private ArrayList<Entry> setYAxisValues(ArrayList<Double> sugars){
        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for(int i = 0; i<sugars.size();i++){
            yVals.add(new Entry(sugars.get(i).floatValue(), i));
        }
        return yVals;
    }

      private void setData(ArrayList<Double> sugars, ArrayList<String> app_date) {
        ArrayList<String> xVals = setXAxisValues(app_date);

        ArrayList<Entry> yVals = setYAxisValues(sugars);

        LineDataSet set1;

            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "Variations of Sugar levels");

             set1.setFillAlpha(40);
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
          //  set1.setDrawFilled(true);

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(xVals, dataSets);

           // linechart.setViewPortOffsets(1,10,80,90);

          linechart.getXAxis().setLabelsToSkip(0);
          linechart.setVisibleXRangeMaximum(150000f);
            // set data
            linechart.setData(data);
    }

    public class GetSugarLevelAsyncTask extends AsyncTask<String, String, Void> {


        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        final String ur_1 = "http://13.127.188.57:8080/PatientHealthHistoryAPI/webapi/patient_history/all_history/"+pat_id;

        private ProgressDialog progressDialog = new ProgressDialog(Display_patient_SugarLevels_Graph.this);
        InputStream inputStream = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    GetSugarLevelAsyncTask.this.cancel(true);
                }
            });
        }

        //{"doc_id":"1","name":"Dr. John","hospital_id":["123"],"hospital_name":["Mayuri hospital"],"designation":"MD","specialization_id":"1","specialization_name":"ENT","gender":null,"year_join":2010}
        @Override
        protected Void doInBackground(String... strings) {
            try {
                // Set up HTTP post

                HttpClient httpClient = new DefaultHttpClient();

                HttpGet httpGet = new HttpGet(ur_1);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();

                // Read content & Log
                inputStream = httpEntity.getContent();
            } catch (UnsupportedEncodingException e1) {
                Log.e("ENCODING EXCEPTION", e1.toString());
                e1.printStackTrace();
            } catch (ClientProtocolException e2) {
                Log.e("ClientProtocolException", e2.toString());
                e2.printStackTrace();
            } catch (IllegalStateException e3) {
                Log.e("IllegalStateException", e3.toString());
                e3.printStackTrace();
            } catch (IOException e4) {
                Log.e("IOException", e4.toString());
                e4.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }

            try {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line + "\n");

                    Log.e("HEYOUTPUT: ",result);
                }

                inputStream.close();
                result = sBuilder.toString();

            } catch (Exception e) {
                Log.e("ERROR....", "Error converting result " + e.toString());
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            int i;
            try {
                Log.e("OUT:",result);
                JSONArray jArray = new JSONArray(result);
                Log.e("lEN",jArray.length()+"");
                //*JSONArray jArray =new JSONArray();

                //  jArray = new JSONArray(result);

                if(jArray==null) {
                    Log.e("I am null","Null");
                    this.progressDialog.dismiss();
                    return;
                }




                for(i=0; i < jArray.length(); i++) {

                    JSONObject jObject = jArray.getJSONObject(i);


                    Double sugar_level_ = jObject.getDouble("sugarLevel");

                    String date = jObject.getString("date");


                    sugar_level.add(sugar_level_);

                    Log.e("Sugar level",sugar_level_+"");

                    app_date.add(date);

                    Log.e("Date",date);


                }

                this.progressDialog.dismiss();

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
                setData(sugar_level,app_date);

                // get the legend (only possible after setting data)
                Legend l = linechart.getLegend();

                // modify the legend ...
                // l.setPosition(LegendPosition.LEFT_OF_CHART);
                l.setForm(Legend.LegendForm.LINE);

                // no description text
                linechart.setDescription("Sugarlevels variation");
                linechart.setNoDataTextDescription("You need to provide data for the chart.");

                // enable touch gestures
                linechart.setTouchEnabled(true);

                linechart.enableScroll();
                linechart.setHorizontalScrollBarEnabled(true);
                //   linechart.setVerticalScrollbarPosition();
                // enable scaling and dragging
                linechart.setDragEnabled(true);
                linechart.setScaleEnabled(true);
                linechart.setScaleXEnabled(true);
                linechart.setScaleYEnabled(true);

   /*     LimitLine upper_limit = new LimitLine(130f, "Upper Limit");
        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(10f);

        LimitLine lower_limit = new LimitLine(-30f, "Lower Limit");
        lower_limit.setLineWidth(4f);
        lower_limit.enableDashedLine(10f, 10f, 0f);
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        lower_limit.setTextSize(10f);
    */
                YAxis leftAxis = linechart.getAxisLeft();
                leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
                //    leftAxis.addLimitLine(upper_limit);
                //  leftAxis.addLimitLine(lower_limit);
                leftAxis.setAxisMaxValue(400f);
                leftAxis.setAxisMinValue(50f);
                //leftAxis.setYOffset(20f);
                leftAxis.enableGridDashedLine(5f, 3f, 0f);
                leftAxis.setDrawZeroLine(false);

                // limit lines are drawn behind data (and not on top)
                leftAxis.setDrawLimitLinesBehindData(true);

                linechart.getAxisRight().setEnabled(false);

                linechart.getViewPortHandler().setMaximumScaleY(2f);
                linechart.getViewPortHandler().setMaximumScaleX(2f);

                linechart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

                //  dont forget to refresh the drawing
                linechart.invalidate();

        } catch (Exception e) {
                Log.e("JSONException", "Error: " + e.toString());
            } // catch (JSONException e)
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this,DisplayProgressReports.class);
        i.putExtra("id",pat_id);
        startActivity(i);
    }
}

