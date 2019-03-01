//Tuner.js

// -- Tuner UI -- \\
function clearTuner() {
	document.querySelector("#tuner-container > #inputBox").innerHTML = "";
	clearChart();
}
google.charts.load('current', { 'packages': ['corechart'] });
google.charts.setOnLoadCallback(initChart);
var chartData, chart, chartIndex = 0, chartDefaultValues;
const chartMaxLengthMs = 10000;
const outputRefreshRateMs = 50;
const chartOptions = {
	series: { 1: { curveType: 'function' } }
};
function initChart() {
	chartData = new google.visualization.DataTable();
	chart = new google.visualization.LineChart(document.getElementById('chart_div'));
}
function clearChart() {
	if (chart) {
		chart.clearChart();
		chartData = new google.visualization.DataTable();
		chartData.addColumn('number', 'x');
		chartIndex = 0;
	}
	else setTimeout(clearChart, 500);
}
function initTunerOutput(names, values) {
	if (chart) {
		for (var i = 0; i < names.length; i++) {
			chartData.addColumn('number', names[i]);
		}
		chartData.addRow(values);
		chart.draw(chartData, chartOptions);
		chartDefaultValues = values;
	}
	else {
		setTimeout(function () {
			initTunerOutput(names, values);
		}, 500);
	}
}
function updateTunerOutput(values) {
	try {
		if (chart) {
			chartData.addRow(values);

			if (chartData.wg.length > (chartMaxLengthMs / outputRefreshRateMs))
				chartData.removeRow(0);

			chart.draw(chartData, chartOptions);
		}
	}
	catch (e) { }
}
const tunerInputElementUI = `
<div class="mdc-text-field mdc-text-field--outlined" data-mdc-auto-init="MDCTextField">
	<input oninput="updateTunerInput(this)" id="tf-outlined" class="mdc-text-field__input">
	<div class="mdc-notched-outline">
		<div class="mdc-notched-outline__leading"></div>
		<div class="mdc-notched-outline__notch">
			<label for="tf-outlined" class="mdc-floating-label">NAME</label>
		</div>
		<div class="mdc-notched-outline__trailing"></div>
	</div>
</div>
`;
function addTunerInputElement(name, value) {
	var element = document.createElement("div");
	element.innerHTML = tunerInputElementUI.replace("NAME", name);
	element.className = "tunerInputElement";
	document.querySelector("#tuner-container > #inputBox").appendChild(element);
	mdc.autoInit(element);
	element.firstElementChild.MDCTextField.value = value;
}
function updateTunerInput(element) {
	var name = element.parentNode.querySelector(".mdc-floating-label").innerText;
	var value = element.parentNode.MDCTextField.value;
	tunerSet(name, value);
}

// -- Webapp Communication -- \\
function tunerSet(name, value) {
	var xhr = new XMLHttpRequest();
	xhr.timeout = 200;

	//Send Request
	xhr.open("POST", "/tuner/set", true);
	xhr.setRequestHeader('Content-Type', 'application/json');
	xhr.send(JSON.stringify({
		name: name,
		value: value
	}));
}

function tunerUpdate() {
	chartIndex += outputRefreshRateMs/1000.0;
	var xhr = new XMLHttpRequest();
	xhr.timeout = 200;
	xhr.onreadystatechange = function () {
		if (this.readyState != 4) return;

		//Data Recieved
		if (this.status == 200) {
			var data = JSON.parse(this.responseText);
			var values = [chartIndex];
			for (var i = 0; i < Object.keys(data).length; i++) {
				values.push(data[Object.keys(data)[i]]);
			}
			updateTunerOutput(values);
		}
	};
	//Send Request
	xhr.open("POST", "/tuner/get", true);
	xhr.setRequestHeader('Content-Type', 'application/json');
	xhr.send("");
}
setInterval(tunerUpdate, outputRefreshRateMs);

function tunerInit() {
	var xhr = new XMLHttpRequest();
	xhr.timeout = 2000;
	xhr.onreadystatechange = function () {
		if (this.readyState != 4) return;

		//Data Recieved
		if (this.status == 200) {
			var data = JSON.parse(this.responseText);

			clearTuner();
			var outputNames = [];
			var outputValues = [0];

			for (var i = 0; i < Object.keys(data).length; i++) {
				var name = Object.keys(data)[i];
				var value = data[Object.keys(data)[i]];
				//input
				if (value[0]) {
					addTunerInputElement(name, value[1]);
				}
				//output
				else {
					outputNames.push(name);
					outputValues.push(value[1]);
				}
			}

			initTunerOutput(outputNames, outputValues);
		}
	};
	//Send Request
	xhr.open("POST", "/tuner/init", true);
	xhr.setRequestHeader('Content-Type', 'application/json');
	xhr.send("");
}
tunerInit();