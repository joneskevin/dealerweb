<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>试驾率环比</title>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>

<body>

<section class="bd" id="rate">

	<div class="bd_rt">
		<div class="bd_rt_nav clearfix">
			<span>主界面 > 试驾率环比</span>

			<p>
				<select name="region" id="region" class="select">
					<option value="0">大华北区</option>
					<option value="1">大华北区</option>
					<option value="2">大华北区</option>
				</select>
				<select name="time_y" id="time_y" class="select">
					<option value="0">2014年</option>
					<option value="1">2014年</option>
					<option value="2">2014年</option>
				</select>
				<select name="time_m" id="time_m" class="select">
					<option value="0">3月</option>
					<option value="1">3月</option>
					<option value="2">3月</option>
				</select>
				<select name="time_n" id="time_n" class="select">
					<option value="0">3个月</option>
					<option value="1">3个月</option>
					<option value="2">3个月</option>
				</select>
			</p>
			<strong id="rate_type_a">试驾率低于20%的经销商</strong>
			<strong id="rate_type_b" class="active">试驾车环比下降5%的经销商</strong>
			<strong id="rate_filter">筛选</strong>
		</div>

		<aside class="bd_rt_main clearfix">
			<div class="rate_curve">
				<strong>试驾率环比</strong>

				<ul class="curve_explain clearfix">
					<li><span>试驾率（%）</span></li>
					<li class="type_b"><span>RBO试驾率</span></li>
					<li class="type_c"><span>全国平均</span></li>
				</ul>

				<div id="curve"><img src="images/curve.jpg" width="100%"></div><!--/#curve-->

				<footer><a><span>图片导出</span></a></footer>
			</div><!--/rate_curve-->
			
			<div class="table_list">
				<ul class="table_hd clearfix">
					<li class="li_1">经销商名称</li>
            		<li class="li_2">当月试驾率(%)</li>
            		<li class="li_3">所属小区</li>
            		<li class="li_4">大区</li>
				</ul>
				<ul class="table_bd clearfix odd">
					<li class="li_1">大连德瑞通汽车销售服务有限公司</li>
            		<li class="li_2"><span>18.48</span></li>
            		<li class="li_3"><span>翼南区</span></li>
            		<li class="li_4"><span>东北区</span></li>
				</ul>
				<ul class="table_bd clearfix even">
					<li class="li_1">大连德瑞通汽车销售服务有限公司</li>
            		<li class="li_2"><span>18.48</span></li>
            		<li class="li_3"><span>翼南区</span></li>
            		<li class="li_4"><span>东北区</span></li>
				</ul>
				<ul class="table_bd clearfix odd">
					<li class="li_1">大连德瑞通汽车销售服务有限公司</li>
            		<li class="li_2"><span>18.48</span></li>
            		<li class="li_3"><span>翼南区</span></li>
            		<li class="li_4"><span>东北区</span></li>
				</ul>
				<ul class="table_bd clearfix even">
					<li class="li_1">大连德瑞通汽车销售服务有限公司</li>
            		<li class="li_2"><span>18.48</span></li>
            		<li class="li_3"><span>翼南区</span></li>
            		<li class="li_4"><span>东北区</span></li>
				</ul>
				<ul class="table_bd clearfix odd">
					<li class="li_1">大连德瑞通汽车销售服务有限公司</li>
            		<li class="li_2"><span>18.48</span></li>
            		<li class="li_3"><span>翼南区</span></li>
            		<li class="li_4"><span>东北区</span></li>
				</ul>
				<ul class="table_bd clearfix even">
					<li class="li_1">大连德瑞通汽车销售服务有限公司</li>
            		<li class="li_2"><span>18.48</span></li>
            		<li class="li_3"><span>翼南区</span></li>
            		<li class="li_4"><span>东北区</span></li>
				</ul>
				<ul class="table_bd clearfix odd">
					<li class="li_1">大连德瑞通汽车销售服务有限公司</li>
            		<li class="li_2"><span>18.48</span></li>
            		<li class="li_3"><span>翼南区</span></li>
            		<li class="li_4"><span>东北区</span></li>
				</ul>
				<ul class="table_bd clearfix even">
					<li class="li_1">大连德瑞通汽车销售服务有限公司</li>
            		<li class="li_2"><span>18.48</span></li>
            		<li class="li_3"><span>翼南区</span></li>
            		<li class="li_4"><span>东北区</span></li>
				</ul>
				<ul class="table_bd clearfix odd">
					<li class="li_1">大连德瑞通汽车销售服务有限公司</li>
            		<li class="li_2"><span>18.48</span></li>
            		<li class="li_3"><span>翼南区</span></li>
            		<li class="li_4"><span>东北区</span></li>
				</ul>
				<ul class="table_bd clearfix even">
					<li class="li_1">大连德瑞通汽车销售服务有限公司</li>
            		<li class="li_2"><span>18.48</span></li>
            		<li class="li_3"><span>翼南区</span></li>
            		<li class="li_4"><span>东北区</span></li>
				</ul>
				<ul class="table_bd clearfix odd">
					<li class="li_1">大连德瑞通汽车销售服务有限公司</li>
            		<li class="li_2"><span>18.48</span></li>
            		<li class="li_3"><span>翼南区</span></li>
            		<li class="li_4"><span>东北区</span></li>
				</ul>
				<ul class="table_bd clearfix even">
					<li class="li_1">大连德瑞通汽车销售服务有限公司</li>
            		<li class="li_2"><span>18.48</span></li>
            		<li class="li_3"><span>翼南区</span></li>
            		<li class="li_4"><span>东北区</span></li>
				</ul>
				<ul class="table_bd clearfix odd">
					<li class="li_1">大连德瑞通汽车销售服务有限公司</li>
            		<li class="li_2"><span>18.48</span></li>
            		<li class="li_3"><span>翼南区</span></li>
            		<li class="li_4"><span>东北区</span></li>
				</ul>
				<ul class="table_bd clearfix even">
					<li class="li_1">大连德瑞通汽车销售服务有限公司</li>
            		<li class="li_2"><span>18.48</span></li>
            		<li class="li_3"><span>翼南区</span></li>
            		<li class="li_4"><span>东北区</span></li>
				</ul>
			</div><!--/table_list-->
			<div class="filter_info">
				<p>
					<span class="clearfix"><b>当月试驾率升序排列</b><i class="checkbox active text_over">checkbox</i></span>
					<span class="clearfix"><b>当月试驾率降序排列</b><i class="checkbox text_over">checkbox</i></span>
				</p>
				<p>
					<span class="clearfix"><b>所属大区分类排列</b><i class="checkbox text_over">checkbox</i></span>
				</p>
				<p>
					<span class="clearfix"><b>所属小区分类排列</b><i class="checkbox text_over">checkbox</i></span>
				</p>
				<p>
					<span class="clearfix"><b>所属大区分类排列</b><i class="checkbox text_over">checkbox</i></span>
				</p>
				<p><button>筛  选</button></p>
				<div class="filter_bg"></div>
			</div><!--/filter_info-->
		</aside><!--/bd_rt_main-->
	</div><!--/.bd_rt-->
</section><!--/.bd-->

</body>
</html>