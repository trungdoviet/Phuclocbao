var randomScalingFactor = function(){ return Math.round(Math.random()*1000)};
	
	/*var lineChartData = {
			labels : ["January","February","March","April","May","June","July"],
			datasets : [
				{
					label: "My First dataset",
					fillColor : "rgba(220,220,220,0.2)",
					strokeColor : "rgba(220,220,220,1)",
					pointColor : "rgba(220,220,220,1)",
					pointStrokeColor : "#fff",
					pointHighlightFill : "#fff",
					pointHighlightStroke : "rgba(220,220,220,1)",
					data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
				},
				{
					label: "My Second dataset",
					fillColor : "rgba(48, 164, 255, 0.2)",
					strokeColor : "rgba(48, 164, 255, 1)",
					pointColor : "rgba(48, 164, 255, 1)",
					pointStrokeColor : "#fff",
					pointHighlightFill : "#fff",
					pointHighlightStroke : "rgba(48, 164, 255, 1)",
					data : [randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor(),randomScalingFactor()]
				}
			]

		}*/
		
	var barChartData = {
			labels : ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"],
			datasets : [
				{
					fillColor : "rgba(48, 164, 255, 0.2)",
					strokeColor : "rgba(48, 164, 255, 0.8)",
					highlightFill : "rgba(48, 164, 255, 0.75)",
					highlightStroke : "rgba(48, 164, 255, 1)",
					data : profitByMonth
				},
				{
					fillColor : "rgba(228, 163, 58, 0.2)",
					strokeColor : "rgba(228, 163, 58, 0.8)",
					highlightFill: "rgba(228, 163, 58,0.75)",
					highlightStroke: "rgba(228, 163, 58,1)",
					
					data : costByMonth
				}
			]
	
		}

	var pieIncomeData = [
				{
					value: curMonthPayoffProfit,
					color:"#30a5ff",
					highlight: "#62b9fb",
					label: "Thanh lý hợp đồng"
				},
				{
					value: curMonthActualProfit,
					color: "#1ebfae",
					highlight: "#3cdfce",
					label: "Tổng Lợi nhuận"
				}

			];
	var pieCostData = [
				{
					value: curMonthRentingNew,
					color: "#ffb53e",
					highlight: "#fac878",
					label: "Cho thuê mới"
				},
				{
					value: curMonthOtherCost,
					color: "#f9243f",
					highlight: "#f6495f",
					label: "Chi phí khác"
				}
		
			];
			
	/*var doughnutData = [
					{
						value: 300,
						color:"#30a5ff",
						highlight: "#62b9fb",
						label: "Blue"
					},
					{
						value: 50,
						color: "#ffb53e",
						highlight: "#fac878",
						label: "Orange"
					},
					{
						value: 100,
						color: "#1ebfae",
						highlight: "#3cdfce",
						label: "Teal"
					},
					{
						value: 120,
						color: "#f9243f",
						highlight: "#f6495f",
						label: "Red"
					}
	
				];*/

function onLoadChart(){
	/*var chart1 = document.getElementById("line-chart").getContext("2d");
	window.myLine = new Chart(chart1).Line(lineChartData, {
		responsive: true
	});*/
	var chart2 = document.getElementById("bar-chart").getContext("2d");
	window.myBar = new Chart(chart2).Bar(barChartData, {
		responsive : true
	});
	/*var chart3 = document.getElementById("doughnut-chart").getContext("2d");
	window.myDoughnut = new Chart(chart3).Doughnut(doughnutData, {responsive : true
	});*/
	var chart4 = document.getElementById("pie-chart-income").getContext("2d");
	window.myPie = new Chart(chart4).Pie(pieIncomeData, {responsive : true
	});
	var chart5 = document.getElementById("pie-chart-cost").getContext("2d");
	window.myPie = new Chart(chart5).Pie(pieCostData, {responsive : true
	});
	
};