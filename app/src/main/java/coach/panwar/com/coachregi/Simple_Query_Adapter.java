package coach.panwar.com.coachregi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Panwar on 18/09/16.
 */import java.util.ArrayList;
import java.util.HashMap;

public class Simple_Query_Adapter extends BaseAdapter {


    ArrayList<HashMap<String,String>> data =    new ArrayList<HashMap<String, String>>();
    Context cont;
    private static LayoutInflater inflater=null;



    public Simple_Query_Adapter(Context context, ArrayList<HashMap<String,String>> result )
    {
        cont = context;
        data = result;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);



    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        View rowView = inflater.inflate(R.layout.newslistitem, null);
        TextView title, des, date;
        title = (TextView)rowView.findViewById(R.id.newstitle);
        des = (TextView)rowView.findViewById(R.id.newsdes);
        date = (TextView)rowView.findViewById(R.id.newsdate);

        title.setText("Query : "+data.get(i).get("query"));
        des.setText( "Reply : "+data.get(i).get("reply"));
        date.setText("Date : "+data.get(i).get("date"));





        return rowView;
    }
}

