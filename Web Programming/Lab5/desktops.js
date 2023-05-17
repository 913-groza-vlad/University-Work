$((
    function () {
        $(".desktop").hide()
        $("#desktop1").show()
        $("#button1").on("click", function () {
            $("#desktop1").slideUp("slow")
            $("#desktop2").slideDown("slow")
        })
        $("#button2-prev").on("click", function () {
            $("#desktop2").slideUp("slow")
            $("#desktop1").show().slideDown("slow")
        })
        $("#button2-next").on("click", function () {
            $("#desktop2").slideUp("slow")
            $("#desktop3").slideDown("slow")
        })
        $("#button3-prev").on("click", function () {
            $("#desktop3").slideUp("slow")
            $("#desktop2").show().slideDown("slow")
        })
        $("#button3-next").on("click", function () {
            $("#desktop3").slideUp("slow")
            $("#desktop4").slideDown("slow")
        })
        $("#button4").on("click", function () {
            $("#desktop4").slideUp("slow")
            $("#desktop3").show().slideDown("slow")
        })
        
        $("#button4-first").on("click", function () {
            $("#desktop4").slideUp("slow")
            $("#desktop1").show().slideDown("slow")
        })   
    }
))
