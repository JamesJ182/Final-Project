/*
The ActorSearchFragment class
By James Jacobson and Phillip Nam
5/3/19
This class lets the user input a persons name, and sends the message to start searching for them
 */

package edu.quinnipiac.ser210.finalproject;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class ActorSearchFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnActorSearchListener mListener;

    public ActorSearchFragment() {
        // Required empty public constructor
    }

    public static ActorSearchFragment newInstance(String param1, String param2) {
        ActorSearchFragment fragment = new ActorSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /*
   When the search button is clicked, the text from the search text (EditText) gets converted
   to a string. That string is then sent to mListener which is responsible for
   passing the string to the AsyncTask (FetchDetails)
    */

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //
        Button showButton=(Button)getView().findViewById(R.id.actor_search_button);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchText = (EditText)getView().findViewById(R.id.actor_search_text);
                String search = searchText.getText().toString();
                mListener.onClickActorSearch(search);
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Fragment layout inflated
        return inflater.inflate(R.layout.fragment_actor_search, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActorSearchListener) {
            mListener = (OnActorSearchListener) context;
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

    //Interface that sends a message that the search for actor button was clicked
    public interface OnActorSearchListener {
        void onClickActorSearch(String actor);
    }
}
