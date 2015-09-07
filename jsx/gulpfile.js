var gulp = require('gulp');
var browserify = require('browserify');
var babelify = require('babelify');
var uglify = require('gulp-uglify')
var source = require('vinyl-source-stream');
var streamify = require('gulp-streamify');

gulp.task('build', function () {
	browserify({
		entries: 'main.jsx',
		extensions: ['.jsx'],
		debug: true
	})
	.transform(babelify)
	.bundle()
	.on('error', function (err) { console.log("Error : " + err.message); })
	.pipe(source('application.js'))
	// .pipe(streamify(uglify()))
	.pipe(gulp.dest('static/js'));
});

gulp.task('watch',function() {
	gulp.watch('./*.jsx',['build']);
});

gulp.task('default', ['build','watch']);
