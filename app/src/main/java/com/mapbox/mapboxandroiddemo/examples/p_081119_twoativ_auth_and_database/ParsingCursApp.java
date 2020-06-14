package com.mapbox.mapboxandroiddemo.examples.p_081119_twoativ_auth_and_database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ParsingCursApp extends AppCompatActivity {

    private Document doc;
    private Thread secThread;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsing_curs_app);

        init();


    }

    private void init(){
        runnable= new Runnable() {
            @Override
            public void run() {
                getWeb();

            }
        };
        secThread=new Thread(runnable);
        secThread.start();
    }

    private void getWeb(){
        try {
            doc=Jsoup.connect("https://www.kja.aero/page-online-tablo/?day=3").get();
            //Elements table = doc.getElementsByTag("boardList");
            Elements table = doc.getElementsByTag("item");
            Elements ddd = doc.getElementsByClass("overscrolled-header");
//            Element our_table=table.get(0);
//            Elements text=our_table.children();
//            Log.d("MyLog","Title: "+doc.title());
//            Log.d("MyLog","boardList size: "+table.size());
//            Log.d("MyLog","text: "+text);

            Log.d("MyLog","Title: "+ddd.text());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
