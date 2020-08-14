package com.kwawannan.SquareEmployeeDirectory.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kwawannan.SquareEmployeeDirectory.R;
import com.kwawannan.SquareEmployeeDirectory.model.Employee;
import com.kwawannan.SquareEmployeeDirectory.utils.ImageCacheManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ViewHolder>{

    private final String TAG = getClass().getSimpleName();
    private List<Employee> mEmployeeList;
    private Context mContext;

    public EmployeeListAdapter(Context context) {
        mEmployeeList = new ArrayList<>();
        this.mContext = context;
    }

    public void setmEmployeeList(List<Employee> list) {
        mEmployeeList = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_employee_name) TextView employeeNameTV;
        @BindView(R.id.tv_employee_email) TextView employeeEmailTV;
        @BindView(R.id.tv_employee_phone) TextView employeePhoneTV;
        @BindView(R.id.tv_employee_team) TextView employeeTeamTV;
        @BindView(R.id.iv_employee) ImageView employeeIV;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.incl_employee_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Employee item = mEmployeeList.get(position);
        viewHolder.employeeNameTV.setText(item.getFullName());
        viewHolder.employeeEmailTV.setText(item.getEmailAddress());

        String number = item.getPhoneNumber().replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
        viewHolder.employeePhoneTV.setText(number);
        viewHolder.employeeTeamTV.setText(item.getTeam());

        try{
        Bitmap bitmap = ImageCacheManager.getImage(mContext,item);

        if(bitmap == null){
            EmployeeImageTask task = new EmployeeImageTask();
            task.setViewHolder(viewHolder);
            task.execute(item);
        }else {
            viewHolder.employeeIV.setImageBitmap(bitmap);
        }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mEmployeeList.size();
    }

    class EmployeeImageTask extends AsyncTask<Employee,Void, Bitmap> {

        private Employee mEmployee;
        private ViewHolder mViewHolder;

        public void setViewHolder(ViewHolder myViewHolder){
            this.mViewHolder=myViewHolder;
        }

        @Override
        protected Bitmap doInBackground(Employee... employees) {
            Bitmap bitmap = null;
            mEmployee = employees[0];

            String imageurl = mEmployee.getPhotoUrlSmall();

            InputStream inputStream=null;

            try {
                URL imageUrl=new URL(imageurl);
                inputStream = (InputStream) imageUrl.getContent();
                bitmap= BitmapFactory.decodeStream(inputStream);

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Log.d(TAG, "doInBackground: Image downloaded: "+imageurl);

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap == null){
            }else {
                mViewHolder.employeeIV.setImageBitmap(bitmap);
                ImageCacheManager.putImage(mContext,mEmployee,bitmap);
            }
        }


    }

}
