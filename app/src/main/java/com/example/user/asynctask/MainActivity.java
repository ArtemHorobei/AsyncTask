package com.example.user.asynctask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {
    ImageView image; //экземпляр типа ImageView
    Button runButt; //экземпляр типа Button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //устанавливаем вью из наших лейаутов(мейнактивити)
        image=(ImageView) findViewById(R.id.image1); //инициализируем найденым по айди в разметке компонент ImageView
        runButt=(Button) findViewById(R.id.button); //инициализируем найденым по айди в разметке компонент Button
    }

    String pictUrl = "http://cdn.akamai.steamstatic.com/steam/apps/17460/header.jpg?t=1447351599"; // в стринговую переменную загоняем адрес картинки

    public void runClick(View v){
        new DownloadImageTask().execute(pictUrl);
    } //обработчик события Click, создаем объект класса DownloadImageTask, который наследуется от AsyncTask и выполняем его вызывая метод execute

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) //Метод doInBackGround() выполняется в фоновом потоке, потому доступа к потоку UI внутри данного метода нет.
        {

            String url = "";
            if( params.length > 0 ){
                url = params[0];
            }

            InputStream input = null;
            try {
                URL urlConn = new URL(url);
                input = urlConn.openStream();
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            return BitmapFactory.decodeStream(input);
        }

        @Override
        protected void onProgressUpdate(Void... values) //в данном примере не используется
        {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Bitmap result) //Методы onPostExecute() выполняется в потоке UI, потому мы можем смело обращаться к нашим компонентам UI.
        {
            super.onPostExecute(result);
            image.setImageBitmap(result);
        }
    }
}

