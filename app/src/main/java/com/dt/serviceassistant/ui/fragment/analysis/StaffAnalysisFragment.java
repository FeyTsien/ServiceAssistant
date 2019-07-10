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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dt.serviceassistant.R;
import com.dt.serviceassistant.bean.StaffAnalysisBean;
import com.dt.serviceassistant.mvp.MVPContract;
import com.dt.serviceassistant.mvp.MVPFragment;
import com.dt.serviceassistant.mvp.MVPPresenter;
import com.dt.serviceassistant.ui.adapter.MyBaseAdapter;
import com.dt.serviceassistant.ui.custom.MyMarkerView;
import com.dt.serviceassistant.utils.UrlUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tsienlibrary.bean.CommonBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 业务员分析
 */

public class StaffAnalysisFragment extends MVPFragment<MVPContract.View, MVPPresenter> implements MVPContract.View, OnChartValueSelectedListener {
    private String TAG = getClass().getSimpleName();


    private List<StaffAnalysisBean.AnnualRankingBean> mAnnualRankingList;
    private List<StaffAnalysisBean.MonthRankingBean> mMonthRankingList;
    private List<StaffAnalysisBean.MonthlyStatisticsBean> mMonthlyStatisticsList;
    List<ValuesBean> mValuesList = new ArrayList<>();

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
    protected int getLayoutId() {
        return R.layout.fragment_staff_analysis;
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
                    tvRank.setTextColor(0xFFF5891D);
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
                    tvRank.setTextColor(0xFFF5891D);
                } else if (position == 2) {
                    tvRank.setTextColor(0xFFF5CF1D);
                }
                tvRank.setText("" + (position + 1));
                tvName.setText(mMonthRankingList.get(position).getName());
                tvWeight.setText(mMonthRankingList.get(position).getWeight() + "吨");
                progressBar.setMax(100);
                int progressValue = (int) (mMonthRankingList.get(position).getWeight() / mMonthRankingList.get(0).getWeight() * 100);
                if (progressValue > 100) {
                    progressValue = 100;
                }
                progressBar.setProgress(progressValue);
            }
        };
        mRecyclerViewAnnual.setAdapter(mAdapterAnnual);
        mRecyclerViewMonth.setAdapter(mAdapterMonth);

        initLineChart();

        //触动下拉刷新
        smartRefreshLayout.autoRefresh();
        //刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                request();
            }
        });
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
        chart.getDescription().setEnabled(false); // 右下角的描述文本
        chart.setTouchEnabled(true);     //能否点击
        chart.setDragEnabled(false);   //能否拖拽
        chart.setScaleEnabled(false);  //能否缩放

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

        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart
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
        mPresenter.request(UrlUtils.BOSS_STAFF_ANALYSIS, "", StaffAnalysisBean.class);
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

        mValuesList.clear();
        for (int i = 0; i < mMonthlyStatisticsList.size(); i++) {
            ValuesBean values = new ValuesBean();
            List<Entry> valueList = new ArrayList<>();
            for (int j = 0; j < mMonthlyStatisticsList.get(i).getWorkload().size(); j++) {
                valueList.add(new Entry(j, (float) mMonthlyStatisticsList.get(i).getWorkload().get(j).getWeight(), mMonthlyStatisticsList.get(i).getWorkload().get(j).getMonth()));//
            }
            values.setName(mMonthlyStatisticsList.get(i).getStaff_name());
            values.setColor(mMonthlyStatisticsList.get(i).getColor());
            values.setValues(valueList);
            mValuesList.add(values);
        }

        refreshUI(staffAnalysisBean);
        initLineChartXY();
        setLineChartData();
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


    /**
     * 获取到数据后，再初始化X轴和Y轴
     */
    private void initLineChartXY() {

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
        xAxis.setTextColor(getResources().getColor(R.color.colorTextTitle));//字体颜色
//        xAxis.setAxisMinimum(0f);//设置x轴的最小值 //`
//        xAxis.setAxisMaximum(12f);//设置X最大值 //
        xAxis.setLabelCount(mValuesList.get(0).getValues().size() - 1);  //设置X轴的显示个数
        xAxis.setAxisLineWidth(0.5f);//设置x轴线宽度
        xAxis.setAxisLineColor(Color.BLACK);//设置X轴线颜色
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X轴显示位置
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setAvoidFirstLastClipping(false);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        //自定义X轴刻度值
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String data = "" + mValuesList.get(0).getValues().get((int) value).getData();
                return data;
            }
        });

        //Y轴设置(左侧)
        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setTypeface(tfLight);
        leftAxis.setTextSize(11f);//Y轴字体大小
        leftAxis.setTextColor(getResources().getColor(R.color.colorTextTitle));//Y轴字体颜色
        leftAxis.setAxisLineWidth(0.5f);//Y轴线宽
        leftAxis.setAxisLineColor(Color.BLACK);//Y轴线颜色
