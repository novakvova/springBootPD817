"use strict";

var gulp = require('gulp');
var uglify = require('gulp-uglify');
var concat = require('gulp-concat');
var rimraf = require("rimraf");
var merge = require('merge-stream');

gulp.task("minify", function () {

    var streams = [
        //gulp.src(["wwwroot/js/*.js", '!wwwroot/js/tour*.js', '!wwwroot/js/contact.js'])
        //.pipe(uglify())
        //.pipe(concat("wilderblog.min.js"))
        //.pipe(gulp.dest("wwwroot/lib/site")),
        //gulp.src(["wwwroot/js/contact.js"])
        //.pipe(uglify())
        //.pipe(concat("contact.min.js"))
        //.pipe(gulp.dest("wwwroot/lib/site"))
    ];

    return merge(streams);
});

// Dependency Dirs
var deps = {
    // "jquery": {
    //     "dist/*": ""
    // },
    "bootstrap": {
        "dist/**/*": ""
    },

};

gulp.task("clean", function (cb) {
    return rimraf("src/main/resources/static/lib", cb);
});

gulp.task("scripts", function () {

    var streams = [];

    for (var prop in deps) {
        console.log("Prepping Scripts for: " + prop);
        for (var itemProp in deps[prop]) {
            streams.push(gulp.src("node_modules/" + prop + "/" + itemProp)
                .pipe(gulp.dest("src/main/resources/static/lib/" + prop + "/" + deps[prop][itemProp])));
        }
    }

    return merge(streams);

});

//gulp.task("default", ['clean', 'scripts', 'minify']);