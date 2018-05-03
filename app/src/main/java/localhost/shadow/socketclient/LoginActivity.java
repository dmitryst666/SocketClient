package localhost.shadow.socketclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    Button btnLogin;
    EditText tryLogin, tryPwd;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

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
                    String account = "user: " + tryLogin.getText() + " pwd: " + tryPwd.getText()  ;
                    Toast.makeText(getApplicationContext(), account, Toast.LENGTH_SHORT).show();
                }


            }
        };

        btnLogin = (Button)findViewById(R.id.loginBtn);
        btnLogin.setOnClickListener(oclLogin);

    }


}
