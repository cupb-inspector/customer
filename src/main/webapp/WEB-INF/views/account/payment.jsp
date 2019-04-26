<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/commons.jsp" />
<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="">
<!--<![endif]-->

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>财富充值</title>
<meta name="description" content="Ela Admin - HTML5 Admin Template">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="assets/css/normalize.css">
<link rel="stylesheet" href="assets/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/font-awesome.min.css">
<link rel="stylesheet" href="assets/css/themify-icons.css">
<link rel="stylesheet" href="assets/css/pe-icon-7-filled.css">
<link rel="stylesheet" href="assets/css/flag-icon.min.css">
<link rel="stylesheet" href="assets/css/cs-skin-elastic.css">
<link rel="stylesheet" href="assets/css/style.css">
<!-- <script type="text/javascript" src="https://cdn.jsdelivr.net/html5shiv/3.7.3/html5shiv.min.js"></script> -->
<link href="assets/weather/css/weather-icons.css" rel="stylesheet" />
<link href="assets/calendar/fullcalendar.css" rel="stylesheet" />

<link href="assets/css/charts/chartist.min.css" rel="stylesheet">
<link href="assets/css/lib/vector-map/jqvmap.min.css" rel="stylesheet">
<script src="js/jquery.min.js"></script>

<style>
        html,
        body {
            margin: 0px;
            width: 100%;
            height: 100%;
        }
#weatherWidget .currentDesc {
	color: #ffffff !important;
}

.traffic-chart {
	min-height: 335px;
}

#flotPie1 {
	height: 150px;
}

#flotPie1 td {
	padding: 3px;
}

#flotPie1 table {
	top: 20px !important;
	right: -10px !important;
}

.chart-container {
	display: table;
	min-width: 270px;
	text-align: left;
	padding-top: 10px;
	padding-bottom: 10px;
}

#flotLine5 {
	height: 105px;
}

#flotBarChart {
	height: 150px;
}

#cellPaiChart {
	height: 160px;
}
</style>
 <script type="text/javascript">  
        function jqSubmit() {
           // {# $('#fafafa')[0]#}
            var file_obj = document.getElementById('file').files[0];
            var value =$("#value").val()
            var notes =$("#notes").val()
            console.log(value+"\t"+notes)
            var fd = new FormData();
            fd.append('value', value)
            fd.append('notes', notes)
            fd.append('file', file_obj);

            $.ajax({
                url:'account-charge',
                type:'POST',
                data:fd,
                dataType : "json",//预期服务器返回的数据类型
                processData:false,  //tell jQuery not to process the data
                contentType: false,  //tell jQuery not to set contentType
                //这儿的三个参数其实就是XMLHttpRequest里面带的信息。
                success:function (result,arg,a1,a2) {
                	 console.log(result);
                    console.log(arg);
                    console.log(a1);
                    console.log(a2);
            		if (result.resultCode == 200) {
        				//thisE.previousElementSibling.innerHTML = "允许"
        					//自动消失的消息
        					$('.alert').removeClass('alert-warning')
        					$('.alert').html('提交成功').addClass('alert-success').show().delay(2000).fadeOut();
            		}
                    
                }

            })
        }
</script>    

</head>

