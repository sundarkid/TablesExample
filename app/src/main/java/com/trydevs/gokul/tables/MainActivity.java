package com.trydevs.gokul.tables;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.trydevs.gokul.tables.R.id.tests;

public class MainActivity extends AppCompatActivity {

    /*  link for the json object example
     *
     *  http://www.jsoneditoronline.org/?id=7aae843f3d2f33925986adf59e216ffc
     *
     */

    String jsonData = "{ \"testName\": [ \"blood test\", \"sugar test\", \"doc checkup\" ], " +
            "\"date\": [ \"29-11-2016\", \"28-11-2016\", \"27-11-2016\" ], " +
            "\"data\": { \"blood test\": [ { \"28-11-2016\": \"100 mg\" }, { \"29-11-2016\": \"100mg\" } ], " +
            "\"sugar test\": [ { \"28-11-2016\": \"120 mg\" }, { \"27-11-2016\": \"130 mg\" } ], " +
            "\"doc checkup\": [ { \"29-10-2016\": \"100\" }, { \"27-10-2016\": \"120mg\" } ] } }";

    TableLayout tableLayoutTests, tableLayoutResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mapping the views to the objects
        tableLayoutResults = (TableLayout) findViewById(R.id.results);
        tableLayoutTests = (TableLayout) findViewById(tests);

        try {
            // Calling the function with the data and the table objects
            start(jsonData, tableLayoutTests, tableLayoutResults, MainActivity.this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Function that does the desired functions
    private void start(String jsonData, TableLayout tableLayoutTests, TableLayout tableLayoutResults, Context context) throws JSONException {
        // Parsing the json data
        JSONObject object = new JSONObject(jsonData);
        JSONArray dates = object.getJSONArray("date");
        JSONArray testNames = object.getJSONArray("testName");
        JSONObject data = object.getJSONObject("data");
        /* creating the tests table body */
        // Creating the params
        LinearLayout.LayoutParams t = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams r = new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        // First cell date
        TextView cell = new TextView(context);
        cell.setText("Date");
        cell.setLayoutParams(r);
        cell.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
        TableRow row = new TableRow(context);
        row.addView(cell);
        tableLayoutTests.addView(row, t);
        // populating the tests table with the tests names
        for (int i = 0; i < testNames.length(); i++) {
            cell = new TextView(context);
            cell.setLayoutParams(r);
            cell.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
            cell.setText(testNames.getString(i));
            row = new TableRow(context);
            row.setLayoutParams(t);
            row.addView(cell);
            tableLayoutTests.addView(row);
        }
        // Populating the dates in the results
        row = new TableRow(context);
        for(int i = 0; i < dates.length(); i++){
            cell = new TextView(context);
            cell.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
            cell.setText(dates.getString(i));
            cell.setLayoutParams(r);
            row.addView(cell);
        }
        tableLayoutResults.addView(row);
        // Populating the test results
        for (int i = 0; i < testNames.length(); i++){
            row = new TableRow(context);
            row.setLayoutParams(t);
            JSONArray testResults =  data.getJSONArray(testNames.getString(i));
            int k = 0;
            for(int j = 0; j < dates.length(); j++){
                cell = new TextView(context);
                cell.setLayoutParams(r);
                cell.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
                if(testResults.getJSONObject(k).has(dates.getString(j))) {
                    cell.setText(testResults.getJSONObject(k).getString(dates.getString(j)));
                    k++;
                }else {
                    cell.setText("-");
                }
                row.addView(cell);
            }
            tableLayoutResults.addView(row);
        }
    }
}
