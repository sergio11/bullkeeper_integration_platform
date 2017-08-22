var IndexPage = (function($){

    var iterations_data = [];
    var comments_data = [];
    var iterationChart;
    var commentsByUserChart;
    
    
    var showIterationsGraphData = function(){
        if (iterations_data.length > 0) {
            $("#lastIteration").text("Last Iteration at: " + iterations_data[iterations_data.length - 1].finishDate);
            $("#no-iterations-found").hide();
            if ( iterationChart != null ) {
                iterationChart.setData(iterations_data);
            } else {
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
        } else {
            $("#no-iterations-found").show();
        }
    }
    
    var showCommentsByUserGraphData = function(){
        if (comments_data.length > 0) {
            $("#no-comments-found").hide();
            if ( commentsByUserChart != null ) {
            	commentsByUserChart.setData(comments_data);
            } else {
            	commentsByUserChart = Morris.Donut({
                    element: 'morris-donut-chart',
                    data: comments_data,
                    resize: true
                });
            }
        } else {
            $("#no-comments-found").show();
            if(commentsByUserChart != null)
            	commentsByUserChart.el.remove();
        }
        
    }
    
    var handlerAllIterations = function (message) {
        iterations_data = JSON.parse(message.body);
        showIterationsGraphData();
    }
    
    var handlerNewIteration = function (message) {
        console.log("New Iteration Received");
        iterations_data.push(JSON.parse(message.body));
        showIterationsGraphData();
        $("#totalIterations").text(iterations_data.length);
        var totalComments = iterations_data.map(function(iteration) { return iteration.totalComments })
                .reduce(function(acc, comments) { return acc + comments });
        $("#totalComments").text(totalComments);
       
    }
    
    var handlerCommentsBySon = function(message) {
    	comments_data = JSON.parse(message.body);
    	console.log(comments_data);
    	showCommentsByUserGraphData();
    }
    
    return {
        
        init: function () {
        	var websocketurl = _ctx ? _ctx+'/web-socket' : '/web-socket';
            var wsocket = new SockJS(websocketurl);
            var client = Stomp.over(wsocket);
            client.connect({}, function (frame) {
                // Subscribe for all iterations.
                client.subscribe('/topic/iterations/all',handlerAllIterations);
                // Subscribe for new iterations.
                client.subscribe('/topic/iterations/new', handlerNewIteration);
                // Subscribe for comments by son on last iterations.
                client.subscribe('/topic/iterations/last/comments-by-son', handlerCommentsBySon);
                // Load Initial Iterations.
                client.send("/app/web-socket/admin/iterations", {});
                // Load Comments by user for last iteration
                client.send("/app/web-socket/admin/iterations/last/comments-by-son", {});
            });
        }
    }
    
})(jQuery);