package localhost.shadow.socketclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends Activity {

    Button btnLogin;
    EditText tryLogin, tryPwd;
    ProgressBar pBar;
    String delimiter = "|";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //  hide progressbar
        pBar = (ProgressBar)findViewById(R.id.progressBar);
        pBar.setVisibility(View.GONE);

        View.OnClickListener oclLogin = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryLogin = (EditText)findViewById(R.id.trylogin);
                tryPwd =  (EditText)findViewById(R.id.trypass);

                if (tryLogin.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Empty username!", Toast.LENGTH_SHORT).show();
                } else if (tryPwd.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Empty password!", Toast.LENGTH_SHORT).show();
                } else {
                    pBar.setVisibility(View.VISIBLE);
                    String account = "user: " + tryLogin.getText() + " pwd: " + tryPwd.getText()  ;
                    Toast.makeText(getApplicationContext(), account, Toast.LENGTH_SHORT).show();
                    ///// check if user is OK
                    MainActivity.Client client = new MainActivity.Client();
                    client.setCommand("CHECK" + delimiter + tryLogin.getText() + delimiter + tryPwd.getText());

                    pBar.setVisibility(View.GONE);
                }


            }
        };

        btnLogin = (Button)findViewById(R.id.loginBtn);
        btnLogin.setOnClickListener(oclLogin);

    }


}
