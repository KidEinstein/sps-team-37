// This file uses the Google Chart Library to create graphs. 
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawGraph);

// Adds leading zeros if a number is less than 10.
function addLeadingZeros(number) {
    if (number < 10) {
        return "0" + number;
    }
    return number;
}

// Formats time from a 24-hour format into 12-hour format.
function formatTime(time) {
    hour = time.getHours();
    minute = time.getMinutes();
    afternoon = false;
    if (hour > 11) {
        afternoon = true;
    }
    formattedHour = hour % 12;
    if (formattedHour == 0) {
        formattedHour = 12;
    }
    if (afternoon) {
        return addLeadingZeros(formattedHour) + ":" + addLeadingZeros(minute) + " p.m.";
    }
    return addLeadingZeros(formattedHour) + ":" + addLeadingZeros(minute) + " a.m.";
}

function drawGraph() {
  // Fetches Journal entry data from the DataServlet. 
  fetch('/my-data-url').then(response => response.json()).then((journalData) => {

    // Add the data from the journal entry into the data table. 
    const data = new google.visualization.DataTable();
    data.addColumn('string', 'Date');
    data.addColumn('number', 'Mood Rating');
    Object.keys(journalData).forEach((journal) => {
        var date = new Date(journalData[journal].timestamp);
        var journalDate = (date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear() + "\n" + formatTime(date);
        data.addRow([journalDate, journalData[journal].moodValue]);
    });

    // Additional attributes to update the y-axis. 
    const options = {
        title: "Mood Graph",
        width: data.getNumberOfRows() * 200,
        vAxis: {
            ticks: [0, 2, 4, 6, 8, 10]
        },
        chartArea: {left: 10}
    };

    // Creates the graph in the following element in the HTML file. 
    const chart = new google.visualization.LineChart(document.getElementById('graph-div'));
    chart.draw(data, options);
  });
}
