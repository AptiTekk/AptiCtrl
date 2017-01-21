import 'core-js/es6';
import 'core-js/es7/reflect';
import 'web-animations-js/web-animations.min.js';
require('zone.js/dist/zone');
if (process.env.ENV === 'production') {
    // Production
} else {
    // Development
    Error['stackTraceLimit'] = 5;
    require('zone.js/dist/long-stack-trace-zone');
}
