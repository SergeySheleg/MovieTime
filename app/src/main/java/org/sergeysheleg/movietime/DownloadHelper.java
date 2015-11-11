package org.sergeysheleg.movietime;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadHelper extends AsyncTask<String, Void, Boolean> {
    static enum Type {
        String,
        JSONObject,
        Bitmap
    };

    private Type type;

    private String stringResult = null;
    private JSONObject jsonResult = null;
    private Bitmap bitmapResult = null;

    public String getString() {
        return stringResult;
    }
    public JSONObject getJSONObject() {
        return jsonResult;
    }
    public Bitmap getBitmap() {
        return bitmapResult;
    }

    public DownloadHelper setType(Type type) {
        this.type = type;
        return this;
    }

    @Override
    protected Boolean doInBackground(String... urls) {
        try {
            downloadUrl(urls[0]);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    protected Boolean downloadUrl(String urlToDownload) throws IOException {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(urlToDownload);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);

            inputStream = httpURLConnection.getInputStream();
            switch (type) {
                case String: {
                    stringResult = toString(inputStream, 1024);
                } break;

                case JSONObject: {
                    jsonResult = toJSONObject(inputStream);
                } break;

                case Bitmap: {
                    bitmapResult = toBitmap(inputStream, 16384);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if(httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return true;
    }

    private String toString(InputStream inputStream, int length) throws IOException, UnsupportedEncodingException {
        Reader reader;
        reader = new InputStreamReader(inputStream, "UTF-8");
        char[] buffer = new char[length];
        reader.read(buffer);
        return new String(buffer);
    }
    private JSONObject toJSONObject(InputStream inputStream) throws IOException, JSONException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();

        String inputSting;
        while ((inputSting = bufferedReader.readLine()) != null) {
            stringBuilder.append(inputSting);
        }
        return new JSONObject(stringBuilder.toString());
    }
    private Bitmap toBitmap(InputStream inputStream, int length) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[length];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return BitmapFactory.decodeByteArray(buffer.toByteArray(), 0, buffer.toByteArray().length);
    }
}

