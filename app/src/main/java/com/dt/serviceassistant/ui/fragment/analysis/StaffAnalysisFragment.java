package com.dt.serviceassistant.ui.fragment.analysis;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.app.AppData;
import com.dt.serviceassistant.bean.AnalysisBean;
import com.dt.serviceassistant.bean.AppBean;
import com.dt.serviceassistant.bean.StaffAnalysisBean;
import com.dt.serviceassistant.mvp.MVPContract;
import com.dt.serviceassistant.mvp.MVPFragment;
import com.dt.serviceassistant.mvp.MVPPresenter;
import com.dt.serviceassistant.ui.activity.mainboss.MainBossActivity;
import com.dt.serviceassistant.ui.adapter.MyBaseAdapter;
import com.dt.serviceassistant.utils.DateUtils;
import com.dt.serviceassistant.utils.UrlUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tsienlibrary.bean.CommonBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 业务员分析
 */

public class StaffAnalysisFragment extends MVPFragment<MVPContract.View, MVPPresenter> implements MVPContract.View, OnChartValueSelectedListener {
    private String TAG = getClass().getSimpleName();


    private List<StaffAnalysisBean.AnnualRankingBean> mAnnualRankingList;
    private List<StaffAnalysisBean.MonthRankingBean> mMonthRankingList;
    private List<StaffAnalysisBean.MonthlyStatisticsBean> mMonthlyStatisticsList;
    List<ValuesBean> mValuesList = new ArrayList<>();

    private String mKeyword;
    private String mStartTime;
    private String mEndTime;
    private int mAnalysistype;
    private int mStart = 0;

    private View mRootView;
    private MyBaseAdapter mAdapterAnnual;
    private MyBaseAdapter mAdapterMonth;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_annual_cumulative)
    TextView mTvAnnualCumulative;
    @BindView(R.id.tv_month_cumulative)
    TextView mTvMonthCumulative;
    @BindView(R.id.recycler_view_annual)
    RecyclerView mRecyclerViewAnnual;
    @BindView(R.id.recycler_view_month)
    RecyclerView mRecyclerViewMonth;
    @BindView(R.id.chart1)
    LineChart chart;

    public static StaffAnalysisFragment newInstance() {
        return new StaffAnalysisFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_staff_analysis, container, false);
        ButterKnife.bind(this, mRootView);
        initData();
        initView();
        return mRootView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_analysis;
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mAnnualRankingList = new ArrayList<>();
        mMonthRankingList = new ArrayList<>();
        mMonthlyStatisticsList = new ArrayList<>();

