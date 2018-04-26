package localhost.shadow.socketclient;

import android.os.AsyncTask;
import android.util.Log;
import java.net.Socket;

public class Client {
    public static String command;
    AsyncClient asyncClient;

    public void setCommand(String command) {
        this.command = command;
        asyncClient = new AsyncClient();
        asyncClient.execute();
    }

    ////////////////////////////////////////////////////////
    class AsyncClient extends AsyncTask<Void, Void, Void> {

        Socket s;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                s = new Socket("127.0.0.1",11000);
                //String msg = "hi server";
                s.getOutputStream().write(command.getBytes()); //посылаем команду на сервер
                byte[] buf = new byte[64*1024];
                int r = s.getInputStream().read(buf);
                String data = new String(buf,0,r); //строка от сервера

                Log.d("test", "Сообщение сервера: " + data);

                buf = new byte[500*1024];
                s.getOutputStream().write("ok".getBytes()); //говорим серверу что подтверждение полученно

                r = s.getInputStream().read(buf);
                //r = s.getInputStream().read();
                String xml = new String(buf,0,r); // принимаем большие данные
                Log.d("test",xml);


                s.close();
                s = null;
            }catch (Exception e){
                Log.d("test","Произошла ошибка: "+e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void resuld){
            super.onPostExecute(resuld);
        }
    }

}
