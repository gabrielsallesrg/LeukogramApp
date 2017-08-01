package br.com.rg.gabrielsalles.leukogramapp.cellsListing;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import br.com.rg.gabrielsalles.leukogramapp.R;

/**
 * Created by gabriel on 15/10/16.
 */

public class MainCells_Fragment extends Fragment{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button cell0;
    private Button cell1;
    private Button cell2;
    private Button cell3;
    private Button cell4;
    private Button cell5;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainCells_Fragment() {
        // Required empty public constructor
    }

    public static MainCells_Fragment newInstance(String param1, String param2) {
        MainCells_Fragment fragment = new MainCells_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static MainCells_Fragment newInstance() {
        MainCells_Fragment fragment = new MainCells_Fragment();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.main_cells_fragment, container, false);

        cell0 = (Button) view.findViewById(R.id.cell0);
        cell1 = (Button) view.findViewById(R.id.cell1);
        cell2 = (Button) view.findViewById(R.id.cell2);
        cell3 = (Button) view.findViewById(R.id.cell3);
        cell4 = (Button) view.findViewById(R.id.cell4);
        cell5 = (Button) view.findViewById(R.id.cell5);
        superSetOnLongCLickListener(cell0);
        superSetOnLongCLickListener(cell1);
        superSetOnLongCLickListener(cell2);
        superSetOnLongCLickListener(cell3);
        superSetOnLongCLickListener(cell4);
        superSetOnLongCLickListener(cell5);
        superSetOnClickListener(cell0);
        superSetOnClickListener(cell1);
        superSetOnClickListener(cell2);
        superSetOnClickListener(cell3);
        superSetOnClickListener(cell4);
        superSetOnClickListener(cell5);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //outState.putBoolean("first run", false);
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

    public boolean superSetOnLongCLickListener(Button btn) {
        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String [] split = view.getResources().getResourceName(view.getId()).split("/");
                Button btn = (Button) getActivity().findViewById(getResources().getIdentifier(split[1], "id", getContext().getPackageName()));
                ((CellsListing_Activity)getActivity()).minusCell(Integer.parseInt(split[1].split("ll")[1]), btn);
                return true;
            }
        });
        return false;
    }

    public void superSetOnClickListener(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String [] split = view.getResources().getResourceName(view.getId()).split("/");
                final Button btn = (Button) getActivity().findViewById(getResources().getIdentifier(split[1], "id", getContext().getPackageName()));
                final int pos = Integer.parseInt(split[1].split("ll")[1]);
                if (((CellsListing_Activity)getActivity()).isStudentHelperOn()) {
                    View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_cell_image, null);
                    ImageView cellImageView = (ImageView) dialogView.findViewById(R.id.cell_image);
                    Drawable cellImageDrawable = getResources().getDrawable(R.drawable.ic_add_circle_black_18dp);
                    switch (pos) {
                        case 0:
                            cellImageDrawable = getResources().getDrawable(R.drawable.neutrofilo);
                            break;
                        case 1:
                            cellImageDrawable = getResources().getDrawable(R.drawable.bastonete);
                            break;
                        case 2:
                            cellImageDrawable = getResources().getDrawable(R.drawable.linfocito);
                            break;
                        case 3:
                            cellImageDrawable = getResources().getDrawable(R.drawable.monocito);
                            break;
                        case 4:
                            cellImageDrawable = getResources().getDrawable(R.drawable.eosinofilo);
                            break;
                        case 5:
                            cellImageDrawable = getResources().getDrawable(R.drawable.basofilo);
                            break;
                    }
                    cellImageView.setImageDrawable(cellImageDrawable);
                    new AlertDialog.Builder(getActivity())
                            .setView(dialogView)
                            .setPositiveButton(R.string.correct, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((CellsListing_Activity)getActivity()).addCell(pos, btn);

                                    CheckBox checkBox = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.dialog_cell_checkbox);
                                    if (checkBox.isChecked()) {
                                        ((CellsListing_Activity) getActivity()).setStudentHelperOff();
                                        ((CellsListing_Activity) getActivity()).toggleHideyBar();
                                    }
                                }
                            })
                            .setNegativeButton(R.string.incorrect, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CheckBox checkBox = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.dialog_cell_checkbox);
                                    if (checkBox.isChecked()) {
                                        ((CellsListing_Activity) getActivity()).setStudentHelperOff();
                                        ((CellsListing_Activity) getActivity()).toggleHideyBar();
                                    }
                                }
                            })
                            .show();
                }
                else {
                    ((CellsListing_Activity)getActivity()).addCell(pos, btn);
                }
                ((CellsListing_Activity)getActivity()).playSound(pos);
            }
        });
    }
}
