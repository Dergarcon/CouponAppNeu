package com.example.nick.couponappneu;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PostParserDelegate {

    ListView listView;
    String xmlUrl;
    public static String[] eatFoodyImages;
    Context context;


    String test;
    //GitHubTest



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.liste);
        xmlUrl = "https://www.hidrive.strato.com/wget/hoSpizp9";

        XMLProcessor2 processor = new XMLProcessor2(xmlUrl, this);
        processor.execute();



    }

    @Override
    public void xmlFeedParsed(ArrayList<String> bilderUrls) {
        String[] bilderArray = new String[bilderUrls.size()];

        for (int i=0; i<bilderUrls.size(); i++){
            bilderArray[i] = bilderUrls.get(i);
        }
        eatFoodyImages = bilderArray;
        listView.setAdapter(new ImageListAdapter(this, eatFoodyImages));
        Toast.makeText(this, bilderArray[0].toString(), Toast.LENGTH_LONG).show();
    }
}
