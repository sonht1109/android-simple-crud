package com.example.exam1.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exam1.R;
import com.example.exam1.model.Cat;

import java.util.ArrayList;
import java.util.List;

public class CatItemAdapter extends RecyclerView.Adapter<CatItemAdapter.CatItemViewHolder> {

    private Context context;
    private List<Cat> listCat;
    private CatItemListener catItemListener;

    public CatItemAdapter(Context context, CatItemListener catItemListener) {
        this.context = context;
        this.listCat = new ArrayList<>();
        this.catItemListener = catItemListener;
    }

    @NonNull
    @Override
    public CatItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cat_item, parent, false);
        return new CatItemViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CatItemViewHolder holder, int position) {
        Cat cat = listCat.get(position);
        if (cat == null) return;
        holder.img.setImageResource(cat.getImg());
        holder.tvName.setText(cat.getName());
        holder.tvPrice.setText(String.valueOf(cat.getPrice()));
        holder.tvDate.setText(cat.getDate());
        holder.tvTime.setText(cat.getTime());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catItemListener.edit(position);
            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCat(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listCat != null) return listCat.size();
        return 0;
    }

    public class CatItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tvName, tvPrice, tvDate, tvTime;
        private Button btnEdit, btnRemove;

        public CatItemViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cat_item_img);
            tvName = itemView.findViewById(R.id.cat_item_tv_name);
            tvPrice = itemView.findViewById(R.id.cat_item_tv_price);
            btnEdit = itemView.findViewById(R.id.cat_item_btn_edit);
            btnRemove = itemView.findViewById(R.id.cat_item_btn_remove);
            tvDate = itemView.findViewById(R.id.cat_item_date);
            tvTime = itemView.findViewById(R.id.cat_item_time);
        }
    }

    public void addCat(Cat cat) {
        listCat.add(cat);
        notifyDataSetChanged();
    }

    public void editCat(Cat cat, int i) {
        listCat.set(i, cat);
        notifyDataSetChanged();
    }

    public void removeCat(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning !");
        builder.setMessage("Bạn có chắc muỗn xóa " + listCat.get(i).getName() + " ?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            listCat.remove(i);
            notifyDataSetChanged();
            catItemListener.remove(i);
        });
        builder.setNegativeButton("No", (dialog, which) -> {
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public interface CatItemListener {
        void edit(int position);

        void remove(int position);
    }

    public List<Cat> getListCat() {
        return listCat;
    }
}
