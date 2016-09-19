package coach.panwar.com.coachregi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Panwar on 18/09/16.
 */
public class Team_detail_Adapter extends BaseAdapter
{


    ArrayList<String> data =    new ArrayList<String>();
    Context cont;
    private static LayoutInflater inflater=null;



    public Team_detail_Adapter(Context context, ArrayList<String> result )
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


        View rowView = inflater.inflate(R.layout.eventitemdetail, null);
        TextView title, des, date;
        title = (TextView)rowView.findViewById(R.id.teamdetailitem);

        title.setText(data.get(i));





        return rowView;
    }


}