package edu.quinnipiac.ser210.finalproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    private static FirstPageFragmentListener fragmentListener;

    public ShowSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowSearchFragment newInstance(String param1, String param2) {
        ShowSearchFragment fragment = new ShowSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static ShowSearchFragment newInstance(FirstPageFragmentListener listener)
    {
        ShowSearchFragment fragment = new ShowSearchFragment();
        fragmentListener = listener;
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Button showButton=(Button)getView().findViewById(R.id.show_search_button);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchText=(EditText)getView().findViewById(R.id.show_search_text);
                String search=searchText.getText().toString();
                mListener.onClickShowSearch(search);
                fragmentListener.onSwitchToNextFragment();
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        int containerId=container.getId();
        Log.e("ContainerId=",containerId+"");
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

    public void beginSearch(View view)
    {

    }
    public interface OnShowSearchListener {
        // TODO: Update argument type and name
        void onClickShowSearch(String show);
    }
}
