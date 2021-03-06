package tool;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.application.Http_Application;
import com.bean.Messages;

import com.clocle.huxiang.clocle.Other_Self_infos;
import com.clocle.huxiang.clocle.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.function.Clocle_help;
import com.function.Clocle_help_details;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;

    private List<Messages> datas;
    public static Context mcontext;

    private final static int NO_IMAGE = 0;
    private final static int SINGLE_IMAGE = 1;
    private final static int MUTI_IMAGE = 2;
    private int deviceWidth;
    private int deviceHeight;
    private int marginleft;
    private int img0width;
    private int img1width;
    private int img0height;
    private int img1height;


    DisplayImageOptions options;

    public RecycleViewAdapter(Context context, List<Messages> datas) {
        //deviceWidth = ((Clocle_help) context).deviceWidth;
       // deviceHeight = ((Clocle_help) context).deviceHeight;
        //一张图片时的宽度
       // img0width = deviceWidth / 2;
      //  //一张图片时的高度
      //  img0height = deviceHeight / 4;
      // //多张图片的宽度
        //img1width = (deviceWidth - marginleft) / 3;
        //多张图片的高度
      //  img1height = deviceHeight / 2;
       // final float scale = context.getResources().getDisplayMetrics().densityDpi;
      //  marginleft = (int) (10 * scale / 160);
        this.datas = datas;
        this.inflater = LayoutInflater.from(context);
        this.mcontext = context;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.t9)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("tag", "创建Viewholder");
        View view = null;
        if (viewType == MUTI_IMAGE) {
            view = inflater.inflate(R.layout.messege_layout, parent, false);
           /* SimpleDraweeView img1= (SimpleDraweeView) view.findViewById(R.id.help_imgs1);
            ViewGroup.LayoutParams lp1 = img1.getLayoutParams();
            lp1.height = img1height;
            lp1.width = img1width;
            SimpleDraweeView img2= (SimpleDraweeView) view.findViewById(R.id.help_imgs2);
            ViewGroup.LayoutParams lp2 = img2.getLayoutParams();
            lp2.height = img1height;
            lp2.width = img1width;
            SimpleDraweeView img3= (SimpleDraweeView) view.findViewById(R.id.help_imgs3);
            ViewGroup.LayoutParams lp3 = img3.getLayoutParams();
            lp3.height = img1height;
            lp3.width = img1width;*/
            return new ViewHolderwithMutiImg(view);
        } else if (viewType == NO_IMAGE) {
            view = inflater.inflate(R.layout.messege_layout_noimage, parent, false);

            return new ViewHolderwithoutImg(view);
        } else {
            view = inflater.inflate(R.layout.messege_layout_single_img, parent, false);
            return new ViewHolderwithSingleImg(view);
        }
        // Log.d("tag","创建Viewholder1");
        // RecycleviewViewHolder viewHolder = new RecycleviewViewHolder(view);
        //  Log.d("tag","创建Viewholder2");
        // viewHolder.help_framelayout.setVisibility(View.VISIBLE);
        //  viewHolder.linearLayout.setVisibility(View.VISIBLE);
        // return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        String img1url = datas.get(position).getImg1();

        String img2url = datas.get(position).getImg2();

        if (img1url == null) {
            return NO_IMAGE;
        }
        if (img2url == null) {
            return SINGLE_IMAGE;
        } else {
            return MUTI_IMAGE;
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(datas.get(position).getHelp_id() + "_" + datas.get(position).getUser_id());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, "点击了" + holder.itemView.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
        //无图片
        if (holder instanceof ViewHolderwithoutImg) {
            //性别
            if (datas.get(position).getSex().equals("女")) {
                ((ViewHolderwithoutImg) holder).sex.setImageResource(R.mipmap.woman);
            } else {
                ((ViewHolderwithoutImg) holder).sex.setImageResource(R.mipmap.man);
            }
            //头像

            ((ViewHolderwithoutImg) holder).photo.setImageURI(Uri.parse(datas.get(position).getPic()));

            //  ImageLoader.getInstance().displayImage(datas.get(position).getPic(), ((ViewHolderwithoutImg) holder).photo, options);
            //昵称，时间，学校

            ((ViewHolderwithoutImg) holder).name.setText(datas.get(position).getName());//昵称
            ((ViewHolderwithoutImg) holder).contexttext.setText(datas.get(position).getMessage());//正文内容
        }
        //单图
        if (holder instanceof ViewHolderwithSingleImg) {
            if (datas.get(position).getSex().equals("女")) {
                ((ViewHolderwithSingleImg) holder).sex.setImageResource(R.mipmap.woman);
            } else {
                ((ViewHolderwithSingleImg) holder).sex.setImageResource(R.mipmap.man);
            }
            //头像
            ((ViewHolderwithSingleImg) holder).photo.setImageURI(Uri.parse(datas.get(position).getPic()));
            // ImageLoader.getInstance().displayImage("http://sqimg.qq.com/qq_product_operations/im/2016/pc/ay/mb65_b.jpg", ((ViewHolderwithSingleImg) holder).photo, options);
            //昵称，时间，学校
            ((ViewHolderwithSingleImg) holder).name.setText(datas.get(position).getName());//昵称
            ((ViewHolderwithSingleImg) holder).contexttext.setText(datas.get(position).getMessage());//正文内容
            //图片1
            ((ViewHolderwithSingleImg) holder).only_one_image.setImageURI(Uri.parse(datas.get(position).getImg1()));
            Log.i("img1url", "onBindViewHolder: "+datas.get(position).getImg1());
            // ImageLoader.getInstance().displayImage(datas.get(position).getImg1(), ((ViewHolderwithSingleImg) holder).only_one_image, options);
        }
        //多图
        if (holder instanceof ViewHolderwithMutiImg) {
            if (datas.get(position).getSex().equals("女")) {
                ((ViewHolderwithMutiImg) holder).sex.setImageResource(R.mipmap.woman);
            } else {
                ((ViewHolderwithMutiImg) holder).sex.setImageResource(R.mipmap.man);
            }
            String img1 = datas.get(position).getImg1();
            String img2 = datas.get(position).getImg2();
            String img3 = datas.get(position).getImg3();
            ImageLoader.getInstance().displayImage(img1, ((ViewHolderwithMutiImg) holder).help_imgs1, options);
            ImageLoader.getInstance().displayImage(img2, ((ViewHolderwithMutiImg) holder).help_imgs2, options);
            if (img3 != null) {
                ImageLoader.getInstance().displayImage(datas.get(position).getImg3(), ((ViewHolderwithMutiImg) holder).help_imgs3, options);
            }


            //头像
            ((ViewHolderwithMutiImg) holder).photo.setImageURI(Uri.parse(datas.get(position).getPic()));
            // ImageLoader.getInstance().displayImage(datas.get(position).getPic(), ((ViewHolderwithMutiImg) holder).photo, options);
            //昵称，时间，学校
            ((ViewHolderwithMutiImg) holder).name.setText(datas.get(position).getName());//昵称
            ((ViewHolderwithMutiImg) holder).contexttext.setText(datas.get(position).getMessage());//正文内容
        }

        //之所以出现错乱，是因为holder复用了img1url为空的视图
        //不显示图片

    }

    @Override
    public long getItemId(int position) {
        return datas.get(position).getHelp_id();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

/*    //每个Item的点击事件
    @Override
    public void onClick(View v) {

        Toast.makeText(mcontext, getItemId()+"点击了", Toast.LENGTH_SHORT).show();
    }*/


    class ViewHolderwithoutImg extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView time;
        public TextView contexttext;//正文
        public SimpleDraweeView photo;
        public TextView school;
        public ImageView sex;
        public TextView help_order;

        //private RecycleViewAdapter.OnItemClickListener monItemClickListener;
        public ViewHolderwithoutImg(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.namedongtai);
            contexttext = (TextView) itemView.findViewById(R.id.contextdongtai);
            time = (TextView) itemView.findViewById(R.id.timetext);
            photo = (SimpleDraweeView) itemView.findViewById(R.id.help_user_photo);
            school = (TextView) itemView.findViewById(R.id.school);
            help_order = (TextView) itemView.findViewById(R.id.help_order);
            sex = (ImageView) itemView.findViewById(R.id.help_sex);
            photo.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent intent = null;
            String itemid = (String) itemView.getTag();
            switch (v.getId()) {
                //点击头像跳转个人资料页
                case R.id.help_user_photo:
                    Log.d("tagtiaozhuang", (String) itemView.getTag());
                    //tag是help_id+userid
                    intent = new Intent(Http_Application.getContext(), Other_Self_infos.class);
                    intent.putExtra("itemtag", itemid);
                    mcontext.startActivity(intent);
                    break;
                //点击评论跳转到详情页
                case R.id.help_comment:
                    intent = new Intent(Http_Application.getContext(), Clocle_help_details.class);
                    intent.putExtra("itemtag", itemid);
                    mcontext.startActivity(intent);
                    break;
                case R.id.help_order:
                    new AlertDialog.Builder(mcontext).setTitle("确认帮助TA")
                            .setMessage("帮助TA后您可获取300颗圈圈豆，请尽快帮TA完成").setNegativeButton("我能完成", null).setPositiveButton("点错了", null).show();
                default:
                    break;
            }
        }
    }


    class ViewHolderwithSingleImg extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView time;
        public TextView contexttext;//正文
        public SimpleDraweeView photo;
        public TextView school;
        public TextView help_order;
        public SimpleDraweeView only_one_image;


        public ImageView sex;
        //private RecycleViewAdapter.OnItemClickListener monItemClickListener;

        public ViewHolderwithSingleImg(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.namedongtai);
            contexttext = (TextView) itemView.findViewById(R.id.contextdongtai);
            time = (TextView) itemView.findViewById(R.id.timetext);
            photo = (SimpleDraweeView) itemView.findViewById(R.id.help_userphoto1);
            school = (TextView) itemView.findViewById(R.id.school);
            only_one_image = (SimpleDraweeView) itemView.findViewById(R.id.only_one_image);
            //获取view的宽高像素
          //  ViewGroup.LayoutParams lp = only_one_image.getLayoutParams();
          //  lp.width = img0width;
          //  lp.height = img0height;

            help_order = (TextView) itemView.findViewById(R.id.help_order);
            sex = (ImageView) itemView.findViewById(R.id.help_sex);
            photo.setOnClickListener(this);
            // this.monItemClickListener=onItemClickListener;
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = null;
            String itemid = (String) itemView.getTag();
            switch (v.getId()) {
                //点击头像跳转个人资料页
                case R.id.help_userphoto1:
                    Log.d("tagtiaozhuang", (String) itemView.getTag());
                    //tag是help_id+userid
                    intent = new Intent(Http_Application.getContext(), Other_Self_infos.class);
                    intent.putExtra("itemtag", itemid);
                    mcontext.startActivity(intent);
                    break;
                //点击评论跳转到详情页
                case R.id.help_comment:
                    intent = new Intent(Http_Application.getContext(), Clocle_help_details.class);
                    intent.putExtra("itemtag", itemid);
                    mcontext.startActivity(intent);
                    break;
                case R.id.help_order:
                    new AlertDialog.Builder(mcontext).setTitle("确认帮助TA")
                            .setMessage("帮助TA后您可获取300颗圈圈豆，请尽快帮TA完成").setNegativeButton("我能完成", null).setPositiveButton("点错了", null).show();
                default:
                    break;
            }
        }

    }

    /**
     * image的加载用了fresco
     */

    class ViewHolderwithMutiImg extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView time;
        public TextView contexttext;//正文
        public SimpleDraweeView photo;
        public TextView school;
        public TextView help_comment;
        public TextView help_order;
        public SimpleDraweeView help_imgs1;
        public SimpleDraweeView help_imgs2;
        public SimpleDraweeView help_imgs3;
        public ImageView sex;

        //private RecycleViewAdapter.OnItemClickListener monItemClickListener;

        public ViewHolderwithMutiImg(View itemView) {

            super(itemView);


            name = (TextView) itemView.findViewById(R.id.namedongtai);
            contexttext = (TextView) itemView.findViewById(R.id.contextdongtai);
            time = (TextView) itemView.findViewById(R.id.timetext);
            photo = (SimpleDraweeView) itemView.findViewById(R.id.help_userphoto3);
            school = (TextView) itemView.findViewById(R.id.school);
            help_comment = (TextView) itemView.findViewById(R.id.help_comment);
            help_imgs1 = (SimpleDraweeView) itemView.findViewById(R.id.help_imgs1);
            //获取view的宽高像素
          //  ViewGroup.LayoutParams lp1 = help_imgs1.getLayoutParams();
          //  lp1.height = img1height;
          //  lp1.width = img1width;

            help_imgs2 = (SimpleDraweeView) itemView.findViewById(R.id.help_imgs2);
         //   ViewGroup.LayoutParams lp2 = help_imgs2.getLayoutParams();
         //   lp2.height = img1height;
        //    lp2.width = img1width;
            help_imgs3 = (SimpleDraweeView) itemView.findViewById(R.id.help_imgs3);
        //    ViewGroup.LayoutParams lp3 = help_imgs3.getLayoutParams();
        //    lp3.height = img1height;
        //    lp3.width = img1width;

            sex = (ImageView) itemView.findViewById(R.id.help_sex);
            help_order = (TextView) itemView.findViewById(R.id.help_order);
            help_order.setOnClickListener(this);
            photo.setOnClickListener(this);
            help_comment.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent intent = null;
            String itemid = (String) itemView.getTag();
            switch (v.getId()) {
                case R.id.help_userphoto3:
                    //跳转到查看个人信息的页面

                    intent = new Intent(Http_Application.getContext(), Other_Self_infos.class);
                    intent.putExtra("itemtag", itemid);
                    mcontext.startActivity(intent);


                    break;
                case R.id.help_comment:
                    Log.i("tag2", "查看悬赏" + itemView.getTag());
                    intent = new Intent(Http_Application.getContext(), Clocle_help_details.class);
                    mcontext.startActivity(intent);
                    break;


                case R.id.help_order:
                    new AlertDialog.Builder(mcontext).setTitle("确认帮助TA")
                            .setMessage("帮助TA后您可获取300颗圈圈豆，请尽快帮TA完成").setNegativeButton("我能完成", null).setPositiveButton("点错了", null).show();
                default:
                    break;
            }
        }
    }
}
