package me.rokevin.demo;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by luokaiwen on 15/12/14.
 * <p/>
 * 公共适配器
 */
public abstract class KBaseAdapter<T> extends BaseAdapter {

    private ArrayList<T> list;
    private final Context context;
    private final int itemLayout;

    public KBaseAdapter(Context context, ArrayList<T> list, int itemLayout) {
        this.context = context;
        this.setList(list);
        this.itemLayout = itemLayout;
    }

    public KBaseAdapter(Context context, int itemLayout) {
        this.context = context;
        this.setList(new ArrayList<T>());
        this.itemLayout = itemLayout;
    }

    @Override
    public int getCount() {
        return getList().size();
    }

    @Override
    public T getItem(int position) {
        return getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    SparseArray<View> viewHolder = new SparseArray<>();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if (viewHolder.get(position) == null) {
//            convertView = View.inflate(context, itemLayout, null);
//            viewHolder.put(position, convertView);
//        } else {
//            convertView = viewHolder.get(position);
//        }

        if (convertView == null) {
            convertView = View.inflate(context, itemLayout, null);
        }
        getItemView(position, convertView);
        return convertView;
    }

    /**
     * example: ImageView bananaView = ViewHolder.get(convertView,R.id.banana);<br/>
     *
     * @param position
     * @param convertView
     */
    public abstract void getItemView(int position, View convertView);

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }

    public static class ViewHolder {

        @SuppressWarnings("unchecked")
        public static <V extends View> V get(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (V) childView;
        }
    }
}