<body>


	<!-- Content -->
        <div class="content"  style="background:#f1f2f7;height: 100%;">
		<!-- Animated -->
		<div class="animated fadeIn">
			<!--  moneytable  -->
			<div class="row">
				<div class="col-md-12">
					<div class="card">
						<div class="card-header">
							<h4>充值</h4>
						</div>
						<div class="card-body">
							<div class="form-horizontal">

								<div class="row form-group">
									<div class="col col-md-3">
										<label for="text-input" class=" form-control-label">充值金额</label>
									</div>
									<div class="col-12 col-md-9">
										<input type="number" id="value" name="text-input"
											placeholder="金额数值为整数" class="form-control" value="100.00">
									</div>
								</div>

								<div class="row form-group">
									<div class="col col-md-3">
										<label for="text-input" class=" form-control-label">充值凭证</label>
									</div>
									<div class="col-12 col-md-9">
										<input type="file" id="file" name="text-input" class="form-control">
									</div>
								</div>
								
								<div class="row form-group">
									<div class="col col-md-3">
										<label for="text-input" class=" form-control-label">备注</label>
									</div>
									<div class="col-12 col-md-9">
										<input type="text" id="notes" name="notes"
											placeholder="时间" class="form-control" >
									</div>
								</div>


								<div>
									<a href='#'>
										<button type="submit" onclick="jqSubmit();" id="btn"  class="btn btn-primary">
											<i class="fa fa-lock fa-lg"></i>&nbsp; 充值
										</button>
									</a>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<script src="assets/js/vendor/jquery-2.1.4.min.js"></script>
	<script src="assets/js/popper.min.js"></script>
	<script src="assets/js/plugins.js"></script>
	<script src="assets/js/main.js"></script>
	<script src="assets/js/lib/chart-js/Chart.bundle.js"></script>

	<script src="assets/js/lib/chartist/chartist.min.js"></script>
	<script src="assets/js/lib/chartist/chartist-plugin-legend.js"></script>
	<script src="assets/js/lib/flot-chart/jquery.flot.js"></script>
	<script src="assets/js/lib/flot-chart/jquery.flot.pie.js"></script>
	<script src="assets/js/lib/flot-chart/jquery.flot.spline.js"></script>
	<script src="assets/weather/js/jquery.simpleWeather.min.js"></script>
	<script src="assets/weather/js/weather-init.js"></script>
	<script src="assets/js/lib/moment/moment.js"></script>
	<script src="assets/calendar/fullcalendar.min.js"></script>
	<script src="assets/calendar/fullcalendar-init.js"></script>
	<script src="assets/js/init/weather-init.js"></script>

	<script src="https://cdn.jsdelivr.net/npm/moment@2.22.2/moment.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/fullcalendar@3.9.0/dist/fullcalendar.min.js"></script>
	<script src="assets/js/init/fullcalendar-init.js"></script>

	<!--Local Stuff-->
	<script>
		jQuery(document)
				.ready(
						function($) {
							"use strict";

							// Pie chart flotPie1
							var piedata = [ {
								label : "Desktop visits",
								data : [ [ 1, 32 ] ],
								color : '#5c6bc0'
							}, {
								label : "Tab visits",
								data : [ [ 1, 33 ] ],
								color : '#ef5350'
							}, {
								label : "Mobile visits",
								data : [ [ 1, 35 ] ],
								color : '#66bb6a'
							} ];

							$.plot('#flotPie1', piedata, {
								series : {
									pie : {
										show : true,
										radius : 1,
										innerRadius : 0.65,
										label : {
											show : true,
											radius : 2 / 3,
											threshold : 1
										},
										stroke : {
											width : 0
										}
									}
								},
								grid : {
									hoverable : true,
									clickable : true
								}
							});
							// Pie chart flotPie1  End
							// cellPaiChart
							var cellPaiChart = [ {
								label : "Direct Sell",
								data : [ [ 1, 65 ] ],
								color : '#5b83de'
							}, {
								label : "Channel Sell",
								data : [ [ 1, 35 ] ],
								color : '#00bfa5'
							} ];
							$.plot('#cellPaiChart', cellPaiChart, {
								series : {
									pie : {
										show : true,
										stroke : {
											width : 0
										}
									}
								},
								legend : {
									show : false
								},
								grid : {
									hoverable : true,
									clickable : true
								}

							});
							// cellPaiChart End
							// Line Chart  #flotLine5
							var newCust = [ [ 0, 3 ], [ 1, 5 ], [ 2, 4 ],
									[ 3, 7 ], [ 4, 9 ], [ 5, 3 ], [ 6, 6 ],
									[ 7, 4 ], [ 8, 10 ] ];

							var plot = $.plot($('#flotLine5'), [ {
								data : newCust,
								label : 'New Data Flow',
								color : '#fff'
							} ], {
								series : {
									lines : {
										show : true,
										lineColor : '#fff',
										lineWidth : 2
									},
									points : {
										show : true,
										fill : true,
										fillColor : "#ffffff",
										symbol : "circle",
										radius : 3
									},
									shadowSize : 0
								},
								points : {
									show : true,
								},
								legend : {
									show : false
								},
								grid : {
									show : false
								}
							});
							// Line Chart  #flotLine5 End
							// Traffic Chart using chartist
							if ($('#traffic-chart').length) {
								var chart = new Chartist.Line('#traffic-chart',
										{
											labels : [ 'Jan', 'Feb', 'Mar',
													'Apr', 'May', 'Jun' ],
											series : [
													[ 0, 18000, 35000, 25000,
															22000, 0 ],
													[ 0, 33000, 15000, 20000,
															15000, 300 ],
													[ 0, 15000, 28000, 15000,
															30000, 5000 ] ]
										}, {
											low : 0,
											showArea : true,
											showLine : false,
											showPoint : false,
											fullWidth : true,
											axisX : {
												showGrid : true
											}
										});

								chart
										.on(
												'draw',
												function(data) {
													if (data.type === 'line'
															|| data.type === 'area') {
														data.element
																.animate({
																	d : {
																		begin : 2000 * data.index,
																		dur : 2000,
																		from : data.path
																				.clone()
																				.scale(
																						1,
																						0)
																				.translate(
																						0,
																						data.chartRect
																								.height())
																				.stringify(),
																		to : data.path
																				.clone()
																				.stringify(),
																		easing : Chartist.Svg.Easing.easeOutQuint
																	}
																});
													}
												});
							}
							// Traffic Chart using chartist End
							//Traffic chart chart-js
							if ($('#TrafficChart').length) {
								var ctx = document
										.getElementById("TrafficChart");
								ctx.height = 150;
								var myChart = new Chart(
										ctx,
										{
											type : 'line',
											data : {
												labels : [ "Jan", "Feb", "Mar",
														"Apr", "May", "Jun",
														"Jul" ],
												datasets : [
														{
															label : "Visit",
															borderColor : "rgba(4, 73, 203,.09)",
															borderWidth : "1",
															backgroundColor : "rgba(4, 73, 203,.5)",
															data : [ 0, 2900,
																	5000, 3300,
																	6000, 3250,
																	0 ]
														},
														{
															label : "Bounce",
															borderColor : "rgba(245, 23, 66, 0.9)",
															borderWidth : "1",
															backgroundColor : "rgba(245, 23, 66,.5)",
															pointHighlightStroke : "rgba(245, 23, 66,.5)",
															data : [ 0, 4200,
																	4500, 1600,
																	4200, 1500,
																	4000 ]
														},
														{
															label : "Targeted",
															borderColor : "rgba(40, 169, 46, 0.9)",
															borderWidth : "1",
															backgroundColor : "rgba(40, 169, 46, .5)",
															pointHighlightStroke : "rgba(40, 169, 46,.5)",
															data : [ 1000,
																	5200, 3600,
																	2600, 4200,
																	5300, 0 ]
														} ]
											},
											options : {
												responsive : true,
												tooltips : {
													mode : 'index',
													intersect : false
												},
												hover : {
													mode : 'nearest',
													intersect : true
												}

											}
										});
							}
							//Traffic chart chart-js  End
							// Bar Chart #flotBarChart
							$.plot("#flotBarChart", [ {
								data : [ [ 0, 18 ], [ 2, 8 ], [ 4, 5 ],
										[ 6, 13 ], [ 8, 5 ], [ 10, 7 ],
										[ 12, 4 ], [ 14, 6 ], [ 16, 15 ],
										[ 18, 9 ], [ 20, 17 ], [ 22, 7 ],
										[ 24, 4 ], [ 26, 9 ], [ 28, 11 ] ],
								bars : {
									show : true,
									lineWidth : 0,
									fillColor : '#ffffff8a'
								}
							} ], {
								grid : {
									show : false
								}
							});
							// Bar Chart #flotBarChart End
						});
	</script>
</body>

</html>