"use strict";
exports.__esModule = true;
var day6_data_1 = require("./day6.data");
var distinctQuestionCount = day6_data_1.testInput
    .trim()
    .split("\n\n")
    .map(function (group) {
    var questions = group
        .split('\n')
        .flatMap(function (person) { return person.split(''); })
        .map(function (questions) { return console.log("Incoming: " + questions); });
    // console.log(`Current group has ${questions.length} questions`);
    // return questions.length;
});
// .reduce((sum, val) => sum + val, 0)
console.log(distinctQuestionCount + " distinct questions were answered.");