//        mAnalysistype = getArguments().getInt(MainBossActivity.ANALYSIS_TYPE, 0);
    }


    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewAnnual.setLayoutManager(layoutManager);
        mRecyclerViewMonth.setLayoutManager(layoutManager2);

        mAdapterAnnual = new MyBaseAdapter<StaffAnalysisBean.AnnualRankingBean>(mAnnualRankingList, R.layout.item_ranking_staff) {
            @SuppressLint("SetTextI18n")
            @Override
            public void bindView(MyViewHolder holder, int position) {
                TextView tvRank = holder.getView(R.id.tv_rank);
                TextView tvName = holder.getView(R.id.tv_name);
                TextView tvWeight = holder.getView(R.id.tv_weight);
                ProgressBar progressBar = holder.getView(R.id.progress_bar);

                if (position == 0) {
                    tvRank.setTextColor(0xFFF51D1D);
                } else if (position == 1) {
                    tvRank.setTextColor(0xFFF5691D);
                } else if (position == 2) {
                    tvRank.setTextColor(0xFFF5CF1D);
                }
                tvRank.setText("" + (position + 1));
                tvName.setText(mAnnualRankingList.get(position).getName());
                tvWeight.setText(mAnnualRankingList.get(position).getWeight() + "吨");
                progressBar.setMax(100);
                int progressValue = (int) (mAnnualRankingList.get(position).getWeight() / mAnnualRankingList.get(0).getWeight() * 100);
                if (progressValue > 100) {
                    progressValue = progressValue - 100;
                }
                progressBar.setProgress(progressValue);
            }
        };

        mAdapterMonth = new MyBaseAdapter<StaffAnalysisBean.MonthRankingBean>(mMonthRankingList, R.layout.item_ranking_staff) {
            @SuppressLint("SetTextI18n")
            @Override
            public void bindView(MyViewHolder holder, int position) {
                TextView tvRank = holder.getView(R.id.tv_rank);
                TextView tvName = holder.getView(R.id.tv_name);
                TextView tvWeight = holder.getView(R.id.tv_weight);
                ProgressBar progressBar = holder.getView(R.id.progress_bar);

                if (position == 0) {
                    tvRank.setTextColor(0xFFF51D1D);
                } else if (position == 1) {
                    tvRank.setTextColor(0xFFF5691D);
                } else if (position == 2) {
                    tvRank.setTextColor(0xFFF5CF1D);
                }
                tvRank.setText("" + (position + 1));
                tvName.setText(mMonthRankingList.get(position).getName());
                tvWeight.setText(mMonthRankingList.get(position).getWeight() + "吨");
                progressBar.setMax(100);
                int progressValue = (int) (mMonthRankingList.get(position).getWeight() / mMonthRankingList.get(0).getWeight() * 100);
                if (progressValue > 100) {
                    progressValue = progressValue - 100;
                }
                progressBar.setProgress(progressValue);
            }
        };
        mRecyclerViewAnnual.setAdapter(mAdapterAnnual);
        mRecyclerViewMonth.setAdapter(mAdapterMonth);

        initLineChart();

        //刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                request();
            }
        });
        request();
    }

    /**
     * 初始化折线图控件属性
     */
    private void initLineChart() {
        chart.setNoDataText("没有数据");//没有数据时显示的文字
        chart.setNoDataTextColor(Color.WHITE);//没有数据时显示文字的颜色
        chart.setDrawGridBackground(false);//chart 绘图区后面的背景矩形将绘制
        chart.setDrawBorders(false);//是否禁止绘制图表边框的线
        chart.setOnChartValueSelectedListener(this);

        // 右下角的描述文本
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        chart.setDragDecelerationFrictionCoef(0.9f);

        //启用缩放和拖动
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setHighlightPerDragEnabled(true);

        // 如果禁用，可以分别在x轴和y轴上缩放
        chart.setPinchZoom(false);
        //不展示Y轴右侧刻度
        chart.getAxisRight().setEnabled(false);

        //设置整体控件背景颜色
        chart.setBackgroundColor(Color.WHITE);

        chart.animateX(1500);
        // get the legend (only possible after setting data)
        Legend legend = chart.getLegend();

        // 设置图例
        legend.setForm(Legend.LegendForm.LINE);
//        legend.setTypeface(tfLight);
        legend.setTextSize(11f);
        legend.setTextColor(Color.BLACK);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
//        legend.setYOffset(11f);

        //X轴设置
        XAxis xAxis = chart.getXAxis();
//        xAxis.setTypeface(tfLight);//设置字体样式
        xAxis.setTextSize(11f);//X轴字体大小
        xAxis.setTextColor(Color.BLACK);//字体颜色
//        xAxis.setAxisMinimum(0f);//设置x轴的最小值 //`
//        xAxis.setAxisMaximum(12f);//设置X最大值 //
//        xAxis.setLabelCount(12);  //设置X轴的显示个数
        xAxis.setAxisLineColor(Color.BLACK);//设置X轴线颜色
        xAxis.setAxisLineWidth(0.5f);//设置x轴线宽度
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X轴显示位置
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setAvoidFirstLastClipping(false);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return mValues1.get((int) value).getData() + "";
            }
        });

        //Y轴设置(左侧)
        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
//        leftAxis.setAxisMaximum(200f);//Y轴最大值
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
//
//        leftAxis.setValueFormatter(new IAxisValueFormatter() {
//                                       @Override
//                                       public String getFormattedValue(float value, AxisBase axis) {
//                                           return "￥"+ value;
//                                       }


//
//        //Y轴设置(右侧)
//        YAxis rightAxis = chart.getAxisRight();
////        rightAxis.setTypeface(tfLight);
//        rightAxis.setTextColor(Color.RED);
//        rightAxis.setAxisMaximum(900);
//        rightAxis.setAxisMinimum(-200);
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setDrawZeroLine(false);
//        rightAxis.setGranularityEnabled(false);
        setData();
    }

    private void setData() {

        LineDataSet set1, set2;
        List<LineDataSet> lineDataSets = new ArrayList<>();
        //判断图表中原来是否有数据
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            //获取数据
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) chart.getData().getDataSetByIndex(1);
            set1.setValues(mValues1);
            set2.setValues(mValues2);
            //刷新数据
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(mValues1, "DataSet 1");
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);//数值是以左侧Y轴为标准
            set1.setColor(ColorTemplate.getHoloBlue());//折线颜色
            set1.setLineWidth(2f);//折线宽度
            set1.setCircleColor(ColorTemplate.getHoloBlue());//折线顶点颜色
            set1.setCircleRadius(3f);//顶点半径
            set1.setDrawCircleHole(true);//是否显示空心的顶点
