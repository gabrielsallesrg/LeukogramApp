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

public class OtherCells_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button cell6;
    private Button cell7;
    private Button cell8;
    private Button cell9;
    private Button cell10;
    private Button cell11;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OtherCells_Fragment() {
        // Required empty public constructor
    }

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
        View view = inflater.inflate(R.layout.other_cells_fragment, container, false);

        cell6  = (Button) view.findViewById(R.id.cell6);
        cell7  = (Button) view.findViewById(R.id.cell7);
        cell8  = (Button) view.findViewById(R.id.cell8);
        cell9  = (Button) view.findViewById(R.id.cell9);
        //cell10 = (Button) view.findViewById(R.id.cell10);
        cell11 = (Button) view.findViewById(R.id.cell11);
        superSetOnLongCLickListener(cell6);
        superSetOnLongCLickListener(cell7);
        superSetOnLongCLickListener(cell8);
        superSetOnLongCLickListener(cell9);
        //superSetOnLongCLickListener(cell10);
        superSetOnLongCLickListener(cell11);
        superSetOnClickListener(cell6);
        superSetOnClickListener(cell7);
        superSetOnClickListener(cell8);
        superSetOnClickListener(cell9);
        //superSetOnClickListener(cell10);
        superSetOnClickListener(cell11);

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
                        case 6:
                            cellImageDrawable = getResources().getDrawable(R.drawable.blasto);
                            break;
                        case 7:
                            cellImageDrawable = getResources().getDrawable(R.drawable.promielocito);
                            break;
                        case 8:
                            cellImageDrawable = getResources().getDrawable(R.drawable.mielocito);
                            break;
                        case 9:
                            cellImageDrawable = getResources().getDrawable(R.drawable.metamielocito);
                            break;
                        case 10:
                            cellImageDrawable = getResources().getDrawable(R.drawable.ic_add_circle_outline_white_48dp);
                            break;
                        case 11:
                            cellImageDrawable = getResources().getDrawable(R.drawable.eritoblasto);
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
                                    if (checkBox.isChecked())
                                        ((CellsListing_Activity)getActivity()).setStudentHelperOff();
                                }
                            })
                            .setNegativeButton(R.string.incorrect, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CheckBox checkBox = (CheckBox) ((AlertDialog) dialog).findViewById(R.id.dialog_cell_checkbox);
                                    if (checkBox.isChecked())
                                        ((CellsListing_Activity)getActivity()).setStudentHelperOff();
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
