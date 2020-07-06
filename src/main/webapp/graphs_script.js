// This file uses the Google Chart Library to create graphs. 
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawGraph);

function drawGraph() {
  const data = new google.visualization.DataTable();
  data.addColumn('string', 'Date');
  data.addColumn('number', 'Mood Rating');
        data.addRows([
          ['6/1/20', 3],
          ['6/7/20', 5],
          ['6/14/20', 6],
          ['6/21/20', 8],
          ['6/28/20', 3],
          ['7/1/20', 9],
          ['7/7/20', 5],
          ['7/14/20', 7],
          ['7/21/20', 2],
          ['7/28/20', 3],
        ]);

  const options = {
    'title': 'Mood Rating',
    vAxis: {
        ticks: [0, 2, 4, 6, 8, 10]
    }
  };

  const chart = new google.visualization.LineChart(
      document.getElementById('chart-container'));
  chart.draw(data, options);
}
