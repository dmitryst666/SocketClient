package localhost.shadow.socketclient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.Inet4Address;
import java.net.Socket;

///  http://www.fandroid.info/primer-ispolzovaniya-cardview-i-recyclerview-v-android/

public class MainActivity extends Activity {

    Button btnRefresh;
    EditText replayT, paramsT;
    TextView account, cli_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        account = (TextView)findViewById(R.id.account);
        cli_name = (TextView)findViewById(R.id.name);

        account.setText("141000000");
        cli_name.setText("tgdf g dug uf uy odf ");

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


        public class Person {
            public String Account;
            public String Name;

            public String getAccount() {
                return Account;
            }

            public String getName() {
                return Name;
            }

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
                    s = new Socket("192.168.33.91", 11000);

                    //String msg = "hi server";
                   // s.getOutputStream().write(command.getBytes()); //посылаем команду на сервер
                    s.getOutputStream().write("ok".getBytes()); //говорим серверу что подтверждение полученно

                    byte[] buf = new byte[64 * 1024];
                    int r = s.getInputStream().read(buf);
                    data = new String(buf, 0, r); //строка от сервера


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
              //  replayT = (EditText)findViewById(R.id.replayText);
               // replayT.setText(data);


            }
        }
    }
}
