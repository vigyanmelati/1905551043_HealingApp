package id.vigyan.healingapp;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder>{
    private Context mContext;
    private Cursor mCursor;
    private OnClickListenerData listenerData;


    public DataAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.card_regis,parent,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }

        String nik = mCursor.getString(mCursor.getColumnIndex(DBmain.row_nik));
        String nama = mCursor.getString(mCursor.getColumnIndex(DBmain.row_nama));
        String jk = mCursor.getString(mCursor.getColumnIndex(DBmain.row_jk));
        String keluhan = mCursor.getString(mCursor.getColumnIndex(DBmain.row_keluhan));
        String persentase = String.valueOf(mCursor.getInt(mCursor.getColumnIndex(DBmain.row_persentase)));
        long id = mCursor.getLong(mCursor.getColumnIndex(DBmain.row_id));

        holder.itemView.setTag(id);
        holder.nikTV.setText(nik);
        holder.namaTV.setText(nama);
        holder.jkTV.setText(jk);
        holder.keluhanTV.setText(keluhan);
        holder.cemasTV.setText(persentase);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{

        private TextView nikTV, namaTV, jkTV, keluhanTV, cemasTV;
        public ImageView img_delete;
        public ImageView img_edit;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            nikTV = itemView.findViewById(R.id.nikTV);
            namaTV = itemView.findViewById(R.id.namaTV);
            jkTV = itemView.findViewById(R.id.jkTV);
            keluhanTV = itemView.findViewById(R.id.keluhanTV);
            cemasTV = itemView.findViewById(R.id.cemasTV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long position = (long) itemView.getTag();
                    listenerData.OnItemClickData(position);
                }
            });
        }
    }

    public interface OnClickListenerData{
        void OnItemClickData(long id);
    }

    public void setOnClickListenerData(OnClickListenerData listenerData){
        this.listenerData = listenerData;
    }

    public void swapCursor(Cursor newCursor){
        if(mCursor!=null){
            mCursor.close();
        }
        mCursor=newCursor;

        if(newCursor!=null){
            this.notifyDataSetChanged();
        }
    }
}
