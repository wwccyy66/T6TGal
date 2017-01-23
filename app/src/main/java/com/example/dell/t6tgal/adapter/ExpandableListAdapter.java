package com.example.dell.t6tgal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.t6tgal.R;
import com.example.dell.t6tgal.model.News;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2017/1/4.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<News> newsList;
    private List<News> parentList;
    //构造函数，传入适配器所需要的数据和context
    public ExpandableListAdapter(Context context,List<News> newsList,List<News> parentList){
        this.context=context;
        this.newsList=newsList;
        this.parentList=parentList;
    }
    @Override
    public int getGroupCount() {
        return parentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return newsList!=null?newsList.size():0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return newsList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=LayoutInflater.from(context).inflate(R.layout.newsitemsgroup,null);
            holder.textView=(TextView) convertView.findViewById(R.id.textGroup);
            convertView.setTag(holder);
        }else{
           holder=(ViewHolder) convertView.getTag();
        }
        holder.textView.setText(parentList.get(groupPosition).getTitle());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        News news=newsList.get(childPosition);
        if (convertView==null){
            holder=new ViewHolder();
            convertView=LayoutInflater.from(context).inflate(R.layout.ch04_news_item_layout,null);
            holder.tvTitle= (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvAuthor= (TextView) convertView.findViewById(R.id.tvAuthor);
            holder.tvTimer= (TextView) convertView.findViewById(R.id.tvTime);
            holder.ivImg= (ImageView) convertView.findViewById(R.id.ivImag);

            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(news.getTitle());
        holder.tvAuthor.setText(news.getAuthor());
        holder.ivImg.setImageResource(news.getImage());

        long timer=news.getTime();
        String timeFlag="";
        if (timer<1000*60*5){
            timeFlag="刚刚";
        }else if(timer<1000*60*60){
            timeFlag="一小时之前";
        }else{
            Date date=new Date(new Date().getTime()-timer);
            SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日");
            timeFlag=sdf.format(date);
        }
        holder.tvTimer.setText(timeFlag);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /*****
     * viewHolder机制：为了提高ListView产生的view的效率，
     *      复用已经存在的view，减少UI的创建次数，从而产生的优化方案
     */
    public class ViewHolder{
        public TextView textView;

        public TextView tvTitle;

        public TextView tvAuthor;

        public TextView tvTimer;

        public ImageView ivImg;
    }

}
