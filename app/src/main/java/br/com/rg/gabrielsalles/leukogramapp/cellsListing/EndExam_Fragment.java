package br.com.rg.gabrielsalles.leukogramapp.cellsListing;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.com.rg.gabrielsalles.leukogramapp.R;

/**
 * Created by gabriel on 18/10/16.
 */

public class EndExam_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView name;
    private TextView id;
    private TextView birth;
    private TextView sex;

    private String mParam1;
    private String mParam2;
    private Button endExamBtn;

    private OtherCells_Fragment.OnFragmentInteractionListener mListener;

    public EndExam_Fragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static OtherCells_Fragment newInstance(String param1, String param2) {
        OtherCells_Fragment fragment = new OtherCells_Fragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.end_exam_fragment, container, false);

        name   = (TextView) view.findViewById(R.id.patientName);
        id     = (TextView) view.findViewById(R.id.patientId);
        birth  = (TextView) view.findViewById(R.id.birth);
        sex    = (TextView) view.findViewById(R.id.sex);
        endExamBtn = (Button) view.findViewById(R.id.endExamBtn);
        endExamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CellsListing_Activity)getActivity()).endExam();
            }
        });

        String[] patientData = ((CellsListing_Activity)getActivity()).getIntentExtra();
        name.setText(patientData[0]);
        id.setText("ID: " + patientData[1]);
        sex.setText(patientData[2]);
        birth.setText(patientData[3]);

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

