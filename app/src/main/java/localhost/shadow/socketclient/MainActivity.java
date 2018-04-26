package localhost.shadow.socketclient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.Socket;

public class MainActivity extends Activity {

    Button btnRefresh;
    EditText replayT, paramsT;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Client client = new Client();
        client.setCommand("SELECT TOP 100 * FROM test;");

        paramsT = (EditText)findViewById(R.id.paramsT);

        View.OnClickListener oclBtnRef = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                client.setCommand("SELECT GETDATE() " + paramsT.getText());
            }
        };
        btnRefresh = (Button) findViewById(R.id.refresh);
        btnRefresh.setOnClickListener(oclBtnRef);

    }

    public class Client {
        public  String command;
        AsyncClient asyncClient;


        public void setCommand(String command) {
            this.command = command;
            asyncClient = new AsyncClient();
            asyncClient.execute();
        }

        ////////////////////////////////////////////////////////
        class AsyncClient extends AsyncTask<Void, Void, Void> {

            Socket s;
            public String data;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    s = new Socket("192.168.33.89", 11000);
                    //String msg = "hi server";
                    s.getOutputStream().write(command.getBytes()); //посылаем команду на сервер
                    byte[] buf = new byte[64 * 1024];
                    int r = s.getInputStream().read(buf);
                    data = new String(buf, 0, r); //строка от сервера

                    Log.d("test", "Сообщение сервера: " + data);


             /*       buf = new byte[500 * 1024];
                    s.getOutputStream().write("ok".getBytes()); //говорим серверу что подтверждение полученно

                    r = s.getInputStream().read(buf);
                    //r = s.getInputStream().read();
                    String xml = new String(buf, 0, r); // принимаем большие данные
                    Log.d("test", xml);

            */
                    s.close();
                    s = null;
                } catch (Exception e) {
                    Log.d("test", "Произошла ошибка: " + e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void resuld) {

                super.onPostExecute(resuld);
                replayT = (EditText)findViewById(R.id.replayText);
                replayT.setText(data);
            }
        }
    }
}
