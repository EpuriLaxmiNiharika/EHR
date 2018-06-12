package com.example.niharika.hospitalmanagementsystem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
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
import java.util.ArrayList;
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

public class Display_patient_WeightLevels_Graph extends AppCompatActivity {


    LineChart linechart;
    String pat_id ="";

    ArrayList<Integer> weight_level = new ArrayList<Integer>();
    ArrayList<String>app_date = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_patient__weightlevels__graph);

        pat_id = getIntent().getStringExtra("id");
    /*    GraphView graphView = (GraphView)findViewById(R.id.displayWeightlevelGraph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
           new DataPoint(0,1),
           new DataPoint(1,5)
        });
    graphView.addSeries(series); */

        try {
            GetBPLevelAsyncTask asyncTask =  new GetBPLevelAsyncTask();

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

    private ArrayList<String> setXAxisValues(ArrayList<String> dates){

        ArrayList<String> xVals = new ArrayList<String>();
        for(int i = 0; i<dates.size();i++){
            xVals.add(dates.get(i));
        }


        return xVals;
    }

    private ArrayList<Entry> setYAxisValues(ArrayList<Integer> weights){
        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for(int i = 0; i<weights.size();i++){
            yVals.add(new Entry(weights.get(i), i));
        }
        return yVals;
    }

    private void setData(ArrayList<Integer> weights, ArrayList<String> app_date) {
        ArrayList<String> xVals = setXAxisValues(app_date);

        ArrayList<Entry> yVals = setYAxisValues(weights);

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "Variations of Weight levels");

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

    public class GetBPLevelAsyncTask extends AsyncTask<String, String, Void> {


        ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

        final String ur_1 = "http://13.127.188.57:8080/PatientHealthHistoryAPI/webapi/patient_history/all_history/"+pat_id;

        private ProgressDialog progressDialog = new ProgressDialog(Display_patient_WeightLevels_Graph.this);
        InputStream inputStream = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Downloading your data...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    GetBPLevelAsyncTask.this.cancel(true);
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


                    int weight_level_ = jObject.getInt("weight");

                    String date = jObject.getString("date");


                    weight_level.add(weight_level_);

                    Log.e("Weight level",weight_level_+"");

                    app_date.add(date);

                    Log.e("Date",date);


                }

                this.progressDialog.dismiss();

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

                linechart = (LineChart) findViewById(R.id.line_chart_weight_level);

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
                        System.out.println("double tapped ");
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
                setData(weight_level,app_date);

                // get the legend (only possible after setting data)
                Legend l = linechart.getLegend();

                // modify the legend ...
                // l.setPosition(LegendPosition.LEFT_OF_CHART);
                l.setForm(Legend.LegendForm.LINE);

                // no description text
                linechart.setDescription("Weight levels variation");
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


                YAxis leftAxis = linechart.getAxisLeft();
                leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
                //    leftAxis.addLimitLine(upper_limit);
                //  leftAxis.addLimitLine(lower_limit);
                leftAxis.setAxisMaxValue(200f);
                leftAxis.setAxisMinValue(10f);
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
