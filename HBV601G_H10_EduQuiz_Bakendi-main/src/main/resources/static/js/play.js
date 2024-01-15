$(document).ready(function(e) {
    var questions = [];
    var data = "";
    var desc = "";
    var title = 0;
    var totalAvgScore;
    var totalPlays;
    $.ajax({
        type : "GET",
        url : window.location + "/api/quiz",
        success: function(result){
          if(result.status == "Done"){
            for(var i = 0; i < result.data.questions.length; i++) {
                questions.push(result.data.questions[i]);
            }
            data = result.data.type;
            desc = result.data.description;
            $('#title-quiz').append(result.data.title);
            totalPlays = result.data.totalPlays;
            totalAvgScore = result.data.averageScore;

          }else{
            $("#getResultDiv").html("<strong>Villa</strong>");
            console.log("Fail: ", result);
          }
        },
        error : function(e) {
          $("#getResultDiv").html("<strong>Error</strong>");
          console.log("Villa að sækja gögn: ", e);
          console.log(result.data);
        }
    });
    var counter = 0;
    $('#next-question-button').hide();
    $('#prev-question-button').hide();
    $('#finish-quiz-button').hide();
    $('#retry-quiz-button').hide();
    $('#start-quiz').on('click', function () {
        $('#title-quiz').hide();
        game.counter = questions.length * 15;
        game.start();
        $('.question-div').hide();
        $('.question-div:first').show();
        $('#next-question-button').show();
        $('#prev-question-button').show();
        $('#finish-quiz-button').show();
        $('#prev-question-button').attr("disabled", true);
        $('#finish-quiz-button').attr("disabled", true);
    });
    $('#next-question-button').on('click', function(e) {
        counter++;
        if($('.question-div').length <= counter + 1) {
            //$('#next-question-button').show();
            $('#next-question-button').attr("disabled", true);
            /*$('#finish-quiz-button').show();*/
            $("#finish-quiz-button").attr("disabled", false);
        }
        if(counter >= 1) {
            $('#prev-question-button').attr("disabled", false);
        }
        $('.question-div').eq(counter - 1).hide();
        $('.question-div').eq(counter).show();
    });
    $('#prev-question-button').on('click', function(e) {
        counter--;
        if($('.question-div').length >= counter - 1) {
            //$('#prev-question-button').hide();
            $('#prev-question-button').attr("disabled", true);
        }
        if(counter != 0) {
            //$('#prev-question-button').show();
            $('#prev-question-button').attr("disabled", false);
        }
        if(counter != $('.question-div').length) {
            //$('#next-question-button').show();
            //$('#finish-quiz-button').hide();
            $('#next-question-button').attr("disabled", false);
            $("#finish-quiz-button").attr("disabled", true);
        }
        $('.question-div').eq(counter).show();
        $('.question-div').eq(counter + 1).hide();
    });
    $('#finish-quiz-button').on('click', function(e) {
        game.done();
    });
    $('#retry-quiz-button').on('click', function(e) {
            location.reload();
    });
    console.log(title);
    var game = {
        correct: 0,
        incorrect: 0,
        counter: 10,
        countdown: function () {
            game.counter--;
            $('#counter').html(game.counter);
            if (game.counter <= 0) {
                console.log("Tíminn er liðinn");
                game.done();

            }
        },
        start: function () {
            timer = setInterval(game.countdown, 1000);
            $('#quiz').prepend('<h4 class="h4">Niðurtalning: <span id="counter">' + game.counter + '</span> sekúndur </h4>');
            $('#start-quiz').remove();
            console.log(data);
            $('#quiz').append('<h4>' + desc + '</h4>');
            for (var i = 0; i < questions.length; i++) {
                $('#quiz').append('<div class="question-div border-top border-dark">');
                //if(questions[i].type == 0)
                if(data == 'questionsAreImageUrl') {
                    $('.question-div').eq(i).append('<div><img src="' + questions[i].question + '" class="rounded mx-auto d-block mt-3 mb-3" alt="hús" width=600></div>');
                } else {
                    $('.question-div').eq(i).append('<div><h2>' + questions[i].question + '</h2></div>');
                }
                $('.question-div').eq(i).append('<div class="row quests border-top border-bottom border-secondary mb-2"></div>');
                for (var j = 0; j < questions[i].answers.length; j++) {
                    $('.quests').eq(i).append("<h2 class='col form-check text-center'><input id='option" + i + j + "' type='radio'  class='form-check-input' name='question-" + i + "'value='" + questions[i].answers[j].answer + "'><label class='form-check-label' for='option"+ i + j + "'>" + questions[i].answers[j].answer + "</label>");
                    $('#option' + i + j).hide();
                    if(j == 1) {
                        $('.quests').eq(i).append('<div class="w-100"></div>');
                    }
                }
            }
        },
        done: function () {
            $('.question-div').eq(counter).hide();
            for(var i = 0; i < questions.length; i++) {
                $.each($('input[name="question-' + i +'"]:checked'),
                function () {
                    var correct = '';
                    for (var j = 0; j < questions[i].answers.length; j++) {
                        console.log(questions[i].answers[j].correct);
                        if(questions[i].answers[j].correct ==  true) {
                            correct = questions[i].answers[j].answer;
                            break;
                        }
                    }
                    console.log($(this).val() + " | " + correct);
                    if ($(this).val() == correct) {
                        game.correct++;
                    } else {
                        game.incorrect++;
                    }
            });
            }
            $('#next-question-button').hide();
            $('#prev-question-button').hide();
            $('#finish-quiz-button').hide();
            $('#retry-quiz-button').show();
            this.result();

        },
        result: function () {
            clearInterval(timer);
            $('#quiz h4').remove();
            console.log(this.correct/questions.length);
            $('#quiz').append("<h3 class='text-center'>Leik Lokið</h3>");
            $("#quiz").append("<div id='result-grid' class='row'></div>")
            const avg = (100*(this.correct/questions.length));

            if (totalPlays != 0) {
                totalAvgScore = ( totalAvgScore + ( (avg - totalAvgScore) / (totalPlays + 1) ));
            } else {
                totalAvgScore = avg;
            }

            $('#result-grid').append("<p class='h3 col text-center'>Stig: " + avg.toFixed(2) + "%</p>");
            $('#result-grid').append("<p class='h3 col text-center'>Meðaltal: " + totalAvgScore.toFixed(2) + "%</p>")
            $.ajax({
                type : "POST",
                contentType : "application/json",
                url : window.location + "/finished/" +this.correct,
                data : null,
                dataType : null,
                error : function(e) {
                    alert("Error")
                    console.log("ERROR: ", e);
                }
            });
        },
    }
});