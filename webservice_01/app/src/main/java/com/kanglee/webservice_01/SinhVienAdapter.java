package com.kanglee.webservice_01;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class SinhVienAdapter extends BaseAdapter {

    private MainActivity context;
    private  int laypout;
    private List<SinhVien> sinhVienList;

    public SinhVienAdapter(MainActivity context, int laypout, List<SinhVien> sinhVienList) {
        this.context = context;
        this.laypout = laypout;
        this.sinhVienList = sinhVienList;
    }

    @Override
    public int getCount() {
        return sinhVienList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        TextView txtTen, txtNamSinh, txtDiaChi;
        ImageView imageViewEdit, imageViewDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(laypout, null);

            holder.txtTen            =  convertView.findViewById(R.id.txtHoTen);
            holder.txtNamSinh        = convertView.findViewById(R.id.txtNamSinh);
            holder.txtDiaChi        = convertView.findViewById(R.id.txtDiaChi);
            holder.imageViewEdit    = convertView.findViewById(R.id.imgviewEdit);
            holder.imageViewDelete   = convertView.findViewById(R.id.imgviewDelete);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final SinhVien sinhVien = sinhVienList.get(position);
        holder.txtTen.setText(sinhVien.getHoTen());
        holder.txtNamSinh.setText("Nam Sinh:"+sinhVien.getNamSinh());
        holder.txtDiaChi.setText(sinhVien.getDiaChi());

        //bat su kien xoa va sua

        holder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, sinhVien.getHoTen(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context,CapNhatActivity.class);
                intent.putExtra("dataSinhVien", sinhVien);
                context.startActivity(intent);
            }
        });

        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage("Ban co muon xoa: "+sinhVien.getHoTen());
                alert.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.DeleteHocSinh(sinhVien.getId());

                    }
                });
                alert.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        });

        return convertView;
    }
}
