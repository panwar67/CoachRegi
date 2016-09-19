package coach.panwar.com.coachregi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubmitQueryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubmitQueryFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SubmitQueryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DBHelper dbHelper;
    TextView query, date;
    Button submit;
    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    ListView listView;
    SessionManager sessionManager;
    String UPLOAD_URL="http://www.whydoweplay.com/CoachData/insertquery.php";
    String uid, dates;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubmitQueryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubmitQueryFragment newInstance(String param1, String param2) {
        SubmitQueryFragment fragment = new SubmitQueryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public SubmitQueryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        sessionManager = new SessionManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_submit_query, container, false);

        dbHelper = new DBHelper(getContext());



        query = (TextView)root.findViewById(R.id.queryname);
        date = (TextView)root.findViewById(R.id.querydate);
        submit = (Button) root.findViewById(R.id.submitquery);


         uid = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
         dates = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        date.setText(dates);



        dbHelper.InsertQuery(uid,sessionManager.getUserDetails().get("uid"),query.getText().toString(),"Under Review", date.getText().toString());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SignUpCredentials(query.getText().toString(),"Under Review", uid, sessionManager.getUserDetails().get("uid"), date.getText().toString());

            }
        });




        return root;
    }



    public boolean SignUpCredentials(final String query, final String reply, final String uid, final String useruid, final String date){

        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(getContext(),"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        loading.dismiss();

                        if(s!=null){

                            Toast.makeText(getActivity(), s , Toast.LENGTH_LONG).show();


                        }
                        else                             Toast.makeText(getActivity(), "Please Try Again Later" , Toast.LENGTH_LONG).show();

                        //Disimissing the progress dialog
                        //Showing toast message of the response
                         }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(getActivity(), "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<>();
                Keyvalue.put("query",query);
                Log.d("reply", reply);
                Keyvalue.put("reply",reply);
                Keyvalue.put("date",date);
                Keyvalue.put("uid", uid);
                Keyvalue.put("useruid",useruid);

               // Log.d("pass", pass);


                //returning parameters
                return Keyvalue;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());



        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding request to the queue
        requestQueue.add(stringRequest);






        return false;
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