//        leftAxis.setAxisMaximum(200f);//Y轴最大值
        leftAxis.setAxisMinimum(0f);//Y轴最小值
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
    }

    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();

    /**
     * 给折线图填入数据，显示图谱
     */
    private void setLineChartData() {

//        LineDataSet[] lineDataSets = new LineDataSet[mValuesList.size()];
        LineDataSet lineDataSet;
        //判断图表中原来是否有数据
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            //获取数据
            for (int i = 0; i < mValuesList.size(); i++) {
                lineDataSet = (LineDataSet) chart.getData().getDataSetByIndex(i);
                lineDataSet.setValues(mValuesList.get(i).getValues());
                iLineDataSets.set(i, lineDataSet);
            }
            //刷新数据，刷新图谱（好像不好使）
//            chart.getData().notifyDataChanged();
//            chart.notifyDataSetChanged();
            chart.invalidate();//绘制
        } else {
            // create a dataset and give it a type
            for (int i = 0; i < mValuesList.size(); i++) {
                int color =  Color.parseColor(mValuesList.get(i).getColor());
                lineDataSet = new LineDataSet(mValuesList.get(i).getValues(), mValuesList.get(i).getName());
                lineDataSet.setMode( LineDataSet.Mode.CUBIC_BEZIER);//折线的样式
                lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);//数值是以左侧Y轴为标准
                lineDataSet.setColor(color);//折线颜色
                lineDataSet.setLineWidth(2f);//折线宽度
                lineDataSet.setCircleColor(color);//折线顶点颜色
                lineDataSet.setCircleRadius(3f);//顶点半径
                lineDataSet.setDrawCircleHole(true);//是否显示空心的顶点
//            set1.setDrawFilled(true);//显示折线图之下填充色
                lineDataSet.setFillColor(Color.GREEN);//填充的颜色
                lineDataSet.setFillAlpha(65);//填充色的透明度
//            set1.setHighlightEnabled(true);//是否显示十字横纵轴
                lineDataSet.setHighLightColor(color);//十字轴颜色
                lineDataSet.setDrawValues(true);//是否绘制数值
                lineDataSet.setValueTextColor(color);

                iLineDataSets.add(lineDataSet);
            }

            // create a data object with the data sets
            LineData data = new LineData(iLineDataSets);
//            data.setValueTextColor(Color.BLACK);//折线顶点字体颜色
            data.setDrawValues(false);//是否绘制数值
            data.setValueTextSize(9f);//数值字体大小
            data.setHighlightEnabled(true);//是否显示十字横纵轴

            // set data
            chart.setData(data);
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        //根据点击的位置自动移动
        chart.centerViewToAnimated(e.getX(), e.getY(), chart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency(), 500);
    }

    @Override
    public void onNothingSelected() {

    }


    private class ValuesBean {
        String name;
        String color;
        List<Entry> values;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public List<Entry> getValues() {
            return values;
        }

        public void setValues(List<Entry> values) {
            this.values = values;
        }
    }
}
