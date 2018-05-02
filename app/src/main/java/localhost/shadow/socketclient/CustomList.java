package localhost.shadow.socketclient;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] account;
    private final String[] name;


    public CustomList(Activity context, String[] account, String[] name){
        super(context, R.layout.item, account);
        this.context = context;
        this.account = account;
        this.name = name;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater  inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item, null, true);
        TextView txtTitle = (TextView)rowView.findViewById(R.id.account) ;
        txtTitle.setText(account[position]);
        TextView txtDet = (TextView)rowView.findViewById(R.id.name);
        txtDet.setText(name[position]);

        return rowView;
    }

}


