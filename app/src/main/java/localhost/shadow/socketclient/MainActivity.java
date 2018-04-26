package localhost.shadow.socketclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button btnRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Client client = new Client();
        client.setCommand("SELECT TOP 100 * FROM test;");

        View.OnClickListener oclBtnRef = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                client.setCommand("SELECT GETDATE()");
            }
        };
        btnRefresh = (Button) findViewById(R.id.refresh);
        btnRefresh.setOnClickListener(oclBtnRef);

    }

}
