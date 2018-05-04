package localhost.shadow.socketclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Params extends Activity implements OnClickListener {

    EditText paramsIP, paramsUser, paramsPwd;
    Button btnOK;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_params);

        paramsIP = (EditText)findViewById(R.id.paramsIP);
        paramsUser = (EditText)findViewById(R.id.paramsUser);
        paramsPwd = (EditText)findViewById(R.id.paramsPwd);
        btnOK = (Button)findViewById(R.id.btnOK);
        btnOK.setOnClickListener(this);
    }

    @Override
     public void onClick(View v){
        Intent intent = new Intent();
        intent.putExtra("paramsIP", paramsIP.getText().toString());
        intent.putExtra("paramsUser", paramsUser.getText().toString());
        intent.putExtra("paramsPwd", paramsPwd.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }


}
