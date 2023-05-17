$((function() {
    var previousType = "";
    var previousFormat = "";

    function filter() {
        var type = $("#type");
        var format = $("#format");

        var currentType = type.val(); // Get the current type value
        var currentFormat = format.val(); // Get the current format value
        
        if (currentType !== previousType || currentFormat !== previousFormat) {
            // Only update the previous filter values if the filter is performed
            previousType = currentType; // Update the previous type value
            previousFormat = currentFormat; // Update the previous format value
        }

        $.getJSON("showDocuments.php", {type: type.val(), format: format.val()}, function(data) {
            $("table tr:gt(0)").remove()
            data.forEach(function (document) {
                $("table").append(`<tr> 
                    <td>${document.author}</td>
                    <td>${document.title}</td>
                    <td>${document.number_of_pages}</td>
                    <td>${document.type}</td>
                    <td>${document.format}</td>
                    <td>
                        <a href=updateDocument.php?id=${document.id}>Update</a>
                        <br>
                        <a href=deleteDocument.php?id=${document.id}>Delete</a>
                        <br>
                    </td>
               </tr>`)
            });
            $("#filter-type").empty()
            $("#filter-format").empty()
            $("#filter-type").text(`Type: ${previousType}`)
            $("#filter-format").text(`Format: ${previousFormat}`)
        });
    }
    $("#type, #format").on("input", function () {
        filter()
    })

    filter()
}));