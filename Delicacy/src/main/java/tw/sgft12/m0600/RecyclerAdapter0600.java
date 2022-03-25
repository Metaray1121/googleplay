package tw.sgft12.m0600;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter0600 extends RecyclerView.Adapter<RecyclerAdapter0600.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private ArrayList<Post0600> mData;
    //    -------------------------------------------------------------------
    private OnItemClickListener mOnItemClickListener = null;
    //--------------------------------------------
    public RecyclerAdapter0600(Context context, ArrayList<Post0600> data) {
        this.mContext = context;
        this.mData = data;
    }
    //    -------------------------------------------------------------------
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    //-------------------------------------------------------------------
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.cell_post0600, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.img = (ImageView) view.findViewById(R.id.img);
        holder.Name = (TextView) view.findViewById(R.id.Name);
        holder.Content = (TextView) view.findViewById(R.id.Content);
        holder.Add = (TextView) view.findViewById(R.id.Addr);
        holder.Zipcode = (TextView) view.findViewById(R.id.Zipcode);
        holder.Px = (TextView) view.findViewById(R.id.Px);
        holder.Py = (TextView) view.findViewById(R.id.Py);
//        holder.mWebsite = (TextView) view.findViewById(R.id.mWebsite);

        //----------------------------------------------------
        //將創建的View註冊點擊事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Post0600 post = mData.get(position);
        holder.Name.setText(post.Name);
        holder.Add.setText(mContext.getString(R.string.m0600_add) + post.Add);
        holder.Content.setText(post.Content);
        if (post.Zipcode.length()>0){
            holder.Zipcode.setText("["+post.Zipcode+"]");
        }else{
            holder.Zipcode.setText("[000]");
        }
        holder.Px.setText(post.Px);
        holder.Py.setText(post.Py);
//        holder.mWebsite.setText(post.mWebsite);

        //------------------------------
        if(post.Py.length() > 0){
            holder.Py.setText(post.Py);
        }else{
            holder.Py.setText("");
        }
        if(post.Px.length() > 0){
            holder.Px.setText(post.Px);
        }else{
            holder.Px.setText("");
        }

        //---------------------------------------
//        若圖片檔名是中文無法下載,可用此段檢查圖片網址且將中文解碼
//        String ans_Url = post.posterThumbnailUrl;
//        if (post.posterThumbnailUrl.getBytes().length == post.posterThumbnailUrl.length() ||
//                post.posterThumbnailUrl.getBytes().length > 100) {
//            ans_Url = post.posterThumbnailUrl;//不包含中文，不做處理
//        } else {
////    ans_Url = utf8Togb2312(post.posterThumbnailUrl);
////           ans_Url = utf8Togb2312(post.posterThumbnailUrl).replace("http://", "https://");
//        }
//        Glide.with(mContext)
//                .load(ans_Url)
//                .into(holder.img);
//----------------------------------------

        Glide.with(mContext)
                .load(post.posterThumbnailUrl)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .override(100, 75)
                .transition(withCrossFade())
                .error(
                        Glide.with(mContext)
                                .load("https://tcnr122021.000webhostapp.com/OpenData/nopic1.jpg"))
                .into(holder.img);

        //將position保存在itemView的Tag中，以便點擊時進行獲取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意這裡使用getTag方法獲取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    //======= sub class   ==================
    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView Name;
        public TextView Add;
        public TextView Content;
        public TextView Zipcode;
        public TextView mWebsite;
        public TextView Px;
        public TextView Py;


        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
//-----------------------------------------------
}

