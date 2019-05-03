/*
The ShowSearchFragment class
By James Jacobson and Phillip Nam
5/3/19
This class lets the user input a shows name, and sends the message to start searching for it
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


public class ShowSearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnShowSearchListener mListener;

    public ShowSearchFragment() {
        // Required empty public constructor
    }

    public static ShowSearchFragment newInstance(String param1, String param2) {
        ShowSearchFragment fragment = new ShowSearchFragment();
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
        Button showButton=(Button)getView().findViewById(R.id.show_search_button);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchText=(EditText)getView().findViewById(R.id.show_search_text);
                String search=searchText.getText().toString();
                mListener.onClickShowSearch(search);
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Fragment layout inflated
        return inflater.inflate(R.layout.fragment_show_search, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnShowSearchListener) {
            mListener = (OnShowSearchListener) context;
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

    //Interface that sends a message that the search for show button was clicked
    public interface OnShowSearchListener {
        void onClickShowSearch(String show);
    }
}
