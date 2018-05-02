package localhost.shadow.socketclient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Socket;
import java.util.ArrayList;

///  http://www.fandroid.info/primer-ispolzovaniya-cardview-i-recyclerview-v-android/

public class MainActivity extends Activity {

    Button btnRefresh;
    EditText replayT, paramsT;
    ListView lv;
    ArrayList<String> accz = new ArrayList<>();
    ArrayList<String> detz = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     //   accz.add("Android");
     //   detz.add("4.4");

        CustomList adapter = new CustomList(MainActivity.this, accz, detz);
        lv = (ListView)findViewById(R.id.lv);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "You Clicked at " +accz.get(position), Toast.LENGTH_SHORT).show();

            }
        });


      //  account = (TextView)findViewById(R.id.account);
      //  cli_name = (TextView)findViewById(R.id.name);

       // account.setText("141000000");
       // cli_name.setText("tgdf g dug uf uy odf ");

        final Client client = new Client();
        client.setCommand("SELECT TOP 100 * FROM test;");

        paramsT = (EditText)findViewById(R.id.paramsT);

        View.OnClickListener oclBtnRef = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // client.setCommand("SELECT GETDATE() " + paramsT.getText());
            }
        };
        btnRefresh = (Button) findViewById(R.id.refresh);
        btnRefresh.setOnClickListener(oclBtnRef);

    }




    public class Client {
        String command, data;
        AsyncClient asyncClient;


        public void setCommand(String command) {
            this.command = command;
            asyncClient = new AsyncClient();
            asyncClient.execute();
        }


        public void fillArray(String data){

            this.data = data;
            try {
                JSONObject json = new JSONObject(data);
                /// TODO: parce JSON here
            } catch (JSONException e) {
                e.printStackTrace();
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
                    s = new Socket("192.168.1.29", 11000);

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
                replayT = (EditText)findViewById(R.id.paramsT);
                replayT.setText(data);
            fillArray(data);

            }
        }
    }
}
