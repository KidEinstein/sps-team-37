// This file uses the Google Chart Library to create graphs. 
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawGraph);

function drawGraph() {
  // Fetches Journal entry data from the DataServlet. 
  fetch('/my-data-url').then(response => response.json()).then((journalData) => {

    // Add the data from the journal entry into the data table. 
    const data = new google.visualization.DataTable();
    data.addColumn('string', 'Date');
    data.addColumn('number', 'Mood Rating');
    Object.keys(journalData).forEach((journal) => {
        var date = new Date(journalData[journal].timestamp);
        var journalDate = (date.getMonth()+1) + "/" + date.getDate() + "/" + date.getFullYear() + "\n" + date.getHours() + ":" + date.getMinutes();
        data.addRow([journalDate, journalData[journal].moodValue]);
    });

    // Additional attributes to update the y-axis. 
    const options = {
        vAxis: {
            ticks: [0, 2, 4, 6, 8, 10]
        }
    };

    // Creates the graph in the following element in the HTML file. 
    const chart = new google.visualization.LineChart(document.getElementById('graph-div'));
    chart.draw(data, options);
  });
}
