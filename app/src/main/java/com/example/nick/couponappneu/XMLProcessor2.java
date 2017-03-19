package com.example.nick.couponappneu;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Nick on 15.03.2017.
 */

public class XMLProcessor2 extends AsyncTask<String, Void, String> {

    private String xmlUrl;
    private PostParserDelegate delegate;
    private ArrayList<String> bilderUrls;
    private StringBuilder buffer;
    private static final String ns = null;


    public XMLProcessor2(String xmlUrl, PostParserDelegate delegate) {
        this.xmlUrl = xmlUrl;
        this.delegate = delegate;
        bilderUrls = new ArrayList<String>();
    }

    //herunterladen XML
    @Override
    protected String doInBackground(String... params) {
        buffer = new StringBuilder();
        try {
            URL url = new URL(xmlUrl);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

            int httpsRespnse = httpsURLConnection.getResponseCode();
            if (httpsRespnse != 200) {
                throw new Exception("HTTPFehlercode: " + httpsRespnse);
            }

            InputStream input = httpsURLConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);

            int charactersRead;
            char[] tmpChars = new char[400];
            while (true) {
                charactersRead = reader.read(tmpChars); //anzahl der gelesenen chars kommt zurück
                if (charactersRead <= 0) {
                    break;
                }
                buffer.append(String.copyValueOf(tmpChars, 0, charactersRead));
            }

            return buffer.toString();


        } catch (Exception e) {
            Log.e("XMLProcessor", e.getMessage());
            Log.e("XMLProcessor", e.getStackTrace().toString());
        }


        return null;
    }

    //Dokument verarbeiten
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        parse();
    }

    //SAX-Parser einabeun
    protected void parse() {

        String rawXML = buffer.toString();


        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(rawXML));


            delegate.xmlFeedParsed(readFeed(parser));

        } catch (Exception e) {
            Log.e("XMLProcessor", e.getStackTrace().toString());
        }

    }

    private ArrayList<String> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<String> entries = new ArrayList<String>();
        parser.nextTag();
        //   parser.require(XmlPullParser.START_TAG, null, null);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equalsIgnoreCase("link")) {
    /*            parser.next();
                String link = parser.getText();
                 //parser.gettext versuchen oder readEntry(parser) ODER GETVALUE!!!
                entries.add(link);
                */

                parser.next();
                entries.add(parser.getText());
                parser.next();

            } else {
                skip(parser);
            }
        }
        return entries;
    }
/*
    private String readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, null);
        String link = "lalalala";
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equalsIgnoreCase("link")) {
                parser.next();
                link = parser.getText();
            } else {
                skip(parser);
            }
        }
        return link;
    }
*/
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
