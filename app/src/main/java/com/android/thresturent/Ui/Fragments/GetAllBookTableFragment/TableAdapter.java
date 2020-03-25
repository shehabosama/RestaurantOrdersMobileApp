package com.android.thresturent.Ui.Fragments.GetAllBookTableFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.thresturent.R;
import com.android.thresturent.common.model.Tables;

import java.util.List;

public class TableAdapter extends BaseAdapter {

    private Context context;
    List<Tables> listitems;

    public TableAdapter(Context context, List<Tables> items){
        this.listitems=items;
        this.context = context;

    }

    @Override
    public int getCount() {
        return listitems.size();
    }

    @Override
    public Object getItem(int i) {
        return listitems.get(i).getUsername();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
       // LayoutInflater layoutInflater=LayoutInflater.from(context).getLayoutInflater();
         view = LayoutInflater.from(context).inflate(R.layout.row_lay, viewGroup, false);
       // View view3=layoutInflater.inflate(R.layout.row_lay,null);
        final Tables table = listitems.get(i);
        final TextView textView=view.findViewById(R.id.textname);
        final TextView id = view.findViewById(R.id.textid);


            textView.setText(table.getUsername());
            id.setText(String.valueOf(table.getId()));



//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               if (tablesSpinnerInterAction!=null)
//                   tablesSpinnerInterAction.onClickTableItem(table);
//            }
//        });
        return view;
    }
}