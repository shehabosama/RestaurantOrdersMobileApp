package com.android.thresturent.Ui.Fragments.GetAllBookTableFragment;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.thresturent.R;
import com.android.thresturent.common.helper.AppPreferences;
import com.android.thresturent.common.helper.Constants;
import com.android.thresturent.common.model.BookTables;
import com.android.thresturent.common.model.Tables;

import java.util.ArrayList;
import java.util.List;

public class BookTablesAdapter extends RecyclerView.Adapter<BookTablesAdapter.BookTableHolder>{
    private BookTableInterAction interAction;
    private Context context;
    private List<BookTables> list;
    List<Tables> tables;
    AdapterView.OnItemSelectedListener spinnerListenr;
    public BookTablesAdapter(Context context, List<BookTables> list, BookTableInterAction interAction) {
        this.context = context;
        this.list = list;
        this.interAction = interAction;
    }

    @NonNull
    @Override
    public BookTableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_display_book_table, parent, false);

        return new BookTableHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookTableHolder holder, int position) {
        BookTables bookTables = list.get(position);


        holder.email.setText(bookTables.getEmail());
        holder.userName.setText(bookTables.getUsername());
        holder.date.setText(bookTables.getDate());
        holder.time.setText(bookTables.getTime());
        holder.setListener(bookTables);
        if(bookTables.getConfirmation() == 0){
            holder.status.setText("Pending");
            holder.status.setBackgroundColor(Color.YELLOW);
        }else if(bookTables.getConfirmation() == 1){
            holder.status.setText("Accept");
            holder.status.setBackgroundColor(Color.GREEN);
        }else{
            holder.status.setText("Rejected");
            holder.status.setBackgroundColor(Color.RED);
        }

         tables = new ArrayList<>();
        tables.add(0,new Tables(0,"Select table"));
        for(int i =1 ;i<=bookTables.getTables().size();i++){
            tables.add(i,bookTables.getTables().get(i-1));
        }
        holder.spinner.setOnItemSelectedListener(null);
        if(AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN,context,false)){
            holder.btnReject.setVisibility(View.VISIBLE);
            holder.btnAccept.setVisibility(View.VISIBLE);
            holder.spinner.setVisibility(View.VISIBLE);
        }else{
            holder.btnReject.setVisibility(View.GONE);
            holder.btnAccept.setVisibility(View.GONE);
            holder.spinner.setVisibility(View.GONE);
            if (bookTables.getConfirmation()==1){
                holder.table.setText("Table number: "+bookTables.getTable_id());
            }else if (bookTables.getConfirmation()==2){
                holder.table.setText("Sorry the tables are completed");
            }
        }
        TableAdapter myCustomeAdapter3= new TableAdapter(context,tables);
        holder.spinner.setAdapter(myCustomeAdapter3);
        holder.spinner.setSelection(0,false);
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                interAction.setOnClickTableItem(tables.get(position),position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public interface BookTableInterAction {

        void setRejectListener(BookTables bookTables);

        void setAcceptListener(BookTables bookTables);

        void setOnClickTableItem(Tables listitems, int position);
    }

    public class BookTableHolder extends RecyclerView.ViewHolder {
        TextView userName, email,status,table,time,date;
        Button btnAccept,btnReject;
        Spinner spinner;
        int table_id;
        public BookTableHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.text_user_name);
            email = itemView.findViewById(R.id.text_email);
            btnAccept = itemView.findViewById(R.id.accept_btn);
            btnReject = itemView.findViewById(R.id.reject_btn);
            spinner = itemView.findViewById(R.id.spin_table);
            time = itemView.findViewById(R.id.text_user_time);
            date =itemView.findViewById(R.id.text_user_date);
            status = itemView.findViewById(R.id.status);
            table= itemView.findViewById(R.id.spin);
            spinner.setOnItemSelectedListener(null);
        }

        public void setListener(final BookTables bookTables) {
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(interAction!=null)
                        interAction.setAcceptListener(bookTables);

                }
            });

            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(interAction !=null)
                        interAction.setRejectListener(bookTables);
                }
            });



//             spinnerListenr = new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    interAction.setOnClickTableItem(tables.get(position));
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            };

        }

    }
}
