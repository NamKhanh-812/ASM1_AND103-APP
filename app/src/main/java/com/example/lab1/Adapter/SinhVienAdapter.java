package com.example.lab1.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lab1.Api.ApiService;
import com.example.lab1.DanhSach;
import com.example.lab1.Model.SinhVien;
import com.example.lab1.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinhVienAdapter extends RecyclerView.Adapter<SinhVienAdapter.viewHolder>{
    private final Context context;
    private List<SinhVien> list;
    DanhSach home = new DanhSach();


    public SinhVienAdapter(Context context, List<SinhVien> list) {
        this.context = context;
        this.list = list;
        home= (DanhSach) context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_sinhvien,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (position >= 0 && position <= list.size()){
            SinhVien sv = list.get(position);

            holder.txtMaSV.setText("Mã SV : "+sv.getMasv());
            holder.txtNameSV.setText("Họ tên : "+sv.getName());
            holder.txtDiemTB.setText("Điểm TB : "+sv.getPoint());

            Glide.with(holder.itemView.getContext())
                    .load(sv.getAvatar())
                    .into(holder.imgAvatar);

            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String idStudent = sv.get_id();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Cảnh báo");
                    builder.setMessage("Bạn có muốn xóa không?");
                    builder.setNegativeButton("No",null);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ApiService.apiService.deleteStudent(idStudent).enqueue(new Callback<SinhVien>() {
                                @Override
                                public void onResponse(Call<SinhVien> call, Response<SinhVien> response) {
                                    if (response.isSuccessful()){
                                        Toast.makeText(context, "Delete success", Toast.LENGTH_SHORT).show();
                                        list.remove(position);
                                        notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SinhVien> call, Throwable t) {
                                    Toast.makeText(context, "Delete fail", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    builder.show();
                }
            });

            holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    home.showDialog(context,list.get(position),0);
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView txtNameSV, txtMaSV, txtDiemTB;
        ImageView imgAvatar;
        Button btnUpdate,btnDelete;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameSV = itemView.findViewById(R.id.txtNameSV);
            txtMaSV = itemView.findViewById(R.id.txtMaSV);
            txtDiemTB = itemView.findViewById(R.id.txtDiemTB);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            btnUpdate = itemView.findViewById(R.id.btnSua);
            btnDelete = itemView.findViewById(R.id.btnXoa);
        }
    }

}
