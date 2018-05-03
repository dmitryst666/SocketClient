package localhost.shadow.socketclient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
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

    final int MENU_DETAILS = 1;
    final int MENU_EDIT = 2;
    final int MENU_REMOVE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CustomList adapter = new CustomList(MainActivity.this, accz, detz);
        lv = (ListView)findViewById(R.id.lv);
        lv.setAdapter(adapter);
/*
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "You Clicked at " +accz.get(position), Toast.LENGTH_SHORT).show();

            }
        });
*/
    //    final Client client = new Client();
    //    client.setCommand("SELECT TOP 100 * FROM test;");
        onClick(null);

        paramsT = (EditText)findViewById(R.id.paramsT);

     View.OnClickListener oclBtnRef = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          //      client.setCommand("SELECT GETDATE() " + paramsT.getText());
            }
        };

        btnRefresh = (Button) findViewById(R.id.refresh);
        btnRefresh.setOnClickListener(oclBtnRef);

        registerForContextMenu(lv);


    }

////  new added
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

        menu.add(0, MENU_DETAILS, 0, "Details");
        menu.add(1, MENU_EDIT,0,"Edit");
        menu.add(1, MENU_REMOVE, 0, "Remove");

    }

    public boolean onContextItemSelected(MenuItem item){

        return super.onContextItemSelected(item);
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

            //this.data = data;
           // Log.d("JSON", data);
            try {
               // JSONObject json = new JSONObject(data);
                JSONArray json = new JSONArray(data);
                int i = 0;
                while (i < json.length()) {
                    //String acc = json.getString(0);
                    JSONObject jObj = json.getJSONObject(i);
                    String acc = jObj.getString("Account");
                    accz.add(acc);
                    String nam = jObj.getString("Name");
                    detz.add(nam);
                    //Log.d("JSON", acc + " = = = "+ nam);

                    i++;
                }


            } catch (JSONException e) {
                Log.e("JSON", e.getLocalizedMessage());
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
                //replayT = (EditText)findViewById(R.id.paramsT);
                //replayT.setText(data);

            fillArray(data);

            }
        }
    }
}
