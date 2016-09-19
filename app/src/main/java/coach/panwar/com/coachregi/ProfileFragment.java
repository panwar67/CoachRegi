package coach.panwar.com.coachregi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    HashMap<String,String> datamap = new HashMap<String, String>();
    ImageLoader imageLoader;
    DisplayImageOptions options;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView dp;
    DBHelper dbHelper;
    SessionManager sessionManager;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Check();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        dbHelper = new DBHelper(getContext());
        sessionManager = new SessionManager(getContext());
        options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.EXACTLY).resetViewBeforeLoading(true).build();
        ImageLoaderConfiguration.Builder config1 = new ImageLoaderConfiguration.Builder(getContext());
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_profile, container, false);


        dp = (ImageView)root.findViewById(R.id.displayimage);
        datamap = dbHelper.GetProfileMap(sessionManager.getUserDetails().get("uid"));

        TextView name, place, batting, bowling, age;

        name = (TextView)root.findViewById(R.id.profilename);
        place = (TextView)root.findViewById(R.id.profileplace);
        batting = (TextView)root.findViewById(R.id.profilebat);
        bowling = (TextView)root.findViewById(R.id.profilebowl);

        age = (TextView)root.findViewById(R.id.profileage);

        imageLoader.displayImage(datamap.get("path"),dp);

        name.setText(datamap.get("name").toString());
        place.setText("PLACE "+ datamap.get("place").toString());
        batting.setText("BAT HAND "+ datamap.get("batting").toString());
        bowling.setText("BOWL HAND "+datamap.get("bowl").toString());
        age.setText(getDateDifference(datamap.get("dob")));



        return root;
    }


    public Boolean Check() {
        ConnectivityManager cn = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();
        if (nf != null && nf.isConnected() == true) {
            return true;
        } else {

            Toast.makeText(getContext(), "No internet connection.!",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(getActivity(), NoConnectionActivity.class));


            return false;
        }
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public String getCurrentDate(){

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return  df.format(Calendar.getInstance().getTime());
    }

    public String getDateDifference(String dob){

        String n = null;
        String dateOfBirth = dob;
        String dateCurrent = getCurrentDate();

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateOfBirth);
            d2 = format.parse(dateCurrent);


            //in milliseconds

            long diff = d2.getTime() - d1.getTime();

            int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
            int age = diffDays/365;
            int thres = age/4;

            int days=diffDays%365;
            days = days-thres;
            n = age+" Years & days "+days;
            Log.d("diff", n);


        } catch (Exception e) {
            e.printStackTrace();
        }



        return n;
    }



}
