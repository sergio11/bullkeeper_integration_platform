var IndexPage = (function($){

    var iterations_data = [];
    var iterationChart;
    
    var createIterationChart = function () {
        iterationChart = Morris.Area({
            element: 'morris-area-chart',
            data: iterations_data,
            xkey: 'finishDate',
            ykeys: ['totalComments'],
            labels: ['Total Comments'],
            pointSize: 2,
            hideHover: 'auto',
            resize: true,
            parseTime: false
        });
    }
    
    var showGraphData = function(){
        if (iterations_data.length > 0) {
            $("#lastIteration").text("Last Iteration at: " + iterations_data[iterations_data.length - 1].finishDate);
            $("#no-iterations-found").hide();
            if ( iterationChart != null ) {
                iterationChart.setData(iterations_data);
            } else {
                createIterationChart();
            }
        } else {
            $("#no-iterations-found").show();
        }
    }
    
    var handlerAllIterations = function (message) {
        iterations_data = JSON.parse(message.body);
        showGraphData();
    }
    
    var handlerNewIteration = function (message) {
        console.log("New Iteration Received");
        iterations_data.push(JSON.parse(message.body));
        showGraphData();
        $("#totalIterations").text(iterations_data.length);
        var totalComments = iterations_data.map(function(iteration) { return iteration.totalComments })
                .reduce(function(acc, comments) { return acc + comments });
        $("#totalComments").text(totalComments);
    }
    
    return {
        
        init: function () {
            var wsocket = new SockJS('/web-socket');
            var client = Stomp.over(wsocket);
            client.connect({}, function (frame) {
                // Subscribe for all iterations.
                client.subscribe('/topic/iterations/all',handlerAllIterations);
                // Subscribe for new iterations.
                client.subscribe('/topic/iterations/new', handlerNewIteration);
                // Load Initial Iterations.
                client.send("/app/web-socket/admin/iterations", {});
            });

        }
    }
    
})(jQuery);