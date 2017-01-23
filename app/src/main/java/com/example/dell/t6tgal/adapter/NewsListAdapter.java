package com.example.dell.t6tgal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.t6tgal.R;
import com.example.dell.t6tgal.model.News;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2017/1/4.
 */

public class NewsListAdapter extends BaseAdapter {

    private Context context;
    private List<News> newsList;
    //编写构造函数，传入适配器所需要的数据和context
    public NewsListAdapter(Context context, List newsList){
        this.context=context;
        this.newsList=newsList;
    }

    /*******
     * 重写BaseAdapter方法
     *
     * getCount 返回总数量
     *
     * getItem  根据position得到相应位置数据
     *
     * getItemId  得到相应位置项的Id
     *
     * getView   产生列表一项的视图并填充数据，并返回产生的视图
     * @return
     */
    @Override
    public int getCount() {
        return newsList!=null?newsList.size():0;
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*若convertView为空时创建UI，并使用ViewHolder实体引用列表项中产生的UI ，
        若convertView不为空，则不需要创建，使用保存在viewHolder中的view即可*/
        News news=newsList.get(position);
        ViewHolder holder=null;
        if (convertView==null){//若convertView为空时创建UI，并使用ViewHolder实体引用列表项中产生的UI
            convertView= LayoutInflater.from(context).inflate(R.layout.ch04_news_item_layout,null);
            holder=new ViewHolder();

            holder.tvTitle= (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvAuthor= (TextView) convertView.findViewById(R.id.tvAuthor);
            holder.ivImg= (ImageView) convertView.findViewById(R.id.ivImag);
            holder.tvTimer= (TextView) convertView.findViewById(R.id.tvTime);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();// 若convertView不为空，则不需要创建，使用保存在viewHolder中的view即可
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


    /*****
     * viewHolder机制：为了提高ListView产生的view的效率，
     *      复用已经存在的view，减少UI的创建次数，从而产生的优化方案
     */
    public class ViewHolder{
        public TextView tvTitle;

        public TextView tvAuthor;

        public TextView tvTimer;

        public ImageView ivImg;
    }
}
