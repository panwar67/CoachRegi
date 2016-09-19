package coach.panwar.com.coachregi;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Panwar on 18/09/16.
 */
public class Simple_Gallery_Adapter extends BaseAdapter {


    ArrayList<HashMap<String,String>> data =    new ArrayList<HashMap<String, String>>();
    Context cont;
    private static LayoutInflater inflater=null;
    ImageLoader imageLoader;
    DisplayImageOptions options;




    public Simple_Gallery_Adapter(Context context, ArrayList<HashMap<String,String>> result )
    {
        cont = context;
        data = result;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.EXACTLY).resetViewBeforeLoading(true).build();
        ImageLoaderConfiguration.Builder config1 = new ImageLoaderConfiguration.Builder(context);
        config1.defaultDisplayImageOptions(options);
        config1.threadPriority(Thread.NORM_PRIORITY - 2);
        config1.denyCacheImageMultipleSizesInMemory();
        config1.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config1.diskCacheSize(100 * 1024 * 1024); // 50 MiB
        config1.tasksProcessingOrder(QueueProcessingType.LIFO);
        config1.writeDebugLogs();





        imageLoader = ImageLoader.getInstance();
//        imageLoader.destroy();
        imageLoader.init(config1.build());




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


        View rowView = inflater.inflate(R.layout.galleryitem, null);
        TextView cap,  date;
        ImageView gal;
        gal = (ImageView)rowView.findViewById(R.id.galleryimage);
        //title = (TextView)rowView.findViewById(R.id.newstitle);
        cap = (TextView)rowView.findViewById(R.id.caption);
        date = (TextView)rowView.findViewById(R.id.gallerydate);


        cap.setText("CAPTION : "+data.get(i).get("caption"));
        //date.setText( "DESCRIPTION : "+data.get(i).get("desc"));
        date.setText("DATE : "+data.get(i).get("date"));

        imageLoader.displayImage(data.get(i).get("path"),gal);





        return rowView;
    }
}