//            set1.setDrawFilled(true);//显示折线图之下填充色
            set1.setFillColor(Color.GREEN);//填充的颜色
            set1.setFillAlpha(65);//填充色的透明度
//            set1.setHighlightEnabled(true);//是否显示十字横纵轴
            set1.setHighLightColor(ColorTemplate.getHoloBlue());//十字轴颜色
            set1.setDrawValues(true);//是否绘制数值
            set1.setValueTextColor(ColorTemplate.getHoloBlue());
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = new LineDataSet(mValues2, "DataSet 2");
            set2.setAxisDependency(YAxis.AxisDependency.LEFT);
            set2.setColor(Color.RED);
            set2.setCircleColor(Color.RED);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            set2.setDrawValues(false);//是否显示数值
            //set2.setFillFormatter(new MyFillFormatter(900f));

            // create a data object with the data sets
            LineData data = new LineData(set1, set2);
//            data.setValueTextColor(Color.BLACK);//折线顶点字体颜色
            data.setDrawValues(false);//是否绘制数值
            data.setValueTextSize(9f);//数值字体大小
            data.setHighlightEnabled(true);//是否显示十字横纵轴

            // set data
            chart.setData(data);
        }
    }

    /**
     * 请求
     */
    private void request() {
//        AppBean.DataBean appDataBean = new AppBean.DataBean();
//        appDataBean.setUserid(AppData.getUserId());
//        appDataBean.setKeyword(mKeyword);
//        appDataBean.setStarttime(mStartTime);
//        appDataBean.setEndtime(mEndTime);
//        appDataBean.setAnalysistype(mAnalysistype);
//        appDataBean.setStart(mStart);
//        Gson gson = new Gson();
//        String jsonData = gson.toJson(appDataBean);
//        mPresenter.request(UrlUtils.BOSS_ANALYSIS_LIST, jsonData, AnalysisBean.class);
        mPresenter.request("7Oh6OScO", "", StaffAnalysisBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        //关闭下拉
        smartRefreshLayout.finishRefresh();

        StaffAnalysisBean staffAnalysisBean = (StaffAnalysisBean) commonBean.getData();
        mAnnualRankingList.clear();
        mAnnualRankingList.addAll(staffAnalysisBean.getAnnual_ranking());
        mMonthRankingList.clear();
        mMonthRankingList.addAll(staffAnalysisBean.getMonth_ranking());
        mMonthlyStatisticsList.clear();
        mMonthlyStatisticsList.addAll(staffAnalysisBean.getMonthly_statistics());

        for (int i = 0; i < mMonthlyStatisticsList.size(); i++) {
            ValuesBean values = new ValuesBean();
            List<Entry> valueList = new ArrayList<>();
            for (int j = 0; i < mMonthlyStatisticsList.get(i).getWorkload().size(); j++) {
                valueList.add(new Entry(0, (float) mMonthlyStatisticsList.get(i).getWorkload().get(j).getWeight(), mMonthlyStatisticsList.get(i).getWorkload().get(j).getMonth()));
            }
            values.setName(mMonthlyStatisticsList.get(i).getStaff_name());
            values.setValues(valueList);
            mValuesList.add(values);
        }
        refreshUI(staffAnalysisBean);
    }

    @Override
    public void requestFail(String requestUrl, String msg) {
        super.requestFail(requestUrl, msg);
        //关闭下拉
        smartRefreshLayout.finishRefresh();
        ToastUtils.showLong(msg);
    }

    @SuppressLint("SetTextI18n")
    private void refreshUI(StaffAnalysisBean staffAnalysisBean) {
        mTvAnnualCumulative.setText(staffAnalysisBean.getAnnual_cumulative() + "吨");
        mTvMonthCumulative.setText(staffAnalysisBean.getMonth_cumulative() + "吨");
        mAdapterAnnual.notifyDataSetChanged();
        mAdapterMonth.notifyDataSetChanged();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private class ValuesBean {
        String name;
        List<Entry> values;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Entry> getValues() {
            return values;
        }

        public void setValues(List<Entry> values) {
            this.values = values;
        }
    }
}
