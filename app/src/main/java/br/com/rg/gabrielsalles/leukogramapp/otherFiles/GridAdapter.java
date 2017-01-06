package br.com.rg.gabrielsalles.leukogramapp.otherFiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.rg.gabrielsalles.leukogramapp.R;

/**
 * Created by gabriel on 17/10/16.
 */

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Cell> cellArray = new ArrayList<>();

    public GridAdapter(Context c,ArrayList<Cell> cellArray ) {
        mContext = c;
        this.cellArray = cellArray;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return cellArray.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return cellArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return cellArray.get(position).getId();
    }

    public String getItemName(int position) {
        return cellArray.get(position).getName();
    }

    public int getTotal(int position) {
        return cellArray.get(position).getCount();
    }

    public void addOne(int position) {
        cellArray.get(position).setCount(cellArray.get(position).getCount() + 1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.cell_layout, null);
            TextView textView = (TextView) grid.findViewById(R.id.cellTextView);
            textView.setText(cellArray.get(position).getName());
        } else {
            grid = (View) convertView;
        }
        return grid;
    }
}